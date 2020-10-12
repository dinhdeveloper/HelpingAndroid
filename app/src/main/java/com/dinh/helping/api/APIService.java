package com.dinh.helping.api;

import com.dinh.helping.model.CategoryModel;
import com.dinh.helping.model.ProductModel;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface APIService {

    @GET("product/list")
    Call<List<ProductModel>> getAllProduct();

    @GET("category/list")
    Call<List<CategoryModel>> getAllCategory();
//
//    @GET("/product/search={search}")
//    Call<List<Product>> searchProduct(@Path("search") String search);
//
//    @Headers(Consts.HEADES)
//    @POST(Consts.REST_ENDPOINT)
//    Call<BaseResponseModel<BookingResultModel>> updateImage(@Body RequestBody params);
}
