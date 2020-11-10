package com.dinh.helping.repository.product;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.dinh.helping.api.APIService;
import com.dinh.helping.api.ServiceGenerator;
import com.dinh.helping.helper.SharePrefs;
import com.dinh.helping.model.ApiParams;
import com.dinh.helping.model.BaseResponseModel;
import com.dinh.helping.model.ProductModel;
import com.dinh.helping.model.UserResponseModel;


import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

    public MutableLiveData<BaseResponseModel<ProductModel>> createProduct(ProductModel model,String id_customer) {
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
        builder.addFormDataPart("phone_contact",model.getPhone_contact() );
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
}
