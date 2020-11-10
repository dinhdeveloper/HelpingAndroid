package com.dinh.helping.viewmodel.product;

import android.content.Intent;

import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dinh.helping.R;
import com.dinh.helping.activity.HomeActivity;
import com.dinh.helping.fragment.login_nomal.LoginFragment;
import com.dinh.helping.fragment.product_detail.ProductDetailFragment;
import com.dinh.helping.fragment.profile.info.ProfileFragment;
import com.dinh.helping.model.BaseResponseModel;
import com.dinh.helping.model.ProductModel;
import com.dinh.helping.repository.product.ProductReprository;

public class ProductViewModel extends ViewModel {
    private MutableLiveData<BaseResponseModel<ProductModel>> data;
    private MutableLiveData<BaseResponseModel<ProductModel>> data_by_date;
    private MutableLiveData<BaseResponseModel<ProductModel>> data_create;
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
        return data = repository.getDataProduct();
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

    public MutableLiveData<BaseResponseModel<ProductModel>> createProduct(ProductModel model,String id_customer) {
        data_create = repository.createProduct(model,id_customer);
        return data_create;
    }

    public void changToFragmentLogin(HomeActivity activity) {
        activity.hideBottomBar();
        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.layoutRoot, new LoginFragment());
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
