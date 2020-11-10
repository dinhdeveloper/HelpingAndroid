package com.dinh.helping.viewmodel.category;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dinh.helping.model.BaseResponseModel;
import com.dinh.helping.model.CategoryModel;
import com.dinh.helping.model.ProductModel;
import com.dinh.helping.repository.category.CategoryRepository;

public class CategoryViewModel extends ViewModel {
    private MutableLiveData<BaseResponseModel<CategoryModel>> data;
    private MutableLiveData<BaseResponseModel<ProductModel>> productByCategory;
    private CategoryRepository repository;

    public void init() {
        if (data != null) {
            return;
        }
        repository = CategoryRepository.getInstance();
        data = repository.getDataCategory();
    }

    public LiveData<BaseResponseModel<CategoryModel>> getListCategory() {
        return data;
    }

    public void setListProductByCategory(CategoryModel model1) {
        productByCategory = repository.getDataProductByCategory(model1);
    }

    public MutableLiveData<BaseResponseModel<ProductModel>> getProductByCategory() {
        return productByCategory;
    }
}
