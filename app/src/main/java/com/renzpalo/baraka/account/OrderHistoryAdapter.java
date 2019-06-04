package com.renzpalo.baraka.account;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.renzpalo.baraka.R;
import com.renzpalo.baraka.Utility.Constant;
import com.renzpalo.baraka.Utility.SharedPreferenceUtility;
import java.util.List;

public class OrderHistoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private String TAG = "AddressAdapter";

    private List<OrderHistoryModel> mListOrderHistory;

    private Context mContext;

    private String userId = SharedPreferenceUtility.getInstance().getString(Constant.USER_DATA);

    public OrderHistoryAdapter(Context context, List<OrderHistoryModel> listOrderHistoryModel) {
        this.mListOrderHistory = listOrderHistoryModel;
        this.mContext = context;
    }

    public class OrderHistoryHolder extends RecyclerView.ViewHolder {
        TextView tvHistoryOrderId, tvHistoryOrderPayment, tvHistoryOrderStatus, tvHistoryOrderName,
                tvHistoryOrderPrice, tvHistoryOrderDate, tvHistoryOrderViewDetails;

        RelativeLayout rlOrderHistoryItem;


        public OrderHistoryHolder(View itemView) {
            super(itemView);

            tvHistoryOrderId = itemView.findViewById(R.id.tvHistoryOrderId);
            tvHistoryOrderPayment = itemView.findViewById(R.id.tvHistoryOrderPayment);
            tvHistoryOrderStatus = itemView.findViewById(R.id.tvHistoryOrderStatus);
            tvHistoryOrderName = itemView.findViewById(R.id.tvHistoryOrderName);
            tvHistoryOrderPrice = itemView.findViewById(R.id.tvHistoryOrderPrice);
            tvHistoryOrderDate = itemView.findViewById(R.id.tvHistoryOrderDate);
            tvHistoryOrderViewDetails = itemView.findViewById(R.id.tvHistoryOrderViewDetails);

            rlOrderHistoryItem = itemView.findViewById(R.id.rlOrderHistoryItem);

        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_history_item, parent, false);
        Log.i(TAG, "View created.");

        return new OrderHistoryAdapter.OrderHistoryHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
        final OrderHistoryModel model = mListOrderHistory.get(position);

        Log.i(TAG, "Assign value");

        ((OrderHistoryHolder) holder).tvHistoryOrderId.setText("Order ID: " + model.getOrderId());
        ((OrderHistoryHolder) holder).tvHistoryOrderPayment.setText("Payment: " + model.getOrderPayment());
        ((OrderHistoryHolder) holder).tvHistoryOrderStatus.setText("Status: " + model.getOrderStatus());
        ((OrderHistoryHolder) holder).tvHistoryOrderPrice.setText("PHP " + model.getOrderTotal());
        ((OrderHistoryHolder) holder).tvHistoryOrderDate.setText(model.getOrderDate());

        ((OrderHistoryHolder) holder).rlOrderHistoryItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, com.renzpalo.baraka.account.OrderHistoryDetails.class);
                intent.putExtra("orderId", model.getOrderId());
                intent.putExtra("orderAddress", model.getOrderAddress());
                intent.putExtra("orderFullname", model.getOrderFullname());
                intent.putExtra("orderContact", model.getOrderContact());
                mContext.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return mListOrderHistory.size();
    }
}
