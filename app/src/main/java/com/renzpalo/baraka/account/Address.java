package com.renzpalo.baraka.account;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.renzpalo.baraka.R;
import com.renzpalo.baraka.Utility.AppUtility;
import com.renzpalo.baraka.Utility.Constant;
import com.renzpalo.baraka.Utility.NetworkUtility;
import com.renzpalo.baraka.Utility.SharedPreferenceUtility;
import com.renzpalo.baraka.WebServices.ServiceWrapper;
import com.renzpalo.baraka.cart.CartAdapter;
import com.renzpalo.baraka.checkout.Checkout;
import com.renzpalo.baraka.checkout.PaymentOptions;
import com.renzpalo.baraka.checkout.PlaceOrder;
import com.renzpalo.baraka.phpResponse.AddAddressPhp;
import com.renzpalo.baraka.phpResponse.GetAddressDetailsPhp;
import com.renzpalo.baraka.phpResponse.PlaceOrderPhp;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Address extends AppCompatActivity {

    private String TAG = "Address";

    public static Menu menu;

    private RecyclerView rvAddressItems;

    private Button bAddress;

    private FloatingActionButton fabAddAddress;

    private String userId, cartId;

    private AddressAdapter addressAdapter;
    private ArrayList<AddressModel> listAddressModel = new ArrayList<>();

    private String totalAmount = "";

    public String adId = "";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeButtonEnabled(true);

        Intent intent = getIntent();

        totalAmount = intent.getExtras().getString("totalAmount");

        userId = SharedPreferenceUtility.getInstance().getString(Constant.USER_DATA);
        cartId = SharedPreferenceUtility.getInstance().getString(Constant.QUOTE_ID);

        bAddress = findViewById(R.id.bAddress);

        fabAddAddress = findViewById(R.id.fabAddAddress);

        rvAddressItems = findViewById(R.id.rvAddressItems);

        LinearLayoutManager mLayoutManager;

        mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        rvAddressItems.setLayoutManager(mLayoutManager);
        rvAddressItems.setItemAnimator(new DefaultItemAnimator());

        addressAdapter = new AddressAdapter(this, listAddressModel);

        rvAddressItems.setAdapter(addressAdapter);

        bAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Confirm order.
                if (!adId.equalsIgnoreCase("")) {
                    Intent intent = new Intent(Address.this, PaymentOptions.class);
                    intent.putExtra("totalAmount", String.valueOf(totalAmount));
                    intent.putExtra("adId", String.valueOf(adId));
                    startActivity(intent);
                } else {
                    AppUtility.displayAlertMessage(Address.this, "Please select an address.");
                }
            }
        });

        fabAddAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Address.this, AddAddress.class);

                startActivity(intent);
            }
        });

        getUserAddress();


    }

    @Override
    protected void onResume() {
        super.onResume();

        getUserAddress();
    }

    public void getUserAddress() {
        if (!NetworkUtility.isNetworkConnected(Address.this)) {
            AppUtility.displayAlertMessage(Address.this, getString(R.string.network_not_connected));
        } else {
            ServiceWrapper serviceWrapper = new ServiceWrapper(null);
            Call<GetAddressDetailsPhp> call = serviceWrapper.getAddressDetailsPhpCall("1234", userId);

            call.enqueue(new Callback<GetAddressDetailsPhp>() {
                @Override
                public void onResponse(Call<GetAddressDetailsPhp> call, Response<GetAddressDetailsPhp> response) {
                    Log.i(TAG, " response: " + response.body().getInformation());

                    if (response.body() != null && response.isSuccessful()) {
                        if (response.body().getStatus() == 1) {
                            listAddressModel.clear();
                            if (response.body().getInformation().size() > 0) {
                                for (int i = 0; i < response.body().getInformation().size(); i++) {
                                    listAddressModel.add(new AddressModel(
                                            response.body().getInformation().get(i).getAdId(),
                                            response.body().getInformation().get(i).getAdFulname(),
                                            response.body().getInformation().get(i).getAdContact(),
                                            response.body().getInformation().get(i).getAdStreet(),
                                            response.body().getInformation().get(i).getAdBarangay(),
                                            response.body().getInformation().get(i).getAdCityMuni(),
                                            response.body().getInformation().get(i).getAdProvince(),
                                            response.body().getInformation().get(i).getAdZipcode(),
                                            response.body().getInformation().get(i).getAdNotes()
                                    ));
                                }
                                addressAdapter.notifyDataSetChanged();
                            }
                        } else {
                            Log.e(TAG, "Failed to get address." + response.body().getMessage());
                            // AppUtility.displayMessage(HomeActivity.this, response.body().getMessage());
                        }
                    }  else {
                        // AppUtility.displayMessage(HomeActivity.this, getString(R.string.failed_request));
                        // getFeaturedProducts();
                    }
                }

                @Override
                public void onFailure(Call<GetAddressDetailsPhp> call, Throwable t) {
                    Log.e(TAG, "failed " + t.toString());
                }
            });
        }
    }
}
