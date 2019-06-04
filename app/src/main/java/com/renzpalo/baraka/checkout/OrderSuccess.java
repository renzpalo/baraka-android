package com.renzpalo.baraka.checkout;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.renzpalo.baraka.R;
import com.renzpalo.baraka.home.HomeActivity;

public class OrderSuccess extends AppCompatActivity {

    private String TAG = "PlaceOrder";

    private String totalAmount = "";
    private String orderId = "";

    private TextView tvOrderId;

    private Button bContinueShopping;

    private Boolean onGoHome = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_success);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeButtonEnabled(true);

        Intent intent = getIntent();

        totalAmount = intent.getExtras().getString("totalAmount");
        orderId = intent.getExtras().getString("orderId");
        onGoHome = intent.getExtras().getBoolean("onGoHome");

        tvOrderId = findViewById(R.id.tvOrderId);

        bContinueShopping = findViewById(R.id.bContinueShopping);

        tvOrderId.setText(orderId);

        bContinueShopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OrderSuccess.this, HomeActivity.class);

                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

                startActivity(intent);

                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if (onGoHome) {
            Intent intent = new Intent(OrderSuccess.this, HomeActivity.class);

            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

            startActivity(intent);

            finish();
        }
    }

}
