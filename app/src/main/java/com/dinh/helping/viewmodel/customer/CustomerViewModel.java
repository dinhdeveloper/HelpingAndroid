package com.dinh.helping.viewmodel.customer;

import android.text.TextUtils;

import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.canhdinh.lib.alert.CustomAlertDialog;
import com.dinh.helping.R;
import com.dinh.helping.activity.HomeActivity;
import com.dinh.helping.fragment.dashboard.DashboardFragment;
import com.dinh.helping.fragment.login_nomal.LoginFragment;
import com.dinh.helping.fragment.profile.info.ProfileFragment;
import com.dinh.helping.fragment.profile.verify.VeryCodeFragment;
import com.dinh.helping.fragment.register.RegisterFragment;
import com.dinh.helping.model.BaseResponseModel;
import com.dinh.helping.model.ProductModel;
import com.dinh.helping.model.UserResponseModel;
import com.dinh.helping.repository.category.CategoryRepository;
import com.dinh.helping.repository.customer.CustomerRepository;


public class CustomerViewModel extends ViewModel {
    private MutableLiveData<BaseResponseModel<ProductModel>> data;
    private MutableLiveData<String> phone = new MutableLiveData<>();
    private MutableLiveData<BaseResponseModel<UserResponseModel>> checkCreate = new MutableLiveData<>();
    private MutableLiveData<Boolean> checkLogin = new MutableLiveData<>();
    private MutableLiveData<Boolean> checkLoginFb = new MutableLiveData<>();
    private MutableLiveData<BaseResponseModel<UserResponseModel>> login = new MutableLiveData<>();
    private MutableLiveData<BaseResponseModel> phoneNumber = new MutableLiveData<>();
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

    public MutableLiveData<BaseResponseModel<UserResponseModel>> login(UserResponseModel model) {
        login = repository.login(model);
        return login;
    }

    public MutableLiveData<BaseResponseModel<UserResponseModel>> checkLogin() {
        return login;
    }

    public void changToFragmentLogin(HomeActivity activity) {
        checkLogin.setValue(false);
        activity.hideBottomBar();
        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.layoutRoot, new LoginFragment());
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void changToFragmentProfile(HomeActivity activity, BaseResponseModel<UserResponseModel> user) {
        activity.showBottomBar();
        activity.replaceFragment(new ProfileFragment(), false);
        new CustomAlertDialog(activity, CustomAlertDialog.SUCCESS_TYPE)
                .setTitleText("Đăng nhập thành công.")
                .show();
    }

    public void changToFragmentProfile2(HomeActivity activity) {
        activity.showBottomBar();
        activity.replaceFragment(new ProfileFragment(), false);
        new CustomAlertDialog(activity, CustomAlertDialog.SUCCESS_TYPE)
                .setTitleText("Đăng nhập thành công.")
                .show();
    }

    public void checkPhone(String phone) {
        phoneNumber = repository.checkPhone(phone);
    }

    public MutableLiveData<BaseResponseModel> getCheckPhone() {
        return phoneNumber;
    }
}
