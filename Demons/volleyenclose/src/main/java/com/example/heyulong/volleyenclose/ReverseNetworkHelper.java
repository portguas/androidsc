package com.example.heyulong.volleyenclose;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.VolleyError;

import android.content.Context;
import bean.RequestBean;
import networkreqyest.GetJsonDataModelImp;
import networkreqyest.RequestMsg;

/**
 * Created by heyulong on 8/13/2016.
 */

public class ReverseNetworkHelper extends GetJsonDataModelImp<RequestBean> {

    public ReverseNetworkHelper(Context context) {
        super(context);
    }

    /**
     * 因为每个json对象返回的不一样,因此具体的解析json放到具体的子类中
     *
     * @param response
     */
    @Override
    public void disposeResponse(RequestMsg.RequestType requestType, JSONObject response) {
        RequestBean rBean = null;
        if (null != response) {
            try {
                String errCode = response.getString("errCode");
                String errMsg = response.getString("errMsg");
                String result = response.getString("result");

                if ("0".equals(errCode)) {
                    rBean = new RequestBean();
                    rBean.setErrCode(errCode);
                    rBean.setErrMsg(result);
                    onResonseNodify(requestType, rBean);
                } else {
                    onErrorResonseNodify(requestType, errCode, errMsg);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                onErrorResonseNodify(requestType, String.valueOf(e.hashCode()), e.getMessage());
            }
        } else {
            onErrorResonseNodify(requestType, "1", "Response is null");
        }
    }

    /**
     * 所有请求的错误处理包括vollery和server返回的
     * @param error
     */
    @Override
    public void disposeVolleyError(RequestMsg.RequestType requestType, VolleyError error) {
        onErrorResonseNodify(requestType, String.valueOf(error.hashCode()), error.getMessage());
    }
}
