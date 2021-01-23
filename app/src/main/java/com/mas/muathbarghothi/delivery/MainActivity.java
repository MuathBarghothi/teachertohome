package com.mas.muathbarghothi.delivery;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
Button mbtnRegester,mbtnLogin,mbtnstudents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mbtnRegester=findViewById(R.id.regesterteacher);
        mbtnLogin=findViewById(R.id.login);
        mbtnstudents=findViewById(R.id.regesterstudents);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("استاذك لبيتك");


        mbtnRegester.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,RegesterActivity.class));
            }
        });
mbtnstudents.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        startActivity(new Intent(MainActivity.this,RegesterstudentsActivity.class));
    }
});
mbtnLogin.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        startActivity(new Intent(MainActivity.this,LoginActivity.class));
    }
});
    }

}