package com.whyte.test.data.api

import com.whyte.test.data.model.CategoryModel
import com.whyte.test.data.model.ListModel
import com.whyte.test.data.model.LoginModel
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Headers
import retrofit2.http.POST

interface TestApi {

    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @Field("phone") email: String,
        @Field("password") password: String
    ): Response<LoginModel>

    @Headers("AuthNotNeed:true")
    @POST("getCategories")
    suspend fun getCategory(
    ): Response<CategoryModel>


    @FormUrlEncoded
    @POST("list_products")
    suspend fun getList(
        @Field("page") page: Int,
        @Field("main_category") main_category: Int,
        @Field("sub_category") sub_category: Int,
    ): Response<ListModel>

}