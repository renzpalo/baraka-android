package com.renzpalo.baraka.productPreview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatRatingBar;
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

import com.renzpalo.baraka.R;
import com.renzpalo.baraka.home.FeaturedProductsAdapter;
import com.renzpalo.baraka.home.FeaturedProductsModel;

import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter {

    private String TAG = "ReviewAdapter";

    private Context mContext;

    private List<ReviewModel> mListReviews;

    public ReviewAdapter(Context context, List<ReviewModel> list) {
        mContext = context;
        mListReviews = list;
    }

    public class  ReviewHolder extends RecyclerView.ViewHolder {
        TextView tvReviewSummary, tvReviewFeedback, tvReviewUserName, tvReviewDate;

        AppCompatRatingBar rbReviewRating;

        public ReviewHolder(View itemView) {
            super(itemView);

            tvReviewSummary = itemView.findViewById(R.id.tvReviewSummary);
            tvReviewFeedback = itemView.findViewById(R.id.tvReviewFeedback);
            tvReviewUserName = itemView.findViewById(R.id.tvReviewUserName);
            tvReviewDate = itemView.findViewById(R.id.tvReviewDate);

            rbReviewRating = itemView.findViewById(R.id.rbReviewRating);

        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_review, parent, false);
        Log.i(TAG, "View created.");

        return new ReviewAdapter.ReviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final ReviewModel model = mListReviews.get(position);

        Log.i(TAG, "Assign value");

        ((ReviewHolder)holder).tvReviewSummary.setText(model.getReviewSummary());
        ((ReviewHolder)holder).tvReviewFeedback.setText(model.getReviewFeedback());
        ((ReviewHolder)holder).tvReviewUserName.setText(model.getReviewUserName());
        ((ReviewHolder)holder).tvReviewDate.setText(model.getReviewDate());
        ((ReviewHolder)holder).rbReviewRating.setRating(Float.valueOf(model.getReviewRating()));
    }

    @Override
    public int getItemCount() {
        return mListReviews.size();
    }
}
