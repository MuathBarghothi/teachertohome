package com.mas.muathbarghothi.delivery;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import static java.lang.Thread.sleep;

public class splashActivety extends AppCompatActivity {
ActionBar act;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_activety);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        Thread thread = new Thread() {
            public void run() {
                try {
                    sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Intent ints = new Intent(splashActivety.this, MainActivity.class);
                startActivity(ints);
                finish();

            }


        };
        thread.start();
    }




    }
