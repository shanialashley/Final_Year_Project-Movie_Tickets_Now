package com.example.authapp.UI;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.authapp.R;

import static android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN;

public class Logo extends AppCompatActivity {

    Animation topA, bottomA;
    ImageView image;
    TextView logo;
    private static  int SPLASH_SCREEN=5000;
    MediaPlayer introM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //noinspection deprecation
        getWindow().setFlags(FLAG_FULLSCREEN, FLAG_FULLSCREEN);

        setContentView(R.layout.activity_logo);

//        hideActionBar();

        topA = AnimationUtils.loadAnimation(this,R.anim.topanim);
        bottomA = AnimationUtils.loadAnimation(this,R.anim.bottomanim);

        image=findViewById(R.id.imageView);
        logo=findViewById(R.id.textView);

        image.setAnimation(topA);
        logo.setAnimation(bottomA);

        //noinspection deprecation
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent =new Intent(Logo.this, Home.class);
                startActivity(intent);
                finish();

            }
        },SPLASH_SCREEN);

        introM = MediaPlayer.create(Logo.this, R.raw.intro);
        introM.start();

    }

//    private void hideActionBar() {
//        getSupportActionBar().hide();
//
//    }
}