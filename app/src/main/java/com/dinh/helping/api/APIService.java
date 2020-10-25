package com.dinh.helping.api;

import com.dinh.helping.helper.Consts;
import com.dinh.helping.model.ApiParams;
import com.dinh.helping.model.BaseResponseModel;
import com.dinh.helping.model.CategoryModel;
import com.dinh.helping.model.CityModel;
import com.dinh.helping.model.DistrictModel;
import com.dinh.helping.model.ProductModel;
import com.dinh.helping.model.UserResponseModel;
import com.dinh.helping.model.WardModel;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {

    //    @Headers({
//            "Content-Type: application/json",
//            "Authorization: Basic YWRtaW46cXRjdGVrQDEyMwx=="
//    })
    @POST(Consts.REST_ENDPOINT)
    Call<BaseResponseModel<CategoryModel>> getAllCategory(@Body ApiParams params);

    @POST(Consts.REST_ENDPOINT)
    Call<BaseResponseModel<ProductModel>> getAllProduct(@Body ApiParams params);

    @POST(Consts.REST_ENDPOINT)
    Call<BaseResponseModel<ProductModel>> getAllProductByDate(@Body ApiParams params);


    @POST(Consts.REST_ENDPOINT)
    Call<BaseResponseModel<UserResponseModel>> registerCustomer(@Body RequestBody params);


    @POST(Consts.REST_ENDPOINT)
    Call<BaseResponseModel<UserResponseModel>> login(@Body ApiParams params);


    @POST(Consts.REST_ENDPOINT)
    Call<BaseResponseModel<CityModel>> getListCity(@Body ApiParams params);

    @POST(Consts.REST_ENDPOINT)
    Call<BaseResponseModel<DistrictModel>> getListDistrictByCity(@Body ApiParams params);

    @POST(Consts.REST_ENDPOINT)
    Call<BaseResponseModel<WardModel>> getListWardByDistrict(@Body ApiParams params);

    @POST(Consts.REST_ENDPOINT)
    Call<BaseResponseModel<ProductModel>> searchProduct(@Body ApiParams params);
}
