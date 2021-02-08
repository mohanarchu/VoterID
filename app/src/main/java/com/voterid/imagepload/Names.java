package com.voterid.imagepload;

public class Names {

    String id,names,fathername;

    public Names(String id, String texts, String fathername)
    {

        this.id = id;
        this.names =texts;
    }

    public String getId() {
        return id;
    }

    public String getNames() {
        return names;
    }

    public String getFathername() {
        return fathername;
    }
}
