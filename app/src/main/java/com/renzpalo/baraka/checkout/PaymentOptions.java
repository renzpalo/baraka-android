package com.renzpalo.baraka.checkout;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.renzpalo.baraka.R;

public class PaymentOptions extends AppCompatActivity {

    private String TAG = "PlaceOrder";

    private Button bChoosePayment;

    private String totalAmount = "";
    private String paymentOption = "Cash On Delivery";
    private String adId = "";

    private RadioGroup rgPayOption;

    private RadioButton rbCOD;
    private RadioButton rbPayPal;

    private TextView tvPayTotalPrice;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_option);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeButtonEnabled(true);

        Intent intent = getIntent();

        totalAmount = intent.getExtras().getString("totalAmount");
        adId = intent.getExtras().getString("adId");

        bChoosePayment = findViewById(R.id.bChoosePayment);

        rgPayOption = findViewById(R.id.rgPayOption);

        rbCOD = findViewById(R.id.rbCOD);

        tvPayTotalPrice = findViewById(R.id.tvPayTotalPrice);

        tvPayTotalPrice.setText("PHP " + totalAmount);

        if (rbCOD.isChecked()) {
            paymentOption = "Cash on Delivery";
        }

        bChoosePayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PaymentOptions.this, PlaceOrder.class);

                intent.putExtra("totalAmount", String.valueOf(totalAmount));
                intent.putExtra("paymentOption", String.valueOf(paymentOption));
                intent.putExtra("adId", String.valueOf(adId));

                startActivity(intent);
            }
        });

    }
}
