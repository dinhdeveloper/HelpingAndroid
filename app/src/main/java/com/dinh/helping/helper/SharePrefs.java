package com.dinh.helping.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.google.gson.Gson;

import java.util.Locale;
public class SharePrefs {

    public static final String DEFAULT_BLANK = "";
    public static final String CHECK_LOGIN_SPLASH = "CHECK_LOGIN_SPLASH";
    /**
     * Keys for saving data to shareprefs
     */
    private static final String PREF_TOKEN = "PREF_TOKEN";
    private static final String PREF_USER = "PREF_USER";
    private static final String PREF_LANGUAGE = "PREF_LANGUAGE";
    private static final String PREF_STATUS_LOGIN = "PREF_STATUS_LOGIN";
    private static final String PREF_FIRST_NAME = "PREF_FIRST_NAME";
    private static final String PREF_USER_NAME = "PREF_USER_NAME";
    private static final String PREF_LAST_NAME = "PREF_LAST_NAME";
    private static final String PREF_GENDER = "PREF_GENDER";
    private static final String PREF_BIRTHDAY = "PREF_BIRTHDAY";
    private static final String PREF_EMAIL = "PREF_EMAIL";
    private static final String PREF_ADDRESS = "PREF_ADDRESS";
    private static final String PREF_PHONE = "PREF_PHONE";
    private static final String PREF_USER_ID = "PREF_USER_ID";
    private static final String PREF_GET_LIST_AIRPORT = "PREF_GET_LIST_AIRPORT";
    private static final String PREF_FB_ID = "PREF_FB_ID";
    private static final String PREF_BOOKING_CODE_TEMP = "PREF_BOOKING_CODE_TEMP";
    private static final String PREF_STATUS_CONNECT_INTERNET = "PREF_STATUS_CONNECT_INTERNET";
    private static final String PREF_HOTLINE = "PREF_HOTLINE";
    private static final String PREF_USER_GG_ID = "PREF_USER_GG_ID";
    private static final String PREF_USER_IMAGE = "PREF_USER_IMAGE";
    private static final String PREF_ACCESS_TOKEN_FB = "PREF_ACCESS_TOKEN_FB";

    private static final String PREF_FIREBASE_DISPLAYNAME = "PREF_FIREBASE_DISPLAYNAME";
    private static final String PREF_FIREBASE_EMAIL = "PREF_FIREBASE_EMAIL";
    private static final String PREF_FIREBASE_PHOTO_URL = "PREF_FIREBASE_PHOTO_URL";
    private static final String PREF_FIREBASE_PROVIDER_ID = "PREF_FIREBASE_PROVIDER_ID";
    private static final String PREF_FIREBASE_U_ID = "PREF_FIREBASE_U_ID";
    private static final String PREF_FIRST_INSTALL = "PREF_FIRST_INSTALL";
    private static final String PREF_LOGIN_TYPE = "PREF_LOGIN_TYPE";
    private static final String PREF_USER_PASSWORD = "PREF_USER_PASSWORD";
    private static final String PREF_USER_COVER_IMAGE = "PREF_USER_COVER_IMAGE";
    private static final String PREF_USER_LNG_POSITION = "PREF_USER_LNG_POSITION";
    private static final String PREF_USER_LAT_POSITION = "PREF_USER_LAT_POSITION";

    private SharedPreferences sharedPreferences;

    public SharePrefs(Context ctx) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    public void clear() {
        // clear all
        sharedPreferences.edit().clear().commit();
    }

    public void clear(String key) {

        save(key, "");
    }

    public void save(String key, String value) {
        if (!TextUtils.isEmpty(value))
            sharedPreferences.edit().putString(key, value).commit();
        else
            sharedPreferences.edit().putString(key, DEFAULT_BLANK).commit();
    }

    public void save(String key, long value) {
        sharedPreferences.edit().putLong(key, value).commit();
    }

    public void save(String key, float value) {
        sharedPreferences.edit().putFloat(key, value).commit();
    }

    public String get(String key, String _default) {
        return sharedPreferences.getString(key, _default);
    }

    public double get(String key, float _default) {
        return sharedPreferences.getFloat(key, _default);
    }

