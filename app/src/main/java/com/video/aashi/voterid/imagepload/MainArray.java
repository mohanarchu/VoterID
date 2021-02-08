package com.video.aashi.voterid.imagepload;

public class MainArray {

    String voterid, name,fathername,allyexts ;

    public MainArray(String voterid,String name,String   fathername,String allyexts)
    {
        this.voterid = voterid;
        this.name = name;
        this.fathername = fathername;
        this.allyexts = allyexts;
    }

    public String getFathername() {
        return fathername;
    }

    public String getName() {
        return name;
    }

    public String getAllyexts() {
        return allyexts;
    }

    public String getVoterid() {
        return voterid;
    }
}
