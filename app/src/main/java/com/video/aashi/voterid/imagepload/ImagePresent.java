package com.video.aashi.voterid.imagepload;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import com.google.api.client.json.Json;
import com.google.gson.JsonObject;
import com.video.aashi.voterid.imagepload.Interface.MainInterface;
import com.video.aashi.voterid.imagepload.Interface.NetworkClient;
import com.video.aashi.voterid.imagepload.sesssion.Sessions;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.HttpException;
import retrofit2.http.Part;

public class ImagePresent {


    ImgaeModel imagePresent;
    Context context;
    Sessions menuStrings;
    int positions;
    int counters=0;
    public ImagePresent(Context context, ImgaeModel imagePresent)
    {
        this.context = context;
        this.imagePresent = imagePresent;
    }
    @SuppressLint("CheckResult")
    public void uploadComplaint()
    {

//        getObservable( ).subscribeWith(getCoomplaint());


    }

    public DisposableObserver<ResponseBody> getCoomplaint()
    {
        return new DisposableObserver<ResponseBody>() {
            @Override
            public void onNext(ResponseBody responseBody) {
                imagePresent.hideProgress();
                menuStrings = new Sessions(context);
                String bodyString = null;
                try {
                    bodyString = responseBody.string();
                    String source;
                    JSONObject jsonObject = new JSONObject(bodyString);
                    Log.i("Tag","ComplaintsError"+ bodyString);
                    if(jsonObject.getString("Status").contains("true") )
                    {
                        if (menuStrings.isChange())
                        {
                            imagePresent.showToast("Uploaded successfully...!");
                        }
                        else
                        {
                            imagePresent.showToast("வெற்றிகரமாக பதிவேற்றப்பட்டது...!");
                        }
                        counters = counters+1;
                        imagePresent.removePos(counters);
                    }
                    else
                    {
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                imagePresent.hideProgress();
            }
            @Override
            public void onError(Throwable ee) {
                imagePresent.hideProgress();
                String bodyString = null;
                if(ee instanceof HttpException) {
                    if ( ((HttpException) ee).response().code() == 400)
                    {
                        imagePresent.showToast("User already present in list..!!");
                    }
                    else
                    {
                        imagePresent.showToast("Please try again..!");
                    }
                }



            }
            @Override
            public void onComplete() {

            }
        };
    }
}
