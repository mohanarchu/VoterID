package com.video.aashi.voterid.imagepload.login.otp;




import java.util.ArrayList;

public interface otpPresenter  {
    void showProgress();
    void hideProgress();
    void progressMessage(String message);
    void showScreen(String mobile, String otp);
    void showToast(String string);
    void seetOtp(String text);
    void  shhowLists(ArrayList<Booths> booths);
}
