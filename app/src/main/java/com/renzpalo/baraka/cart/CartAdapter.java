package com.renzpalo.baraka.cart;

        import android.content.Context;
        import android.content.Intent;
        import android.support.annotation.NonNull;
        import android.support.v7.widget.RecyclerView;
        import android.util.Log;
        import android.view.KeyEvent;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.view.inputmethod.EditorInfo;
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
        import com.renzpalo.baraka.phpResponse.AddToWishlistPhp;
        import com.renzpalo.baraka.phpResponse.DeleteProductPhp;
        import com.renzpalo.baraka.phpResponse.UpdateCartPhp;

        import java.util.List;

        import retrofit2.Call;
        import retrofit2.Callback;
        import retrofit2.Response;

public class CartAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private String TAG = "CartAdapter";

    private List<CartModel> mListCart;

    private Context mContext;

    private String userId = SharedPreferenceUtility.getInstance().getString(Constant.USER_DATA);

    public CartAdapter(Context context, List<CartModel> listCartModel) {
        this.mListCart = listCartModel;
        this.mContext = context;
    }

    public class  CartHolder extends RecyclerView.ViewHolder {
        TextView tvCartItemName, tvCartItemPrice, tvCartDeliveryDays;

        ImageView ivCartItemImage, ivRemove, ivCartItemDelete;

        EditText etCartItemQuantity;

        public CartHolder(View itemView) {
            super(itemView);

            tvCartItemName = itemView.findViewById(R.id.tvCartItemName);
            tvCartItemPrice = itemView.findViewById(R.id.tvCartItemPrice);
            tvCartDeliveryDays = itemView.findViewById(R.id.tvCartDeliveryDays);

            ivCartItemImage = itemView.findViewById(R.id.ivCartItemImage);
            ivRemove = itemView.findViewById(R.id.ivRemove);
            ivCartItemDelete = itemView.findViewById(R.id.ivCartItemDelete);

            etCartItemQuantity = itemView.findViewById(R.id.etCartItemQuantity);

        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item, parent, false);
        Log.i(TAG, "View created.");

        return new CartAdapter.CartHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
        final CartModel model = mListCart.get(position);

        Log.i(TAG, "Assign value");

        ((CartHolder)holder).tvCartItemName.setText(model.getProductName());
        ((CartHolder)holder).tvCartItemPrice.setText(model.getProductPrice());

        ((CartHolder)holder).etCartItemQuantity.setText(model.getProductQuantity());

        Glide
                .with(mContext)
                .load(model.getProductImage())
                .into(((CartHolder) holder).ivCartItemImage);

        ((CartHolder) holder).ivCartItemDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addToWishlist(model.getProductId(), model.getProductPrice());
            }
        });

        ((CartHolder) holder).ivRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteProduct(model.getProductId());
            }
        });

        ((CartHolder) holder).ivCartItemImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, com.renzpalo.baraka.productPreview.ProductDetails.class);
                intent.putExtra("prodId", model.getProductId());
                mContext.startActivity(intent);
            }
        });

        ((CartHolder) holder).tvCartItemName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, com.renzpalo.baraka.productPreview.ProductDetails.class);
                intent.putExtra("prodId", model.getProductId());
                mContext.startActivity(intent);
            }
        });

        ((CartHolder) holder).etCartItemQuantity.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_GO || i == EditorInfo.IME_ACTION_DONE || i == EditorInfo.IME_ACTION_SEND) {
                    if (!((CartHolder) holder).etCartItemQuantity.getText().toString().trim().equals("") ||
                            !((CartHolder) holder).etCartItemQuantity.getText().toString().trim().equals("0")) {
                        updateCartQuantity(model.getProductId(), ((CartHolder) holder).etCartItemQuantity.getText().toString().trim());
                    }
                }


                return false;
            }
        });

    }

    @Override
    public int getItemCount() {
        return mListCart.size();
    }

    public void addToWishlist(String productId, String prodPrice) {
        if (!NetworkUtility.isNetworkConnected(mContext)) {
            AppUtility.displayAlertMessage(mContext, mContext.getString(R.string.network_not_connected));
        } else {
            ServiceWrapper serviceWrapper = new ServiceWrapper(null);
            Call<AddToWishlistPhp> call = serviceWrapper.addToWishlistPhpCall("1234", productId, prodPrice, "1", userId);
            call.enqueue(new Callback<AddToWishlistPhp>() {
                @Override
                public void onResponse(Call<AddToWishlistPhp> call, Response<AddToWishlistPhp> response) {
                    Log.i(TAG, "Response is " + response.body().getInformation());
                    if (response.body() != null && response.isSuccessful()) {
                        if (response.body().getStatus() == 1) {
                            Toast.makeText(mContext, "Added to wishlist.", Toast.LENGTH_LONG).show();
                        } else {
                            Log.e(TAG, "Failed to add to wishlist." + response.body().getMessage());
                        }
                    } else {
                        Log.e(TAG, "Failed to get response." + response.body().getMessage());
                    }
                }

                @Override
                public void onFailure(Call<AddToWishlistPhp> call, Throwable t) {
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
            Call<DeleteProductPhp> call = serviceWrapper.deleteCartProductPhpCall("1234", productId, userId);
            call.enqueue(new Callback<DeleteProductPhp>() {
                @Override
                public void onResponse(Call<DeleteProductPhp> call, Response<DeleteProductPhp> response) {
                    Log.i(TAG, "Response is " + response.body().getInformation());
                    if (response.body() != null && response.isSuccessful()) {
                        if (response.body().getStatus() == 1) {
                            Toast.makeText(mContext, "Successfully deleted.", Toast.LENGTH_LONG).show();

                            // Refresh page
                            ((CartDetails) mContext).getCartDetails();

                            // Update cart count.
                            SharedPreferenceUtility.getInstance().saveInt(Constant.CART_ITEM_COUNT,
                                    SharedPreferenceUtility.getInstance().getInt(Constant.CART_ITEM_COUNT) - 1);
                            AppUtility.updateCartCount(mContext, CartDetails.menu);
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

    public void updateCartQuantity(String prodId, String prodQuantity) {
        if (!NetworkUtility.isNetworkConnected(mContext)) {
            AppUtility.displayAlertMessage(mContext, mContext.getString(R.string.network_not_connected));
        } else {
            ServiceWrapper serviceWrapper = new ServiceWrapper(null);
            Call<UpdateCartPhp> call = serviceWrapper.updateCartProductPhpCall("1234", prodId, prodQuantity, userId);
            call.enqueue(new Callback<UpdateCartPhp>() {
                @Override
                public void onResponse(Call<UpdateCartPhp> call, Response<UpdateCartPhp> response) {
                    Log.i(TAG, "Response is " + response.body().getInformation());
                    if (response.body() != null && response.isSuccessful()) {
                        if (response.body().getStatus() == 1) {
                            Toast.makeText(mContext, "Successfully updated.", Toast.LENGTH_LONG).show();
                            // Refresh page
                            ((CartDetails) mContext).getCartDetails();

                        } else {
                            Log.e(TAG, "Failed to update product." + response.body().getMessage());
                        }
                    } else {
                        Log.e(TAG, "Failed to get response." + response.body().getMessage());
                    }
                }

                @Override
                public void onFailure(Call<UpdateCartPhp> call, Throwable t) {
                    Log.e(TAG, "failed " + t.toString());
                }
            });
        }
    }
}
