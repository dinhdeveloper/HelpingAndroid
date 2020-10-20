package com.dinh.helping.repository.city_district;

import android.text.TextUtils;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.dinh.helping.api.APIService;
import com.dinh.helping.api.ServiceGenerator;
import com.dinh.helping.model.ApiParams;
import com.dinh.helping.model.BaseResponseModel;
import com.dinh.helping.model.WardModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WardRepository  {
    public static WardRepository instance;
    public static WardRepository getInstance(){
        if (instance==null){
            instance = new WardRepository();
        }
        return instance;
    }

    public MutableLiveData<BaseResponseModel<WardModel>> getListWardByDistrict(String district_id) {
        MutableLiveData<BaseResponseModel<WardModel>> data = new MutableLiveData<>();
        APIService apiService = ServiceGenerator.createService(APIService.class);
        ApiParams params = new ApiParams();
        params.detect = "list_ward";
        if (!TextUtils.isEmpty(district_id)){
            params.district_id = district_id;
        }
        apiService.getListWardByDistrict(params).enqueue(new Callback<BaseResponseModel<WardModel>>() {
            @Override
            public void onResponse(Call<BaseResponseModel<WardModel>> call, Response<BaseResponseModel<WardModel>> response) {
                data.postValue(response.body());
            }
            @Override
            public void onFailure(Call<BaseResponseModel<WardModel>> call, Throwable t) {
                Log.e("onFailure",t.getMessage());
            }
        });
        return data;
    }
}
