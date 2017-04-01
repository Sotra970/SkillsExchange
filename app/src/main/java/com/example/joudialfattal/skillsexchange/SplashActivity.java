package com.example.joudialfattal.skillsexchange;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {
            public Intent intent;

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                if (new MyPreferenceManager(getApplicationContext()).getUser()== null)
                 intent = new Intent(getApplicationContext(), MainActivity.class);
                else
                intent = new Intent(getApplicationContext(), FindSkill.class);
                startActivity(intent);
                finish();
            }
        }, 2000);
    }
}
