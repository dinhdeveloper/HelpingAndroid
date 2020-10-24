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

public class SearchRepository  {
    public static SearchRepository instance;

    public static SearchRepository getInstance() {
        if (instance == null) {
            instance = new SearchRepository();
        }
        return instance;
    }

    public MutableLiveData<BaseResponseModel<ProductModel>> getListSearchProduct(String product_name) {
        MutableLiveData<BaseResponseModel<ProductModel>> data = new MutableLiveData<BaseResponseModel<ProductModel>>();
        APIService apiService = ServiceGenerator.createService(APIService.class);
        ApiParams params = new ApiParams();
        params.detect = "search_product";
        if (!TextUtils.isEmpty(product_name)){
            params.search = product_name;
        }
        apiService.searchProduct(params).enqueue(new Callback<BaseResponseModel<ProductModel>>() {
            @Override
            public void onResponse(Call<BaseResponseModel<ProductModel>> call, Response<BaseResponseModel<ProductModel>> response) {
                data.postValue(response.body());
            }

            @Override
            public void onFailure(Call<BaseResponseModel<ProductModel>> call, Throwable t) {
                Log.e("onFailure",t.getMessage());
            }
        });
        return data;
    }
}
