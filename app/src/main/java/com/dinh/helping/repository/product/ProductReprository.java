package com.dinh.helping.repository.product;

import android.app.ProgressDialog;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.dinh.helping.activity.HomeActivity;
import com.dinh.helping.api.APIService;
import com.dinh.helping.api.ApiClient;
import com.dinh.helping.api.FileUploader;
import com.dinh.helping.api.ServiceGenerator;
import com.dinh.helping.helper.Consts;
import com.dinh.helping.model.ApiParams;
import com.dinh.helping.model.BaseResponseModel;
import com.dinh.helping.model.PhotoModel;
import com.dinh.helping.model.ProductModel;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okio.BufferedSink;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public class ProductReprository {
    public static ProductReprository instance;


    public static ProductReprository getInstance() {
        if (instance == null) {
            instance = new ProductReprository();
        }
        return instance;
    }

    public MutableLiveData<BaseResponseModel<ProductModel>> getDataProduct() {
        MutableLiveData<BaseResponseModel<ProductModel>> data = new MutableLiveData<BaseResponseModel<ProductModel>>();
        APIService apiService = ServiceGenerator.createService(APIService.class);
        ApiParams params = new ApiParams();
        //params.detect = "list_product";
        params.detect = "list_product_by_discount";
        apiService.getAllProduct(params).enqueue(new Callback<BaseResponseModel<ProductModel>>() {
            @Override
            public void onResponse(Call<BaseResponseModel<ProductModel>> call, Response<BaseResponseModel<ProductModel>> response) {
                if (response.isSuccessful()) {
                    data.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<BaseResponseModel<ProductModel>> call, Throwable t) {
                Log.e("onFailure", t.getMessage());
            }
        });
        return data;
    }

    public MutableLiveData<BaseResponseModel<ProductModel>> getAllProductByDate() {
        MutableLiveData<BaseResponseModel<ProductModel>> data = new MutableLiveData<BaseResponseModel<ProductModel>>();
        APIService apiService = ServiceGenerator.createService(APIService.class);
        ApiParams params = new ApiParams();
        params.detect = "product_by_date";
        apiService.getAllProductByDate(params).enqueue(new Callback<BaseResponseModel<ProductModel>>() {
            @Override
            public void onResponse(Call<BaseResponseModel<ProductModel>> call, Response<BaseResponseModel<ProductModel>> response) {
                if (response.isSuccessful()) {
                    data.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<BaseResponseModel<ProductModel>> call, Throwable t) {
                Log.e("onFailure", t.getMessage());
            }
        });
        return data;
    }

    public MutableLiveData<BaseResponseModel<ProductModel>> createProduct(ProductModel model, String id_customer) {
        MutableLiveData<BaseResponseModel<ProductModel>> data = new MutableLiveData<BaseResponseModel<ProductModel>>();
        MultipartBody.Builder builder = new MultipartBody.Builder();
        if (!TextUtils.isEmpty(model.getProduct_image())) {
            File fileAvatar = new File(model.getProduct_image());
            if (fileAvatar.exists()) {
                RequestBody fileBody = RequestBody.create(MediaType.parse("image/*"), fileAvatar);
                builder.addFormDataPart("product_image", fileAvatar.getName(), fileBody);
            }
        }
        builder.addFormDataPart("category_id", model.getCategory_id());
        builder.addFormDataPart("customer_id", id_customer);
        //builder.addFormDataPart("image_id", "0");
        builder.addFormDataPart("product_name", model.getProduct_name());
        builder.addFormDataPart("city_id", model.getCity_id());
        builder.addFormDataPart("district_id", model.getDistrict_id());
        builder.addFormDataPart("ward_id", model.getWard_id());
        builder.addFormDataPart("phone_contact", model.getPhone_contact());
        builder.addFormDataPart("price_sale", model.getPrice_sale());
        builder.addFormDataPart("quantity", model.getQuantity());
        builder.addFormDataPart("description", model.getDescription());
        builder.addFormDataPart("discount", model.getDiscount());
        builder.addFormDataPart("location", model.getLocation());
        builder.addFormDataPart("status", "Y");
        builder.addFormDataPart("detect", "create_product")
                .setType(MultipartBody.FORM);
        RequestBody requestBody = builder.build();

        APIService apiService = ServiceGenerator.createService(APIService.class);

        apiService.createProduct(requestBody).enqueue(new Callback<BaseResponseModel<ProductModel>>() {
            @Override
            public void onResponse(Call<BaseResponseModel<ProductModel>> call, Response<BaseResponseModel<ProductModel>> response) {
                data.postValue(response.body());
            }

            @Override
            public void onFailure(Call<BaseResponseModel<ProductModel>> call, Throwable t) {
                Log.e("onFailure", t.getMessage());
            }
        });
        return data;
    }
    Timer timer = new Timer();

    public MutableLiveData<BaseResponseModel<PhotoModel>> uploadImageProduct(List<Uri> uriL, ProductModel model1, HomeActivity activity) {
        MutableLiveData<BaseResponseModel<PhotoModel>> data = new MutableLiveData<BaseResponseModel<PhotoModel>>();

        MultipartBody.Builder builder = new MultipartBody.Builder();
        for (int i = 0; i < uriL.size(); i++) {
            Uri uri = uriL.get(i);
            File file = new File(getRealPathFromURI(uri, activity));
            RequestBody requestBody = RequestBody.create(
                    MediaType.parse("image/*"),
                    file);
            builder.addFormDataPart("product_photo", file.getName(), requestBody);
            builder.addFormDataPart("product_id", model1.getProduct_id());
            builder.addFormDataPart("detect", "create_image_product")
                    .setType(MultipartBody.FORM);

            MultipartBody requestBody2 = builder.build();
            APIService apiService = ServiceGenerator.createService(APIService.class);

            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    apiService.createPhotoProduct(requestBody2).enqueue(new Callback<BaseResponseModel<PhotoModel>>() {
                        @Override
                        public void onResponse(Call<BaseResponseModel<PhotoModel>> call, Response<BaseResponseModel<PhotoModel>> response) {
                            data.postValue(response.body());
                        }

                        @Override
                        public void onFailure(Call<BaseResponseModel<PhotoModel>> call, Throwable t) {
                            Log.e("onFailure", t.getMessage());
                        }
                    });
                }

            }, 5000);
        }

        return data;
    }

    private String getRealPathFromURI(Uri uri, HomeActivity activity) {
        String result;
        Cursor cursor = activity.getContentResolver().query(uri, null, null, null, null);
        if (cursor == null) {
            result = uri.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }
}
