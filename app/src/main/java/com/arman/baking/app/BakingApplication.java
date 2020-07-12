package com.arman.baking.app;

import android.app.Application;
import android.content.Context;

public class BakingApplication extends Application {

    private static final String TAG = BakingApplication.class.getSimpleName();
    private static BakingApplication mInstance = null;

    private PrefManager pref;

    private Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;

        mContext = getApplicationContext();
    }

    public static synchronized BakingApplication getInstance() {
        return mInstance;
    }

    public Context getContext() {
        return mContext;
    }

    public PrefManager getPrefManager() {
        if (pref == null) {
            pref = new PrefManager(this);
        }

        return pref;
    }
}
