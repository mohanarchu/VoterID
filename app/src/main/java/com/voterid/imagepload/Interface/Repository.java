package com.voterid.imagepload.Interface;

import com.google.gson.JsonObject;
import com.voterid.imagepload.login.LoginPojo;
import com.voterid.imagepload.pojo.AreaPojo;
import com.voterid.imagepload.pojo.AssemblyPojo;
import com.voterid.imagepload.pojo.BoothsPojo;
import com.voterid.imagepload.pojo.UpdatePojo;
import com.voterid.imagepload.pojo.UserPojo;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class Repository {

    public static Observable<AssemblyPojo> getAssembly() {
        JsonObject  jsonObject= new JsonObject();
        jsonObject.addProperty("table","assembly");
        jsonObject.addProperty("reference","");
        jsonObject.addProperty("id","");


        return NetworkClient.getRetrofit().create(MainInterface.class)
                .getAssemnly(jsonObject)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    public static  Observable<BoothsPojo> getBooths() {
        JsonObject  jsonObject= new JsonObject();
        jsonObject.addProperty("table","assemblybooths");
        jsonObject.addProperty("reference","");
        jsonObject.addProperty("id","");

        return NetworkClient.getRetrofit().create(MainInterface.class)
                .getAsBooths(jsonObject)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static Observable<AreaPojo> getArea(String id) {
        JsonObject  jsonObject= new JsonObject();
        jsonObject.addProperty("table","area");
        jsonObject.addProperty("reference","booth_id");
        jsonObject.addProperty("id",id);

        return NetworkClient.getRetrofit().create(MainInterface.class)
                .getArea(jsonObject)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static Observable<UserPojo> getUsers(String epicId) {
        JsonObject  jsonObject= new JsonObject();
        jsonObject.addProperty("table","user_table");
        jsonObject.addProperty("reference","epic_number");
        jsonObject.addProperty("id",epicId);

        return NetworkClient.getRetrofit().create(MainInterface.class)
                .getUser(jsonObject)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static Observable<UpdatePojo> update(JsonObject jsonObject) {

        return NetworkClient.getRetrofit().create(MainInterface.class)
                .citizenRegister(jsonObject)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
