package com.example.hyl.aapp;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;

public class DrawerActivity extends AppCompatActivity {

    private DrawerLayout mDrawLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);

        mDrawLayout = (DrawerLayout) findViewById(R.id.dl_main);

        Button button = (Button) findViewById(R.id.btn_test);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawLayout.openDrawer(Gravity.LEFT);
            }
        });
    }
}
