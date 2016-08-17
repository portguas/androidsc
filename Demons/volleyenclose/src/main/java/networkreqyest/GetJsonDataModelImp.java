package networkreqyest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import android.content.Context;

public abstract class GetJsonDataModelImp<T> implements IGetJsonDataModel {

    private Context context;
    private IJsonDataResponse<T> iJsonDataResponse;


    public Context getContext() {
        return context;
    }

    public GetJsonDataModelImp(Context context) {
        this.context = context;
    }

    /**
     * 暴露出去的数据获取结果的监听接口
     * @param iJsonDataResponse
     */
    public void setIJsonDataResponse(IJsonDataResponse<T> iJsonDataResponse) {
        this.iJsonDataResponse = iJsonDataResponse;
    }

    /**
     * get 方式获取
     * @param reqType
     * @param url
     * @param param
     * @param listener  自定义处理接口
     * @param errorListener  自定义处理接口
     */
    public void getJsonDataGet(RequestMsg.RequestType reqType, String url, Map<String, String> param,
                               Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {

        VolleyNetworkRequest request = getRequestForGet(reqType, url, param, listener, errorListener);
        ApiController.getInstance().addToRequestQueue(request);
    }

    /**
     * Post 方式获取
     *
     * @param url
     */
//    @Override
//    public void getJsonDataPost(RequestMsg.RequestType reqType, String url, Map<String, String> param) {
//        ApiController.getInstance().addToRequestQueue(getRequestForPOST(reqType, url, param));
//    }

    /**
     * get 请求
     * @param url
     * @param param
     * @return
     */

    protected VolleyNetworkRequest getRequestForGet(RequestMsg.RequestType reqType, String url,
                                                    Map<String, String> param, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        if (null != listener && null != errorListener) {
            return new VolleyNetworkRequest(url, param, listener, errorListener);
        } else {
            return null;
        }
    }

    /**
     * post 请求
     * @param url
     * @param param
     * @return
     */
//    protected VolleyNetworkRequest getRequestForPOST(RequestMsg.RequestType reqType, String url, Map<String, String> param) {
//        return new VolleyNetworkRequest(Request.Method.POST, url, param, this, this);
//    }

/*    *//**
     * Callback method that an error has been occurred with the
     * provided error code and optional user-readable message.
     *
     * @param error
     *//*
    @Override
    public void onErrorResponse(VolleyError error) {
        disposeVolleyError(error);
    }

    *//**
     * Called when a response is received.
     *
     * @param response
     *//*
    @Override
    public void onResponse(JSONObject response) {
        disposeResponse(response);
    }*/

    /**
     * 因为每个json对象返回的不一样,因此具体的解析json放到具体的子类中
     * @param response
     */

    public abstract void disposeResponse(RequestMsg.RequestType reqTyte, JSONObject response);

    public abstract void disposeVolleyError(RequestMsg.RequestType reqTyte, VolleyError error);

    /**
     * 暴露给具体的json请求的函数
     * @param data
     */
    protected void onResonseNodify(RequestMsg.RequestType reqTyte, T data) {
        if (null != iJsonDataResponse) {
            iJsonDataResponse.onSuccess(reqTyte, data);
        }
    }

    /**
     *  暴露给具体的json请求的函数
     * @param errorCode
     * @param errorMsg
     */
    protected void onErrorResonseNodify(RequestMsg.RequestType reqTyte, String errorCode, String errorMsg) {
        if (null != iJsonDataResponse) {
            iJsonDataResponse.onError(reqTyte, errorCode, errorMsg);
        }
    }
}
