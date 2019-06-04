package com.renzpalo.baraka.Utility;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.renzpalo.baraka.R;

public class MessageDialog {

    public AlertDialog alertDialog;

    public MessageDialog(Context mContext, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

        View view;

        // Create a layout
        LayoutInflater inflater = LayoutInflater.from(mContext);
        view = inflater.inflate(R.layout.display_message, null);

        TextView tvPopupMessage = (TextView) view.findViewById(R.id.tvPopupMessage);
        TextView tvPopupButton = (TextView) view.findViewById(R.id.tvPopupButton);

        tvPopupMessage.setText(message);

        builder.setView(view);

        alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        tvPopupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
    }

    public void showDisplayMessage() {
        if (alertDialog != null) {
            alertDialog.show();
        }
    }

    public void hideDisplayMessage() {
        if (alertDialog != null) {
            alertDialog.dismiss();
        }
    }
}
