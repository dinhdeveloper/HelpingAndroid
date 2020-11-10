package com.dinh.helping.repository.customer;

import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.dinh.helping.api.APIService;
import com.dinh.helping.api.ServiceGenerator;
import com.dinh.helping.model.ApiParams;
import com.dinh.helping.model.BaseResponseModel;
import com.dinh.helping.model.CategoryModel;
import com.dinh.helping.model.UserResponseModel;
import com.dinh.helping.repository.category.CategoryRepository;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomerRepository {
    public static CustomerRepository instance;

    public static CustomerRepository getInstance() {
        if (instance == null) {
            instance = new CustomerRepository();
        }
        return instance;
    }

    public MutableLiveData<BaseResponseModel<UserResponseModel>> registerAccount(UserResponseModel model) {
        MutableLiveData<BaseResponseModel<UserResponseModel>> data = new MutableLiveData<BaseResponseModel<UserResponseModel>>();

        MultipartBody.Builder builder = new MultipartBody.Builder();
        if (!TextUtils.isEmpty(model.getImage())) {
            File fileAvatar = new File(model.getImage());
            if (fileAvatar.exists()) {
                RequestBody fileBody = RequestBody.create(MediaType.parse("image/*"), fileAvatar);
                builder.addFormDataPart("image", fileAvatar.getName(), fileBody);
            }
        }
        builder.addFormDataPart("full_name", model.getFull_name());
        builder.addFormDataPart("phone_number", model.getPhone_number());
        builder.addFormDataPart("address", model.getAddress());
        if (!TextUtils.isEmpty(model.getGender())){
            builder.addFormDataPart("sex", model.getGender());
        }
        builder.addFormDataPart("password", model.getPassword());
        builder.addFormDataPart("detect", "register")
                .setType(MultipartBody.FORM);
        RequestBody requestBody = builder.build();

        APIService apiService = ServiceGenerator.createService(APIService.class);

        apiService.registerCustomer(requestBody).enqueue(new Callback<BaseResponseModel<UserResponseModel>>() {
            @Override
            public void onResponse(Call<BaseResponseModel<UserResponseModel>> call, Response<BaseResponseModel<UserResponseModel>> response) {
                data.postValue(response.body());
            }

            @Override
            public void onFailure(Call<BaseResponseModel<UserResponseModel>> call, Throwable t) {
                Log.e("onFailure", t.getMessage());
            }
        });
        return data;
    }

    public MutableLiveData<BaseResponseModel<UserResponseModel>> login(UserResponseModel model) {
        MutableLiveData<BaseResponseModel<UserResponseModel>> data = new MutableLiveData<BaseResponseModel<UserResponseModel>>();
        APIService apiService = ServiceGenerator.createService(APIService.class);
        ApiParams params = new ApiParams();
        params.detect = "login";
        params.phone_number = model.getPhone_number();
        params.password = model.getPassword();

        apiService.login(params).enqueue(new Callback<BaseResponseModel<UserResponseModel>>() {
            @Override
            public void onResponse(Call<BaseResponseModel<UserResponseModel>> call, Response<BaseResponseModel<UserResponseModel>> response) {
                data.postValue(response.body());
            }

            @Override
            public void onFailure(Call<BaseResponseModel<UserResponseModel>> call, Throwable t) {
                Log.e("onFailure",t.getMessage());
            }
        });

        return data;
    }

    public MutableLiveData<BaseResponseModel> checkPhone(String phone) {
        MutableLiveData<BaseResponseModel> data = new MutableLiveData<BaseResponseModel>();
        APIService apiService = ServiceGenerator.createService(APIService.class);
        ApiParams params = new ApiParams();
        params.detect = "check_login";
        params.phone_number = phone;

        apiService.checkLogin(params).enqueue(new Callback<BaseResponseModel>() {
            @Override
            public void onResponse(Call<BaseResponseModel> call, Response<BaseResponseModel> response) {
                data.postValue(response.body());
            }

            @Override
            public void onFailure(Call<BaseResponseModel> call, Throwable t) {
                Log.e("onFailure",t.getMessage());
            }
        });

        return data;
    }
}
