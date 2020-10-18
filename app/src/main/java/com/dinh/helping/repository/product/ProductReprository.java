package com.dinh.helping.repository.product;

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

public class ProductReprository  {
    public static ProductReprository instance;


    public static ProductReprository getInstance(){
        if (instance==null){
            instance = new ProductReprository();
        }
        return instance;
    }

    public MutableLiveData<BaseResponseModel<ProductModel>> getDataProduct() {
        MutableLiveData<BaseResponseModel<ProductModel>> data = new MutableLiveData<BaseResponseModel<ProductModel>>();
        APIService apiService = ServiceGenerator.createService(APIService.class);
        ApiParams params = new ApiParams();
        params.detect = "list_product";
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
}
