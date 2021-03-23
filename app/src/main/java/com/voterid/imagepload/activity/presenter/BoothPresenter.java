package com.voterid.imagepload.activity.presenter;

import android.util.Log;

import com.voterid.imagepload.Interface.Repository;
import com.voterid.imagepload.pojo.AreaPojo;
import com.voterid.imagepload.pojo.AssemblyPojo;
import com.voterid.imagepload.pojo.BoothsPojo;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class BoothPresenter {
    BoothInterface boothInterface;
    private Disposable disposable;

    public BoothPresenter( BoothInterface boothInterface){
        this.boothInterface = boothInterface;
    }


    public void getAssembly() {
        boothInterface.showProgress();
        Repository.getAssembly().subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<AssemblyPojo>() {
            @Override
            public void onSubscribe(Disposable d) {

            }
            @Override
            public void onNext(AssemblyPojo loginModel) {
              if (loginModel.getResult() != null && loginModel.getResult().length > 0) {
                  boothInterface.assembly(loginModel.getResult());
              } else {
                  boothInterface.error("Not found");
              }
            }

            @Override
            public void onError(Throwable e) {


            }

            @Override
            public void onComplete() {
                boothInterface.showHideProgress();
            }
        });

    }

    public  void getBooths() {
        boothInterface.showProgress();
        Repository.getBooths().subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<BoothsPojo>() {
            @Override
            public void onSubscribe(Disposable d) {

            }
            @Override
            public void onNext(BoothsPojo loginModel) {
                if (loginModel.getResult() != null && loginModel.getResult().length > 0) {
                    boothInterface.showBooth(loginModel.getResult());
                } else {
                    boothInterface.error("Not found");
                }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
                boothInterface.showHideProgress();
            }
        });
    }

    public void getArea(String boothiD) {
        boothInterface.showProgress();
        Repository.getArea(boothiD).subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<AreaPojo>() {
            @Override
            public void onSubscribe(Disposable d) {

            }
            @Override
            public void onNext(AreaPojo loginModel) {
                if (loginModel.getResult() != null && loginModel.getResult().length > 0) {
                    boothInterface.area(loginModel.getResult());
                } else {
                    boothInterface.error("Not found");
                }
            }

            @Override
            public void onError(Throwable e) {

            }
            @Override
            public void onComplete() {
                boothInterface.showHideProgress();
            }
        });
    }


    public interface BoothInterface {
        void showProgress();
        void showHideProgress();
        void error(String message);
        void area(AreaPojo.Result[] results);
        void assembly(AssemblyPojo.Result[] results);
        void showBooth(BoothsPojo.Result[] results);

    }
}
