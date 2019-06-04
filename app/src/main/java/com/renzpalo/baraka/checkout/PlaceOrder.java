package com.renzpalo.baraka.checkout;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.renzpalo.baraka.R;
import com.renzpalo.baraka.Utility.AppUtility;
import com.renzpalo.baraka.Utility.Constant;
import com.renzpalo.baraka.Utility.NetworkUtility;
import com.renzpalo.baraka.Utility.SharedPreferenceUtility;
import com.renzpalo.baraka.WebServices.ServiceWrapper;
import com.renzpalo.baraka.account.Address;
import com.renzpalo.baraka.phpResponse.PlaceOrderPhp;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlaceOrder extends AppCompatActivity {

    private String TAG = "PlaceOrder";

    private String orderId = "";

    private String orderNo = "123456";
    private String totalAmount = "";
    private String orderShipFee = "99";
    private String orderStatus = "Pending";

    public String adId = "";
    private String paymentOption = "";

    private String userId = "";
    private String cartId = "";

    private Button bPlaceOrder;

    private Boolean onGoHome = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_placeorder);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeButtonEnabled(true);

        Intent intent = getIntent();

        totalAmount = intent.getExtras().getString("totalAmount");
        adId = intent.getExtras().getString("adId");
        paymentOption = intent.getExtras().getString("paymentOption");

        bPlaceOrder = findViewById(R.id.bPlaceOrder);

        cartId = SharedPreferenceUtility.getInstance().getString(Constant.QUOTE_ID);
        userId = SharedPreferenceUtility.getInstance().getString(Constant.USER_DATA);

        bPlaceOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!adId.equalsIgnoreCase("")) {
                    placeOrder(totalAmount, orderShipFee, paymentOption, orderStatus, adId);
                } else {
                    AppUtility.displayAlertMessage(PlaceOrder.this, "Please select an address.");
                }
            }
        });
    }

    public void placeOrder(String orderTotal, String orderShipFee, String orderPayment, String orderStatus, String adId) {
        if (!NetworkUtility.isNetworkConnected(PlaceOrder.this)) {
            AppUtility.displayAlertMessage(PlaceOrder.this, getString(R.string.network_not_connected));
        } else {
            ServiceWrapper serviceWrapper = new ServiceWrapper(null);
            Call<PlaceOrderPhp> call = serviceWrapper.placeOrderPhpCall("1234", orderTotal, orderShipFee, orderPayment, orderStatus, userId, adId);

            call.enqueue(new Callback<PlaceOrderPhp>() {
                @Override
                public void onResponse(Call<PlaceOrderPhp> call, Response<PlaceOrderPhp> response) {
                    Log.i(TAG, " response: " + response.body().getInformation());

                    if (response.body() != null && response.isSuccessful()) {
                        if (response.body().getStatus() == 1) {
                            orderId = String.valueOf(response.body().getInformation().getOrderId());
                            onGoHome = true;

                            SharedPreferenceUtility.getInstance().saveString(Constant.QUOTE_ID, "");

                            if (!orderId.equalsIgnoreCase("")) {
                                // Intent
                                Intent intent = new Intent(PlaceOrder.this, OrderSuccess.class);
                                intent.putExtra("orderId", String.valueOf(orderId));
                                intent.putExtra("onGoHome", onGoHome);
                                startActivity(intent);
                            }
                        } else {
                            Log.e(TAG, "Failed to get address." + response.body().getMessage());
                            AppUtility.displayAlertMessage(PlaceOrder.this, "Order failed.");
                        }
                    }  else {
                        // AppUtility.displayMessage(HomeActivity.this, getString(R.string.failed_request));
                        // getFeaturedProducts();
                    }
                }

                @Override
                public void onFailure(Call<PlaceOrderPhp> call, Throwable t) {
                    Log.e(TAG, "failed " + t.toString());
                }
            });
        }
    }

}
