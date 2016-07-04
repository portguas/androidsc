package com.example.multidownload.downloaddb;



import com.example.multidownload.entities.DownloadThreadInfo;

import java.util.List;

/**访问数据库线程信息的接口
 * Created by hyl on 2016/7/2.
 */
public interface ThreadDBOperation {

    /**
     * 插入线程的信息
     * @param downloadThreadInfo
     */
    public void insertThreadInfo(DownloadThreadInfo downloadThreadInfo);

    /**
     * 删除线程信息， 根据下载的url和id确定唯一的线程记录
     * @param url
     * @param threadId
     */
    public void deleteThreadInfo(String url);

    /**
     * 更新线程的信息, 下载的进度
     * @param url
     * @param threadId
     * @param finishpercent
     */
    public void updateThreadInfo(String url, int threadId, int finishpercent);

    /**
     * 查询同一个下载url有多少个线程在下载，返回这些线程的信息
     *
     * @param url
     * @return
     */
    public List<DownloadThreadInfo> getThreadInfo(String url);

    /**
     * 判断线程是否已经存在于数据库了
     * @param url
     * @param threadId
     * @return
     */
    public boolean isExist(String url, int threadId);
}
