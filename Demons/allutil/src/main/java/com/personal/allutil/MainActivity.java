package com.personal.allutil;


import java.io.IOException;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bluelinelabs.logansquare.LoganSquare;
import com.orhanobut.logger.Logger;
import com.z.bean.Bean;
import com.z.util.DownloadManagerPro;
import com.z.util.SDCardUtils;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import net.NetworkManager;

public class MainActivity extends BaseActivity {


    private static final String JSON_TEST_URL =
            "http://api.map.baidu.com/telematics/v3/weather?location=嘉兴&output=json&ak=5slgyqGDENN7Sy7pw29IUvrZ";

    private static final String DOWNLOAD_URL =
            "http://count.liqucn.com/d.php?id=401826&urlos=android&from_type=web";

    private TextView textView;
    private Button button;

    private DownloadCompeleteReceiver receiver;
    private DownloadManager downloadManager;
    private DownloadManagerPro downloadManagerPro;

    @Override
    public void initParms(Bundle parms) {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void initView(View v) {
        textView = $(R.id.tv);
        button = $(R.id.btn_download);
    }

    @Override
    public void setListener() {
        textView.setOnClickListener(this);
        button.setOnClickListener(this);
    }

    /**
     * 窗口点击
     *
     * @param view
     */
    @Override
    public void widgetClick(View view) {
        switch (view.getId()) {
            case R.id.tv:
                super.startActivity(Main2Activity.class);
                break;
            case R.id.btn_download:
                DownloadManager.Request request = new DownloadManager.Request(Uri.parse(DOWNLOAD_URL));
//                request.setDestinationInExternalPublicDir(this.getFilesDir().toString(), "test");
                boolean b = SDCardUtils.checkSDCardAvailable();
                if (b) {
                    request.setDestinationInExternalFilesDir(this, "download1", "a.apk");
                }
                request.setTitle("a.apk");
                request.setDescription("介绍");
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI);
                request.setAllowedOverRoaming(false);
                String str1 = getExternalFilesDir("test").toString();
                String str2 = Environment.getExternalStorageDirectory().toString();
                String str3 = Environment.getExternalStoragePublicDirectory("test").toString();
                String str4 = getFilesDir().toString();
                        long downloadId = downloadManager.enqueue(request);

                break;
            default:
                break;
        }
    }

    @Override
    public void initOperation(Context context) {
        boolean b = NetworkManager.isNetworkConnected(this);
        if (b) {
            showToast("lianjie");
        }
        Logger.d("test");

        StringRequest request = new StringRequest(Request.Method.GET, JSON_TEST_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    /**
                     * 使用LoganSquere的时候 注意实体类的要加注解,
                     * 比如这次的Bean的上面就增加了注解
                     * LoganSquare在ART模式下比GSON的速度快很多
                     */
                    Bean bean = LoganSquare.parse(response, Bean.class);

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                showToast(error.hashCode());
                showToast(error.getMessage());
            }
        });
        ApiController.getInstance().addToRequestQueue(request);

        // 测试下载
        downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        downloadManagerPro = new DownloadManagerPro(downloadManager);
        receiver = new DownloadCompeleteReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        filter.addAction(DownloadManager.ACTION_NOTIFICATION_CLICKED);
        registerReceiver(receiver, filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }

    class DownloadCompeleteReceiver extends BroadcastReceiver {
        /**
         *
         * @param context The Context in which the receiver is running.
         * @param intent  The Intent being received.
         */
        @Override
        public void onReceive(Context context, Intent intent) {
            if (DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(intent.getAction())) {
                long completedID = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
                showInstall(completedID);
            } else if (DownloadManager.ACTION_NOTIFICATION_CLICKED.equals(intent.getAction())) {
                long[] ids = intent.getLongArrayExtra(DownloadManager.EXTRA_NOTIFICATION_CLICK_DOWNLOAD_IDS);
                downloadManagerPro.pauseDownload(ids);
                downloadManagerPro.getStatusById(ids[0]);
            }

        }
    }

    private void showInstall(long completedID) {
        DownloadManager downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri downloadfileuri = downloadManager.getUriForDownloadedFile(completedID);
        intent.setDataAndType(downloadfileuri, "application/vnd.android.package-archive");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }



}
