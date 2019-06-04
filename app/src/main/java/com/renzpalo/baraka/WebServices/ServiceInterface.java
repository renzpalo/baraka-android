package com.renzpalo.baraka.WebServices;

        import com.renzpalo.baraka.phpResponse.AddAddressPhp;
        import com.renzpalo.baraka.phpResponse.AddToCartPhp;
        import com.renzpalo.baraka.phpResponse.AddToWishlistPhp;
        import com.renzpalo.baraka.phpResponse.BannersPhp;
        import com.renzpalo.baraka.phpResponse.GetCartDetailsPhp;
        import com.renzpalo.baraka.phpResponse.DeleteProductPhp;
        import com.renzpalo.baraka.phpResponse.FeaturedProductsPhp;
        import com.renzpalo.baraka.phpResponse.FeaturedStoresPhp;
        import com.renzpalo.baraka.phpResponse.ForgotPasswordPhp;
        import com.renzpalo.baraka.phpResponse.GetAddressDetailsPhp;
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

        import okhttp3.RequestBody;
        import retrofit2.Call;
        import retrofit2.http.Multipart;
        import retrofit2.http.POST;
        import retrofit2.http.Part;

public interface ServiceInterface {

    // Method, return type, secondary Url.
    // ecommerce/signup.php

    String hostURL = "baraka/android/";
    // String hostURL = "admin/android/";
//    String hostURL = "android/";

    @Multipart
    @POST(hostURL + "signup.php")
    Call<SignUpPhp> signUpPhpCall (
            @Part("user_fullname") RequestBody userFullname,
            @Part("user_phone_no") RequestBody userPhoneNo,
            @Part("user_username") RequestBody userUsername,
            @Part("user_password") RequestBody userPassword
    );

    @Multipart
    @POST(hostURL + "signin.php")
    Call<SignInPhp> signInPhpCall (
            @Part("user_phone_no") RequestBody userPhoneNo,
            @Part("user_password") RequestBody userPassword
    );

    @Multipart
    @POST(hostURL + "forgot_password.php")
    Call<ForgotPasswordPhp> forgotPasswordPhpCall (
            @Part("user_phone_no") RequestBody userPhoneNo
    );

    // Featured Stores
    @Multipart
    @POST(hostURL + "get_featured_stores.php")
    Call<FeaturedStoresPhp> featuredStoresPhpCall (
            @Part("security_code") RequestBody securitycode
    );

    // Featured Products
    @Multipart
    @POST(hostURL + "get_featured_products.php")
    Call<FeaturedProductsPhp> featuredProductsPhpCall (
            @Part("security_code") RequestBody securitycode
    );

    // Product Details
    @Multipart
    @POST(hostURL + "get_product_details.php")
    Call<ProductDetailsPhp> productDetailsPhpCall (
            @Part("security_code") RequestBody securitycode,
            @Part("prod_id") RequestBody productId
    );

    // Banners
    @Multipart
    @POST(hostURL + "get_banners.php")
    Call<BannersPhp> bannersPhpCall (
            @Part("security_code") RequestBody securitycode
    );

    // Add to Cart
    @Multipart
    @POST(hostURL + "add_to_cart.php")
    Call<AddToCartPhp> addToCartPhpCall (
            @Part("security_code") RequestBody securitycode,
            @Part("prod_id") RequestBody productId,
            @Part("prod_price") RequestBody productPrice,
            @Part("prod_quantity") RequestBody productQuantity,
            @Part("user_id") RequestBody userId
    );

    // Add to Wishlist
    @Multipart
    @POST(hostURL + "add_to_wishlist.php")
    Call<AddToWishlistPhp> addToWishlistPhpCall (
            @Part("security_code") RequestBody securitycode,
            @Part("prod_id") RequestBody productId,
            @Part("prod_price") RequestBody productPrice,
            @Part("prod_quantity") RequestBody productQuantity,
            @Part("user_id") RequestBody userId
    );

    // Get Cart Details
    @Multipart
    @POST(hostURL + "get_cart_details.php")
    Call<GetCartDetailsPhp> getCartDetailsPhpCall (
            @Part("security_code") RequestBody securitycode,
            @Part("user_id") RequestBody userId
    );

    // Get Wishlist Details
    @Multipart
    @POST(hostURL + "get_wishlist_details.php")
    Call<GetWishlistDetailsPhp> getWishlistDetailsPhpCall (
            @Part("security_code") RequestBody securitycode,
            @Part("user_id") RequestBody userId
    );

    // Delete Product from Cart
    @Multipart
    @POST(hostURL + "delete_cart_product.php")
    Call<DeleteProductPhp> deleteCartProductPhpCall (
            @Part("security_code") RequestBody securitycode,
            @Part("prod_id") RequestBody productId,
            @Part("user_id") RequestBody userId
    );

    // Delete Product from Wishlist
    @Multipart
    @POST(hostURL + "delete_wishlist_product.php")
    Call<DeleteProductPhp> deleteWishlistProductPhpCall (
            @Part("security_code") RequestBody securitycode,
            @Part("prod_id") RequestBody productId,
            @Part("user_id") RequestBody userId
    );

