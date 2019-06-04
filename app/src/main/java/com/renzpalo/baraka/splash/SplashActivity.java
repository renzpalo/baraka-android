package com.renzpalo.baraka.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.renzpalo.baraka.R;
import com.renzpalo.baraka.Utility.Constant;
import com.renzpalo.baraka.Utility.SharedPreferenceUtility;
import com.renzpalo.baraka.home.HomeActivity;
import com.renzpalo.baraka.login.SignInActivity;

public class SplashActivity extends AppCompatActivity {

    // Create a 5 second counter
    // Display a screen

    private String TAG = "SplashActivity";

    private String userId;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        userId = SharedPreferenceUtility.getInstance().getString(Constant.USER_DATA);

        init();

        Log.e(TAG, "Showing SplashActivity " + userId + " User ID");
    }

    // 5 second delay.
    public void init() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.e(TAG, "Counter running.");
                // If the user is registered, direct to the MainActivity.
                // If not go to SignUpActivity
                // key registered_user
                if (userId == "" || userId == null) {
                    // Not registered, do not show log in screen.
                    Intent intent = new Intent(SplashActivity.this, SignInActivity.class);
                    startActivity(intent);
                } else {
                    // Direct to HomeActivity
                    Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
                    startActivity(intent);
                }
                finish();
            }
        }, 3000);
    }
}
