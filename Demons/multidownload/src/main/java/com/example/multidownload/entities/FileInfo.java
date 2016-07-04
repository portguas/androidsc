package com.example.multidownload.entities;

import java.io.Serializable;

/**
 * Created by hyl on 2016/7/1.
 */
public class FileInfo implements Serializable{

    // 文件id
    private int id;
    // 文件下载url
    private String url;
    // 文件的名字
    private String name;
    // 文件长度
    private int length;
    // 文件的下载的完成进度
    private int finishpercent;


    public FileInfo() {

    }

    public FileInfo(int id, String url, String name, int length, int finishpercent) {
        this.id = id;
        this.url = url;
        this.name = name;
        this.length = length;
        this.finishpercent = finishpercent;
    }

    @Override
    public String toString() {
        return "FileInfo{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", name='" + name + '\'' +
                ", length=" + length +
                ", finishpercent=" + finishpercent +
                '}';
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getFinishpercent() {
        return finishpercent;
    }

    public void setFinishpercent(int finishpercent) {
        this.finishpercent = finishpercent;
    }
}
