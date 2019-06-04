package com.renzpalo.baraka.provinces;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.renzpalo.baraka.R;
import com.renzpalo.baraka.cart.CartAdapter;

import java.util.List;

public class ProvincesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private String TAG = "ProvincesAdapter";

    private List<ProvincesModel> mListProvinces;

    private int mScreenWidth;

    private Context mContext;

    public ProvincesAdapter(Context context, List<ProvincesModel> listProvincesModel, int screenWidth) {
        this.mListProvinces = listProvincesModel;
        this.mContext = context;
        this.mScreenWidth = screenWidth;
    }

    public class  ProvincesHolder extends RecyclerView.ViewHolder {
        TextView tvProvinceName;

        ImageView ivProvinceImage;

        CardView cvProvince;


        public ProvincesHolder(View itemView) {
            super(itemView);

            tvProvinceName = itemView.findViewById(R.id.tvProvinceName);

            ivProvinceImage = itemView.findViewById(R.id.ivProvinceImage);

            cvProvince = itemView.findViewById(R.id.cvProvince);

            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(mScreenWidth - (mScreenWidth / 100 * 60), 250);

            layoutParams.setMargins(10, 10, 10, 10);
            cvProvince.setLayoutParams(layoutParams);

        }
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.provinces_item, parent, false);
        Log.i(TAG, "View created.");

        return new ProvincesAdapter.ProvincesHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final ProvincesModel model = mListProvinces.get(position);

        Log.i(TAG, "Assign value");

        ((ProvincesHolder) holder).tvProvinceName.setText(model.getProvName());

        Glide
                .with(mContext)
                .load(model.getProvImage())
                .into(((ProvincesHolder) holder).ivProvinceImage);

        ((ProvincesHolder) holder).tvProvinceName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, com.renzpalo.baraka.provinces.ProvinceProducts.class);
                intent.putExtra("provId", model.getProvId());
                intent.putExtra("provName", model.getProvName());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mListProvinces.size();
    }
}