    // Update Product from Cart - Quantity
    @Multipart
    @POST(hostURL + "update_cart.php")
    Call<UpdateCartPhp> updateCartProductPhpCall (
            @Part("security_code") RequestBody securitycode,
            @Part("prod_id") RequestBody productId,
            @Part("prod_quantity") RequestBody prodQuantity,
            @Part("user_id") RequestBody userId
    );

    // Get Order Summary
    @Multipart
    @POST(hostURL + "get_order_summary.php")
    Call<GetOrderSummaryPhp> getOrderSummaryPhpCall (
            @Part("security_code") RequestBody securitycode,
            @Part("user_id") RequestBody userId
    );

    // Get Cart Details
    @Multipart
    @POST(hostURL + "add_address.php")
    Call<AddAddressPhp> addAddressPhpCall (
            @Part("security_code") RequestBody securitycode,
            @Part("ad_fullname") RequestBody adFullname,
            @Part("ad_contact") RequestBody adContact,
            @Part("ad_street") RequestBody adStreet,
            @Part("ad_barangay") RequestBody adBarangay,
            @Part("ad_city_muni") RequestBody adCityMuni,
            @Part("ad_province") RequestBody adProvince,
            @Part("ad_zipcode") RequestBody adZipcode,
            @Part("ad_notes") RequestBody adNotes,
            @Part("user_id") RequestBody userId
    );

    // Get Order Summary
    @Multipart
    @POST(hostURL + "get_address.php")
    Call<GetAddressDetailsPhp> getAddressDetailsPhpCall (
            @Part("security_code") RequestBody securitycode,
            @Part("user_id") RequestBody userId
    );

    // Place Order
    @Multipart
    @POST(hostURL + "place_order.php")
    Call<PlaceOrderPhp> placeOrderPhpCall (
            @Part("security_code") RequestBody securitycode,
            @Part("order_total") RequestBody orderTotal,
            @Part("order_ship_fee") RequestBody orderShipFee,
            @Part("order_payment") RequestBody orderPayment,
            @Part("order_status") RequestBody orderStatus,
            @Part("user_id") RequestBody userId,
            @Part("ad_id") RequestBody adId
    );

    // Get Order History
    @Multipart
    @POST(hostURL + "get_order_history.php")
    Call<GetOrderHistoryPhp> getOrderHistoryPhpCall (
            @Part("security_code") RequestBody securitycode,
            @Part("user_id") RequestBody userId
    );

    // Get Order History Details
    @Multipart
    @POST(hostURL + "get_order_history_details.php")
    Call<GetOrderHistoryDetailsPhp> getOrderHistoryDetailsPhpCall (
            @Part("security_code") RequestBody securitycode,
            @Part("user_id") RequestBody userId,
            @Part("order_id") RequestBody orderId
    );

    // Get Provinces
    @Multipart
    @POST(hostURL + "get_provinces.php")
    Call<GetProvincesPhp> getProvincesPhpCall (
            @Part("security_code") RequestBody securitycode
    );

    // Get Categories
    @Multipart
    @POST(hostURL + "get_categories.php")
    Call<GetCategoriesPhp> getCategoriesPhpCall (
            @Part("security_code") RequestBody securitycode
    );

    // Search Products
    @Multipart
    @POST(hostURL + "search_product.php")
    Call<SearchProductsPhp> searchProductsPhpCall (
            @Part("security_code") RequestBody securitycode,
            @Part("search_query") RequestBody searchquery
    );

    // Get Products Province
    @Multipart
    @POST(hostURL + "get_province_products.php")
    Call<GetProductsByPhp> getProductsByProvincePhpCall (
            @Part("security_code") RequestBody securitycode,
            @Part("prov_id") RequestBody provId
    );

    // Get Products Category
    @Multipart
    @POST(hostURL + "get_category_products.php")
    Call<GetProductsByPhp> getProductsByCategoryPhpCall (
            @Part("security_code") RequestBody securitycode,
            @Part("cat_id") RequestBody catId
    );

    // Get Products Category
    @Multipart
    @POST(hostURL + "get_seller_products.php")
    Call<GetProductsByPhp> getProductsByStorePhpCall (
            @Part("security_code") RequestBody securitycode,
            @Part("seller_id") RequestBody sellerId
    );

    // Get Product Images
    @Multipart
    @POST(hostURL + "get_product_images.php")
    Call<GetProductImagesPhp> getProductImagesPhpCall (
            @Part("security_code") RequestBody securitycode,
            @Part("prod_id") RequestBody prodId
    );

    // Get Products Category
    @Multipart
    @POST(hostURL + "get_store_information.php")
    Call<GetSellerInformationPhp> getSellerInformationPhpCall (
            @Part("security_code") RequestBody securitycode,
            @Part("seller_id") RequestBody sellerId
    );

    // Get Products Category
    @Multipart
    @POST(hostURL + "get_order_status.php")
    Call<GetOrderUpdatesPhp> getOrderUpdatesPhpCall (
            @Part("security_code") RequestBody securitycode,
            @Part("order_id") RequestBody orderId
    );
}
