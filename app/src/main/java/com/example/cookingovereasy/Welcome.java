package com.example.cookingovereasy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

public class Welcome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        SharedPreferences preferences = getSharedPreferences("PREFERENCE", MODE_PRIVATE);
        String firstTime = preferences.getString("FirstTimeInstall", "");

        if(firstTime.equals("Yes")){
            Intent i = new Intent(Welcome.this, Login.class);
            startActivity(i);
        }else{
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("FirstTimeInstall", "Yes");
            editor.apply();
        }
    }

    public void openLogin(View v){
        Intent i = new Intent(this, Login.class);
        startActivity(i);
        overridePendingTransition(R.anim.slide_right, R.anim.slide_out_left);
    }
}