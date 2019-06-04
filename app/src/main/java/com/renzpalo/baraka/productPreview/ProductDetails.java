package com.renzpalo.baraka.productPreview;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
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
import com.renzpalo.baraka.categories.CategoriesAdapter;
import com.renzpalo.baraka.home.FeaturedProductsAdapter;
import com.renzpalo.baraka.home.FeaturedProductsModel;
import com.renzpalo.baraka.home.HomeActivity;
import com.renzpalo.baraka.login.SignInActivity;
import com.renzpalo.baraka.phpResponse.AddToCartPhp;
import com.renzpalo.baraka.phpResponse.AddToWishlistPhp;
import com.renzpalo.baraka.phpResponse.BannersPhp;
import com.renzpalo.baraka.phpResponse.GetProductImagesPhp;
import com.renzpalo.baraka.phpResponse.ProductDetailsPhp;
import com.renzpalo.baraka.provinces.Provinces;
import com.renzpalo.baraka.wishlist.WishlistDetails;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ss.com.bannerslider.banners.Banner;
import ss.com.bannerslider.banners.RemoteBanner;
import ss.com.bannerslider.views.BannerSlider;

public class ProductDetails extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private String TAG = "ProductDetails";

    public String prodId = "";
    public String prodPrice = "";

    public String productOverview = "";
    public String productDetails = "";

    public String userId = "";

    // Reviews
    public String reviewSummary = "";
    public String reviewFeedback = "";
    public String reviewUserName = "";
    public String reviewDate = "";
    public String reviewRating = "";

    private TextView tvProdDetName, tvProdRatingCount, tvProdDetOldPrice, tvProdDetPrice, tvProdDetStocks;
    private EditText etProdQuantity;
    private ImageView ivProdDetImage;
    private RatingBar rbProdDetRating;
    private RecyclerView rvRelatedProducts, rvReviews;
    private TabLayout tlProdDet;
    private FrameLayout flFragContainer;
    private Button bAddToWishlist, bAddToCart;

    private ArrayList<ReviewModel> listReviewModel = new ArrayList<>();
    ReviewAdapter reviewAdapter;

    private ArrayList<FeaturedProductsModel> listRelatedProductsModel = new ArrayList<FeaturedProductsModel>();
    FeaturedProductsAdapter relatedProductsAdapter;

    private FragmentManager fragmentManager = getSupportFragmentManager();

    private NavigationView navigationView;
    private DrawerLayout drawerLayout;

    private BannerSlider productImagesSlider;
    private List<Banner> listProductImages = new ArrayList<>();

    private Menu menu;

    private TextView tvNavUsername, tvNavUserEmail;

    private String username = "";
    private String userEmail = "";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        prodId = intent.getExtras().getString("prodId");
        prodPrice = intent.getExtras().getString("prodPrice");


        userId = SharedPreferenceUtility.getInstance().getString(Constant.USER_DATA);

        tvProdDetName = findViewById(R.id.tvProdDetName);
        tvProdRatingCount = findViewById(R.id.tvProdRatingCount);
        tvProdDetOldPrice = findViewById(R.id.tvProdDetOldPrice);
        tvProdDetPrice = findViewById(R.id.tvProdDetPrice);
        tvProdDetStocks = findViewById(R.id.tvProdDetStocks);

        etProdQuantity = findViewById(R.id.etProdQuantity);

        rbProdDetRating = findViewById(R.id.rbProdRating);

        rvRelatedProducts = findViewById(R.id.rvRelatedProducts);
        rvReviews = findViewById(R.id.rvReviews);

        tlProdDet = findViewById(R.id.tlProdDet);

        flFragContainer = findViewById(R.id.flFragContainer);

        navigationView = findViewById(R.id.navView);
        drawerLayout = findViewById(R.id.drawerLayout);

        bAddToCart = findViewById(R.id.bAddToCart);
        bAddToWishlist = findViewById(R.id.bAddToWishlist);

        productImagesSlider = (BannerSlider) findViewById(R.id.productImagesSlider);

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

        tlProdDet.addTab(tlProdDet.newTab().setText(getString(R.string.overview)));
        tlProdDet.addTab(tlProdDet.newTab().setText(getString(R.string.details)));
        tlProdDet.addTab(tlProdDet.newTab().setText(getString(R.string.reviews)));

        tlProdDet.setTabMode(TabLayout.MODE_FIXED);
        tlProdDet.setTabGravity(TabLayout.GRAVITY_FILL);

        tlProdDet.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                // Check if there is a tab.
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Fragment currentFrag = fragmentManager.findFragmentByTag("ProductDetailsFragment");

                if (currentFrag != null) {
                    fragmentTransaction.remove(currentFrag);
                }

                if (tab.getPosition() == 0) {
                    Overview overview = new Overview();

                    fragmentTransaction.add(R.id.flFragContainer, overview, "ProductDetailsFragment");
                    fragmentTransaction.commit();
                } else if (tab.getPosition() == 1) {
                    Details details = new Details();

                    fragmentTransaction.add(R.id.flFragContainer, details, "ProductDetailsFragment");
                    fragmentTransaction.commit();

                } else if (tab.getPosition() == 2) {
                    Reviews reviews = new Reviews();

                    fragmentTransaction.add(R.id.flFragContainer, reviews, "ProductDetailsFragment");
                    fragmentTransaction.commit();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });




        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvRelatedProducts.setLayoutManager(mLayoutManager);
        rvRelatedProducts.setItemAnimator(new DefaultItemAnimator());

        relatedProductsAdapter = new FeaturedProductsAdapter(this, listRelatedProductsModel, getScreenWidth());
        rvRelatedProducts.setAdapter(relatedProductsAdapter);

