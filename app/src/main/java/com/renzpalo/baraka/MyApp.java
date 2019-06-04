package com.renzpalo.baraka;

import android.app.Application;
import android.content.Context;
import android.util.Log;

public class MyApp extends Application {

    private static Context context;

    private String TAG = "MyApp";

    @Override
    public void onCreate() {
        super.onCreate();

        context = getApplicationContext();

        Log.e(TAG, "Showing MyApp");
    }

    // Return the context.
    public static Context getContext() {
        return context;
    }
}
