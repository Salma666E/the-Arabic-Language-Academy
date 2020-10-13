package com.example.magmaa.pages;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.magmaa.R;
import com.example.magmaa.API_Mange.SessionMangment;
import com.example.magmaa.pages.ForAnyOne.AnyOneActivity;

public class Splash extends AppCompatActivity {
    ImageView mIma;
    SessionMangment mSessionMangment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mSessionMangment=new SessionMangment(this);
        mIma=findViewById(R.id.ima);
        Glide.with(Splash.this).load(R.raw.logo).into(mIma);
        splashTimer();
    }

    private void splashTimer() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mSessionMangment.isLoggedIn())
                    startActivity(new Intent(Splash.this, MainActivity.class));
                 else
                    startActivity(new Intent(Splash.this, AnyOneActivity.class));

            }
        },3500);
    }
}
