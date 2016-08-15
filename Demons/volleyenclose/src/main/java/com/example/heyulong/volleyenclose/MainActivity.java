package com.example.heyulong.volleyenclose;

import java.util.HashMap;
import java.util.Map;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import bean.RequestBean;
import networkreqyest.GetJsonDataModelImp;
import networkreqyest.IGetJsonDataModel;
import networkreqyest.IJsonDataResponse;
import networkreqyest.RequestMsg;
import util.SysUtil;

public class MainActivity extends AppCompatActivity implements IJsonDataResponse<RequestBean>{

    private IGetJsonDataModel iGetJsonDataModel;
    private GetJsonDataModelImp<RequestBean> requestBeanGetJsonDataModelImp;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iGetJsonDataModel = new ReverseNetworkHelper(this);
        requestBeanGetJsonDataModelImp = new ReverseNetworkHelper(this);
        requestBeanGetJsonDataModelImp.setIJsonDataResponse(this);
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, String> params = new HashMap<String, String>();
                params.put("guid", SysUtil.getUid());
                params.put("token", "");
                params.put("lang", SysUtil.getLanguage());
//                iGetJsonDataModel.getJsonDataGet(RequestMsg.RequestType.LOGIN,
//                        RequestMsg.RequestUrls[RequestMsg.RequestType.LOGIN.ordinal()], params);
                requestBeanGetJsonDataModelImp.getJsonDataGet(RequestMsg.RequestType.LOGIN,
                        RequestMsg.RequestUrls[RequestMsg.RequestType.LOGIN.ordinal()], params);
                requestBeanGetJsonDataModelImp.getJsonDataGet(RequestMsg.RequestType.LOGIN_START,
                        RequestMsg.RequestUrls[RequestMsg.RequestType.LOGIN_START.ordinal()], params);
//                iGetJsonDataModel.getJsonDataGet(RequestMsg.RequestType.LOGIN_START,
//                        RequestMsg.RequestUrls[RequestMsg.RequestType.LOGIN_START.ordinal()], null);
            }
        });
    }

    /**
     * 获取数据成功
     *
     * @param reqType
     * @param data
     */
    @Override
    public void onSuccess(RequestMsg.RequestType reqType, RequestBean data) {
        switch (reqType) {
            case LOGIN:
                Toast.makeText(this,  "login", Toast.LENGTH_SHORT).show();
                break;
            case LOGIN_START:
                Toast.makeText(this,  "start", Toast.LENGTH_SHORT).show();
                break;
            default:
                Toast.makeText(this,  data.getErrCode(), Toast.LENGTH_SHORT).show();
                break;
        }

    }

    /**
     * 获取数据失败
     *
     * @param reqType
     * @param errorCode
     * @param errorMsg
     */
    @Override
    public void onError(RequestMsg.RequestType reqType, String errorCode, String errorMsg) {
        switch (reqType) {
            case LOGIN:
                Toast.makeText(this,  "login", Toast.LENGTH_SHORT).show();
                break;
            case LOGIN_START:
                Toast.makeText(this,  "start", Toast.LENGTH_SHORT).show();
                break;
            default:
                Toast.makeText(this,  errorCode, Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
