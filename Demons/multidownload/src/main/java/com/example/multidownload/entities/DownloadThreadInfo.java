package com.example.multidownload.entities;

/**
 * Created by hyl on 2016/7/1.
 */
public class DownloadThreadInfo {
    // 线程id
    private int id;
    // 线程下载的url
    private String url;
    // 线程下载开始的位置
    private int start;
    // 线程借宿的位置
    private int end;
    // 线程下载的进度
    private int finishpercent;

    public DownloadThreadInfo() {
    }

    public DownloadThreadInfo(int id, String url, int start, int end, int finishpercent) {
        this.id = id;
        this.url = url;
        this.start = start;
        this.end = end;
        this.finishpercent = finishpercent;
    }

    @Override
    public String toString() {
        return "DownloadThreadInfo{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", start=" + start +
                ", end=" + end +
                ", finishpercent=" + finishpercent +
                '}';
    }

    public int getFinishpercent() {
        return finishpercent;
    }

    public void setFinishpercent(int finishpercent) {
        this.finishpercent = finishpercent;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }
}
