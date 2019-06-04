package com.renzpalo.baraka.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.renzpalo.baraka.R;

public class ForgotPasswordActivity extends AppCompatActivity {

    private String TAG = "ForgotPasswordActivity";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        Log.e(TAG, "Showing ForgotPasswordActivity");
    }
}
