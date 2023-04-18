package com.example.cookingovereasy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

public class Welcome extends AppCompatActivity {

    BlurLayout blurLayout;

    /**
     * On create gets the preferences as well as determines whether this is the first time the user
     * has installed the application.
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        blurLayout = findViewById(R.id.blurLayoutWelcome);

        SharedPreferences preferences = getSharedPreferences("PREFERENCE", MODE_PRIVATE);
        String firstTime = preferences.getString("FirstTimeInstall", "");

        // checks to see if this is a fresh install, if yes show the welcome screen
        if(firstTime.equals("Yes")){
            Intent i = new Intent(Welcome.this, Login.class);
            startActivity(i);
        }else{
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("FirstTimeInstall", "Yes");
            editor.apply();
        }
    }

    /**
     * When activated sends to the login page.
     *
     * @param v the view object to be referenced
     */
    public void openLogin(View v){
        Intent i = new Intent(this, Login.class);
        startActivity(i);
        overridePendingTransition(R.anim.slide_right, R.anim.slide_out_left);
    }

    @Override
    protected void onStart() {
        super.onStart();
        blurLayout.startBlur();
    }

    @Override
    protected void onStop() {
        blurLayout.pauseBlur();
        super.onStop();
    }
}