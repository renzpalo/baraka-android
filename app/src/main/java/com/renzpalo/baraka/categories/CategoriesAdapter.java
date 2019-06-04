package com.renzpalo.baraka.categories;

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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.renzpalo.baraka.R;
import com.renzpalo.baraka.provinces.ProvincesAdapter;
import com.renzpalo.baraka.provinces.ProvincesModel;

import java.util.List;

public class CategoriesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private String TAG = "CategoriesAdapter";

    private List<CategoriesModel> mListCategories;

    private int mScreenWidth;

    private Context mContext;

    public CategoriesAdapter(Context context, List<CategoriesModel> listCategoriesModel, int screenWidth) {
        this.mListCategories = listCategoriesModel;
        this.mContext = context;
        this.mScreenWidth = screenWidth;
    }

    public class  CategoriesHolder extends RecyclerView.ViewHolder {
        TextView tvCategoryName;

        ImageView ivCategoryImage;

        CardView cvCategory;


        public CategoriesHolder(View itemView) {
            super(itemView);

            tvCategoryName = itemView.findViewById(R.id.tvCategoryName);

            ivCategoryImage = itemView.findViewById(R.id.ivCategoryImage);

            cvCategory = itemView.findViewById(R.id.cvCategory);

            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(mScreenWidth - (mScreenWidth / 100 * 60), 250);

            layoutParams.setMargins(10, 10, 10, 10);
            cvCategory.setLayoutParams(layoutParams);

        }
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item, parent, false);
        Log.i(TAG, "View created.");

        return new CategoriesAdapter.CategoriesHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final CategoriesModel model = mListCategories.get(position);

        Log.i(TAG, "Assign value");

        ((CategoriesHolder) holder).tvCategoryName.setText(model.getCatName());

        Glide
                .with(mContext)
                .load(model.getCatImage())
                .into(((CategoriesHolder) holder).ivCategoryImage);

        ((CategoriesHolder) holder).tvCategoryName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, com.renzpalo.baraka.categories.CategoryProducts.class);
                intent.putExtra("catId", model.getCatId());
                intent.putExtra("catName", model.getCatName());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mListCategories.size();
    }





}
