package com.renzpalo.baraka.home;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.renzpalo.baraka.R;

import java.util.List;

public class FeaturedProductsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private String TAG = "FeaturedProductsAdapter";

    private int mScreenWidth;

    private Context mContext;

    private List<FeaturedProductsModel> mListFeaturedProducts;



    public FeaturedProductsAdapter(Context context, List<FeaturedProductsModel> list, int screenWidth) {
        mContext = context;
        mListFeaturedProducts = list;
        mScreenWidth = screenWidth;
    }

    private class FeaturedProductsHolder extends RecyclerView.ViewHolder {
        ImageView ivProductImage;

        TextView tvProductName, tvProductOldPrice, tvProductPrice;

        RatingBar rbProductRating;

        CardView cvProduct;

        public FeaturedProductsHolder(View itemView) {
            super(itemView);

            ivProductImage = itemView.findViewById(R.id.ivProdImage);

            tvProductName = itemView.findViewById(R.id.tvProdName);
            tvProductOldPrice = itemView.findViewById(R.id.tvProdOldPrice);
            tvProductPrice = itemView.findViewById(R.id.tvProdPrice);

            rbProductRating = itemView.findViewById(R.id.rbProdRating);

            cvProduct = itemView.findViewById(R.id.cvProduct);

            // Set the CardView width.
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(mScreenWidth - (mScreenWidth / 100 * 75), LinearLayout.LayoutParams.MATCH_PARENT);

            layoutParams.setMargins(10, 10, 10, 10);
            cvProduct.setLayoutParams(layoutParams);
            // cvProduct.setMinimumHeight(LinearLayout.LayoutParams.MATCH_PARENT);
            cvProduct.setPadding(5, 5, 5, 5);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_featured_products, parent, false);

        Log.e(TAG, "View created.");
        return new FeaturedProductsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final FeaturedProductsModel model = mListFeaturedProducts.get(position);

        Log.e(TAG, "Assign value ");

        ((FeaturedProductsHolder)holder).tvProductName.setText(model.getProductName());
        ((FeaturedProductsHolder)holder).tvProductOldPrice.setText(model.getProductOldPrice());
        ((FeaturedProductsHolder)holder).tvProductPrice.setText(model.getProductPrice());
        ((FeaturedProductsHolder)holder).rbProductRating.setRating(Float.valueOf(model.getProductRatings()));

        // Get Image from PHP - Using Glider.
        Glide
                .with(mContext)
                .load(model.getProductImage())
                .into(((FeaturedProductsHolder) holder).ivProductImage);

        ((FeaturedProductsHolder)holder).cvProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, com.renzpalo.baraka.productPreview.ProductDetails.class);
                intent.putExtra("prodId", model.getProductId());
                intent.putExtra("prodPrice", model.getProductPrice());

                mContext.startActivity(intent);

            }
        });




}

    @Override
    public int getItemCount() {
        return mListFeaturedProducts.size();
    }
}
