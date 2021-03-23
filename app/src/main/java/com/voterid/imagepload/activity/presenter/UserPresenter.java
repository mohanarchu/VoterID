package com.voterid.imagepload.activity.presenter;

import com.google.api.client.json.Json;
import com.google.gson.JsonObject;
import com.voterid.imagepload.Interface.Repository;
import com.voterid.imagepload.pojo.AssemblyPojo;
import com.voterid.imagepload.pojo.UpdatePojo;
import com.voterid.imagepload.pojo.UserPojo;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class UserPresenter {

    UserView userView;
    public UserPresenter(UserView userView) {
        this.userView = userView;
    }


    public void getUser(String epicId,int position,String epicIdNew){
        userView.showProgress();
        Repository.getUsers(epicId).subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<UserPojo>() {
            @Override
            public void onSubscribe(Disposable d) {

            }
            @Override
            public void onNext(UserPojo loginModel) {
                if (loginModel.getResult() != null && loginModel.getResult().length > 0) {
                    userView.showDetails(loginModel.getResult(),position,epicIdNew);
                } else {
                    userView.error("Not found");
                }
            }

            @Override
            public void onError(Throwable e) {


            }

            @Override
            public void onComplete() {
                userView.hideProgress();
            }
        });
    }


    public void update(JsonObject jsonObject){
        userView.showProgress();
        Repository.update(jsonObject).subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<UpdatePojo>() {
            @Override
            public void onSubscribe(Disposable d) {

            }
            @Override
            public void onNext(UpdatePojo loginModel) {
                userView.updated();
            }

            @Override
            public void onError(Throwable e) {


            }

            @Override
            public void onComplete() {
                userView.hideProgress();
            }
        });
    }



    public interface UserView {
        void showProgress();
        void error(String message);
        void hideProgress();
        void updated();
        void showDetails(UserPojo.Result[] results,int position,String epicIdNew);
    }
}
