package com.dinh.helping.helper;

import java.text.DecimalFormat;

public class Consts {
    private static final String HOST_DOMAIN = "https://hospital-booking.com/";
    private static final String HOST_DEV = "http://ninomotor.qtctek.com/";
    private static final String LOCALHOST = "http://192.168.100.27:8080/hospital/";

    public static final String HOST_API = HOST_DOMAIN;

    public static final String REST_ENDPOINT = "api/";
    public static final String HEADES = "Authorization:Basic YWRtaW46cXRjdGVrQDEyMwx==";
    public static final int REQUEST_CODE_AUTOCOMPLETE = 222;
    public static final int REQUEST_CODE_GPS = 120;

    private static final String MODE_PRODUCTION = "production";
    private static final String MODE_DEBUG = "debug";
    public static final boolean TRUST_CERTIFICATE = false;


    public static final String MODE = MODE_DEBUG;

    public static final String TAG_FRAGMENT_CHECK = "FragmentCheck";

    public static final int LOCATION_INTERVAL = 10000;
    public static final int FASTEST_LOCATION_INTERVAL = 5000;

    public static String pattern = "###,###,###";
    public static DecimalFormat decimalFormat = new DecimalFormat(pattern);
}