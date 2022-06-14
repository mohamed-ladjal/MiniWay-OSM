package com.example.gps_firsttry;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {
    private static int SPLASH_SCREEN_TIMEOUT = 4000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Animation fadeIn = new AlphaAnimation(1.0f,0.0f);
        fadeIn.setInterpolator(new AccelerateInterpolator());
        fadeIn.setStartOffset(500);
        fadeIn.setDuration(1800);
        ImageView imageView5 = findViewById(R.id.imageView5);
        ImageView imageView4 = findViewById(R.id.imageView4);
       // imageView5.setAnimation(fadeIn);
        //imageView4.setAnimation(fadeIn);
        setContentView(R.layout.splash_activity);
         new Handler().postDelayed(new Runnable() {
             @Override
             public void run() {
                 Intent intent = new Intent(SplashActivity.this,MainActivity.class);
                 startActivity(intent);
                 finish();

             }
         },SPLASH_SCREEN_TIMEOUT);
    }
}
