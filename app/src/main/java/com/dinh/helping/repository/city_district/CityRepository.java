package com.dinh.helping.repository.city_district;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.dinh.helping.api.APIService;
import com.dinh.helping.api.ServiceGenerator;
import com.dinh.helping.model.ApiParams;
import com.dinh.helping.model.BaseResponseModel;
import com.dinh.helping.model.CityModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CityRepository {
    public static CityRepository instance;
    public static CityRepository getInstance(){
        if (instance==null){
            instance = new CityRepository();
        }
        return instance;
    }

    public MutableLiveData<BaseResponseModel<CityModel>> getListCity(){
        MutableLiveData<BaseResponseModel<CityModel>> data = new MutableLiveData<>();
        APIService apiService = ServiceGenerator.createService(APIService.class);
        ApiParams params = new ApiParams();
        params.detect = "list_city";
        apiService.getListCity(params).enqueue(new Callback<BaseResponseModel<CityModel>>() {
            @Override
            public void onResponse(Call<BaseResponseModel<CityModel>> call, Response<BaseResponseModel<CityModel>> response) {
                    data.postValue(response.body());
            }

            @Override
            public void onFailure(Call<BaseResponseModel<CityModel>> call, Throwable t) {
                Log.e("onFailure",t.getMessage());
            }
        });
        return data;
    }
}
