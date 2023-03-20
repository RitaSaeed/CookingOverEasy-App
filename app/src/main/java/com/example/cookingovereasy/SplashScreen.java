package com.example.cookingovereasy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import java.util.Timer;
import java.util.TimerTask;

public class SplashScreen extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        SharedPreferences preferences = getSharedPreferences("PREFERENCE", MODE_PRIVATE);
        String firstTimeWelcome = preferences.getString("FirstTimeWelcome", "");

        if(firstTimeWelcome.equals("Yes")){
            Timer timer = new Timer();
            timer.schedule(new TimerTask(){
                @Override
                public void run(){
                    startActivity(new Intent(SplashScreen.this, Welcome.class));
                }
            }, 3000);
        }else{
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("FirstTimeWelcome", "Yes");
            editor.apply();
            Timer timer = new Timer();
            timer.schedule(new TimerTask(){
                @Override
                public void run(){
                    startActivity(new Intent(SplashScreen.this, Welcome.class));
                }
            }, 3000);
        }

    }
}