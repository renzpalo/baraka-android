package com.renzpalo.baraka.account;

import android.content.Context;
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
import com.renzpalo.baraka.checkout.PlaceOrder;

import java.util.ArrayList;
import java.util.List;

public class AddressAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private String TAG = "AddressAdapter";

    private List<AddressModel> mListAddress;
    private ArrayList<RelativeLayout> listRelLayout = new ArrayList<>();

    private Context mContext;

    private String userId = SharedPreferenceUtility.getInstance().getString(Constant.USER_DATA);

    private String fullAddress;

    public AddressAdapter(Context context, List<AddressModel> listAddressModel) {
        this.mListAddress = listAddressModel;
        this.mContext = context;
    }

    public class  AddressHolder extends RecyclerView.ViewHolder {
        TextView tvAdFullname, tvAdContact, tvFullAddress;

        ImageView ivSelectAddress;

        RelativeLayout rlAddressItem;

        public AddressHolder(View itemView) {
            super(itemView);

            tvAdFullname = itemView.findViewById(R.id.tvAdFullname);
            tvAdContact = itemView.findViewById(R.id.tvAdContact);
            tvFullAddress = itemView.findViewById(R.id.tvFullAddress);

            ivSelectAddress = itemView.findViewById(R.id.ivSelectAddress);

            rlAddressItem = itemView.findViewById(R.id.rlAddressItem);

        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.address_item, parent, false);
        Log.i(TAG, "View created.");

        return new AddressAdapter.AddressHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
        final AddressModel model = mListAddress.get(position);

        Log.i(TAG, "Assign value");

        ((AddressAdapter.AddressHolder)holder).tvAdFullname.setText(model.getAdFullname());
        ((AddressAdapter.AddressHolder)holder).tvAdContact.setText(model.getAdContact());

        fullAddress = model.getAdStreet() + ", " +
                model.getAdBarangay() + ", " +
                model.getAdCityMuni() + ", " +
                model.getAdProvince() + ", " +
                model.getAdZipcode();

        ((AddressAdapter.AddressHolder)holder).tvFullAddress.setText(fullAddress);

        ((AddressHolder) holder).rlAddressItem.setTag(model.getAdId());

        listRelLayout.add(((AddressHolder) holder).rlAddressItem);

        ((AddressHolder)holder).rlAddressItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "Address selected." + model.getAdId());

                ((Address) mContext).adId = model.getAdId();

                for (int i = 0; i < listRelLayout.size(); i++) {
                    listRelLayout.get(i).setBackgroundResource(R.drawable.border_black);
                    // ((AddressHolder) holder).rlAddressItem.setBackgroundResource(R.drawable.border_primary);
                }


                ((AddressHolder) holder).rlAddressItem.setBackgroundResource(R.drawable.border_primary);
            }
        });



    }

    @Override
    public int getItemCount() {
        return mListAddress.size();
    }
}
