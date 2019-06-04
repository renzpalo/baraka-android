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

import java.util.List;

public class OrderStatusAdapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {

    private String TAG = "OrderStatusAdapter";

    private List<OrderStatusModel> mListOrderStatus;

    private Context mContext;

    public OrderStatusAdapter(Context context, List<OrderStatusModel> listOrderStatusModel) {
        this.mListOrderStatus = listOrderStatusModel;
        this.mContext = context;
    }

    public class OrderStatusHolder extends RecyclerView.ViewHolder {
        TextView tvOrderStatusInfo, tvOrderStatusDate;

        public OrderStatusHolder(View itemView) {
            super(itemView);

            tvOrderStatusInfo = itemView.findViewById(R.id.tvOrderStatusInfo);
            tvOrderStatusDate = itemView.findViewById(R.id.tvOrderStatusDate);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_status_item, parent, false);
        Log.i(TAG, "View created.");

        return new OrderStatusAdapter.OrderStatusHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
        final OrderStatusModel model = mListOrderStatus.get(position);

        Log.i(TAG, "Assign value");

        ((OrderStatusHolder)holder).tvOrderStatusInfo.setText(model.getOrderStatusInfo());
        ((OrderStatusHolder)holder).tvOrderStatusDate.setText(model.getOrderStatusDate());

    }

    @Override
    public int getItemCount() {
        return mListOrderStatus.size();
    }
}
