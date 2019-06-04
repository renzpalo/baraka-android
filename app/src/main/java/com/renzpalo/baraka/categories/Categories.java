package com.renzpalo.baraka.categories;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import android.widget.SearchView;
import android.widget.TextView;

import com.renzpalo.baraka.R;
import com.renzpalo.baraka.Utility.AppUtility;
import com.renzpalo.baraka.Utility.Constant;
import com.renzpalo.baraka.Utility.NetworkUtility;
import com.renzpalo.baraka.Utility.SharedPreferenceUtility;
import com.renzpalo.baraka.WebServices.ServiceWrapper;
import com.renzpalo.baraka.cart.CartDetails;
import com.renzpalo.baraka.phpResponse.GetCategoriesPhp;
import com.renzpalo.baraka.phpResponse.GetProvincesPhp;
import com.renzpalo.baraka.provinces.Provinces;
import com.renzpalo.baraka.provinces.ProvincesAdapter;
import com.renzpalo.baraka.provinces.ProvincesModel;
import com.renzpalo.baraka.searchProducts.SearchProducts;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Categories extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private String TAG = "Provinces";

    private NavigationView navigationView;
    private DrawerLayout drawerLayout;

    private Menu menu;

    private RecyclerView rvCategories;

    private CategoriesAdapter categoriesAdapter;
    private ArrayList<CategoriesModel> listCategoriesModel = new ArrayList<>();

    private TextView tvNavUsername, tvNavUserEmail;

    private String username = "";
    private String userEmail = "";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        navigationView = findViewById(R.id.navView);
        drawerLayout = findViewById(R.id.drawerLayout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        rvCategories = findViewById(R.id.rvCategories);

        username = SharedPreferenceUtility.getInstance().getString(Constant.USER_FULLNAME);
        userEmail = SharedPreferenceUtility.getInstance().getString(Constant.USER_EMAIL);

        View headerView = navigationView.getHeaderView(0);

        tvNavUsername = headerView.findViewById(R.id.tvNavUsername);
        tvNavUserEmail = headerView.findViewById(R.id.tvNavUserEmail);

        tvNavUsername.setText(username);
        tvNavUserEmail.setText(userEmail);

        GridLayoutManager mLayoutManager;

        mLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        rvCategories.setLayoutManager(mLayoutManager);

        categoriesAdapter = new CategoriesAdapter(this, listCategoriesModel, getScreenWidth());

        rvCategories.setAdapter(categoriesAdapter);

        getCategories();

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);

        this.menu = menu;

        if (this.menu != null) {
            AppUtility.updateCartCount(Categories.this, this.menu);
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.search) {
            SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
            SearchView searchView = (SearchView) this.menu.findItem(R.id.search).getActionView();

            final EditText etSearch = (EditText) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);

            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

            etSearch.setHint(R.string.search);
            etSearch.setHintTextColor(getResources().getColor(R.color.white));

            etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                    if (i == EditorInfo.IME_ACTION_SEARCH) {
                        Log.e(TAG, "Search is " + etSearch.getText().toString());

                        Intent intent = new Intent(Categories.this, SearchProducts.class);
                        intent.putExtra("searchQuery", etSearch.getText().toString());
                        startActivity(intent);

                        return true;
//                        if (null != etSearch.getText().toString().trim() && !etSearch.getText().toString().trim().equals("")) {
////                            // Search
////
////                        }

                        // Start Intent
                    }

                    return false;
                }
            });
        } else if (id == R.id.cart) {
            Intent intent = new Intent(Categories.this, CartDetails.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (this.menu != null) {
            AppUtility.updateCartCount(Categories.this, this.menu);
        }
    }

    public void getCategories() {
        if (!NetworkUtility.isNetworkConnected(Categories.this)) {
            AppUtility.displayAlertMessage(Categories.this, getString(R.string.network_not_connected));
        } else {
            ServiceWrapper serviceWrapper = new ServiceWrapper(null);
            Call<GetCategoriesPhp> call = serviceWrapper.getCategoriesPhpCall("1234");

            call.enqueue(new Callback<GetCategoriesPhp>() {
                @Override
                public void onResponse(Call<GetCategoriesPhp> call, Response<GetCategoriesPhp> response) {
                    Log.i(TAG, " response: " + response.body().getInformation().toString());

                    if (response.body() != null && response.isSuccessful()) {
                        if (response.body().getStatus() == 1) {
                            listCategoriesModel.clear();
                            for (int i = 0; i < response.body().getInformation().getCategories().size(); i++) {
                                listCategoriesModel.add(new CategoriesModel(
                                        response.body().getInformation().getCategories().get(i).getCatId(),
                                        response.body().getInformation().getCategories().get(i).getCatName(),
                                        response.body().getInformation().getCategories().get(i).getCatImage()
                                ));
                            }
                            categoriesAdapter.notifyDataSetChanged();

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
                public void onFailure(Call<GetCategoriesPhp> call, Throwable t) {
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
}
