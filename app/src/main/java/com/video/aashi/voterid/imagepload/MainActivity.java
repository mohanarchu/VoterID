package com.video.aashi.voterid.imagepload;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.video.aashi.voterid.R;
import com.video.aashi.voterid.imagepload.login.LoginScreen;
import com.video.aashi.voterid.imagepload.sesssion.Sessions;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.mainTool)
    android.support.v7.widget.Toolbar toolbar;
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    Sessions sessions;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        sessions = new Sessions(getApplicationContext());
        if (sessions.isLoginKey())
        {
            Intent intent = new Intent(getApplicationContext(),ImagesUpload.class);
            intent.putExtra("boothid","5c82d77f34dc250d4b4d05a7");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
        else
        {

            getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer,new LoginScreen()).commit();
        }
        toolbar.setTitle("KMDK CM");

     }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
     //   getSupportFragmentManager().beginTransaction().replace(R.id.myFrame,new StreetName()).commit();
    }
}
