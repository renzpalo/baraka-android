package com.renzpalo.baraka.provinces;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;

import com.renzpalo.baraka.R;
import com.renzpalo.baraka.Utility.AppUtility;
import com.renzpalo.baraka.Utility.NetworkUtility;
import com.renzpalo.baraka.WebServices.ServiceWrapper;
import com.renzpalo.baraka.account.Account;
import com.renzpalo.baraka.account.OrderHistory;
import com.renzpalo.baraka.cart.CartDetails;
import com.renzpalo.baraka.categories.Categories;
import com.renzpalo.baraka.home.HomeActivity;
import com.renzpalo.baraka.login.SignInActivity;
import com.renzpalo.baraka.phpResponse.GetProductsByPhp;
import com.renzpalo.baraka.phpResponse.GetProvincesPhp;
import com.renzpalo.baraka.searchProducts.ProductAdapter;
import com.renzpalo.baraka.searchProducts.ProductModel;
import com.renzpalo.baraka.searchProducts.SearchProducts;
import com.renzpalo.baraka.wishlist.WishlistDetails;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProvinceProducts extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private String TAG = "ProvinceProducts";

    private NavigationView navigationView;
    private DrawerLayout drawerLayout;

    private Menu menu;

    private RecyclerView rvProvinceProducts;

    private TextView tvTitleProvince;

    private String provId;
    private String provName;

    private ProductAdapter productAdapter;
    private ArrayList<ProductModel> listProducts = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_province_products);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        navigationView = findViewById(R.id.navView);
        drawerLayout = findViewById(R.id.drawerLayout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        rvProvinceProducts = findViewById(R.id.rvProvinceProducts);

        tvTitleProvince = findViewById(R.id.tvTitleProvince);

        GridLayoutManager mLayoutManager;

        mLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        rvProvinceProducts.setLayoutManager(mLayoutManager);

        productAdapter = new ProductAdapter(this, listProducts, getScreenWidth());

        rvProvinceProducts.setAdapter(productAdapter);

        Intent intent = getIntent();

        provId = intent.getExtras().getString("provId");
        provName = intent.getExtras().getString("provName");

        tvTitleProvince.setText(provName);

        getProductsByProvince(provId);


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            Intent intent = new Intent(ProvinceProducts.this, HomeActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_provinces) {
            Intent intent = new Intent(ProvinceProducts.this, Provinces.class);
            startActivity(intent);
        } else if (id == R.id.nav_categories) {
            Intent intent = new Intent(ProvinceProducts.this, Categories.class);
            startActivity(intent);
        } else if (id == R.id.nav_wishlist) {
            Intent intent = new Intent(ProvinceProducts.this, WishlistDetails.class);
            startActivity(intent);
        } else if (id == R.id.nav_orders) {
            Intent intent = new Intent(ProvinceProducts.this, OrderHistory.class);
            startActivity(intent);
        } else if (id == R.id.nav_account) {
            Intent intent = new Intent(ProvinceProducts.this, Account.class);
            startActivity(intent);
        } else if (id == R.id.nav_logout) {
            Intent intent = new Intent(ProvinceProducts.this, SignInActivity.class);
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

        if (this.menu != null) {
            AppUtility.updateCartCount(ProvinceProducts.this, this.menu);
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.search) {
            SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
            android.support.v7.widget.SearchView searchView = (android.support.v7.widget.SearchView) this.menu.findItem(R.id.search).getActionView();
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
                            Intent intent = new Intent(ProvinceProducts.this, SearchProducts.class);
                            intent.putExtra("searchQuery", etSearch.getText().toString());
                            startActivity(intent);

                            return true;
                        }
                    }

                    return false;
                }
            });
        } else if (id == R.id.cart) {
            Intent intent = new Intent(ProvinceProducts.this, CartDetails.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (this.menu != null) {
            AppUtility.updateCartCount(ProvinceProducts.this, this.menu);
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

    public void getProductsByProvince(String provId) {
        if (!NetworkUtility.isNetworkConnected(ProvinceProducts.this)) {
            AppUtility.displayAlertMessage(ProvinceProducts.this, getString(R.string.network_not_connected));
        } else {
            ServiceWrapper serviceWrapper = new ServiceWrapper(null);
            Call<GetProductsByPhp> call = serviceWrapper.getProductsByProvincePhpCall("1234", provId);

            call.enqueue(new Callback<GetProductsByPhp>() {
                @Override
                public void onResponse(Call<GetProductsByPhp> call, Response<GetProductsByPhp> response) {
                    Log.i(TAG, " response: " + response.body().getInformation().toString());

                    if (response.body() != null && response.isSuccessful()) {
                        if (response.body().getStatus() == 1) {
                            listProducts.clear();
                            for (int i = 0; i < response.body().getInformation().size(); i++) {
                                listProducts.add(new ProductModel(
                                        response.body().getInformation().get(i).getProdId(),
                                        response.body().getInformation().get(i).getProdName(),
                                        response.body().getInformation().get(i).getProdPrice(),
                                        response.body().getInformation().get(i).getProdDiscPrice(),
                                        response.body().getInformation().get(i).getProdImage(),
                                        response.body().getInformation().get(i).getProdRating(),
                                        response.body().getInformation().get(i).getProvName(),
                                        response.body().getInformation().get(i).getCatName()
                                ));
                            }
                            productAdapter.notifyDataSetChanged();

                        } else {
                            Log.e(TAG, "Failed to get provinces." + response.body().getMessage());
                            // AppUtility.displayMessage(HomeActivity.this, response.body().getMessage());
                        }
                    }  else {
                        // AppUtility.displayMessage(HomeActivity.this, getString(R.string.failed_request));
                        // getFeaturedProducts();
                    }
                }

                @Override
                public void onFailure(Call<GetProductsByPhp> call, Throwable t) {
                    Log.e(TAG, "failed " + t.toString());
                }
            });
        }
    }
}
