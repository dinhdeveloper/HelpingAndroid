package com.dinh.helping.event;


import com.canhdinh.lib.helper.BusHelper;

public class BackFragment {public static void post() {
    BusHelper.post(new BackFragment());
}
}