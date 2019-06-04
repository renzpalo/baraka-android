package com.renzpalo.baraka.productPreview;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.renzpalo.baraka.R;

public class Overview extends Fragment {

    private Context mContext;

    private TextView tvProductOverview;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_product_overview, container, false);

        mContext = view.getContext();

        tvProductOverview = view.findViewById(R.id.tvProductOverview);
        tvProductOverview.setText(((ProductDetails) getActivity()).productOverview);

        return view;
    }
}
