package com.renzpalo.baraka.searchProducts;

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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.renzpalo.baraka.R;
import com.renzpalo.baraka.home.FeaturedProductsAdapter;
import com.renzpalo.baraka.home.FeaturedProductsModel;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private String TAG = "ProductAdapter";

    private int mScreenWidth;

    private Context mContext;

    private List<ProductModel> mListProduct;

    public ProductAdapter(Context context, List<ProductModel> list, int screenWidth) {
        mContext = context;
        mListProduct = list;
        mScreenWidth = screenWidth;
    }

    private class ProductHolder extends RecyclerView.ViewHolder {
        ImageView ivSearchProductImage;

        TextView tvSearchProductName, tvSearchProductPrice, tvSearchProductProvince;

        RatingBar rbSearchProductRating;

        CardView cvSearchProduct;

        public ProductHolder(View itemView) {
            super(itemView);

            ivSearchProductImage = itemView.findViewById(R.id.ivSearchProductImage);

            tvSearchProductName = itemView.findViewById(R.id.tvSearchProductName);
            tvSearchProductPrice = itemView.findViewById(R.id.tvSearchProductPrice);
            tvSearchProductProvince = itemView.findViewById(R.id.tvSearchProductProvince);

            rbSearchProductRating = itemView.findViewById(R.id.rbSearchProductRating);

            cvSearchProduct = itemView.findViewById(R.id.cvSearchProduct);

            // Set the CardView width.
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(mScreenWidth - (mScreenWidth / 100 * 60), LinearLayout.LayoutParams.WRAP_CONTENT);

            layoutParams.setMargins(10, 10, 10, 10);

            cvSearchProduct.setLayoutParams(layoutParams);
            cvSearchProduct.setPadding(10, 10, 10, 10);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_product_item, parent, false);

        Log.e(TAG, "View created.");
        return new ProductAdapter.ProductHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final ProductModel model = mListProduct.get(position);

        Log.e(TAG, "Assign value ");

        ((ProductHolder)holder).tvSearchProductName.setText(model.getProdName());
        ((ProductHolder)holder).tvSearchProductPrice.setText(model.getProdPrice());
        ((ProductHolder)holder).tvSearchProductProvince.setText(model.getProdProvince());
        ((ProductHolder)holder).rbSearchProductRating.setRating(Float.valueOf(model.getProdRating()));

        // Get Image from PHP - Using Glider.
        Glide
                .with(mContext)
                .load(model.getProdImage())
                .into(((ProductHolder) holder).ivSearchProductImage);

        ((ProductHolder)holder).cvSearchProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, com.renzpalo.baraka.productPreview.ProductDetails.class);
                intent.putExtra("prodId", model.getProdId());
                intent.putExtra("prodPrice", model.getProdPrice());

                mContext.startActivity(intent);

            }
        });




    }

    @Override
    public int getItemCount() {
        return mListProduct.size();
    }
}
