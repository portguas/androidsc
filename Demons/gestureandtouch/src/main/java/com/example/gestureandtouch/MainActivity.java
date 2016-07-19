package com.example.gestureandtouch;

import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener{


    private GestureDetectorCompat mGesDec;
    private TextView mTv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTv = (TextView) findViewById(R.id.tv);
        mTv.setOnTouchListener(this);
        mGesDec = new GestureDetectorCompat(this, new MyGestureDector());
        ScrollView
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        if (mGesDec != null) {
            mGesDec.onTouchEvent(event);
        }

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.d("Tag", "ACTION_DOWN");
                mTv.setText("ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d("Tag", "ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                Log.d("Tag", "ACTION_UP");
                break;
        }



        return false;
    }

    class MyGestureDector extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            Log.d("Tag", "onSingleTapUp");
            return super.onSingleTapUp(e);
        }

        @Override
        public void onLongPress(MotionEvent e) {
            super.onLongPress(e);
            Log.d("Tag", "onLongPress");
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            Log.d("Tag", "onScroll");
            return super.onScroll(e1, e2, distanceX, distanceY);
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            Log.d("Tag", "onFling");
            return super.onFling(e1, e2, velocityX, velocityY);
        }

        @Override
        public void onShowPress(MotionEvent e) {
            super.onShowPress(e);
            Log.d("Tag", "onShowPress");
        }

        @Override
        public boolean onDown(MotionEvent e) {
            Log.d("Tag", "onDown");
            mTv.setText("onDown");
            return super.onDown(e);
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            Log.d("Tag", "onSingleTapConfirmed");
            return super.onSingleTapConfirmed(e);
        }

        @Override
        public boolean onContextClick(MotionEvent e) {
            Log.d("Tag", "onContextClick");
            return super.onContextClick(e);
        }
    }

    private void pringLog(String msg) {
        Log.d("Tag", msg);
    }
}
