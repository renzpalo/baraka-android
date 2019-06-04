package com.renzpalo.baraka.Utility;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkUtility {

    // Return true or false whether we have connection or not.
    public static Boolean isNetworkConnected(Context mContext) {

        ConnectivityManager connectivityManager = (ConnectivityManager) mContext.getSystemService(mContext.CONNECTIVITY_SERVICE) ;
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();

        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();


    }
}
