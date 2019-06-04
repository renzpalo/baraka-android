package com.renzpalo.baraka.account;

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
import com.renzpalo.baraka.checkout.Checkout;
import com.renzpalo.baraka.checkout.CheckoutAdapter;
import com.renzpalo.baraka.checkout.CheckoutModel;
import com.renzpalo.baraka.home.HomeActivity;
import com.renzpalo.baraka.phpResponse.GetOrderHistoryDetailsPhp;
import com.renzpalo.baraka.phpResponse.GetOrderSummaryPhp;
import com.renzpalo.baraka.phpResponse.GetOrderUpdatesPhp;
import com.renzpalo.baraka.wishlist.WishlistDetails;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderHistoryDetails extends AppCompatActivity {

    private String TAG = "OrderHistoryDetails";

    private NavigationView navigationView;
    private DrawerLayout drawerLayout;

    public static Menu menu;

    private String orderId = "";
    private String orderAddress = "";
    private String orderFullname = "";
    private String orderContact = "";
    private String userId = "";

    private RecyclerView rvOrderHistoryItems, rvOrderStatusItems;

    private TextView tvOrderHistorySubTotal, tvOrderHistoryShipFee, tvOrderHistoryOrderTotal, tvOrderDetFullname, tvOrderDetContact, tvOrderDetAddress;

    private OrderHistoryDetailsAdapter orderHistoryDetailsAdapter;
    private ArrayList<OrderHistoryDetailsModel> listOrderHistoryDetailsModel = new ArrayList<>();

    private OrderStatusAdapter orderStatusAdapter;
    private ArrayList<OrderStatusModel> listOrderStatusModel = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history_details);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        orderId = intent.getExtras().getString("orderId");
        orderAddress = intent.getExtras().getString("orderAddress");
        orderFullname = intent.getExtras().getString("orderFullname");
        orderContact = intent.getExtras().getString("orderContact");

        userId = SharedPreferenceUtility.getInstance().getString(Constant.USER_DATA);

        rvOrderHistoryItems = findViewById(R.id.rvOrderHistoryItems);
        rvOrderStatusItems = findViewById(R.id.rvOrderStatusItems);

        tvOrderHistorySubTotal = findViewById(R.id.tvOrderHistorySubTotal);
        tvOrderHistoryShipFee = findViewById(R.id.tvOrderHistoryShipFee);
        tvOrderHistoryOrderTotal = findViewById(R.id.tvOrderHistoryOrderTotal);
        tvOrderDetFullname = findViewById(R.id.tvOrderDetFullname);
        tvOrderDetContact = findViewById(R.id.tvOrderDetContact);
        tvOrderDetAddress = findViewById(R.id.tvOrderDetAddress);

        LinearLayoutManager mLayoutManager;

        mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);

        rvOrderHistoryItems.setLayoutManager(mLayoutManager);
        rvOrderHistoryItems.setItemAnimator(new DefaultItemAnimator());

        orderHistoryDetailsAdapter = new OrderHistoryDetailsAdapter(this, listOrderHistoryDetailsModel);

        rvOrderHistoryItems.setAdapter(orderHistoryDetailsAdapter);

        LinearLayoutManager mLayoutManager2;

        mLayoutManager2 = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);

        rvOrderStatusItems.setLayoutManager(mLayoutManager2);
        rvOrderStatusItems.setItemAnimator(new DefaultItemAnimator());

        orderStatusAdapter = new OrderStatusAdapter(this, listOrderStatusModel);

        rvOrderStatusItems.setAdapter(orderStatusAdapter);

        tvOrderDetAddress.setText(orderAddress);
        tvOrderDetFullname.setText(orderFullname);
        tvOrderDetContact.setText(orderContact);

        getOrderHistoryDetails();
        getOrderStatus();

    }

    public void getOrderHistoryDetails() {
        if (!NetworkUtility.isNetworkConnected(OrderHistoryDetails.this)) {
            AppUtility.displayAlertMessage(OrderHistoryDetails.this, getString(R.string.network_not_connected));
        } else {
            ServiceWrapper serviceWrapper = new ServiceWrapper(null);
            Call<GetOrderHistoryDetailsPhp> call = serviceWrapper.getOrderHistoryDetailsPhpCall("1234", userId, orderId);

            call.enqueue(new Callback<GetOrderHistoryDetailsPhp>() {
                @Override
                public void onResponse(Call<GetOrderHistoryDetailsPhp> call, Response<GetOrderHistoryDetailsPhp> response) {
                    Log.i(TAG, " response: " + response.body().getInformation().toString());

                    if (response.body() != null && response.isSuccessful()) {
                        if (response.body().getStatus() == 1) {

//                            try {
//                                totalAmount = Float.valueOf(response.body().getInformation().getOrderTotal());
//                            } catch (Exception e) {
//                                Log.e(TAG, e.toString());
//                            }

                            tvOrderHistorySubTotal.setText("PHP " + String.valueOf(response.body().getInformation().getSubtotal()));
                            tvOrderHistoryShipFee.setText("PHP " + String.valueOf(response.body().getInformation().getShippingFee()));
                            tvOrderHistoryOrderTotal.setText("PHP " + String.valueOf(response.body().getInformation().getOrderTotal()));

                            listOrderHistoryDetailsModel.clear();
                            for (int i = 0; i < response.body().getInformation().getProdDetails().size(); i++) {
                                listOrderHistoryDetailsModel.add(new OrderHistoryDetailsModel(
                                        response.body().getInformation().getProdDetails().get(i).getProdId(),
                                        response.body().getInformation().getProdDetails().get(i).getProdName(),
                                        response.body().getInformation().getProdDetails().get(i).getProdPrice(),
                                        response.body().getInformation().getProdDetails().get(i).getProdImage(),
                                        String.valueOf(response.body().getInformation().getProdDetails().get(i).getProdQuantity())
                                ));
                            }
                            orderHistoryDetailsAdapter.notifyDataSetChanged();


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
                public void onFailure(Call<GetOrderHistoryDetailsPhp> call, Throwable t) {
                    Log.e(TAG, "failed " + t.toString());
                }
            });
        }
    }

    public void getOrderStatus() {
        if (!NetworkUtility.isNetworkConnected(OrderHistoryDetails.this)) {
            AppUtility.displayAlertMessage(OrderHistoryDetails.this, getString(R.string.network_not_connected));
        } else {
            ServiceWrapper serviceWrapper = new ServiceWrapper(null);
            Call<GetOrderUpdatesPhp> call = serviceWrapper.getOrderUpdatesPhpCall("1234", orderId);

            call.enqueue(new Callback<GetOrderUpdatesPhp>() {
                @Override
                public void onResponse(Call<GetOrderUpdatesPhp> call, Response<GetOrderUpdatesPhp> response) {
                    Log.i(TAG, " response: " + response.body().getInformation().toString());

                    if (response.body() != null && response.isSuccessful()) {
                        if (response.body().getStatus() == 1) {

                            listOrderStatusModel.clear();
                            for (int i = 0; i < response.body().getInformation().getOrderStatus().size(); i++) {
                                listOrderStatusModel.add(new OrderStatusModel(
                                        response.body().getInformation().getOrderStatus().get(i).getOrderUpdateId(),
                                        response.body().getInformation().getOrderStatus().get(i).getOrderUpdateInfo(),
                                        response.body().getInformation().getOrderStatus().get(i).getOrderUpdateDate()
                                ));
                            }
                            orderStatusAdapter.notifyDataSetChanged();


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
                public void onFailure(Call<GetOrderUpdatesPhp> call, Throwable t) {
                    Log.e(TAG, "failed " + t.toString());
                }
            });
        }
    }

}
