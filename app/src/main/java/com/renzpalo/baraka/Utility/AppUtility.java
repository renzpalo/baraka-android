package com.renzpalo.baraka.Utility;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.renzpalo.baraka.R;

public class AppUtility {

    // Pop-up AlertDialog

    public AlertDialog alertDialog;

    public static void displayMessage(Context mContext, String message) {

        MessageDialog messageDialog = null;

        if (messageDialog == null) {
            messageDialog = new MessageDialog(mContext, message);
            messageDialog.showDisplayMessage();
        }
    }

    public static void displayAlertMessage(Context mContext, String message) {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(mContext, android.R.style.Theme_DeviceDefault_Light_Dialog);
        } else {
            builder = new AlertDialog.Builder(mContext);
        }
        builder.setTitle("Alert")
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                // .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    public static void updateCartCount(final Context mContext, Menu menu) {
        MenuItem menuItem = menu.findItem(R.id.cart);

        TextView tvCartCount = menuItem.getActionView().findViewById(R.id.tvCartCount);

        tvCartCount.setText(String.valueOf(SharedPreferenceUtility.getInstance().getInt(Constant.CART_ITEM_COUNT)));

        if (SharedPreferenceUtility.getInstance().getInt(Constant.CART_ITEM_COUNT) > 0) {
            tvCartCount.setVisibility(View.GONE);
        } else {
            // Dont display item count.
            tvCartCount.setVisibility(View.GONE);
        }

        menuItem.getActionView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("AppUtility", "Cart clicked.");

                Intent intent = new Intent(mContext, com.renzpalo.baraka.cart.CartDetails.class);
                mContext.startActivity(intent);
            }
        });
    }





}
