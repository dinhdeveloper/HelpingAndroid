package com.dinh.helping.viewmodel.customer;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dinh.helping.model.BaseResponseModel;
import com.dinh.helping.model.ProductModel;
import com.dinh.helping.model.UserResponseModel;
import com.dinh.helping.repository.category.CategoryRepository;
import com.dinh.helping.repository.customer.CustomerRepository;

public class CustomerViewModel extends ViewModel {
    private MutableLiveData<BaseResponseModel<ProductModel>> data;
    private MutableLiveData<String> phone = new MutableLiveData<>();
    private MutableLiveData<BaseResponseModel<UserResponseModel>> checkCreate = new MutableLiveData<>();
    private MutableLiveData<BaseResponseModel<UserResponseModel>> login = new MutableLiveData<>();
    private CustomerRepository repository;

    public void init() {
        if (data != null) {
            return;
        }
        repository = CustomerRepository.getInstance();
    }

    public void setPhoneNumber(String phoneNumber) {
        phone.postValue(phoneNumber);
    }

    public MutableLiveData<String> getPhoneNumber() {
        return phone;
    }

    public void createCustomer(UserResponseModel model) {
        checkCreate = repository.registerAccount(model);
    }
    public MutableLiveData<BaseResponseModel<UserResponseModel>> getCreate() {
        return checkCreate;
    }

    public void login(UserResponseModel model) {
        login = repository.login(model);
    }
    public MutableLiveData<BaseResponseModel<UserResponseModel>> checkLogin() {
        return login;
    }
}
