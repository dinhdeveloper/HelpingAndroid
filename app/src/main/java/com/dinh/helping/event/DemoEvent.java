package com.dinh.helping.event;

import com.canhdinh.lib.helper.BusHelper;

public class DemoEvent {public static void post() {
    BusHelper.post(new DemoEvent());
}
}
