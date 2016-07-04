package com.example.downloaddemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.example.downloaddemo.entities.FileInfo;
import com.example.downloaddemo.services.DownloadServices;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String QQ = "http://gdown.baidu.com/data/wisegame/2eeee3831c9dbc42/QQ_374.apk";
    private Button btnStart;
    private Button btnStop;
    private FileInfo f;
    private ProgressBar mPb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initDatas();
        initViews();

        IntentFilter filter = new IntentFilter();
        filter.addAction(DownloadServices.ACTION_UPDATEUI);
        registerReceiver(mRecevier, filter);
    }

    private void initDatas() {
        f = new FileInfo(0, QQ, "qq", 0, 0);
    }

    private void initViews() {
        btnStart = (Button) findViewById(R.id.button2);
        btnStart.setOnClickListener(this);
        btnStop = (Button) findViewById(R.id.button);
        btnStop.setOnClickListener(this);
        mPb = (ProgressBar) findViewById(R.id.pb_download);
        mPb.setMax(100);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:  // stop
                startService(DownloadServices.ACTION_STOP, f);
                break;
            case R.id.button2:
                startService(DownloadServices.ACTION_START, f);
                break;
            default:
                break;
        }
    }

    private void startService(String actionStop, FileInfo f) {
        Intent i = new Intent(this, DownloadServices.class);
        i.setAction(actionStop);
        i.putExtra("fileInfo", f);
        startService(i);
    }

    // 更新UI的广播
    BroadcastReceiver mRecevier = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (DownloadServices.ACTION_UPDATEUI == intent.getAction()) {
                int finish = intent.getExtras().getInt("finish");
                mPb.setProgress(finish);
                Log.d("test", finish + "update");
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mRecevier);
    }
}
