package com.example.heyulong.volleyenclose;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import bean.RequestBean;
import networkreqyest.GetJsonDataModelImp;
import networkreqyest.IGetJsonDataModel;
import networkreqyest.IJsonDataResponse;

public class MainActivity extends AppCompatActivity implements IJsonDataResponse<RequestBean>{

    private IGetJsonDataModel iGetJsonDataModel;
    private Button button;

    private static final String URL = "Http://www.baidu.com";
    private static final String URL1 = "Http://www.baidu.com";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iGetJsonDataModel = new ReverseNetworkHelper(this);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iGetJsonDataModel.getJsonDataGet(URL, null);
                iGetJsonDataModel.getJsonDataGet(URL1, null);
            }
        });
    }

    /**
     * 获取数据成功
     *
     * @param data
     */
    @Override
    public void onSuccess(RequestBean data) {
        Toast.makeText(this,  data.getErrCode(), Toast.LENGTH_SHORT).show();

    }

    /**
     * 获取数据失败
     *
     * @param errorCode
     * @param errorMsg
     */
    @Override
    public void onError(String errorCode, String errorMsg) {
        Toast.makeText(this, errorCode + "*****" + errorMsg, Toast.LENGTH_SHORT).show();
    }
}
