package com.renzpalo.baraka.home;

        import android.app.SearchManager;
        import android.content.Context;
        import android.content.Intent;
        import android.content.SharedPreferences;
        import android.os.Bundle;
        import android.support.annotation.NonNull;
        import android.support.annotation.Nullable;
        import android.support.design.widget.NavigationView;
        import android.support.v4.view.GravityCompat;
        import android.support.v4.widget.DrawerLayout;
        import android.support.v7.app.ActionBarDrawerToggle;
        import android.support.v7.app.AppCompatActivity;
        import android.support.v7.widget.DefaultItemAnimator;
        import android.support.v7.widget.LinearLayoutManager;
        import android.support.v7.widget.RecyclerView;
        import android.support.v7.widget.SearchView;
        import android.support.v7.widget.Toolbar;
        import android.util.DisplayMetrics;
        import android.util.Log;
        import android.view.KeyEvent;
        import android.view.Menu;
        import android.view.MenuItem;
        import android.view.View;
        import android.view.WindowManager;
        import android.view.inputmethod.EditorInfo;
        import android.widget.EditText;
        import android.widget.TextView;

        import com.renzpalo.baraka.R;
        import com.renzpalo.baraka.Utility.AppUtility;
        import com.renzpalo.baraka.Utility.Constant;
        import com.renzpalo.baraka.Utility.NetworkUtility;
        import com.renzpalo.baraka.Utility.SharedPreferenceUtility;
        import com.renzpalo.baraka.WebServices.ServiceWrapper;
        import com.renzpalo.baraka.account.Account;
        import com.renzpalo.baraka.account.OrderHistory;
        import com.renzpalo.baraka.cart.CartDetails;
        import com.renzpalo.baraka.cart.CartModel;
        import com.renzpalo.baraka.categories.Categories;
        import com.renzpalo.baraka.login.SignInActivity;
        import com.renzpalo.baraka.phpResponse.BannersPhp;
        import com.renzpalo.baraka.phpResponse.GetCartDetailsPhp;
        import com.renzpalo.baraka.phpResponse.FeaturedProductsPhp;
        import com.renzpalo.baraka.phpResponse.FeaturedStoresPhp;
        import com.renzpalo.baraka.productPreview.ProductDetails;
        import com.renzpalo.baraka.provinces.Provinces;
        import com.renzpalo.baraka.searchProducts.SearchProducts;
        import com.renzpalo.baraka.wishlist.WishlistDetails;

        import java.util.ArrayList;
        import java.util.List;

        import retrofit2.Call;
        import retrofit2.Callback;
        import retrofit2.Response;
        import ss.com.bannerslider.banners.Banner;
        import ss.com.bannerslider.banners.RemoteBanner;
        import ss.com.bannerslider.views.BannerSlider;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private String TAG = "HomeActivity";

    private RecyclerView rvBestSellers, rvFeaturedProducts, rvFeaturedStores;

    private TextView tvNavUsername, tvNavUserEmail;

    // Featured Products
    private ArrayList<FeaturedProductsModel> listFeaturedProductsModel = new ArrayList<FeaturedProductsModel>();
    private FeaturedProductsModel featuredProductsModel;
    private FeaturedProductsAdapter featuredProductsAdapter;

    // Featured Stores
    private ArrayList<FeaturedStoresModel> listFeaturedStoresModel = new ArrayList<FeaturedStoresModel>();
    private FeaturedStoresModel featuredStoresModel;
    private FeaturedStoresAdapter featuredStoresAdapter;

    private NavigationView navigationView;
    private DrawerLayout drawerLayout;

    private Menu menu;

