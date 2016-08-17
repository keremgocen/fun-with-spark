import models.*;
import retrofit2.Call;
import retrofit2.http.*;

/**
 * Documented at https://github.com/x-team/daw-purchases/blob/master/package.json
 * Created by kerem on 8/16/16.
 */
public class ExtApi {

    public static final String TARGET_URL_BASE = "http://localhost:8080/api/";

    public interface PurchasesByUserService {
        @GET("purchases/by_user/{user}")
        Call<PurchaseList> listPurchases(@Path("user") String user, @Query("limit") int limit);
    }

    public interface PurchasesByProductIdService {
        @GET("purchases/by_product/{product_id}")
        Call<PurchaseList> listPurchases(@Path("product_id") int id);
    }

    // not used but it's here if needed
    /*public interface PurchaseService {
        @POST("purchase")
        Call<User> doPurchase(@Body User user);
    }*/

    public interface ProductByIdService {
        @GET("products/{id}")
        Call<ProductWrapper> getProductWrapper(@Path("id") int id);
    }

    public interface UsersService {
        @GET("users/")
        Call<UserList> getUsers(@Query("limit") int limit);
    }

    public interface UserByNameService {
        @GET("users/{username}")
        Call<User> getUser(@Path("username") String username);
    }
}
