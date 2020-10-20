package com.dinh.helping.viewmodel.city_district;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dinh.helping.model.BaseResponseModel;
import com.dinh.helping.model.WardModel;
import com.dinh.helping.repository.city_district.DistrictRepository;
import com.dinh.helping.repository.city_district.WardRepository;

public class WardViewModel extends ViewModel {
    private MutableLiveData<BaseResponseModel<WardModel>> data;
    private WardRepository repository;

    public void init() {
        if (data != null) {
            return;
        }
        repository = WardRepository.getInstance();
    }

    public void getListWardByDistrict(String district_id) {
        data = repository.getListWardByDistrict(district_id);
    }

    public LiveData<BaseResponseModel<WardModel>> getListWardByDistrict(){
        return data;
    }
}
