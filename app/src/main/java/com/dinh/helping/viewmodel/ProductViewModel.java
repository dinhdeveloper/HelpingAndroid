package com.dinh.helping.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dinh.helping.model.ProductModel;
import com.dinh.helping.repository.CategoryRepository;
import com.dinh.helping.repository.ProductReprository;

import java.util.List;

public class ProductViewModel extends ViewModel {
    private MutableLiveData<List<ProductModel>> data;
    private MutableLiveData<ProductModel> mSelectedItem = new MutableLiveData<>();
    private ProductReprository repository;

    public void init() {
        if (data != null) {
            return;
        }
        repository = ProductReprository.getInstance();
        data = repository.getDataProduct();
    }

    public LiveData<List<ProductModel>> getListProduct(){
        return data;
    }

    public void setSelectedItem(ProductModel model) {
        mSelectedItem.postValue(model);
    }

    public LiveData<ProductModel> getSelectedItem() {
        return mSelectedItem;
    }
}
