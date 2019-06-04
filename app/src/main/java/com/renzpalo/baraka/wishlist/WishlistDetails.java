package com.renzpalo.baraka.wishlist;

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
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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
import com.renzpalo.baraka.account.Account;
import com.renzpalo.baraka.account.OrderHistory;
import com.renzpalo.baraka.cart.CartDetails;
import com.renzpalo.baraka.categories.Categories;
import com.renzpalo.baraka.home.HomeActivity;
import com.renzpalo.baraka.login.SignInActivity;
import com.renzpalo.baraka.phpResponse.GetWishlistDetailsPhp;
import com.renzpalo.baraka.provinces.Provinces;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WishlistDetails extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private String TAG = "CartDetails";

    private NavigationView navigationView;
    private DrawerLayout drawerLayout;

    private Menu menu;

    private String userId = "";
    private String cartId = "";

    private TextView tvWishlistItemCount;

    private RecyclerView rvWishlistItems;

    private WishlistAdapter wishlistAdapter;
    private ArrayList<WishlistModel> listWishlistModel = new ArrayList<>();

    private TextView tvNavUsername, tvNavUserEmail;

    private String username = "";
    private String userEmail = "";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wishlist);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        navigationView = findViewById(R.id.navView);
        drawerLayout = findViewById(R.id.drawerLayout);

        tvWishlistItemCount = findViewById(R.id.tvWishlistItemCount);

        rvWishlistItems = findViewById(R.id.rvWishlistItems);

        username = SharedPreferenceUtility.getInstance().getString(Constant.USER_FULLNAME);
        userEmail = SharedPreferenceUtility.getInstance().getString(Constant.USER_EMAIL);

        View headerView = navigationView.getHeaderView(0);

        tvNavUsername = headerView.findViewById(R.id.tvNavUsername);
        tvNavUserEmail = headerView.findViewById(R.id.tvNavUserEmail);

        tvNavUsername.setText(username);
        tvNavUserEmail.setText(userEmail);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        cartId = SharedPreferenceUtility.getInstance().getString(Constant.QUOTE_ID);
        userId = SharedPreferenceUtility.getInstance().getString(Constant.USER_DATA);

        LinearLayoutManager mLayoutManager;

        mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        rvWishlistItems.setLayoutManager(mLayoutManager);
        rvWishlistItems.setItemAnimator(new DefaultItemAnimator());

        wishlistAdapter = new WishlistAdapter(this, listWishlistModel);

        rvWishlistItems.setAdapter(wishlistAdapter);

        getWishlistDetails();

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            Intent intent = new Intent(WishlistDetails.this, HomeActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_provinces) {
            Intent intent = new Intent(WishlistDetails.this, Provinces.class);
            startActivity(intent);
        } else if (id == R.id.nav_categories) {
            Intent intent = new Intent(WishlistDetails.this, Categories.class);
            startActivity(intent);
        } else if (id == R.id.nav_wishlist) {
            Intent intent = new Intent(WishlistDetails.this, WishlistDetails.class);
            startActivity(intent);
        } else if (id == R.id.nav_orders) {
            Intent intent = new Intent(WishlistDetails.this, OrderHistory.class);
            startActivity(intent);
        } else if (id == R.id.nav_account) {
            Intent intent = new Intent(WishlistDetails.this, Account.class);
            startActivity(intent);
        } else if (id == R.id.nav_logout) {
            Intent intent = new Intent(WishlistDetails.this, SignInActivity.class);
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
            AppUtility.updateCartCount(WishlistDetails.this, this.menu);
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

                        if (null != etSearch.getText().toString().trim() && !etSearch.getText().toString().trim().equals("")) {
                            // Search
                        }

                        // Start Intent
                    }

                    return false;
                }
            });
        } else if (id == R.id.cart) {
//            Intent intent = new Intent(CartDetails.this, CartDetails.class);
//            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    public void getWishlistDetails() {
        if (!NetworkUtility.isNetworkConnected(WishlistDetails.this)) {
            AppUtility.displayAlertMessage(WishlistDetails.this, getString(R.string.network_not_connected));
        } else {
            ServiceWrapper serviceWrapper = new ServiceWrapper(null);
            Call<GetWishlistDetailsPhp> call = serviceWrapper.getWishlistDetailsPhpCall("1234", userId);

            call.enqueue(new Callback<GetWishlistDetailsPhp>() {
                @Override
                public void onResponse(Call<GetWishlistDetailsPhp> call, Response<GetWishlistDetailsPhp> response) {
                    Log.i(TAG, " response: " + response.body().getInformation().toString());

                    if (response.body() != null && response.isSuccessful()) {
                        if (response.body().getStatus() == 1) {
                            tvWishlistItemCount.setText(String.valueOf(response.body().getInformation().getProductArray().size()));
                            if (response.body().getInformation().getProductArray().size() > 0) {
                                listWishlistModel.clear();
                                for (int i = 0; i < response.body().getInformation().getProductArray().size(); i++) {
                                    listWishlistModel.add(new WishlistModel(
                                            response.body().getInformation().getProductArray().get(i).getProdId(),
                                            response.body().getInformation().getProductArray().get(i).getProdName(),
                                            response.body().getInformation().getProductArray().get(i).getProdPrice(),
                                            response.body().getInformation().getProductArray().get(i).getProdImage(),
                                            response.body().getInformation().getProductArray().get(i).getProdQuantity()
                                    ));
                                }
                                wishlistAdapter.notifyDataSetChanged();
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
                public void onFailure(Call<GetWishlistDetailsPhp> call, Throwable t) {
                    Log.e(TAG, "failed " + t.toString());
                }
            });
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (this.menu != null) {
            AppUtility.updateCartCount(WishlistDetails.this, this.menu);
        }
    }

    public void updateCartCount() {
        AppUtility.updateCartCount(WishlistDetails.this, menu);
    }
}
