package com.dinh.helping.event;

import com.canhdinh.lib.helper.BusHelper;

public class BackLoginFragment {
    public static void post() {
        BusHelper.post(new BackLoginFragment());
    }
}