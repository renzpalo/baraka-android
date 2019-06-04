package com.renzpalo.baraka.wishlist;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.renzpalo.baraka.R;
import com.renzpalo.baraka.Utility.AppUtility;
import com.renzpalo.baraka.Utility.Constant;
import com.renzpalo.baraka.Utility.NetworkUtility;
import com.renzpalo.baraka.Utility.SharedPreferenceUtility;
import com.renzpalo.baraka.WebServices.ServiceWrapper;
import com.renzpalo.baraka.cart.CartAdapter;
import com.renzpalo.baraka.cart.CartModel;
import com.renzpalo.baraka.phpResponse.AddToCartPhp;
import com.renzpalo.baraka.phpResponse.AddToWishlistPhp;
import com.renzpalo.baraka.phpResponse.DeleteProductPhp;
import com.renzpalo.baraka.productPreview.ProductDetails;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WishlistAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private String TAG = "WishlistAdapter";

    private List<WishlistModel> mListWishlist;

    private Context mContext;

    private String userId = SharedPreferenceUtility.getInstance().getString(Constant.USER_DATA);

    private Menu menu;

    public WishlistAdapter(Context context, List<WishlistModel> listWishlistModel) {
        this.mListWishlist = listWishlistModel;
        this.mContext = context;
    }

    public class  WishlistHolder extends RecyclerView.ViewHolder {
        TextView tvWishlistItemName, tvWishlistItemPrice, tvWishlistRatingCount;

        ImageView ivWishlistItemImage, ivWishlistRemove, ivWishlistAddToCart;

        AppCompatRatingBar rbWishlistRating;

        public WishlistHolder(View itemView) {
            super(itemView);

            tvWishlistItemName = itemView.findViewById(R.id.tvWishlistItemName);
            tvWishlistItemPrice = itemView.findViewById(R.id.tvWishlistItemPrice);
            tvWishlistRatingCount = itemView.findViewById(R.id.tvWishlistRatingCount);

            ivWishlistItemImage = itemView.findViewById(R.id.ivWishlistItemImage);
            ivWishlistRemove = itemView.findViewById(R.id.ivWishlistRemove);
            ivWishlistAddToCart = itemView.findViewById(R.id.ivWishlistAddToCart);

            rbWishlistRating = itemView.findViewById(R.id.rbWishlistRating);

        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.wishlist_item, parent, false);
        Log.i(TAG, "View created.");

        return new WishlistAdapter.WishlistHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final WishlistModel model = mListWishlist.get(position);

        Log.i(TAG, "Assign value");

        ((WishlistHolder)holder).tvWishlistItemName.setText(model.getProductName());
        ((WishlistHolder)holder).tvWishlistItemPrice.setText(model.getProductPrice());

        Glide
                .with(mContext)
                .load(model.getProductImage())
                .into(((WishlistHolder) holder).ivWishlistItemImage);

        ((WishlistHolder) holder).ivWishlistAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addToCart(model.getProductId(), model.getProductPrice());
            }
        });

        ((WishlistHolder) holder).ivWishlistRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteProduct(model.getProductId());
            }
        });

        ((WishlistHolder) holder).ivWishlistItemImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, com.renzpalo.baraka.productPreview.ProductDetails.class);
                intent.putExtra("prodId", model.getProductId());
                mContext.startActivity(intent);
            }
        });

        ((WishlistHolder) holder).tvWishlistItemName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, com.renzpalo.baraka.productPreview.ProductDetails.class);
                intent.putExtra("prodId", model.getProductId());
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mListWishlist.size();
    }

    public void addToCart(String prodId, String prodPrice) {
        if (!NetworkUtility.isNetworkConnected(mContext)) {
            AppUtility.displayAlertMessage(mContext, mContext.getString(R.string.network_not_connected));
        } else {
            ServiceWrapper serviceWrapper = new ServiceWrapper(null);
            Call<AddToCartPhp> call = serviceWrapper.addToCartPhpCall("1234", prodId, prodPrice, "1", userId);
            call.enqueue(new Callback<AddToCartPhp>() {
                @Override
                public void onResponse(Call<AddToCartPhp> call, Response<AddToCartPhp> response) {
                    Log.i(TAG, "Response is " + response.body().getInformation().toString());
                    if (response.body() != null && response.isSuccessful()) {
                        if (response.body().getStatus() == 1) {
                            SharedPreferenceUtility.getInstance().saveInt(Constant.CART_ITEM_COUNT,
                                    response.body().getInformation());

                            AppUtility.updateCartCount(mContext, menu);

                            Toast.makeText(mContext, "Added to cart.", Toast.LENGTH_LONG).show();
                        } else {
                            Log.e(TAG, "Failed to add to cart." + response.body().getMessage());
                        }
                    } else {
                        Log.e(TAG, "Failed to get response." + response.body().getMessage());
                    }
                }

                @Override
                public void onFailure(Call<AddToCartPhp> call, Throwable t) {
                    Log.e(TAG, "failed " + t.toString());
                }
            });
        }
    }

    public void deleteProduct(String productId) {
        if (!NetworkUtility.isNetworkConnected(mContext)) {
            AppUtility.displayAlertMessage(mContext, mContext.getString(R.string.network_not_connected));
        } else {
            ServiceWrapper serviceWrapper = new ServiceWrapper(null);
            Call<DeleteProductPhp> call = serviceWrapper.deleteWishlistProductPhpCall("1234", productId, userId);
            call.enqueue(new Callback<DeleteProductPhp>() {
                @Override
                public void onResponse(Call<DeleteProductPhp> call, Response<DeleteProductPhp> response) {
                    Log.i(TAG, "Response is " + response.body().getInformation());
                    if (response.body() != null && response.isSuccessful()) {
                        if (response.body().getStatus() == 1) {
                            Toast.makeText(mContext, "Successfully deleted.", Toast.LENGTH_LONG).show();

                            // Refresh page
                            ((WishlistDetails) mContext).getWishlistDetails();
                        } else {
                            Log.e(TAG, "Failed to delete product." + response.body().getMessage());
                        }
                    } else {
                        Log.e(TAG, "Failed to get response." + response.body().getMessage());
                    }
                }

                @Override
                public void onFailure(Call<DeleteProductPhp> call, Throwable t) {
                    Log.e(TAG, "failed " + t.toString());
                }
            });
        }
    }


}
