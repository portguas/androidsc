package networkreqyest;

import org.json.JSONObject;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import android.content.Context;

/**
 * Created by heyulong on 8/9/2016.
 */

public abstract class VolleyNetworkHelper<T> implements Response.Listener<JSONObject>, Response.ErrorListener {

    private Context context;

    public VolleyNetworkHelper(Context context) {
        this.context = context;
    }

    protected Context getContext() {
        return context;
    }

    /**
     * get 请求
     * @param url
     * @param requestBody
     * @return
     */
    protected VolleyNetworkRequest getRequestForGet(String url, String requestBody) {
        if (null == requestBody) {
            return new VolleyNetworkRequest(url, requestBody, this, this);
        } else {
            return null;
        }
    }

    /**
     * post 请求
     * @param url
     * @param requestBody
     * @return
     */
    protected VolleyNetworkRequest getRequestForPOST(String url, String requestBody) {
        if (null == requestBody) {
            return new VolleyNetworkRequest(Request.Method.POST, url, requestBody, this, this);
        } else {
            return null;
        }
    }

    public void sendGETRequest(String url, String requstBody) {
        Vo
    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }

    @Override
    public void onResponse(JSONObject response) {

    }
}
