package com.voterid.imagepload;


import com.voterid.imagepload.view.ViewIntract;

public interface ImgaeModel extends ViewIntract {
    @Override
    void showProgress();

    @Override
    void hideProgress();

    @Override
    void progressMessage(String message);

   void showToast(String message);

   void  removePos(int pos);


}
