package com.renzpalo.baraka.checkout;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.renzpalo.baraka.R;
import com.renzpalo.baraka.Utility.AppUtility;
import com.renzpalo.baraka.Utility.Constant;
import com.renzpalo.baraka.Utility.NetworkUtility;
import com.renzpalo.baraka.Utility.SharedPreferenceUtility;
import com.renzpalo.baraka.WebServices.ServiceWrapper;
import com.renzpalo.baraka.account.Address;
import com.renzpalo.baraka.phpResponse.GetOrderSummaryPhp;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Checkout extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private String TAG = "Checkout";

    public static Menu menu;

    private TextView tvCheckOutSubTotal, tvCheckOutShipFee, tvCheckOutOrderTotal;

    private Button bCheckoutContinue;

    private RecyclerView rvCheckoutItems;

    private CheckoutAdapter checkoutAdapter;
    private ArrayList<CheckoutModel> listCheckoutModel = new ArrayList<>();

    private String userId = "";
    private String cartId = "";

    private String php = "PHP ";

    private Float totalAmount;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeButtonEnabled(true);

        tvCheckOutSubTotal = findViewById(R.id.tvCheckOutSubTotal);
        tvCheckOutShipFee = findViewById(R.id.tvCheckOutShipFee);
        tvCheckOutOrderTotal = findViewById(R.id.tvCheckOutOrderTotal);

        bCheckoutContinue = findViewById(R.id.bCheckoutContinue);

        rvCheckoutItems = findViewById(R.id.rvCheckoutItems);

        cartId = SharedPreferenceUtility.getInstance().getString(Constant.QUOTE_ID);
        userId = SharedPreferenceUtility.getInstance().getString(Constant.USER_DATA);

        LinearLayoutManager mLayoutManager;

        mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        rvCheckoutItems.setLayoutManager(mLayoutManager);
        rvCheckoutItems.setItemAnimator(new DefaultItemAnimator());

        checkoutAdapter = new CheckoutAdapter(this, listCheckoutModel);

        rvCheckoutItems.setAdapter(checkoutAdapter);

        getCartDetails();

        bCheckoutContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    if (totalAmount > 0) {
                        Intent intent = new Intent(Checkout.this, Address.class);
                        intent.putExtra("totalAmount", String.valueOf(totalAmount));
                        startActivity(intent);
                    }
                } catch (Exception e) {
                    Log.e(TAG, e.toString());
                }


            }
        });


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

    public void getCartDetails() {
        if (!NetworkUtility.isNetworkConnected(Checkout.this)) {
            AppUtility.displayAlertMessage(Checkout.this, getString(R.string.network_not_connected));
        } else {
            ServiceWrapper serviceWrapper = new ServiceWrapper(null);
            Call<GetOrderSummaryPhp> call = serviceWrapper.getOrderSummaryPhpCall("1234", userId);

            call.enqueue(new Callback<GetOrderSummaryPhp>() {
                @Override
                public void onResponse(Call<GetOrderSummaryPhp> call, Response<GetOrderSummaryPhp> response) {
                    Log.i(TAG, " response: " + response.body().getInformation().toString());

                    if (response.body() != null && response.isSuccessful()) {
                        if (response.body().getStatus() == 1) {

                            try {
                                totalAmount = Float.valueOf(response.body().getInformation().getOrderTotal());
                            } catch (Exception e) {
                                Log.e(TAG, e.toString());
                            }

                            tvCheckOutSubTotal.setText("PHP " + String.valueOf(response.body().getInformation().getSubtotal()));
                            tvCheckOutShipFee.setText("PHP " + String.valueOf(response.body().getInformation().getShippingFee()));
                            tvCheckOutOrderTotal.setText("PHP " + String.valueOf(response.body().getInformation().getOrderTotal()));

                            listCheckoutModel.clear();
                            for (int i = 0; i < response.body().getInformation().getProdDetails().size(); i++) {
                                listCheckoutModel.add(new CheckoutModel(
                                        response.body().getInformation().getProdDetails().get(i).getProdId(),
                                        response.body().getInformation().getProdDetails().get(i).getProdName(),
                                        response.body().getInformation().getProdDetails().get(i).getProdPrice(),
                                        response.body().getInformation().getProdDetails().get(i).getProdImage(),
                                        String.valueOf(response.body().getInformation().getProdDetails().get(i).getProdQuantity())
                                ));
                            }
                            checkoutAdapter.notifyDataSetChanged();


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
                public void onFailure(Call<GetOrderSummaryPhp> call, Throwable t) {
                    Log.e(TAG, "failed " + t.toString());
                }
            });
        }
    }
}