//        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
//        rvReviews.setLayoutManager(mLayoutManager);
//        rvReviews.setItemAnimator(new DefaultItemAnimator());
//
//        reviewAdapter = new ReviewAdapter(this, listReviewModel);
//        rvReviews.setAdapter(reviewAdapter);

        getProductDetails();
        getProductImages();

        bAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!tvProdDetPrice.getText().toString().equals("") && tvProdDetPrice.getText().toString() != null && etProdQuantity.getText().toString() != null) {
                    addToCart(etProdQuantity.getText().toString());
                }
            }
        });

        bAddToWishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addToWishlist();
            }
        });
    }

    public void getProductDetails() {
        if (!NetworkUtility.isNetworkConnected(ProductDetails.this)) {
            AppUtility.displayAlertMessage(ProductDetails.this, getString(R.string.network_not_connected));
        } else {
            ServiceWrapper serviceWrapper = new ServiceWrapper(null);
            Call<ProductDetailsPhp> call = serviceWrapper.productDetailsPhpCall("1234", prodId);
            call.enqueue(new Callback<ProductDetailsPhp>() {
                @Override
                public void onResponse(Call<ProductDetailsPhp> call, Response<ProductDetailsPhp> response) {
                    Log.i(TAG, "Response is " + response.body().getInformation().toString());
                    if (response.body() != null && response.isSuccessful()) {
                        if (response.body().getStatus() == 1) {
                            if (response.body().getInformation().getRelatedProducts() != null) {
                                tvProdDetName.setText(response.body().getInformation().getProductDetails().get(0).getProdName());
                                tvProdDetPrice.setText("PHP " + String.valueOf(response.body().getInformation().getProductDetails().get(0).getProdPrice()));
                                tvProdDetOldPrice.setText(String.valueOf(response.body().getInformation().getProductDetails().get(0).getProdOldPrice()));
                                rbProdDetRating.setRating(response.body().getInformation().getProductDetails().get(0).getProdRating());

                                if (response.body().getInformation().getProductDetails().get(0).getProdStock() > 0) {
                                    tvProdDetStocks.setText(response.body().getInformation().getProductDetails().get(0).getProdStock() + " " +  getString(R.string.stocks_left));
                                } else if (response.body().getInformation().getProductDetails().get(0).getProdStock() <= 0){
                                    tvProdDetStocks.setText(getString(R.string.out_of_stock));
                                    bAddToCart.setEnabled(false);
                                }

                                if (response.body().getInformation().getRelatedProducts().size() > 0) {
                                    listRelatedProductsModel.clear();
                                    for (int i = 0; i < response.body().getInformation().getRelatedProducts().size(); i++) {
                                        listRelatedProductsModel.add(new FeaturedProductsModel(
                                                response.body().getInformation().getRelatedProducts().get(i).getRelProdId(),
                                                response.body().getInformation().getRelatedProducts().get(i).getRelProdName(),
                                                response.body().getInformation().getRelatedProducts().get(i).getRelProdImage(),
                                                response.body().getInformation().getRelatedProducts().get(i).getRelProdDiscPrice(),
                                                response.body().getInformation().getRelatedProducts().get(i).getRelProdPrice(),
                                                String.valueOf(response.body().getInformation().getRelatedProducts().get(i).getRelProdRating())
                                        ));

                                    }
                                    relatedProductsAdapter.notifyDataSetChanged();
                                }

//                                if (response.body().getInformation().getReviews().size() > 0) {
//                                    listReviewModel.clear();
//                                    for (int i = 0; i < response.body().getInformation().getReviews().size(); i++) {
//                                        listReviewModel.add(new ReviewModel(
//                                                response.body().getInformation().getReviews().get(i).getRevId(),
//                                                response.body().getInformation().getReviews().get(i).getUserName(),
//                                                response.body().getInformation().getReviews().get(i).getRevFeedback(),
//                                                response.body().getInformation().getReviews().get(i).getRevDate(),
//                                                response.body().getInformation().getReviews().get(i).getRevImage(),
//                                                response.body().getInformation().getReviews().get(i).getRevRating()
//                                        ));
//
//                                    }
//                                    reviewAdapter.notifyDataSetChanged();
//                                }

                                productOverview = response.body().getInformation().getProductDetails().get(0).getProdDesc();
                                productDetails = response.body().getInformation().getProductDetails().get(0).getProdDesc();

                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                Overview overview = new Overview();

                                fragmentTransaction.add(R.id.flFragContainer, overview, "ProductDetailsFragment");
                                fragmentTransaction.commit();

//                                reviewFeedback = response.body().getInformation().getReviews().get(0).getRevFeedback();
//                                reviewUserName = response.body().getInformation().getReviews().get(0).getUserName();
//                                reviewDate = response.body().getInformation().getReviews().get(0).getRevDate();
                            }

                        } else {
                            Log.e(TAG, "No products." + response.body().getMessage());
                        }
                    } else {
                        Log.e(TAG, "Failed to get response." + response.body().getMessage());
                    }
                }

                @Override
                public void onFailure(Call<ProductDetailsPhp> call, Throwable t) {
                    Log.e(TAG, "failed " + t.toString());
                }
            });
        }
    }

    public void getProductImages() {
        if (!NetworkUtility.isNetworkConnected(ProductDetails.this)) {

        } else {
            ServiceWrapper serviceWrapper = new ServiceWrapper(null);
            Call<GetProductImagesPhp> call = serviceWrapper.getProductImagesPhpCall("1234", prodId);

            call.enqueue(new Callback<GetProductImagesPhp>() {
                @Override
                public void onResponse(Call<GetProductImagesPhp> call, Response<GetProductImagesPhp> response) {
                    Log.i(TAG, " response: " + response.body().getInformation().toString());

                    if (response.body() != null && response.isSuccessful()) {
                        if (response.body().getStatus() == 1) {
                            if (response.body().getInformation().getImages().size() > 0) {
                                listProductImages.clear();
                                for (int i = 0; i < response.body().getInformation().getImages().size(); i++) {
                                    listProductImages.add(new RemoteBanner(
                                            response.body().getInformation().getImages().get(i).getImage()
                                    ));
                                }
                                productImagesSlider.setBanners(listProductImages);
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
                public void onFailure(Call<GetProductImagesPhp> call, Throwable t) {
                    Log.e(TAG, "failed " + t.toString());
                }
            });
        }
    }

    public void addToCart(String prodQuantity) {
        if (!NetworkUtility.isNetworkConnected(ProductDetails.this)) {
            AppUtility.displayAlertMessage(ProductDetails.this, getString(R.string.network_not_connected));
        } else {
            ServiceWrapper serviceWrapper = new ServiceWrapper(null);
            Call<AddToCartPhp> call = serviceWrapper.addToCartPhpCall("1234", prodId, prodPrice, prodQuantity, userId);
            call.enqueue(new Callback<AddToCartPhp>() {
                @Override
                public void onResponse(Call<AddToCartPhp> call, Response<AddToCartPhp> response) {
                    Log.i(TAG, "Response is " + response.body().getInformation().toString());
                    if (response.body() != null && response.isSuccessful()) {
                        if (response.body().getStatus() == 1) {
                            SharedPreferenceUtility.getInstance().saveInt(Constant.CART_ITEM_COUNT,
                                    response.body().getInformation());

                            AppUtility.updateCartCount(ProductDetails.this, menu);

                            // Toast.makeText(ProductDetails.this, "Added to cart.", Toast.LENGTH_LONG).show();

                            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.clProductDetails), "Added to cart", Snackbar.LENGTH_LONG);
                            mySnackbar.setAction("View Cart", new CartListener());
                            mySnackbar.show();

//                            finish();


                        } else {
                            Log.e(TAG, "Failed to add to cart." + response.body().getMessage());
                        }
                    } else {
                        Log.e(TAG, "Failed to get response." + response.body().getMessage());
                    }
                }

                @Override
                public void onFailure(Call<AddToCartPhp> call, Throwable t) {
                    Log.e(TAG, "failed " + t.toString());
                }
            });
        }
    }

    public void addToWishlist() {
        if (!NetworkUtility.isNetworkConnected(ProductDetails.this)) {
            AppUtility.displayAlertMessage(ProductDetails.this, getString(R.string.network_not_connected));
        } else {
            ServiceWrapper serviceWrapper = new ServiceWrapper(null);
            Call<AddToWishlistPhp> call = serviceWrapper.addToWishlistPhpCall("1234", prodId, prodPrice, "1", userId);
            call.enqueue(new Callback<AddToWishlistPhp>() {
                @Override
                public void onResponse(Call<AddToWishlistPhp> call, Response<AddToWishlistPhp> response) {
                    Log.i(TAG, "Response is " + response.body().getInformation());
                    if (response.body() != null && response.isSuccessful()) {
                        if (response.body().getStatus() == 1) {
                            // Toast.makeText(ProductDetails.this, "Added to wishlist.", Toast.LENGTH_LONG).show();

                            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.clProductDetails), "Added to cart", Snackbar.LENGTH_LONG);
                            mySnackbar.setAction("View Wishlist", new WishlistListener());
                            mySnackbar.show();
                        } else {
                            Log.e(TAG, "Failed to add to wishlist." + response.body().getMessage());
                        }
                    } else {
                        Log.e(TAG, "Failed to get response." + response.body().getMessage());
                    }
                }

                @Override
                public void onFailure(Call<AddToWishlistPhp> call, Throwable t) {
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
            Intent intent = new Intent(ProductDetails.this, HomeActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_provinces) {
            Intent intent = new Intent(ProductDetails.this, Provinces.class);
            startActivity(intent);
        } else if (id == R.id.nav_categories) {
            Intent intent = new Intent(ProductDetails.this, Categories.class);
            startActivity(intent);
        } else if (id == R.id.nav_wishlist) {
            Intent intent = new Intent(ProductDetails.this, WishlistDetails.class);
            startActivity(intent);
        } else if (id == R.id.nav_orders) {
            Intent intent = new Intent(ProductDetails.this, OrderHistory.class);
            startActivity(intent);
        } else if (id == R.id.nav_account) {
            Intent intent = new Intent(ProductDetails.this, Account.class);
            startActivity(intent);
        } else if (id == R.id.nav_logout) {
            Intent intent = new Intent(ProductDetails.this, SignInActivity.class);
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
            AppUtility.updateCartCount(ProductDetails.this, this.menu);
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
            etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                    if (i == EditorInfo.IME_ACTION_SEARCH) {
                        Log.e(TAG, "Search is " + etSearch.getText().toString());

                        // Start Intent
                    }

                    return false;
                }
            });
        } else if (id == R.id.cart) {
            Intent intent = new Intent(ProductDetails.this, CartDetails.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (menu != null) {
            AppUtility.updateCartCount(ProductDetails.this, menu);
        }
    }

    public class CartListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(ProductDetails.this, CartDetails.class);
            startActivity(intent);
        }
    }

    public class WishlistListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(ProductDetails.this, WishlistDetails.class);
            startActivity(intent);
        }
    }
}
