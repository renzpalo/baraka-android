package com.renzpalo.baraka.checkout;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.renzpalo.baraka.R;
import com.renzpalo.baraka.Utility.Constant;
import com.renzpalo.baraka.Utility.SharedPreferenceUtility;

import java.util.List;

public class CheckoutAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private String TAG = "CheckoutAdapter";

    private List<CheckoutModel> mListCheckout;

    private Context mContext;

    private String userId = SharedPreferenceUtility.getInstance().getString(Constant.USER_DATA);

    public CheckoutAdapter(Context context, List<CheckoutModel> listCheckoutModel) {
        this.mListCheckout = listCheckoutModel;
        this.mContext = context;
    }

    public class  CheckoutHolder extends RecyclerView.ViewHolder {
        TextView tvOrderItemName, tvOrderItemQuantity, tvOrderItemPrice;

        public CheckoutHolder(View itemView) {
            super(itemView);

            tvOrderItemName = itemView.findViewById(R.id.tvOrderItemName);
            tvOrderItemQuantity = itemView.findViewById(R.id.tvOrderItemQuantity);
            tvOrderItemPrice = itemView.findViewById(R.id.tvOrderItemPrice);

        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item, parent, false);
        Log.i(TAG, "View created.");

        return new CheckoutAdapter.CheckoutHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
        final CheckoutModel model = mListCheckout.get(position);

        Log.i(TAG, "Assign value");

        ((CheckoutAdapter.CheckoutHolder)holder).tvOrderItemName.setText(model.getProductName());
        ((CheckoutAdapter.CheckoutHolder)holder).tvOrderItemQuantity.setText(model.getProductQuantity());

        ((CheckoutAdapter.CheckoutHolder)holder).tvOrderItemPrice.setText("PHP " + model.getProductPrice());

//        Glide
//                .with(mContext)
//                .load(model.getProductImage())
//                .into(((CheckoutAdapter.CheckoutHolder) holder).ivCartItemImage);
    }

    @Override
    public int getItemCount() {
        return mListCheckout.size();
    }

}
