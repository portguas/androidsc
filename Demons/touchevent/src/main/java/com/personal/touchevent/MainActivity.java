package com.personal.touchevent;

import java.util.Observable;
import java.util.Observer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MyTextView tv = (MyTextView) findViewById(R.id.txt1);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("Demo:","点击了TextView--txt1");
            }
        });

//        tv.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//
//                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
//                    Log.e("Demo:","txt1按下");
//                }else if(motionEvent.getAction() == MotionEvent.ACTION_UP){
//                    Log.e("Demo:","txt1弹起");
//                }
//
//                return false;
//            }
//        });

    }
}
