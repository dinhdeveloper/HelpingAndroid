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

    public void searchProduct(String product_name) {
        data = repository.getListSearchProduct(product_name);
    }

    public LiveData<BaseResponseModel<ProductModel>> getListSearch(){
        return data;
    }
}
