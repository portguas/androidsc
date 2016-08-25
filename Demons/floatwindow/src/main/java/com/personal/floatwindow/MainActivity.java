package com.personal.floatwindow;

import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "悬浮";

    private Context mContext;
    private WindowManager mWindowManager;
    LinearLayout mFloatLayout;
    Button mFloatView;
    Button button;

    private float mTouchStartX;
    private float mTouchStartY;
    private float x;
    private float y;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button) findViewById(R.id.btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MainActivity.this, Main2Activity.class));
            }
        });
//        initView();

        FloatViewController fvc = new FloatViewController(this, "99");
        fvc.show();
        TextInputEditText textInputEditText = new TextInputEditText(this);
    }


    private void initView() {

        // 获取应用的Context
        mContext = getApplicationContext();
        // 获取WindowManager
        mWindowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);

        final WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        params.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        int flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        params.flags = flags;
        params.format = PixelFormat.TRANSLUCENT;
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.LEFT | Gravity.TOP;;

        params.x = 0;
        params.y = 0;


        LayoutInflater inflater = LayoutInflater.from(getApplication());
        mFloatLayout = (LinearLayout) inflater.inflate(R.layout.float_layout1, null);

        mWindowManager.addView(mFloatLayout, params);

        mFloatView = (Button) mFloatLayout.findViewById(R.id.btn);
        // 这里不能用mFloatLayout.setOnTouchListener, 因为子view会消耗到touch中的事件 不会传到ViewGroup中
        mFloatView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                x = event.getRawX();
                y = event.getRawY()-25;
                Log.i("startP", y + "--y");
                Log.i("startP", event.getRawY() + "--y--");
                Log.i("startP", x + "--x");
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mTouchStartX =  event.getX();
                        mTouchStartY =  event.getY();
                        Log.i("startP","startX"+mTouchStartX+"====startY"+mTouchStartY);
                        break;
                    case MotionEvent.ACTION_MOVE:   //捕获手指触摸移动动作
                        updateViewPosition(mFloatLayout, mWindowManager, params);
                        break;
                    case MotionEvent.ACTION_UP:    //捕获手指触摸离开动作
                        updateViewPosition(mFloatLayout,mWindowManager, params);
                        mTouchStartX=mTouchStartY=0;
                        break;
                }

                return false;
            }
        });
        mFloatView.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                // TODO Auto-generated method stub
                Toast.makeText(MainActivity.this, "onClick", Toast.LENGTH_SHORT).show();
            }
        });
        
        

    }

    private void updateViewPosition(LinearLayout mFloatLayout, WindowManager mWindowManager,
                                    WindowManager.LayoutParams params) {

        params.x=(int)( x-mTouchStartX);
        params.y=(int) (y-mTouchStartY);
        mWindowManager.updateViewLayout(mFloatLayout, params);
        Log.i("startP", params.y + "--params.y");
        Log.i("startP", params.x + "--params.x");
        Log.i("startP", y + "--y");
        Log.i("startP", mTouchStartY + "--mTouchStartY");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        if(mFloatLayout != null)
//        {
//            //移除悬浮窗口
//            mWindowManager.removeView(mFloatLayout);
//        }
    }
}
