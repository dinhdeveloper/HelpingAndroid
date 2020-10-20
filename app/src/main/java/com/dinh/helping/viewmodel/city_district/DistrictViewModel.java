package com.dinh.helping.viewmodel.city_district;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dinh.helping.model.BaseResponseModel;
import com.dinh.helping.model.CategoryModel;
import com.dinh.helping.model.DistrictModel;
import com.dinh.helping.repository.city_district.CityRepository;
import com.dinh.helping.repository.city_district.DistrictRepository;

public class DistrictViewModel extends ViewModel {
    private MutableLiveData<BaseResponseModel<DistrictModel>> data;
    private DistrictRepository repository;

    public void init() {
        if (data != null) {
            return;
        }
        repository = DistrictRepository.getInstance();
    }

    public void getListDistrictByCity(String city_id) {
        data = repository.getListDistrictByCity(city_id);
    }

    public LiveData<BaseResponseModel<DistrictModel>> getListDistrictByCity(){
        return data;
    }
}
