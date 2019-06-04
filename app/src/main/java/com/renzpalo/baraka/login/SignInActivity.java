package com.renzpalo.baraka.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.renzpalo.baraka.R;
import com.renzpalo.baraka.Utility.AppUtility;
import com.renzpalo.baraka.Utility.Constant;
import com.renzpalo.baraka.Utility.DataValidation;
import com.renzpalo.baraka.Utility.NetworkUtility;
import com.renzpalo.baraka.Utility.SharedPreferenceUtility;
import com.renzpalo.baraka.WebServices.ServiceWrapper;
import com.renzpalo.baraka.home.HomeActivity;
import com.renzpalo.baraka.phpResponse.SignInPhp;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignInActivity extends AppCompatActivity {

    private String TAG = "SignInActivity";

    private TextView tvSkipSignIn, tvForgotPassword, tvSignUp;
    private EditText etSignInPhoneNumber, etSignInPassword;
    private Button bSignIn;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        Log.e(TAG, "Showing SignInActivity");

        tvSkipSignIn = findViewById(R.id.tvSkipSignIn);
//        tvForgotPassword = findViewById(R.id.tvForgotPassword);
        tvSignUp = findViewById(R.id.tvSignUp);

        etSignInPhoneNumber = findViewById(R.id.etSignInPhoneNumber);
        etSignInPassword = findViewById(R.id.etSignInPassword);

        bSignIn = findViewById(R.id.bSignIn);

        tvSkipSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignInActivity.this, HomeActivity.class);
                startActivity(intent);

                // End the current activity.
                finish();
            }
        });

//        tvForgotPassword.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(SignInActivity.this, ForgotPasswordActivity.class);
//                startActivity(intent);
//            }
//        });

        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

        bSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if (DataValidation.isNotValidEmail(etSignInPhoneNumber.getText().toString())) {
//                    AppUtility.displayAlertMessage(SignInActivity.this, getString(R.string.email) + " " + getString(R.string.is_invalid));
//                } else
//                    if (DataValidation.isNotValidPassword(etSignInPassword.getText().toString())) {
//                    AppUtility.displayAlertMessage(SignInActivity.this, getString(R.string.password) + " " + getString(R.string.is_invalid));
//                } else {
//                    // Call Retrofit method.
//                    // Check network connection.
//                    // and Progress Dialog.
//
//                    sendSignInRequest();
//                }
                sendSignInRequest();
            }
        });


    }

    public void sendSignInRequest() {
        if (!NetworkUtility.isNetworkConnected(SignInActivity.this)) {
            AppUtility.displayAlertMessage(SignInActivity.this, getString(R.string.failed_request));
        } else {
            ServiceWrapper serviceWrapper = new ServiceWrapper(null);
            retrofit2.Call<SignInPhp> signInCall = serviceWrapper.signInPhpCall(
                    etSignInPhoneNumber.getText().toString(),
                    etSignInPassword.getText().toString()
            );
            signInCall.enqueue(new Callback<SignInPhp>() {
                @Override
                public void onResponse(Call<SignInPhp> call, Response<SignInPhp> response) {
                    Log.e(TAG, "Response: " + response.body().toString());
                    if (response.body() != null && response.isSuccessful()) {
                        // If successful.
                        if (response.body().getStatus() == 1) {
                            // Store the User data to SharedPreference.
                            SharedPreferenceUtility.getInstance().saveString(Constant.USER_DATA, response.body().getInformation().getUserId());
                            SharedPreferenceUtility.getInstance().saveString(Constant.USER_ID, response.body().getInformation().getUserId());
                            SharedPreferenceUtility.getInstance().saveString(Constant.USER_FULLNAME, response.body().getInformation().getUserFullname());
                            SharedPreferenceUtility.getInstance().saveString(Constant.USER_EMAIL, response.body().getInformation().getUserEmail());
                            SharedPreferenceUtility.getInstance().saveString(Constant.USER_PHONE, response.body().getInformation().getUserPhoneNo());

                            Intent intent = new Intent(SignInActivity.this, HomeActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            // AppUtility.displayAlertMessage(SignInActivity.this, response.body().getMessage());
                            AppUtility.displayAlertMessage(SignInActivity.this, getString(R.string.invalid_credentials));
                        }
                    } else {
                        AppUtility.displayAlertMessage(SignInActivity.this, getString(R.string.failed_request));
                    }
                }

                @Override
                public void onFailure(Call<SignInPhp> call, Throwable t) {
                    Log.e(TAG, "Failed " + t.toString());
                    AppUtility.displayAlertMessage(SignInActivity.this, getString(R.string.failed_request));
                }
            });
        }
    }
}
