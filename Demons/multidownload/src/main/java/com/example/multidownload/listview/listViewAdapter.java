package com.example.multidownload.listview;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.multidownload.R;
import com.example.multidownload.entities.FileInfo;
import com.example.multidownload.services.DownloadServices;

import java.util.List;

/**
 * Created by hyl on 2016/7/2.
 */
public class ListViewAdapter extends BaseAdapter {


    private Context mContext;
    private List<FileInfo> mFileList;

    public ListViewAdapter(Context mContext, List<FileInfo> mFileList) {
        this.mContext = mContext;
        this.mFileList = mFileList;
    }

    @Override
    public int getCount() {
        return mFileList.size();
    }

    @Override
    public Object getItem(int position) {
        return mFileList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final FileInfo fileInfo = mFileList.get(position);
        Viewholder viewholder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.activity_litem, null);
            viewholder = new Viewholder();
            viewholder.tvDesc = (TextView) convertView.findViewById(R.id.tv_desc);
            viewholder.btnStart = (Button) convertView.findViewById(R.id.button2);
            viewholder.btnStop = (Button) convertView.findViewById(R.id.button);
            viewholder.progressBar = (ProgressBar) convertView.findViewById(R.id.pb_download);

            viewholder.tvDesc.setText(fileInfo.getName());
            viewholder.progressBar.setMax(100);
            viewholder.btnStart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent i = new Intent(mContext, DownloadServices.class);
                    i.setAction(DownloadServices.ACTION_START);
                    i.putExtra("fileInfo", fileInfo);
                    mContext.startService(i);
                }
            });

            viewholder.btnStop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(mContext, DownloadServices.class);
                    i.setAction(DownloadServices.ACTION_STOP);
                    i.putExtra("fileInfo", fileInfo);
                    mContext.startService(i);
                }
            });
            convertView.setTag(viewholder);
        } else {
            viewholder = (Viewholder) convertView.getTag();
        }
        viewholder.progressBar.setProgress(fileInfo.getFinishpercent());
        return convertView;
    }

    // 更新列表项中的进度条
    public void updateProgress(int id, int progress) {
        FileInfo fileInfo = mFileList.get(id);
        fileInfo.setFinishpercent(progress);
        notifyDataSetChanged();
    }
    static class Viewholder {
        TextView tvDesc;
        ProgressBar progressBar;
        Button btnStart;
        Button btnStop;
    }
}
