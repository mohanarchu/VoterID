package com.voterid.imagepload;

import android.content.Context;

import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.services.vision.v1.Vision;
import com.google.api.services.vision.v1.VisionRequestInitializer;

public class VisionBuilder {
     Context context;
     Vision vision;
    public VisionBuilder(Context context)
    {
        this.context = context;

    }

    public Vision getVision()
    {
        Vision.Builder visionBuilder = new Vision.Builder(
                new NetHttpTransport(),
                new AndroidJsonFactory(),
                null);

        visionBuilder.setVisionRequestInitializer(
                new VisionRequestInitializer("AIzaSyAFSyFB4673g0alLleIyVlI_RAiOOA4ztE"));

        vision = visionBuilder.build();
        return vision;
    }

}
