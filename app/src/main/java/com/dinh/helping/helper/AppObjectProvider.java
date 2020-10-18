package com.dinh.helping.helper;

import android.content.Context;

public class AppObjectProvider implements ObjectProviderInterface{
    private final Context context;

    // singleton instances
    private SharePrefs preferences;

    public AppObjectProvider(Context context) {
        this.context = context;

        ObjectProviderInterface objectProviderInterface1 =  new AppObjectProvider(context);

        AppProvider.init(objectProviderInterface1);
    }

    @Override
    public SharePrefs getPreferences() {
        return (preferences == null) ? (preferences = new SharePrefs(context)) : preferences;
    }
}
