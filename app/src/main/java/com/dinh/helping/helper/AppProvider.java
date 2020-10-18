package com.dinh.helping.helper;

public class AppProvider {
    private static ObjectProviderInterface instance;

    public static void init(ObjectProviderInterface objectProviderInterface) {
        instance = objectProviderInterface;
    }

    public static SharePrefs getPreferences() {
        return instance.getPreferences();
    }
}
