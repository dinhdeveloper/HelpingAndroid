package com.dinh.helping.viewmodel.search;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dinh.helping.model.BaseResponseModel;
import com.dinh.helping.model.ProductModel;
import com.dinh.helping.repository.search.SearchRepository;

public class SearchViewModel extends ViewModel {
    private MutableLiveData<BaseResponseModel<ProductModel>> data;
    private SearchRepository repository;

    public void init() {
        if (data != null) {
            return;
        }
        repository = SearchRepository.getInstance();
    }

    public void searchProduct(String product_name,String city_id,String district_id,String ward_id) {
        data = repository.getListSearchProduct(product_name,city_id,district_id,ward_id);
    }

    public MutableLiveData<BaseResponseModel<ProductModel>> getListSearch(){
        if (data == null) {
            data = new MutableLiveData<>();
        }
        return data;
    }
}
