package com.voterid.imagepload.Interface;


import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.voterid.imagepload.login.LoginPojo;
import com.voterid.imagepload.login.otp.OtpPojo;

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

    @Multipart
    @POST("CitizenRegister/AppCitizenRegisterCreate")
    Observable<ResponseBody> CitizenRegister(@PartMap HashMap<String, JsonArray> listHashMap,
                                             @Part List<MultipartBody.Part> files);

    @POST("Campaign/AppCampaignManagerDetail")
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Observable<OtpPojo> getmanager(@Body JsonObject jsonObject);

    @POST("CitizenRegister/AppCitizenRegisterList")
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Observable<OtpPojo> CityZenList(@Body JsonObject jsonObject);

}
