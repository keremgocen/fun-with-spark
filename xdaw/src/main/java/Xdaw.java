/**
 * Created by kerem on 8/15/16.
 */

import models.*;
import org.eclipse.jetty.util.ConcurrentHashSet;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.HashSet;

import static spark.Spark.get;
import static spark.Spark.port;

public class Xdaw {

    private static final int LISTEN_PORT = 8080;
    private static final int HTTP_BAD_REQUEST = 400;
    private static final int USERS_GET_LIMIT = 10;
    private static final int PURCHASE_GET_LIMIT = 5;

    public static void main(String[] args) {

        ConcurrentHashSet<String> userHashSet = new ConcurrentHashSet<>();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ExtApi.TARGET_URL_BASE)
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

                if (userList.getUsers() != null && !userList.getUsers().isEmpty())
                    for (User u : userList.getUsers()) {
                        System.out.println(u);
                        userHashSet.add(u.getUsername());
                    }
                System.out.println("Cached user count:" + userHashSet.size());
            }

            @Override
            public void onFailure(Call<UserList> call, Throwable throwable) {
                System.out.println("Failed to get users. " + throwable.getMessage());
                throwable.printStackTrace();
            }
        };

        // Fetch 10 users at start
        Call<UserList> userListCall = usersService.getUsers(USERS_GET_LIMIT);
        userListCall.enqueue(userListCallback);

        port(LISTEN_PORT);

        get("/api/recent_purchases/:username", (req, res) -> {

            // check if user exists
            String username = req.params(":username");

            // TODO check users
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

   /* interface Validable {
        boolean isValid();
    }*/

    // used json transformer instead
    /*public static String dataToJson(Object data) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            StringWriter sw = new StringWriter();
            mapper.writeValue(sw, data);
            return sw.toString();
        } catch (IOException e){
            throw new RuntimeException("IOException from a StringWriter?");
        }
    }*/

}