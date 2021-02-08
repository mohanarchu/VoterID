package com.voterid.imagepload.sesssion;

public class MainArray {

    String voterid,name,fathername,age,sex,dooenumber,alldata;

    public MainArray(String voterid,String name,String fathername,String age,String sex ,String dooenumber,String alldata)
    {
        this.voterid = voterid;
        this.name = name;
        this.fathername = fathername;
        this.age = age;
        this.sex = sex;
        this.dooenumber = dooenumber;
        this.alldata = alldata;
    }

    public String getSex() {
        return sex;
    }

    public String getName() {
        return name;
    }

    public String getAlldata() {
        return alldata;
    }

    public String getAge() {
        return age;
    }

    public String getFathername() {
        return fathername;
    }

    public String getVoterid() {
        return voterid;
    }

    public String getDooenumber() {
        return dooenumber;
    }

}