    public long get(String key, long defValue) {
        return sharedPreferences.getLong(key, defValue);
    }

    public int get(String key, int defValue) {
        return sharedPreferences.getInt(key, defValue);
    }

    public void saveLostInternet(int value) {
        save(PREF_STATUS_CONNECT_INTERNET, value);
    }

    public int countLostInternet() {
        return get(PREF_STATUS_CONNECT_INTERNET, 0);
    }

    public void save(String key, boolean value) {
        sharedPreferences.edit().putBoolean(key, value).commit();
    }

    public void save(String key, int value) {
        sharedPreferences.edit().putInt(key, value).commit();
    }

    public boolean get(String key, boolean _default) {
        return sharedPreferences.getBoolean(key, _default);
    }

    public void saveStatusLogin(boolean value) {
        save(PREF_STATUS_LOGIN, value);
    }

    public boolean checkLoginStatus() {
        return get(PREF_STATUS_LOGIN, false);
    }

    public void saveUserFirstName(String firstName) {
        save(PREF_FIRST_NAME, firstName);
    }

    public String getUserFirstName() {
        return get(PREF_FIRST_NAME, "");
    }

    public void saveUsername(String username) {
        save(PREF_USER_NAME, username);
    }

    public String getUsername() {
        return get(PREF_USER_NAME, "");
    }

    public void saveUserLastName(String lastName) {
        save(PREF_LAST_NAME, lastName);
    }

    public String getUserLastName() {
        return get(PREF_LAST_NAME, "");
    }

    public void saveUserGender(String gender) {
        save(PREF_GENDER, gender);
    }

    public String getUserGender() {
        return get(PREF_GENDER, "male");
    }

    public void saveUserBirthday(String value) {
        save(PREF_BIRTHDAY, value);
    }

    public String getUserBirthday() {
        return get(PREF_BIRTHDAY, "");
    }

    public void saveUserAddress(String address) {
        save(PREF_ADDRESS, address);
    }

    public String getUserAddress() {
        return get(PREF_ADDRESS, "");
    }

    public void saveUserEmail(String email) {
        save(PREF_EMAIL, email);
    }

    public String getUserEmail() {
        return get(PREF_EMAIL, "");
    }

    public void saveUserPhone(String value) {
        save(PREF_PHONE, value);
    }

    public String getUserPhone() {
        return get(PREF_PHONE, "");
    }

    public void saveUserId(String value) {
        save(PREF_USER_ID, value);
    }

    public String getUserId() {
        return get(PREF_USER_ID, "");
    }

    public void saveFacebookId(String id) {
        save(PREF_FB_ID, id);
    }

    public String getFacebookId() {
        return get(PREF_FB_ID, "");
    }


    public void saveBookingCodeTemp(String value) {
        save(PREF_BOOKING_CODE_TEMP, value);
    }

    public String getBookingCodeTemp() {
        return get(PREF_BOOKING_CODE_TEMP, "");
    }

    public void saveUserGGId(String value) {
        save(PREF_USER_GG_ID, value);
    }

    public String getUserGGID() {

        return get(PREF_USER_GG_ID, "");
    }

    public void saveUserImage(String urlImage) {
        save(PREF_USER_IMAGE, urlImage);
    }

    public String getUserImage() {
        return get(PREF_USER_IMAGE, "");
    }

    public void saveToken(String token) {
        sharedPreferences.edit().putString(PREF_TOKEN, token).commit();
    }

    public void saveListAirportDone(boolean value) {
        save(PREF_GET_LIST_AIRPORT, value);
    }

    public boolean checkGetListAirport() {
        return get(PREF_GET_LIST_AIRPORT, false);
    }

    public void saveHotline(String hotline) {
        save(PREF_HOTLINE, hotline);
    }

    public String getHotline() {
        return get(PREF_HOTLINE, "");
    }

    public String getAssetTokenFB() {
        return get(PREF_ACCESS_TOKEN_FB, "");
    }

    public void saveAccessTokenFB(String token) {

        save(PREF_ACCESS_TOKEN_FB, token);
    }

    public void saveFirebaseDisplayname(String value) {
        save(PREF_FIREBASE_DISPLAYNAME, value);
    }

