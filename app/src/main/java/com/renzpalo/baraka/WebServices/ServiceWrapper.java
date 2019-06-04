package com.renzpalo.baraka.WebServices;

        import com.google.gson.Gson;
        import com.google.gson.GsonBuilder;
        import com.renzpalo.baraka.BuildConfig;
        import com.renzpalo.baraka.Utility.Constant;
        import com.renzpalo.baraka.phpResponse.AddAddressPhp;
        import com.renzpalo.baraka.phpResponse.AddToCartPhp;
        import com.renzpalo.baraka.phpResponse.AddToWishlistPhp;
        import com.renzpalo.baraka.phpResponse.BannersPhp;
        import com.renzpalo.baraka.phpResponse.DeleteProductPhp;
        import com.renzpalo.baraka.phpResponse.FeaturedProductsPhp;
        import com.renzpalo.baraka.phpResponse.FeaturedStoresPhp;
        import com.renzpalo.baraka.phpResponse.ForgotPasswordPhp;
        import com.renzpalo.baraka.phpResponse.GetAddressDetailsPhp;
        import com.renzpalo.baraka.phpResponse.GetCartDetailsPhp;
        import com.renzpalo.baraka.phpResponse.GetCategoriesPhp;
        import com.renzpalo.baraka.phpResponse.GetOrderHistoryDetailsPhp;
        import com.renzpalo.baraka.phpResponse.GetOrderHistoryPhp;
        import com.renzpalo.baraka.phpResponse.GetOrderSummaryPhp;
        import com.renzpalo.baraka.phpResponse.GetOrderUpdatesPhp;
        import com.renzpalo.baraka.phpResponse.GetProductImagesPhp;
        import com.renzpalo.baraka.phpResponse.GetProductsByPhp;
        import com.renzpalo.baraka.phpResponse.GetProvincesPhp;
        import com.renzpalo.baraka.phpResponse.GetSellerInformationPhp;
        import com.renzpalo.baraka.phpResponse.PlaceOrderPhp;
        import com.renzpalo.baraka.phpResponse.ProductDetailsPhp;
        import com.renzpalo.baraka.phpResponse.SearchProductsPhp;
        import com.renzpalo.baraka.phpResponse.SignInPhp;
        import com.renzpalo.baraka.phpResponse.SignUpPhp;
        import com.renzpalo.baraka.phpResponse.UpdateCartPhp;
        import com.renzpalo.baraka.phpResponse.GetWishlistDetailsPhp;

        import java.util.concurrent.TimeUnit;

        import okhttp3.Interceptor;
        import okhttp3.MediaType;
        import okhttp3.OkHttpClient;
        import okhttp3.RequestBody;
        import okhttp3.logging.HttpLoggingInterceptor;
        import retrofit2.Call;
        import retrofit2.Retrofit;
        import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceWrapper {

    private ServiceInterface mServiceInterface;

    public ServiceWrapper(Interceptor mInterceptorHeader) {
        mServiceInterface = getRetrofit(mInterceptorHeader).create(ServiceInterface.class);
    }

    public Retrofit getRetrofit(Interceptor mIntercentorHeader) {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient mOkHttpClient = null;
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(Constant.API_CONNECTION_TIMEOUT, TimeUnit.SECONDS);
        builder.readTimeout(Constant.API_READ_TIMEOUT, TimeUnit.SECONDS);

        if (BuildConfig.DEBUG) {
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(loggingInterceptor);
        }

        mOkHttpClient = builder.build();
        Gson gson = new GsonBuilder().create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(mOkHttpClient)
                .build();

        return retrofit;
    }

    public Call<SignUpPhp> signUpPhpCall(String userFullname, String userPhoneNo, String userUsername, String userPassword) {
        return mServiceInterface.signUpPhpCall(
                convertPlainString(userFullname),
                convertPlainString(userPhoneNo),
                convertPlainString(userUsername),
                convertPlainString(userPassword)
        );
    }

    public Call<SignInPhp> signInPhpCall(String userPhoneNo, String userPassword) {
        return mServiceInterface.signInPhpCall(
                convertPlainString(userPhoneNo),
                convertPlainString(userPassword)
        );
    }

    public Call<ForgotPasswordPhp> forgotPasswordPhpCall(String userPhoneNo) {
        return mServiceInterface.forgotPasswordPhpCall(
                convertPlainString(userPhoneNo)
        );
    }

    // Featured Stores
    public Call<FeaturedStoresPhp> featuredStoresPhpCall(String securitycode) {
        return mServiceInterface.featuredStoresPhpCall(
                convertPlainString(securitycode)
        );
    }

    // Featured Products
    public Call<FeaturedProductsPhp> featuredProductsPhpCall(String securitycode) {
        return mServiceInterface.featuredProductsPhpCall(
                convertPlainString(securitycode)
        );
    }

    // Product Details
    public Call<ProductDetailsPhp> productDetailsPhpCall(String securitycode, String prodId) {
        return mServiceInterface.productDetailsPhpCall(
                convertPlainString(securitycode),
                convertPlainString(prodId)
        );
    }

    // Banners
    public Call<BannersPhp> bannersPhpCall(String securitycode) {
        return mServiceInterface.bannersPhpCall(
                convertPlainString(securitycode)
        );
    }

    // Convert a parameter into plain text
    public RequestBody convertPlainString(String data) {
        RequestBody plainString = RequestBody.create(MediaType.parse("text/plain"), data);
        return plainString;
    }

    // Add to Cart
    public Call<AddToCartPhp> addToCartPhpCall(String securitycode, String prodId, String prodPrice, String prodQuantity, String userId) {
        return mServiceInterface.addToCartPhpCall(
                convertPlainString(securitycode),
                convertPlainString(prodId),
                convertPlainString(prodPrice),
                convertPlainString(prodQuantity),
                convertPlainString(userId)
        );
    }

    // Add to Wishlist
    public Call<AddToWishlistPhp> addToWishlistPhpCall(String securitycode, String prodId, String prodPrice, String prodQuantity, String userId) {
        return mServiceInterface.addToWishlistPhpCall(
                convertPlainString(securitycode),
                convertPlainString(prodId),
                convertPlainString(prodPrice),
                convertPlainString(prodQuantity),
                convertPlainString(userId)
        );
    }

    // Get Cart Details
    public Call<GetCartDetailsPhp> getCartDetailsPhpCall(String securitycode, String userId) {
        return mServiceInterface.getCartDetailsPhpCall(
                convertPlainString(securitycode),
                convertPlainString(userId)
        );
    }

    // Get Cart Details
    public Call<GetWishlistDetailsPhp> getWishlistDetailsPhpCall(String securitycode, String userId) {
        return mServiceInterface.getWishlistDetailsPhpCall(
                convertPlainString(securitycode),
                convertPlainString(userId)
        );
    }

    // Delete to Cart
    public Call<DeleteProductPhp> deleteCartProductPhpCall(String securitycode, String prodId, String userId) {
        return mServiceInterface.deleteCartProductPhpCall(
                convertPlainString(securitycode),
                convertPlainString(prodId),
                convertPlainString(userId)
        );
    }

    // Delete to Wishlist
    public Call<DeleteProductPhp> deleteWishlistProductPhpCall(String securitycode, String prodId, String userId) {
        return mServiceInterface.deleteWishlistProductPhpCall(
                convertPlainString(securitycode),
                convertPlainString(prodId),
                convertPlainString(userId)
        );
    }

    // Update Product from Cart - Quantity
    public Call<UpdateCartPhp> updateCartProductPhpCall(String securitycode, String prodId, String prodQuantity, String userId) {
        return mServiceInterface.updateCartProductPhpCall(
                convertPlainString(securitycode),
                convertPlainString(prodId),
                convertPlainString(prodQuantity),
                convertPlainString(userId)
        );
    }

    // Get Order Summary
    public Call<GetOrderSummaryPhp> getOrderSummaryPhpCall(String securitycode, String userId) {
        return mServiceInterface.getOrderSummaryPhpCall(
                convertPlainString(securitycode),
                convertPlainString(userId)
        );
    }

    // Get Cart Details
    public Call<AddAddressPhp> addAddressPhpCall(String securitycode, String adFullname, String adContact, String adStreet,
                                                 String adBarangay, String adCityMuni, String adProvince,
                                                 String adZipcode, String adNotes, String userId) {
        return mServiceInterface.addAddressPhpCall(
                convertPlainString(securitycode),
                convertPlainString(adFullname),
                convertPlainString(adContact),
                convertPlainString(adStreet),
                convertPlainString(adBarangay),
                convertPlainString(adCityMuni),
                convertPlainString(adProvince),
                convertPlainString(adZipcode),
                convertPlainString(adNotes),
                convertPlainString(userId)
        );
    }

    // Get Address Details
    public Call<GetAddressDetailsPhp> getAddressDetailsPhpCall(String securitycode, String userId) {
        return mServiceInterface.getAddressDetailsPhpCall(
                convertPlainString(securitycode),
                convertPlainString(userId)
        );
    }

    // Get Cart Details
    public Call<PlaceOrderPhp> placeOrderPhpCall(String securitycode, String orderTotal, String orderShipFee, String orderPayment,
                                                 String orderStatus, String userId, String adId) {
        return mServiceInterface.placeOrderPhpCall(
                convertPlainString(securitycode),
                convertPlainString(orderTotal),
                convertPlainString(orderShipFee),
                convertPlainString(orderPayment),
                convertPlainString(orderStatus),
                convertPlainString(userId),
                convertPlainString(adId)
        );
    }

    // Get Order History
    public Call<GetOrderHistoryPhp> getOrderHistoryPhpCall(String securitycode, String userId) {
        return mServiceInterface.getOrderHistoryPhpCall(
                convertPlainString(securitycode),
                convertPlainString(userId)
        );
    }

    // Get Order History
    public Call<GetOrderHistoryDetailsPhp> getOrderHistoryDetailsPhpCall(String securitycode, String userId, String orderId) {
        return mServiceInterface.getOrderHistoryDetailsPhpCall(
                convertPlainString(securitycode),
                convertPlainString(userId),
                convertPlainString(orderId)
        );
    }

    // Get Provinces
    public Call<GetProvincesPhp> getProvincesPhpCall(String securitycode) {
        return mServiceInterface.getProvincesPhpCall(
                convertPlainString(securitycode)
        );
    }

    // Get Categories
    public Call<GetCategoriesPhp> getCategoriesPhpCall(String securitycode) {
        return mServiceInterface.getCategoriesPhpCall(
                convertPlainString(securitycode)
        );
    }

    // Search Products
    public Call<SearchProductsPhp> searchProductsPhpCall(String securitycode, String searchquery) {
        return mServiceInterface.searchProductsPhpCall(
                convertPlainString(securitycode),
                convertPlainString(searchquery)
        );
    }

    // Get Products by Province
    public Call<GetProductsByPhp> getProductsByProvincePhpCall(String securitycode, String provId) {
        return mServiceInterface.getProductsByProvincePhpCall(
                convertPlainString(securitycode),
                convertPlainString(provId)
        );
    }

    // Get Products by Categories
    public Call<GetProductsByPhp> getProductsByCategoryPhpCall(String securitycode, String catId) {
        return mServiceInterface.getProductsByCategoryPhpCall(
                convertPlainString(securitycode),
                convertPlainString(catId)
        );
    }

    // Get Products by Categories
    public Call<GetProductsByPhp> getProductsByStorePhpCall(String securitycode, String sellerId) {
        return mServiceInterface.getProductsByStorePhpCall(
                convertPlainString(securitycode),
                convertPlainString(sellerId)
        );
    }

    // Get Products Images
    public Call<GetProductImagesPhp> getProductImagesPhpCall(String securitycode, String prodId) {
        return mServiceInterface.getProductImagesPhpCall(
                convertPlainString(securitycode),
                convertPlainString(prodId)
        );
    }

    // Get Products by Categories
    public Call<GetSellerInformationPhp> getSellerInformationPhpCall(String securitycode, String sellerId) {
        return mServiceInterface.getSellerInformationPhpCall(
                convertPlainString(securitycode),
                convertPlainString(sellerId)
        );
    }

    // Get Products by Categories
    public Call<GetOrderUpdatesPhp> getOrderUpdatesPhpCall(String securitycode, String orderId) {
        return mServiceInterface.getOrderUpdatesPhpCall(
                convertPlainString(securitycode),
                convertPlainString(orderId)
        );
    }


}
