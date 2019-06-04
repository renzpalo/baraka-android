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
import android.widget.Toast;

import com.renzpalo.baraka.R;
import com.renzpalo.baraka.Utility.AppUtility;
import com.renzpalo.baraka.Utility.Constant;
import com.renzpalo.baraka.Utility.DataValidation;
import com.renzpalo.baraka.Utility.NetworkUtility;
import com.renzpalo.baraka.Utility.SharedPreferenceUtility;
import com.renzpalo.baraka.WebServices.ServiceWrapper;
import com.renzpalo.baraka.phpResponse.SignUpPhp;

import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {

    private String TAG = "SignUpActivity";

    private EditText etSignUpName, etSignUpPhoneNumber, etSignUpEmail, etSignUpPassword, etSignUpConfirmPassword;
    private TextView tvSignIn;
    private Button bSignUp;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        Log.e(TAG, "Showing SignUpActivity");

        etSignUpName = findViewById(R.id.etSignUpName);
        etSignUpPhoneNumber = findViewById(R.id.etSignUpPhoneNumber);
        etSignUpEmail = findViewById(R.id.etSignUpEmail);
        etSignUpPassword = findViewById(R.id.etSignUpPassword);
        etSignUpConfirmPassword = findViewById(R.id.etSignUpConfirmPassword);

        tvSignIn = findViewById(R.id.tvSignIn);

        bSignUp = findViewById(R.id.bSignUp);

        tvSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
                startActivity(intent);
            }
        });

        bSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if (DataValidation.isNotValidFullName(etSignUpName.getText().toString())) {
//                    // Show error pop-up.
//                    AppUtility.displayAlertMessage(SignUpActivity.this, getString(R.string.full_name) + " " + getString(R.string.is_invalid));
//                } else if (DataValidation.isNotValidPhoneNumber(etSignUpPhoneNumber.getText().toString())) {
//                    AppUtility.displayAlertMessage(SignUpActivity.this, getString(R.string.phone_number) + " " + getString(R.string.is_invalid));
//                } else if (DataValidation.isNotValidEmail(etSignUpEmail.getText().toString())) {
//                    AppUtility.displayAlertMessage(SignUpActivity.this, getString(R.string.email) + " " + getString(R.string.is_invalid));
//                } else if (DataValidation.isNotValidPassword(etSignUpPassword.getText().toString())) {
//                    AppUtility.displayAlertMessage(SignUpActivity.this, getString(R.string.pass_should_contain));
//                } else if (DataValidation.isNotValidPassword(etSignUpConfirmPassword.getText().toString())) {
//                    AppUtility.displayAlertMessage(SignUpActivity.this, getString(R.string.confirm_password) + " " + getString(R.string.is_invalid));
//                } else if (!etSignUpPassword.getText().toString().equals(etSignUpConfirmPassword.getText().toString())) {
//                    AppUtility.displayAlertMessage(SignUpActivity.this, getString(R.string.password_not_matched));
//                } else {
//                    // Call Retrofit method.
//                    // Check network connection.
//                    // and Progress Dialog.
//
//                    // sendSignUpRequest();
//                }

                sendSignUpRequest();
            }
        });
    }

    public void sendSignUpRequest() {
        // If not connected, show a message
        if (!NetworkUtility.isNetworkConnected(SignUpActivity.this)) {
            AppUtility.displayAlertMessage(SignUpActivity.this, getString(R.string.failed_request));
        } else {
            ServiceWrapper serviceWrapper = new ServiceWrapper(null);
            retrofit2.Call<SignUpPhp> signUpCall =  serviceWrapper.signUpPhpCall(
                    etSignUpName.getText().toString(),
                    etSignUpPhoneNumber.getText().toString(),
                    etSignUpEmail.getText().toString(),
                    etSignUpPassword.getText().toString()
            );
            signUpCall.enqueue(new Callback<SignUpPhp>() {
                @Override
                public void onResponse(retrofit2.Call<SignUpPhp> call, Response<SignUpPhp> response) {
                    Log.e(TAG, "Response: " + response.body().toString());
                    if (response.body() != null && response.isSuccessful()) {
                        // If successful.
                        if (response.body().getStatus() == 1) {
                            // Store the User data to SharedPreference.
                            SharedPreferenceUtility.getInstance().saveString(Constant.USER_DATA, response.body().getInformation());
//
//                            Intent intent = new Intent(SignUpActivity.this, HomeActivity.class);
//                            startActivity(intent);
//                            finish();

                            Toast.makeText(getApplicationContext(), "Successfully registered.", Toast.LENGTH_LONG).show();

                            Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            AppUtility.displayAlertMessage(SignUpActivity.this, response.body().getMessage());
                        }
                    } else {
                        AppUtility.displayAlertMessage(SignUpActivity.this, getString(R.string.failed_request));
                    }
                }

                @Override
                public void onFailure(retrofit2.Call<SignUpPhp> call, Throwable t) {
                    Log.e(TAG, "Failed " + t.toString());
                    AppUtility.displayAlertMessage(SignUpActivity.this, getString(R.string.failed_request));
                }
            });
        }
    }
}
