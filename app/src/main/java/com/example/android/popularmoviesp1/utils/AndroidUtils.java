package com.example.android.popularmoviesp1.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by ARVIND on 7/24/2016.
 */
public class AndroidUtils {
    private static final String TAG = AndroidUtils.class.getSimpleName();

    private Context mContext;

    public AndroidUtils(Context context) {
        mContext = context;
    }

    public boolean isDeviceOnline() {

        ConnectivityManager connectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        boolean retVal = (networkInfo != null && networkInfo.isConnected());
        return retVal;
    }
}