//    private Slider slider;
//    private List<Banner> listBannerImages = new ArrayList<>()

    private BannerSlider bannerSlider;
    private List<Banner> listBannerImages = new ArrayList<>();

    private String userId = "";
    private String cartId = "";
    private String username = "";
    private String userEmail = "";


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        LinearLayoutManager mLayoutManager;

        navigationView = findViewById(R.id.navView);
        drawerLayout = findViewById(R.id.drawerLayout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);



        cartId = SharedPreferenceUtility.getInstance().getString(Constant.QUOTE_ID);
        userId = SharedPreferenceUtility.getInstance().getString(Constant.USER_DATA);
        username = SharedPreferenceUtility.getInstance().getString(Constant.USER_FULLNAME);
        userEmail = SharedPreferenceUtility.getInstance().getString(Constant.USER_EMAIL);

        View headerView = navigationView.getHeaderView(0);

        tvNavUsername = headerView.findViewById(R.id.tvNavUsername);
        tvNavUserEmail = headerView.findViewById(R.id.tvNavUserEmail);

        tvNavUsername.setText(username);
        tvNavUserEmail.setText(userEmail);

        // For Featured Products
        rvFeaturedProducts = findViewById(R.id.rvFeaturedProducts);
        mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        rvFeaturedProducts.setLayoutManager(mLayoutManager);
        rvFeaturedProducts.setItemAnimator(new DefaultItemAnimator());

        featuredProductsAdapter = new FeaturedProductsAdapter(this, listFeaturedProductsModel, getScreenWidth());

        rvFeaturedProducts.setAdapter(featuredProductsAdapter);

        // For Featured Stores
        rvFeaturedStores = findViewById(R.id.rvFeaturedStores);
        mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        rvFeaturedStores.setLayoutManager(mLayoutManager);
        rvFeaturedStores.setItemAnimator(new DefaultItemAnimator());

        featuredStoresAdapter = new FeaturedStoresAdapter(this, listFeaturedStoresModel, getScreenWidth());

        rvFeaturedStores.setAdapter(featuredStoresAdapter);

        bannerSlider = (BannerSlider) findViewById(R.id.homeSlider);

        getFeaturedStores();
        getFeaturedProducts();

        try {
            getCartDetails();
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }

        try {
            getBanners();
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }



    }

    public void getFeaturedStores() {
        if (!NetworkUtility.isNetworkConnected(HomeActivity.this)) {
            AppUtility.displayAlertMessage(HomeActivity.this, getString(R.string.network_not_connected));
        } else {
            ServiceWrapper serviceWrapper = new ServiceWrapper(null);
            Call<FeaturedStoresPhp> call = serviceWrapper.featuredStoresPhpCall("1234");

            call.enqueue(new Callback<FeaturedStoresPhp>() {
                @Override
                public void onResponse(Call<FeaturedStoresPhp> call, Response<FeaturedStoresPhp> response) {
                    Log.i(TAG, " response: " + response.body().getInformation().toString());

                    if (response.body() != null && response.isSuccessful()) {
                        if (response.body().getStatus() == 1) {
                            if (response.body().getInformation().size() > 0) {
                                listFeaturedStoresModel.clear();
                                for (int i = 0; i < response.body().getInformation().size(); i++) {
                                    listFeaturedStoresModel.add(new FeaturedStoresModel(
                                            response.body().getInformation().get(i).getSellerId(),
                                            response.body().getInformation().get(i).getSellerName(),
                                            response.body().getInformation().get(i).getSellerImage()
                                    ));
                                }
                                featuredStoresAdapter.notifyDataSetChanged();
                            }
                        } else {
                            Log.e(TAG, "Failed to get products." + response.body().getMessage());
                            // AppUtility.displayMessage(HomeActivity.this, response.body().getMessage());
                        }
                    }  else {
                        // AppUtility.displayMessage(HomeActivity.this, getString(R.string.failed_request));
                        // getFeaturedProducts();
                    }
                }

                @Override
                public void onFailure(Call<FeaturedStoresPhp> call, Throwable t) {
                    Log.e(TAG, "failed " + t.toString());
                }
            });
        }
    }

    public void getFeaturedProducts() {
        if (!NetworkUtility.isNetworkConnected(HomeActivity.this)) {

        } else {
            ServiceWrapper serviceWrapper = new ServiceWrapper(null);
            Call<FeaturedProductsPhp> call = serviceWrapper.featuredProductsPhpCall("1234");

            call.enqueue(new Callback<FeaturedProductsPhp>() {
                @Override
                public void onResponse(Call<FeaturedProductsPhp> call, Response<FeaturedProductsPhp> response) {
                    Log.i(TAG, " response: " + response.body().getInformation().toString());

                    if (response.body() != null && response.isSuccessful()) {
                        if (response.body().getStatus() == 1) {
                            if (response.body().getInformation().size() > 0) {
                                listFeaturedProductsModel.clear();
                                for (int i = 0; i < response.body().getInformation().size(); i++) {
                                    listFeaturedProductsModel.add(new FeaturedProductsModel(
                                            response.body().getInformation().get(i).getProdId(),
                                            response.body().getInformation().get(i).getProdName(),
                                            response.body().getInformation().get(i).getProdImage(),
                                            response.body().getInformation().get(i).getProdDiscPrice(),
                                            response.body().getInformation().get(i).getProdPrice(),
                                            response.body().getInformation().get(i).getProdRating()
                                    ));
                                }
                                featuredProductsAdapter.notifyDataSetChanged();
                            }
                        } else {
                            Log.e(TAG, "Failed to get products." + response.body().getMessage());
                            // AppUtility.displayMessage(HomeActivity.this, response.body().getMessage());
                        }
                    }  else {
                        // AppUtility.displayMessage(HomeActivity.this, getString(R.string.failed_request));
                        // getFeaturedProducts();
                    }
                }

                @Override
                public void onFailure(Call<FeaturedProductsPhp> call, Throwable t) {
                    Log.e(TAG, "failed " + t.toString());
                }
            });
        }
    }

    public void getBanners() {
        if (!NetworkUtility.isNetworkConnected(HomeActivity.this)) {

        } else {
            ServiceWrapper serviceWrapper = new ServiceWrapper(null);
            Call<BannersPhp> call = serviceWrapper.bannersPhpCall("1234");

            call.enqueue(new Callback<BannersPhp>() {
                @Override
                public void onResponse(Call<BannersPhp> call, Response<BannersPhp> response) {
                    Log.i(TAG, " response: " + response.body().getInformation().toString());

                    if (response.body() != null && response.isSuccessful()) {
                        if (response.body().getStatus() == 1) {
                            if (response.body().getInformation().size() > 0) {
                                listBannerImages.clear();
                                for (int i = 0; i < response.body().getInformation().size(); i++) {
                                    listBannerImages.add(new RemoteBanner(
                                            response.body().getInformation().get(i).getCarImage()
                                    ));
                                }
                                bannerSlider.setBanners(listBannerImages);
                            }
                        } else {
                            Log.e(TAG, "Failed to banners." + response.body().getMessage());
                            // AppUtility.displayMessage(HomeActivity.this, response.body().getMessage());
                        }
                    }  else {
                        // AppUtility.displayMessage(HomeActivity.this, getString(R.string.failed_request));
                        // getFeaturedProducts();
                    }
                }

                @Override
                public void onFailure(Call<BannersPhp> call, Throwable t) {
                    Log.e(TAG, "failed " + t.toString());
                }
            });
        }
    }

    public void getCartDetails() {
        if (!NetworkUtility.isNetworkConnected(HomeActivity.this)) {

        } else {
            ServiceWrapper serviceWrapper = new ServiceWrapper(null);
            Call<GetCartDetailsPhp> call = serviceWrapper.getCartDetailsPhpCall("1234", userId);

            call.enqueue(new Callback<GetCartDetailsPhp>() {
                @Override
                public void onResponse(Call<GetCartDetailsPhp> call, Response<GetCartDetailsPhp> response) {
                    Log.i(TAG, " response: " + response.body().getInformation().toString());

                    if (response.body() != null && response.isSuccessful()) {
                        if (response.body().getStatus() == 1) {
                            try {
                                AppUtility.updateCartCount(HomeActivity.this, menu);
                            } catch (Exception e) {
                                Log.e("Error: ", e.toString());
                            }

                            SharedPreferenceUtility.getInstance().saveInt(Constant.CART_ITEM_COUNT,
                                    response.body().getInformation().getProductArray().size());

                        } else {
                            Log.e(TAG, "Failed to banners." + response.body().getMessage());
                            // AppUtility.displayMessage(HomeActivity.this, response.body().getMessage());
                        }
                    }  else {
                        // AppUtility.displayMessage(HomeActivity.this, getString(R.string.failed_request));
                        // getFeaturedProducts();
                    }
                }

                @Override
                public void onFailure(Call<GetCartDetailsPhp> call, Throwable t) {
                    Log.e(TAG, "failed " + t.toString());
                }
            });
        }
    }

    // Measure the device screen width
    public int getScreenWidth() {
        int width = 100;

        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) getApplicationContext().getSystemService(WINDOW_SERVICE);

        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        width = displayMetrics.widthPixels;

        return width;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            Intent intent = new Intent(HomeActivity.this, HomeActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_provinces) {
            Intent intent = new Intent(HomeActivity.this, Provinces.class);
            startActivity(intent);
        } else if (id == R.id.nav_categories) {
            Intent intent = new Intent(HomeActivity.this, Categories.class);
            startActivity(intent);
        } else if (id == R.id.nav_wishlist) {
            Intent intent = new Intent(HomeActivity.this, WishlistDetails.class);
            startActivity(intent);
        } else if (id == R.id.nav_orders) {
            Intent intent = new Intent(HomeActivity.this, OrderHistory.class);
            startActivity(intent);
        } else if (id == R.id.nav_account) {
            Intent intent = new Intent(HomeActivity.this, Account.class);
            startActivity(intent);
        } else if (id == R.id.nav_logout) {
            SharedPreferenceUtility.getInstance().saveString(Constant.USER_DATA, null);
            SharedPreferenceUtility.getInstance().saveString(Constant.USER_ID, null);
            SharedPreferenceUtility.getInstance().saveString(Constant.USER_FULLNAME, null);
            SharedPreferenceUtility.getInstance().saveString(Constant.USER_EMAIL, null);
            SharedPreferenceUtility.getInstance().saveString(Constant.USER_PHONE, null);

            Intent intent = new Intent(HomeActivity.this, SignInActivity.class);
            startActivity(intent);

            // Clear SharedPreference.
        }

        drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);

        this.menu = menu;

