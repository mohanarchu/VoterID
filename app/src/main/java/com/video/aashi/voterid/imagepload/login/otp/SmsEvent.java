package com.video.aashi.voterid.imagepload.login.otp;

public class SmsEvent {
    private String otp;

    public SmsEvent(String otp) {
        this.otp = otp;
    }

    public String getOtp() {
        return otp;
    }
}
