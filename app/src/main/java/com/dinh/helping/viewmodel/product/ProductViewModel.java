package com.dinh.helping.viewmodel.product;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dinh.helping.model.BaseResponseModel;
import com.dinh.helping.model.ProductModel;
import com.dinh.helping.repository.product.ProductReprository;

public class ProductViewModel extends ViewModel {
    private MutableLiveData<BaseResponseModel<ProductModel>> data;
    private MutableLiveData<BaseResponseModel<ProductModel>> data_by_date;
    private MutableLiveData<ProductModel> mSelectedItem = new MutableLiveData<>();
    private ProductReprository repository;

    public void init() {
        if (data != null) {
            return;
        }
        repository = ProductReprository.getInstance();
        data = repository.getDataProduct();
    }

    public LiveData<BaseResponseModel<ProductModel>> getListProduct() {
        return data;
    }

    public LiveData<BaseResponseModel<ProductModel>> getListProductByDate() {
        return data_by_date = repository.getAllProductByDate();
    }

    public void setSelectedItem(ProductModel model) {
        mSelectedItem.postValue(model);
    }

    public LiveData<ProductModel> getSelectedItem() {
        return mSelectedItem;
    }
}
