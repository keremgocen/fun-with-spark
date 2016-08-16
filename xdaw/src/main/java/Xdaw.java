/**
 * Created by kerem on 8/15/16.
 */

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import models.*;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.post;

public class Xdaw {

    private static final int HTTP_BAD_REQUEST = 400;

    interface Validable {
        boolean isValid();
    }

    public static void main(String[] args) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ExtApi.TARGET_URL_BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ExtApi.PurchasesByUserService purchasesByUserService = retrofit.create(ExtApi.PurchasesByUserService.class);
        ExtApi.PurchasesByProductService purchasesByProductService = retrofit.create(ExtApi.PurchasesByProductService.class);
        ExtApi.ProductsByIdService productsByIdService = retrofit.create(ExtApi.ProductsByIdService.class);


        /*Callback<ProductWrapper> productCallback = new Callback<ProductWrapper>() {
            @Override
            public void onResponse(Call<ProductWrapper> call, Response<ProductWrapper> response) {
                System.out.println("product recevied:" + response.body().getProduct());
            }

            @Override
            public void onFailure(Call<ProductWrapper> call, Throwable throwable) {
                System.out.println("productCall failed:" + throwable.getMessage());
                System.out.println("productCall request was:" + call.request().toString());
            }
        };

        Callback<PurchaseList> purchaseListCallback = new Callback<PurchaseList>() {
            @Override
            public void onResponse(Call<PurchaseList> call, Response<PurchaseList> response) {
                System.out.println("purchases recevied:" + response.body().getPurchases().size());
                for (Purchase p1 : response.body().getPurchases()) {
                    System.out.println(p1);

                    System.out.println("user:" + p1.getUsername());

                    Call<ProductWrapper> productWrapperCall = productsByIdService.getProductWrapper(p1.getProductId());
                    productWrapperCall.enqueue(productCallback);
                }
            }

            @Override
            public void onFailure(Call<PurchaseList> call, Throwable throwable) {
                System.out.println("purchaseListCall failed" + throwable.getMessage());
                System.out.println("purchaseListCall request was:" + call.request().toString());
            }
        };*/

        port(8080);

        get("/api/recent_purchases/:username", (req, res) -> {
/*
            Call<PurchaseList> purchaseListCall = purchasesByUserService.listPurchases(req.params(":username"));
            purchaseListCall.enqueue(purchaseListCallback);
*/
            List<Result> resultList = new ArrayList<Result>();


            Call<PurchaseList> purchaseListCall = purchasesByUserService.listPurchases(req.params(":username"));

            Response<PurchaseList> resp1 = purchaseListCall.execute();

            for (Purchase p1 : resp1.body().getPurchases()) {
                System.out.println("p1:" + p1);

                Result r1 = new Result();
                HashSet<String> recent = new HashSet<String>();

                Call<PurchaseList> productPurchases = purchasesByProductService.listPurchases(p1.getProductId());
                Response<PurchaseList> resp2 = productPurchases.execute();

                for(Purchase p2 : resp2.body().getPurchases()) {
                    System.out.println("p2" + p2);

                    recent.add(p2.getUsername());

                    Call<ProductWrapper> productsById = productsByIdService.getProductWrapper(p2.getProductId());
                    Response<ProductWrapper> productResponse = productsById.execute();

                    System.out.println(productResponse.isSuccessful());
                    System.out.println(productResponse.body().getProduct());
                    System.out.println(productResponse.message());

                    Product newP = productResponse.body().getProduct();

                    r1.setFace(newP.getFace());
                    r1.setId(newP.getId());
                    r1.setPrice(newP.getPrice());
                    r1.setSize(newP.getSize());


                }
                r1.setRecent(recent);
                resultList.add(r1);

            }

            System.out.println(resultList);



            /*res.body();               // get response content
            response.body("Hello");        // sets content to Hello
            response.header("FOO", "bar"); // sets header FOO with value bar
            response.raw();                // raw response handed in by Jetty
            response.redirect("/example"); // browser redirect to /example
            response.status();             // get the response status
            response.status(401);          // set status code to 401
            response.type();               // get the content type
            response.type("text/xml");     // set content type to text/xml

*/

            return resultList;
        }, new JsonTransformer());

/*        DataModel model = new DataModel();*/


        /*// insert a post (using HTTP post method)
        post("/posts", (request, response) -> {
            try {
                ObjectMapper mapper = new ObjectMapper();
                ExpectedPostPload creation = mapper.readValue(request.body(), ExpectedPostPload.class);
                if (!creation.isValid()) {
                    response.status(HTTP_BAD_REQUEST);
                    return "";
                }
                int id = model.createPost(creation.getTitle(), creation.getContent(), creation.getCategories());
                response.status(200);
                response.type("application/json");
                return id;
            } catch (JsonParseException jpe) {
                response.status(HTTP_BAD_REQUEST);
                return "";
            }
        });

        // get all post (using HTTP get method)
        get("/posts", (request, response) -> {
            response.status(200);
            response.type("application/json");
            return dataToJson(model.getAllPosts());
        });*/
    }

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