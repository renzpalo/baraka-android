package com.renzpalo.baraka.account;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.renzpalo.baraka.R;
import com.renzpalo.baraka.Utility.AppUtility;
import com.renzpalo.baraka.Utility.Constant;
import com.renzpalo.baraka.Utility.NetworkUtility;
import com.renzpalo.baraka.Utility.SharedPreferenceUtility;
import com.renzpalo.baraka.WebServices.ServiceWrapper;
import com.renzpalo.baraka.cart.CartDetails;
import com.renzpalo.baraka.cart.CartModel;
import com.renzpalo.baraka.phpResponse.AddAddressPhp;
import com.renzpalo.baraka.phpResponse.GetCartDetailsPhp;
import com.renzpalo.baraka.productPreview.ProductDetails;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddAddress extends AppCompatActivity {

    private String TAG = "Address";

    public static Menu menu;

    private String adFullname, adContact, adStreet, adBarangay, adCityMuni, adProvince, adZipcode, adNotes;

    private EditText etAdFullname, etAdPContactNumber, etAdStreet, etAdBarangay, etAdCityMuni, etAdProvince, etAdZipCode, etAdNotes;

    private Button bSaveAddress;

    private CheckBox cbBillAddress, cbShipAddress;

    private String userId;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeButtonEnabled(true);

        userId = SharedPreferenceUtility.getInstance().getString(Constant.USER_DATA);

        etAdFullname = findViewById(R.id.etAdFullname);
        etAdPContactNumber = findViewById(R.id.etAdPContactNumber);
        etAdStreet = findViewById(R.id.etAdStreet);
        etAdBarangay = findViewById(R.id.etAdBarangay);
        etAdCityMuni = findViewById(R.id.etAdCityMuni);
        etAdProvince = findViewById(R.id.etAdProvince);
        etAdZipCode = findViewById(R.id.etAdZipCode);
        etAdNotes = findViewById(R.id.etAdNotes);

        bSaveAddress = findViewById(R.id.bSaveAddress);

        cbBillAddress = findViewById(R.id.cbBillAddress);
        cbShipAddress = findViewById(R.id.cbShipAddress);

        bSaveAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adFullname = etAdFullname.getText().toString();
                adContact = etAdPContactNumber.getText().toString();
                adStreet = etAdStreet.getText().toString();
                adBarangay = etAdBarangay.getText().toString();
                adCityMuni = etAdCityMuni.getText().toString();
                adProvince = etAdProvince.getText().toString();
                adZipcode = etAdZipCode.getText().toString();
                adNotes = etAdNotes.getText().toString();

                // Validate

                addAddress(adFullname, adContact, adStreet, adBarangay, adCityMuni, adProvince, adZipcode, adNotes, userId);
            }
        });
    }

    public void addAddress(String adFullname, String adContact, String adStreet,
                           String adBarangay, String adCityMuni, String adProvince,
                           String adZipcode, String adNotes, String userId) {

        if (!NetworkUtility.isNetworkConnected(AddAddress.this)) {
            AppUtility.displayAlertMessage(AddAddress.this, getString(R.string.network_not_connected));
        } else {
            ServiceWrapper serviceWrapper = new ServiceWrapper(null);
            Call<AddAddressPhp> call = serviceWrapper.addAddressPhpCall("1234", adFullname, adContact, adStreet,
                    adBarangay, adCityMuni, adProvince, adZipcode, adNotes, userId);

            call.enqueue(new Callback<AddAddressPhp>() {
                @Override
                public void onResponse(Call<AddAddressPhp> call, Response<AddAddressPhp> response) {
                    Log.i(TAG, " response: " + response.body().getInformation());

                    if (response.body() != null && response.isSuccessful()) {
                        if (response.body().getStatus() == 1) {
                            Toast.makeText(AddAddress.this, "Address saved.", Toast.LENGTH_LONG).show();

                            finish();
                        } else {
                            Log.e(TAG, "Failed to add address." + response.body().getMessage());
                            // AppUtility.displayMessage(HomeActivity.this, response.body().getMessage());
                        }
                    }  else {
                        // AppUtility.displayMessage(HomeActivity.this, getString(R.string.failed_request));
                        // getFeaturedProducts();
                    }
                }

                @Override
                public void onFailure(Call<AddAddressPhp> call, Throwable t) {
                    Log.e(TAG, "failed " + t.toString());
                }
            });
        }

    }


}
