package com.video.aashi.voterid.imagepload;


import com.video.aashi.voterid.imagepload.view.ViewIntract;

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
