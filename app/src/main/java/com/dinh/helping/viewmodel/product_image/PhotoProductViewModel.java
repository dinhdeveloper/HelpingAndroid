package com.dinh.helping.viewmodel.product_image;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dinh.helping.model.BaseResponseModel;
import com.dinh.helping.model.PhotoModel;
import com.dinh.helping.repository.product_image.PhotoProductRepository;

public class PhotoProductViewModel extends ViewModel {
    private MutableLiveData<BaseResponseModel<PhotoModel>> data;
    private MutableLiveData<BaseResponseModel<PhotoModel>> data_by_date;
    private MutableLiveData<BaseResponseModel<PhotoModel>> data_create;
    private MutableLiveData<PhotoModel> mSelectedItem = new MutableLiveData<>();
    private PhotoProductRepository repository;

    public void init() {
        if (data != null) {
            return;
        }
        repository = PhotoProductRepository.getInstance();
    }


    public void createPhotoProduct(PhotoModel model) {
        data_create = repository.createPhotoProduct(model);
    }

    public MutableLiveData<BaseResponseModel<PhotoModel>> getDataCreateProduct(){
        return data_create;
    }
}
