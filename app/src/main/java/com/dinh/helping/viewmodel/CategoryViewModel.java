package com.dinh.helping.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dinh.helping.model.CategoryModel;
import com.dinh.helping.repository.CategoryRepository;

import java.util.List;

public class CategoryViewModel extends ViewModel {
    private MutableLiveData<List<CategoryModel>> data;
    private CategoryRepository repository;

    public void init() {
        if (data != null) {
            return;
        }
        repository = CategoryRepository.getInstance();
        data = repository.getDataCategory();
    }

    public LiveData<List<CategoryModel>> getListCategory(){
        return data;
    }
}
