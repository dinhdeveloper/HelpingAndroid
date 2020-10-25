package com.dinh.helping.repository.search;


import android.text.TextUtils;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.dinh.helping.api.APIService;
import com.dinh.helping.api.ServiceGenerator;
import com.dinh.helping.model.ApiParams;
import com.dinh.helping.model.BaseResponseModel;
import com.dinh.helping.model.ProductModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchRepository {
    public static SearchRepository instance;

    public static SearchRepository getInstance() {
        if (instance == null) {
            instance = new SearchRepository();
        }
        return instance;
    }

    public MutableLiveData<BaseResponseModel<ProductModel>> getListSearchProduct(String product_name, String city_id, String district_id, String ward_id) {
        MutableLiveData<BaseResponseModel<ProductModel>> data = new MutableLiveData<BaseResponseModel<ProductModel>>();
        APIService apiService = ServiceGenerator.createService(APIService.class);
        ApiParams params = new ApiParams();
        params.detect = "search_product";
        if (!TextUtils.isEmpty(product_name)) {
            params.filter = product_name;
        }
        if (!TextUtils.isEmpty(city_id)) {
            params.city_id = city_id;
        }
        if (!TextUtils.isEmpty(district_id)) {
            params.district_id = district_id;
        }
        if (!TextUtils.isEmpty(ward_id)) {
            params.ward_id = ward_id;
        }
        apiService.searchProduct(params).enqueue(new Callback<BaseResponseModel<ProductModel>>() {
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
