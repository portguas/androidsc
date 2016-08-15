package networkreqyest;

/**
 * Created by heyulong on 8/15/2016.
 */

public class RequestMsg {

    // 请求域名
    private static String RequestUrlsHost = "http://api.pocketbonus.cc";

    /**
     * 请求类型
     */
    public enum RequestType {
        LOGIN_START,
        LOGIN,         // 登录帐号
        LOGOUT,         // 登出
        USERINFO,       // 用户个人信息
        TASKLIST,       // 任务列表
        TASK,           // 任务状态请求, 请求开始,告知结果
        RXCHANGE,       // 提现
        FRIEND,         // 好友
        HISTORY,        // 账单
        LANDINGPAGE,    // landing page
        NOTIFICATION,   // 通知中心
        PHONECODE,      // 短信码
        FEEDBACK,      // 反馈
        EULA,      //EULA
        UPDATE,   // 升级

        COUNT           // 在这个之上添加类型
    }

    // 跟请求类型对应的url format
    public static String RequestUrls[] = new String[] {RequestUrlsHost + "/sparrow/user/start?",        // 无账号登录
            RequestUrlsHost + "/sparrow/user/login?",        // 登录帐号
            RequestUrlsHost + "/sparrow/user/logout?",       // 登出
            RequestUrlsHost + "/sparrow/user/userinfo?",     // 用户个人信息
            RequestUrlsHost + "/sparrow/task/tasklist?",     // 任务列表
            RequestUrlsHost + "/sparrow/task/task?",         // 任务状态请求, 请求开始,告知结果
            RequestUrlsHost + "/sparrow/user/recharge?",     // 提现
            RequestUrlsHost + "/sparrow/user/friend?",       // 好友
            RequestUrlsHost + "/sparrow/user/history?",      // 账单
            RequestUrlsHost + "/sparrow/user/landingpage?",      // landing page
            RequestUrlsHost + "/sparrow/user/notification?",      // 通知中心
            RequestUrlsHost + "/sparrow/user/sendvc?",      // 发送短信
            RequestUrlsHost + "/sparrow/sz-feedback/",
            RequestUrlsHost + "/sparrow/agreement",
            RequestUrlsHost+"/sparrow/update/apk_version_update?"
    };

}
