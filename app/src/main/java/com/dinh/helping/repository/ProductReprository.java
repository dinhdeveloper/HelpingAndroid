package com.dinh.helping.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.dinh.helping.api.APIService;
import com.dinh.helping.api.APIUntil;
import com.dinh.helping.model.ProductModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductReprository  {
    public static ProductReprository instance;
    APIService apiService = APIUntil.getServer();

    public static ProductReprository getInstance(){
        if (instance==null){
            instance = new ProductReprository();
        }
        return instance;
    }

    public MutableLiveData<List<ProductModel>> getDataProduct() {
        MutableLiveData<List<ProductModel>> data = new MutableLiveData<List<ProductModel>>();
        apiService.getAllProduct().enqueue(new Callback<List<ProductModel>>() {
            @Override
            public void onResponse(Call<List<ProductModel>> call, Response<List<ProductModel>> response) {
                if (response.isSuccessful()) {
                    data.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<ProductModel>> call, Throwable t) {
                Log.e("onFailure", t.getMessage());
            }
        });
        return data;
    }
}
