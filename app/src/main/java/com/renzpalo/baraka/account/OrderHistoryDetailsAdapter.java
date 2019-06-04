package com.renzpalo.baraka.account;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.renzpalo.baraka.R;
import com.renzpalo.baraka.Utility.Constant;
import com.renzpalo.baraka.Utility.SharedPreferenceUtility;
import com.renzpalo.baraka.checkout.CheckoutAdapter;
import com.renzpalo.baraka.checkout.CheckoutModel;

import java.util.List;

public class OrderHistoryDetailsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private String TAG = "OrderHistoryDetailsAdapter";

    private List<OrderHistoryDetailsModel> mListOrderHistoryDetails;

    private Context mContext;

    private String userId = SharedPreferenceUtility.getInstance().getString(Constant.USER_DATA);

    public OrderHistoryDetailsAdapter(Context context, List<OrderHistoryDetailsModel> listOrderHistoryDetailsModel) {
        this.mListOrderHistoryDetails = listOrderHistoryDetailsModel;
        this.mContext = context;
    }

    public class  OrderHistoryDetailsHolder extends RecyclerView.ViewHolder {
        TextView tvOrderHistoryDetailsItemName, tvOrderHistoryDetailsItemQuantity, tvOrderHistoryDetailsItemPrice;

        public OrderHistoryDetailsHolder(View itemView) {
            super(itemView);

            tvOrderHistoryDetailsItemName = itemView.findViewById(R.id.tvOrderHistoryDetailsItemName);
            tvOrderHistoryDetailsItemQuantity = itemView.findViewById(R.id.tvOrderHistoryDetailsItemQuantity);
            tvOrderHistoryDetailsItemPrice = itemView.findViewById(R.id.tvOrderHistoryDetailsItemPrice);

        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_history_details_item, parent, false);
        Log.i(TAG, "View created.");

        return new OrderHistoryDetailsAdapter.OrderHistoryDetailsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
        final OrderHistoryDetailsModel model = mListOrderHistoryDetails.get(position);

        Log.i(TAG, "Assign value");

        ((OrderHistoryDetailsAdapter.OrderHistoryDetailsHolder)holder).tvOrderHistoryDetailsItemName.setText(model.getProductName());
        ((OrderHistoryDetailsAdapter.OrderHistoryDetailsHolder)holder).tvOrderHistoryDetailsItemQuantity.setText(model.getProductQuantity());

        ((OrderHistoryDetailsAdapter.OrderHistoryDetailsHolder)holder).tvOrderHistoryDetailsItemPrice.setText("PHP " + model.getProductPrice());

//        Glide
//                .with(mContext)
//                .load(model.getProductImage())
//                .into(((CheckoutAdapter.CheckoutHolder) holder).ivCartItemImage);
    }

    @Override
    public int getItemCount() {
        return mListOrderHistoryDetails.size();
    }
}
