package com.dinh.helping.repository.city_district;

import android.text.TextUtils;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.dinh.helping.api.APIService;
import com.dinh.helping.api.ServiceGenerator;
import com.dinh.helping.model.ApiParams;
import com.dinh.helping.model.BaseResponseModel;
import com.dinh.helping.model.DistrictModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DistrictRepository  {
    public static DistrictRepository instance;
    public static DistrictRepository getInstance(){
        if (instance==null){
            instance = new DistrictRepository();
        }
        return instance;
    }

    public MutableLiveData<BaseResponseModel<DistrictModel>> getListDistrictByCity(String city_id) {
        MutableLiveData<BaseResponseModel<DistrictModel>> data = new MutableLiveData<>();
        APIService apiService = ServiceGenerator.createService(APIService.class);
        ApiParams params = new ApiParams();
        params.detect = "list_district";
        if (!TextUtils.isEmpty(city_id)){
            params.city_id = city_id;
        }
        apiService.getListDistrictByCity(params).enqueue(new Callback<BaseResponseModel<DistrictModel>>() {
            @Override
            public void onResponse(Call<BaseResponseModel<DistrictModel>> call, Response<BaseResponseModel<DistrictModel>> response) {
                data.postValue(response.body());
            }
            @Override
            public void onFailure(Call<BaseResponseModel<DistrictModel>> call, Throwable t) {
                Log.e("onFailure",t.getMessage());
            }
        });
        return data;
    }
}
