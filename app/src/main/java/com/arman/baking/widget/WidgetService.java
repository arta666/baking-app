package com.arman.baking.widget;

import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViewsService;


public class WidgetService extends RemoteViewsService {

    private static final String TAG = WidgetService.class.getSimpleName();

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        Log.d(TAG, "onGetViewFactory: widget service called");
        return new WidgetRemoteViewsFactory(getApplicationContext(), intent);
    }
}