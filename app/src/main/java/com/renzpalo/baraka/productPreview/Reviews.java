package com.renzpalo.baraka.productPreview;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.renzpalo.baraka.R;
import com.renzpalo.baraka.Utility.NetworkUtility;
import com.renzpalo.baraka.WebServices.ServiceWrapper;
import com.renzpalo.baraka.home.FeaturedProductsModel;
import com.renzpalo.baraka.home.HomeActivity;
import com.renzpalo.baraka.phpResponse.FeaturedProductsPhp;
import com.renzpalo.baraka.phpResponse.ProductDetailsPhp;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Reviews extends Fragment {

    private String TAG = "ReviewFragment";

    private Context mContext;

    private RecyclerView rvReviews;

    private ArrayList<ReviewModel> listReviewModel = new ArrayList<>();

    private ReviewAdapter reviewAdapter;

    private String prodId = "";



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_product_reviews, container, false);
        mContext = view.getContext();

        rvReviews = view.findViewById(R.id.rvReviews);

        prodId = ((ProductDetails) getActivity()).prodId;

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        rvReviews.setLayoutManager(mLayoutManager);
        rvReviews.setItemAnimator(new DefaultItemAnimator());

        reviewAdapter = new ReviewAdapter(mContext, listReviewModel);
        rvReviews.setAdapter(reviewAdapter);

        getReviews();

        return view;
    }

    public void getReviews() {
        if (!NetworkUtility.isNetworkConnected(mContext)) {

        } else {
            ServiceWrapper serviceWrapper = new ServiceWrapper(null);
            Call<ProductDetailsPhp> call = serviceWrapper.productDetailsPhpCall("1234", prodId);

            call.enqueue(new Callback<ProductDetailsPhp>() {
                @Override
                public void onResponse(Call<ProductDetailsPhp> call, Response<ProductDetailsPhp> response) {
                    Log.i(TAG, " response: " + response.body().getInformation().toString());

                    if (response.body() != null && response.isSuccessful()) {
                        if (response.body().getStatus() == 1) {
                            if (response.body().getInformation().getReviews().size() > 0) {
                                listReviewModel.clear();
                                for (int i = 0; i < response.body().getInformation().getReviews().size(); i++) {
                                    listReviewModel.add(new ReviewModel(
                                            response.body().getInformation().getReviews().get(i).getRevId(),
                                            response.body().getInformation().getReviews().get(i).getRevSummary(),
                                            response.body().getInformation().getReviews().get(i).getRevFeedback(),
                                            response.body().getInformation().getReviews().get(i).getUserName(),
                                            response.body().getInformation().getReviews().get(i).getRevDate(),
                                            String.valueOf(response.body().getInformation().getReviews().get(i).getRevRating())
                                    ));
                                }
                                reviewAdapter.notifyDataSetChanged();
                            }
                        } else {
                            Log.e(TAG, "Failed to get products." + response.body().getMessage());
                            // AppUtility.displayMessage(HomeActivity.this, response.body().getMessage());
                        }
                    }  else {
                        // AppUtility.displayMessage(HomeActivity.this, getString(R.string.failed_request));
                        // getFeaturedProducts();
                    }
                }

                @Override
                public void onFailure(Call<ProductDetailsPhp> call, Throwable t) {
                    Log.e(TAG, "failed " + t.toString());
                }
            });
        }
    }
}