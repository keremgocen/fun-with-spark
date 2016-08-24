package cmd; /**
 * Created by kerem on 8/15/16.
 */

import models.*;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.eclipse.jetty.util.ConcurrentHashSet;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import util.AppSettings;
import util.ExtApi;
import util.JsonTransformer;

import java.io.File;
import java.util.HashSet;

import static spark.Spark.get;
import static spark.Spark.port;

public class App {

    private static final int HTTP_BAD_REQUEST = 400;
    private static final int USERS_GET_LIMIT = 10;
    private static final int PURCHASE_GET_LIMIT = 5;

    public static void main(String[] args) {

        ConcurrentHashSet<String> userHashSet = new ConcurrentHashSet<>();

        AppSettings appSettings = new AppSettings();
        appSettings.init();

        File httpCacheDirectory = new File("rcache");
        int cacheSize = 10 * 1024 * 1024; // 10 MiB
        Cache cache = new Cache(httpCacheDirectory, cacheSize);
        OkHttpClient client = new OkHttpClient.Builder().cache(cache) // 10 MB
                .addNetworkInterceptor(chain -> {
                    Request request = chain.request();
                    okhttp3.Response response = chain.proceed(request);
                    response = response.newBuilder().header("Cache-Control", "max-age=" + appSettings.getCacheControlMaxAge()).build();
                    return response;
                })
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(appSettings.getExternalBaseUrl())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ExtApi.PurchasesByUserService purchasesByUserService = retrofit.create(ExtApi.PurchasesByUserService.class);
        ExtApi.PurchasesByProductIdService purchasesByProductService = retrofit.create(ExtApi.PurchasesByProductIdService.class);
        ExtApi.ProductByIdService productsByIdService = retrofit.create(ExtApi.ProductByIdService.class);
        ExtApi.UsersService usersService = retrofit.create(ExtApi.UsersService.class);
        ExtApi.UserByNameService userByNameService = retrofit.create(ExtApi.UserByNameService.class);

        Callback<UserList> userListCallback = new Callback<UserList>() {
            @Override
            public void onResponse(Call<UserList> call, Response<UserList> response) {
                UserList userList = response.body();

                System.out.println("response headers:" + response.headers().toString());

                if (userList != null && userList.getUsers() != null && !userList.getUsers().isEmpty())
                    for (User u : userList.getUsers()) {
                        System.out.println(u);
                        userHashSet.add(u.getUsername());
                    }
                System.out.println("Cached user count:" + userHashSet.size());
            }

            @Override
            public void onFailure(Call<UserList> call, Throwable throwable) {
                System.out.println("Failed to get users. Make sure daw-api is running and accesible..\n" + throwable.getMessage());
                System.out.println("Failed request:" + call.request().toString());
            }
        };

        // Fetch 10 users at start
        Call<UserList> userListCall = usersService.getUsers(USERS_GET_LIMIT);
        userListCall.enqueue(userListCallback);

        port(Integer.parseInt(appSettings.getListenPort()));

        get("/api/recent_purchases/:username", (req, res) -> {

            // check if user exists
            String username = req.params(":username");

            if (!userHashSet.contains(username)) {
                Call<User> userCall = userByNameService.getUser(username);
                Response<User> userResponse = userCall.execute();
                try {
                    if (userResponse == null || !userResponse.isSuccessful() || userResponse.body() == null || userResponse.body().getUsername() == null) {
                        res.type("text/plain");
                        return String.format("User with username of %s was not found.", username);
                    }
                } catch (Exception e) {
                    res.status(HTTP_BAD_REQUEST);
                    res.type("text/plain");
                    e.printStackTrace();
                    return String.format("User with username of %s was not found.", username);
                }
            }

            Product resultsHolder = new Product();

            Call<PurchaseList> purchaseListCall = purchasesByUserService.listPurchases(username, PURCHASE_GET_LIMIT);
            Response<PurchaseList> resp1 = purchaseListCall.execute();

            for (Purchase p1 : resp1.body().getPurchases()) {
                System.out.println("p1:" + p1);

                Product r1 = new Product();
                HashSet recent = new HashSet<>();

                Call<PurchaseList> productPurchases = purchasesByProductService.listPurchases(p1.getProductId());
                Response<PurchaseList> resp2 = productPurchases.execute();

                for (Purchase p2 : resp2.body().getPurchases()) {
                    System.out.println("p2" + p2);

                    recent.add(p2.getUsername());

                    Call<ProductWrapper> productsById = productsByIdService.getProductWrapper(p2.getProductId());
                    Response<ProductWrapper> productResponse = productsById.execute();

                    Product newP = productResponse.body().getProduct();

                    r1.setFace(newP.getFace());
                    r1.setId(newP.getId());
                    r1.setPrice(newP.getPrice());
                    r1.setSize(newP.getSize());
                }

                r1.setRecent(recent);
                r1.setRecentSize(recent.size());
                resultsHolder.addProduct(r1);
            }

            System.out.println(resultsHolder.getAllProducts());

            res.type("application/json");

            return resultsHolder.getProducts();
        }, new JsonTransformer());

    }

}