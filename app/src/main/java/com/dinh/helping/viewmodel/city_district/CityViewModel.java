package com.dinh.helping.viewmodel.city_district;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dinh.helping.model.BaseResponseModel;
import com.dinh.helping.model.CategoryModel;
import com.dinh.helping.model.CityModel;
import com.dinh.helping.repository.category.CategoryRepository;
import com.dinh.helping.repository.city_district.CityRepository;

public class CityViewModel extends ViewModel {
    private MutableLiveData<BaseResponseModel<CityModel>> data;
    private CityRepository repository;

    public void init() {
        if (data != null) {
            return;
        }
        repository = CityRepository.getInstance();
        data = repository.getListCity();
    }

    public LiveData<BaseResponseModel<CityModel>> getListCity(){
        return data;
    }
}
