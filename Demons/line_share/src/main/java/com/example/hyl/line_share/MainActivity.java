package com.example.hyl.line_share;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try
        {
            ShareClass sample =new ShareClass();
            sample.getShareList("分享标题", "要分享的内容哦。呵呵", this);
        }catch (Exception e) {
            e.printStackTrace();
            // TODO: handle exception
        }
    }
}
