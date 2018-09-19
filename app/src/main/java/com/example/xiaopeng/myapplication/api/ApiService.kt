package com.example.xiaopeng.myapplication.api


import com.example.xiaopeng.myapplication.response.LoginResponse
import com.example.xiaopeng.myapplication.response.ResponseData
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

/**
 * @author xiaopeng
 * @date 2018/9/18
 */
interface ApiService {

    @FormUrlEncoded
    @POST("shopping_login.htm")
    fun LoginByRx(@Field("username") username: String, @Field("password") password: String): Observable<ResponseData<LoginResponse>>

}