    public String getFirebaseDisplayname() {
        return get(PREF_FIREBASE_DISPLAYNAME, DEFAULT_BLANK);
    }

    public void saveFirebaseEmail(String value) {
        save(PREF_FIREBASE_EMAIL, value);
    }

    public String getFirebaseEmail() {
        return get(PREF_FIREBASE_EMAIL, DEFAULT_BLANK);
    }

    public void saveFirebasePhotoUrl(String value) {
        save(PREF_FIREBASE_PHOTO_URL, value);
    }

    public String getFirebasePhotoUrl() {
        return get(PREF_FIREBASE_PHOTO_URL, DEFAULT_BLANK);
    }

    public void saveFirebaseProviderId(String value) {
        save(PREF_FIREBASE_PROVIDER_ID, value);
    }

    public String getFirebaseProviderId() {
        return get(PREF_FIREBASE_PROVIDER_ID, DEFAULT_BLANK);
    }

    public void saveFirebaseUID(String value) {
        save(PREF_FIREBASE_U_ID, value);
    }

    public String getFirebaseUID() {
        return get(PREF_FIREBASE_U_ID, DEFAULT_BLANK);
    }

    public void saveFirstInstall(boolean value) {
        save(PREF_FIRST_INSTALL, value);
    }

    public boolean isFirstInstall() {
        return get(PREF_FIRST_INSTALL, true);
    }

    public void saveLoginType(String value) {
        save(PREF_LOGIN_TYPE, value);
    }

    public void saveUserPassword(String value) {
        save(PREF_USER_PASSWORD, value);
    }

    public String getUserPassword() {
        return get(PREF_USER_PASSWORD, DEFAULT_BLANK);
    }

    public void saveUserCoverImage(String value) {
        save(PREF_USER_COVER_IMAGE, value);
    }

    public String getUserCoverImage() {

        return get(PREF_USER_COVER_IMAGE, DEFAULT_BLANK);
    }

    public String getLoginType() {

        return get(PREF_LOGIN_TYPE, DEFAULT_BLANK);
    }

    public boolean isEmptyUserData() {
        String userName = getUsername();

        String userPhone = getUserPhone();

        String userGender = getUserGender();

        String userBirthday = getUserBirthday();

        if (!TextUtils.isEmpty(userName) || !TextUtils.isEmpty(userPhone) || !TextUtils.isEmpty(userBirthday) || !TextUtils.isEmpty(userGender)) {
            return false;
        }


        return true;
    }


    public void saveUserLatitude(String lat) {
        save(PREF_USER_LAT_POSITION, lat);

    }

    public void saveUserLongtitude(String lng) {
        save(PREF_USER_LNG_POSITION, lng);
    }

    public String getUserLat() {
        return get(PREF_USER_LAT_POSITION, "");
    }

    public String getUserLng() {
        return get(PREF_USER_LNG_POSITION, "");
    }

    public void clearUserData() {

        clear();
    }

    public void clearToken() {
        clear(PREF_TOKEN);
    }

    public String getToken() {
        return sharedPreferences.getString(PREF_TOKEN, DEFAULT_BLANK);
    }

    private static final String PREF_USER_MODEL = "PREF_USER_MODEL";

    private final Gson gson = new Gson();

//    public void saveUserModel(UserResponseModel model) {
//        save(PREF_USER_MODEL, gson.toJson(model));
//    }
//
//    public UserResponseModel getUserModel() {
//        String json = get(PREF_USER_MODEL, "");
//        if (!TextUtils.isEmpty(json)) {
//            return gson.fromJson(json, UserResponseModel.class);
//        }
//        return null;
//    }

    private static final String PREF_DEVICE_IMEI = "PREF_DEVICE_IMEI";

    public String getDeviceImei() {
        return sharedPreferences.getString(PREF_DEVICE_IMEI, DEFAULT_BLANK);
    }

    public void saveDeviceImei(String deviceImei) {
        save(PREF_DEVICE_IMEI, deviceImei);
    }


    public void saveStatusSplash(boolean value) {
        save(CHECK_LOGIN_SPLASH, value);
    }
}
