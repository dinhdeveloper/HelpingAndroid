package com.dinh.helping.repository.product_image;

import android.text.TextUtils;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.dinh.helping.api.APIService;
import com.dinh.helping.api.ServiceGenerator;
import com.dinh.helping.model.ApiParams;
import com.dinh.helping.model.BaseResponseModel;
import com.dinh.helping.model.PhotoModel;
import com.dinh.helping.model.ProductModel;
import com.dinh.helping.repository.product.ProductReprository;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PhotoProductRepository  {
    public static PhotoProductRepository instance;


    public static PhotoProductRepository getInstance(){
        if (instance==null){
            instance = new PhotoProductRepository();
        }
        return instance;
    }

    public MutableLiveData<BaseResponseModel<PhotoModel>> createPhotoProduct(PhotoModel model) {
        MutableLiveData<BaseResponseModel<PhotoModel>> data = new MutableLiveData<BaseResponseModel<PhotoModel>>();
        MultipartBody.Builder builder = new MultipartBody.Builder();
        if (!TextUtils.isEmpty(model.getProduct_photo())) {
            File fileAvatar = new File(model.getProduct_photo());
            if (fileAvatar.exists()) {
                RequestBody fileBody = RequestBody.create(MediaType.parse("image/*"), fileAvatar);
                builder.addFormDataPart("product_photo", fileAvatar.getName(), fileBody);
            }
        }
        builder.addFormDataPart("detect", "create_product")
                .setType(MultipartBody.FORM);
        RequestBody requestBody = builder.build();

        APIService apiService = ServiceGenerator.createService(APIService.class);

        apiService.createPhotoProduct(requestBody).enqueue(new Callback<BaseResponseModel<PhotoModel>>() {
            @Override
            public void onResponse(Call<BaseResponseModel<PhotoModel>> call, Response<BaseResponseModel<PhotoModel>> response) {
                data.postValue(response.body());
            }

            @Override
            public void onFailure(Call<BaseResponseModel<PhotoModel>> call, Throwable t) {
                Log.e("onFailure", t.getMessage());
            }
        });
        return data;
    }
}
