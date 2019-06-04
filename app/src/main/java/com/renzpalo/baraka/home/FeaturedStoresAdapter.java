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
        import android.widget.TextView;

        import com.bumptech.glide.Glide;
        import com.renzpalo.baraka.R;

        import java.util.List;

public class FeaturedStoresAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private String TAG = "FeaturedStoresAdapter";

    private int mScreenWidth;

    private Context mContext;

    private List<FeaturedStoresModel> mListNewProduct;

    public FeaturedStoresAdapter(Context context, List<FeaturedStoresModel> list, int screenWidth) {
        mContext = context;
        mListNewProduct = list;
        mScreenWidth = screenWidth;
    }

    private class NewProductHolder extends RecyclerView.ViewHolder {
        ImageView ivNewProductImage;

        TextView tvNewProductName;

        CardView cvNewProduct;

        public NewProductHolder(View itemView) {
            super(itemView);

            ivNewProductImage = itemView.findViewById(R.id.ivNewProductImage);

            tvNewProductName = itemView.findViewById(R.id.tvNewProductName);

            cvNewProduct = itemView.findViewById(R.id.cvNewProduct);

            // Set the CardView width.
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(mScreenWidth - (mScreenWidth / 100 * 75), LinearLayout.LayoutParams.MATCH_PARENT);

            layoutParams.setMargins(10, 10, 10, 10);
            cvNewProduct.setLayoutParams(layoutParams);
            cvNewProduct.setPadding(5, 5, 5, 5);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_featured_stores, parent, false);

        Log.e(TAG, "View created.");
        return new NewProductHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final FeaturedStoresModel model = mListNewProduct.get(position);

        Log.e(TAG, "Assign value ");

        ((NewProductHolder)holder).tvNewProductName.setText(model.getProductName());

        // Get Image from PHP - Using Glider.
        Glide
                .with(mContext)
                .load(model.getProductImage())
                .into(((NewProductHolder) holder).ivNewProductImage);

        ((NewProductHolder)holder).cvNewProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, com.renzpalo.baraka.store.Store.class);
                intent.putExtra("storeId", model.getProductId());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mListNewProduct.size();
    }
}