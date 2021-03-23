package com.voterid.imagepload.Interface;


import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.voterid.imagepload.login.LoginPojo;
import com.voterid.imagepload.login.otp.OtpPojo;
import com.voterid.imagepload.pojo.AreaPojo;
import com.voterid.imagepload.pojo.AssemblyPojo;
import com.voterid.imagepload.pojo.BoothsPojo;
import com.voterid.imagepload.pojo.UpdatePojo;
import com.voterid.imagepload.pojo.UserPojo;

import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

public interface MainInterface {
    @POST("Campaign/AppCampaignLoginValidate")
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Observable<LoginPojo> getLoginstatus(@Body JsonObject jsonObject);

    @POST("Campaign/AppCampaignOTPValidate")
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Observable<OtpPojo> getBooths(@Body JsonObject jsonObject);

//    @Multipart
//    @POST("uploadFile")
//    Observable<ResponseBody> CitizenRegister(@Part MultipartBody.Part files);

    @POST("update")
    Observable<UpdatePojo> citizenRegister(@Body JsonObject jsonObject);

    @POST("Campaign/AppCampaignManagerDetail")
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Observable<OtpPojo> getmanager(@Body JsonObject jsonObject);

    @POST("CitizenRegister/AppCitizenRegisterList")
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Observable<OtpPojo> CityZenList(@Body JsonObject jsonObject);

    @POST("getDetails")
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Observable<AssemblyPojo> getAssemnly(@Body JsonObject jsonObject);

    @POST("getDetails")
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Observable<BoothsPojo> getAsBooths(@Body JsonObject jsonObject);

    @POST("getDetails")
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Observable<AreaPojo> getArea(@Body JsonObject jsonObject);

    @POST("getDetails")
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Observable<UserPojo> getUser(@Body JsonObject jsonObject);

}
