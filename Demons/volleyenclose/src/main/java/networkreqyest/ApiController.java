package networkreqyest;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import android.text.TextUtils;

/**
 * Created by heyulong on 8/9/2016.
 */

public class ApiController {

    private RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(BdApplication.getContext());
        }
        return mRequestQueue;
    }

    /**
     * 添加网络请求到请求队列
     *
     * @param req 网络请求
     * @param tag 请求标识，方便取消 ; 如果传入tag为空则使用默认TAG
     */
    public <T> void addToRequestQueue(Request<T> req, String tag) {
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    /**
     * 添加网络请求到请求队列
     *
     * @param req 网络请求
     */
    public <T> void addToRequestQueue(Request<T> req) {
        addToRequestQueue(req, TAG);
    }

    /**
     * 根据指定标识取消执行所有相同标识的网络请求
     *
     * @param tag 网络请求标识
     */
    public void cancelRequest(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }

    public static final String TAG = ApiController.class.getSimpleName();
    private RequestQueue mRequestQueue;
    private static ApiController mInstance;

    public static synchronized ApiController getInstance() {
        if (mInstance == null) {
            mInstance = new ApiController();
        }
        return mInstance;
    }
}
