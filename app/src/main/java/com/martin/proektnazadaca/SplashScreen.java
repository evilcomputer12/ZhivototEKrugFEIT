package com.martin.proektnazadaca;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.airbnb.lottie.LottieAnimationView;


public class SplashScreen extends AppCompatActivity {
    LottieAnimationView animacija;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        animacija = findViewById(R.id.animacija);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent main = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(main);
            }
        },6000);
    }
}