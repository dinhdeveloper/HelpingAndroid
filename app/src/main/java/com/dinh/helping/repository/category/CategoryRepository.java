package com.dinh.helping.repository.category;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.dinh.helping.api.APIService;
import com.dinh.helping.api.ServiceGenerator;
import com.dinh.helping.model.ApiParams;
import com.dinh.helping.model.BaseResponseModel;
import com.dinh.helping.model.CategoryModel;
import com.dinh.helping.model.ProductModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryRepository {
    public static CategoryRepository instance;

    public static CategoryRepository getInstance() {
        if (instance == null) {
            instance = new CategoryRepository();
        }
        return instance;
    }

    public MutableLiveData<BaseResponseModel<CategoryModel>> getDataCategory() {
        MutableLiveData<BaseResponseModel<CategoryModel>> data = new MutableLiveData<BaseResponseModel<CategoryModel>>();
        APIService apiService = ServiceGenerator.createService(APIService.class);
        ApiParams params = new ApiParams();
        params.detect = "list_category";
        apiService.getAllCategory(params).enqueue(new Callback<BaseResponseModel<CategoryModel>>() {
            @Override
            public void onResponse(Call<BaseResponseModel<CategoryModel>> call, Response<BaseResponseModel<CategoryModel>> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<BaseResponseModel<CategoryModel>> call, Throwable t) {
                Log.e("onFailure", t.getMessage());
            }
        });
        return data;
    }

    public MutableLiveData<BaseResponseModel<ProductModel>> getDataProductByCategory(CategoryModel model1) {
        MutableLiveData<BaseResponseModel<ProductModel>> data = new MutableLiveData<BaseResponseModel<ProductModel>>();
        APIService apiService = ServiceGenerator.createService(APIService.class);
        ApiParams params = new ApiParams();
        params.detect = "list_product";
        params.category_id = model1.getCategory_id();
        apiService.getAllProduct(params).enqueue(new Callback<BaseResponseModel<ProductModel>>() {
            @Override
            public void onResponse(Call<BaseResponseModel<ProductModel>> call, Response<BaseResponseModel<ProductModel>> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<BaseResponseModel<ProductModel>> call, Throwable t) {
                Log.e("onFailure", t.getMessage());
            }
        });
        return data;
    }
}
