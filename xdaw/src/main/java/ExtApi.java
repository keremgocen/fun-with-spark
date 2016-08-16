import models.Product;
import models.ProductWrapper;
import models.PurchaseList;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by kerem on 8/16/16.
 */
public class ExtApi {

    public static final String TARGET_URL_BASE = "http://74.50.59.155:6000/api/";

    public interface PurchasesByUserService {
        @GET("purchases/by_user/{user}?limit=5")
        Call<PurchaseList> listPurchases(@Path("user") String user);
    }

    public interface PurchasesByProductService {
        @GET("purchases/by_product/{product_id}")
        Call<PurchaseList> listPurchases(@Path("product_id") int id);
    }

    public interface ProductsByIdService {
        @GET("products/{product_id}")
        Call<ProductWrapper> getProductWrapper(@Path("product_id") int id);
    }
}
