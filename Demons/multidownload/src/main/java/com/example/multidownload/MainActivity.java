package com.example.multidownload;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.multidownload.R;
import com.example.multidownload.entities.FileInfo;
import com.example.multidownload.listview.ListViewAdapter;
import com.example.multidownload.services.DownloadServices;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String QQ = "http://gdown.baidu.com/data/wisegame/2eeee3831c9dbc42/QQ_374.apk";
    private static final String IMMOC = "http://www.imooc.com/mobile/imooc.apk";

    //    private Button btnStart;
//    private Button btnStop;
//    private ProgressBar mPb;
    private ListView mList;
    private ListViewAdapter mAdapter;
    private List<FileInfo> mFileList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initDatas();
        initViews();

        IntentFilter filter = new IntentFilter();
        filter.addAction(DownloadServices.ACTION_UPDATEUI);
        filter.addAction(DownloadServices.ACTION_DOWNLOADED);
        registerReceiver(mRecevier, filter);

    }

    private void initDatas() {
        mFileList = new ArrayList<>();
        FileInfo f = new FileInfo(0, QQ, "qq", 0, 0);
        FileInfo f1 = new FileInfo(1, IMMOC, "immoc", 0, 0);
        mFileList.add(f);
        mFileList.add(f1);
    }

    private void initViews() {
        /*btnStart = (Button) findViewById(R.id.button2);
        btnStart.setOnClickListener(this);
        btnStop = (Button) findViewById(R.id.button);
        btnStop.setOnClickListener(this);
        mPb = (ProgressBar) findViewById(R.id.pb_download);
        mPb.setMax(100);*/
        mList = (ListView) findViewById(R.id.lv_download);
        mAdapter = new ListViewAdapter(this, mFileList);
        mList.setAdapter(mAdapter);
    }

   /* @Override
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
    }*/

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
                int id = intent.getIntExtra("id", 0);
                mAdapter.updateProgress(id, finish);
                Log.d("test", finish + "update");
            } else if (DownloadServices.ACTION_DOWNLOADED == intent.getAction()) {
                FileInfo f = (FileInfo) intent.getSerializableExtra("fileinfo");
                mAdapter.updateProgress(f.getId(), 0);
                Toast.makeText(MainActivity.this, mFileList.get(f.getId()).getName()+"下载完毕",
                        Toast.LENGTH_SHORT).show();
            }
        }
    };
    @Override
    protected void onDestroy() {
        super.onDestroy();
//        unregisterReceiver(mRecevier);
        stopService(new Intent(this, DownloadServices.class));
    }
}