//        SearchManager searchManager =
//                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
//        SearchView searchView =
//                (SearchView) this.menu.findItem(R.id.search).getActionView();
//        searchView.setSearchableInfo(
//                searchManager.getSearchableInfo(getComponentName()));

        if (this.menu != null) {
            AppUtility.updateCartCount(HomeActivity.this, this.menu);
            getCartDetails();
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.search) {
            SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
            SearchView searchView = (SearchView) this.menu.findItem(R.id.search).getActionView();
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

            final EditText etSearch = (EditText) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);

            etSearch.setHint(R.string.search);
            etSearch.setHintTextColor(getResources().getColor(R.color.white));

            etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                    if (i == EditorInfo.IME_ACTION_SEARCH) {
                        Log.e(TAG, "Search is " + etSearch.getText().toString());

                        if (null != etSearch.getText().toString().trim() && !etSearch.getText().toString().trim().equals("")) {
                            // Start Intent
                            Intent intent = new Intent(HomeActivity.this, SearchProducts.class);
                            intent.putExtra("searchQuery", etSearch.getText().toString());
                            startActivity(intent);

                            return true;
                        }
                    }

                    return false;
                }
            });
        } else if (id == R.id.cart) {
            Intent intent = new Intent(HomeActivity.this, CartDetails.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (this.menu != null) {
            AppUtility.updateCartCount(HomeActivity.this, this.menu);
            getCartDetails();
        }
    }
}
