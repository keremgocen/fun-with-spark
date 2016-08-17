import models.*;
import retrofit2.Call;
import retrofit2.http.*;

/**
 * Documented at https://github.com/x-team/daw-purchases/blob/master/package.json
 * Created by kerem on 8/16/16.
 */
public class ExtApi {

    public static final String TARGET_URL_BASE = "http://74.50.59.155:6000/api/";

    public interface PurchasesByUserService {
        @Headers("Cache-Control: max-age=640000")
        @GET("purchases/by_user/{user}")
        Call<PurchaseList> listPurchases(@Path("user") String user, @Query("limit") int limit);
    }

    public interface PurchasesByProductIdService {
        @Headers("Cache-Control: max-age=640000")
        @GET("purchases/by_product/{product_id}")
        Call<PurchaseList> listPurchases(@Path("product_id") int id);
    }

    // not used but it's here if needed
    /*public interface PurchaseService {
        @POST("purchase")
        Call<User> doPurchase(@Body User user);
    }*/

    public interface ProductByIdService {
        @Headers("Cache-Control: max-age=640000")
        @GET("products/{id}")
        Call<ProductWrapper> getProductWrapper(@Path("id") int id);
    }

    public interface UsersService {
        @Headers("Cache-Control: max-age=640000")
        @GET("users/")
        Call<UserList> getUsers(@Query("limit") int limit);
    }

    public interface UserByNameService {
        @Headers("Cache-Control: max-age=640000")
        @GET("users/{username}")
        Call<User> getUser(@Path("username") String username);
    }
}
