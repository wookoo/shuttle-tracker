package me.synology.wookoo.shuttle;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface RetrofitAPI {

    @FormUrlEncoded
    @POST("login/register/")
    Call<registerDATA> register(@FieldMap HashMap<String,Object> body);
}
