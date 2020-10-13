package com.dinh.helping.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.dinh.helping.api.APIService;
import com.dinh.helping.api.APIUntil;
import com.dinh.helping.model.CategoryModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryRepository  {
    public static CategoryRepository instance;
    APIService apiService = APIUntil.getServer();

    public static CategoryRepository getInstance(){
        if (instance==null){
            instance = new CategoryRepository();
        }
        return instance;
    }

    public MutableLiveData<List<CategoryModel>> getDataCategory() {
        MutableLiveData<List<CategoryModel>> data = new MutableLiveData<List<CategoryModel>>();
        apiService.getAllCategory().enqueue(new Callback<List<CategoryModel>>() {
            @Override
            public void onResponse(Call<List<CategoryModel>> call, Response<List<CategoryModel>> response) {
                if (response.isSuccessful()) {
                    data.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<CategoryModel>> call, Throwable t) {
                Log.e("onFailure", t.getMessage());
            }
        });

        return data;
    }
}
