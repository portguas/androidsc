package networkreqyest;

import java.util.Map;

import org.json.JSONObject;

/**
 * Created by heyulong on 8/13/2016.
 * model for get the json data
 */

public interface IGetJsonDataModel {

    /**
     * get 方式获取
     * @param url
     */
    void getJsonDataGet(RequestMsg.RequestType reqType, String url, Map<String, String> param);

    /**
     * Post 方式获取
     * @param url
     */
    void getJsonDataPost(RequestMsg.RequestType reqType, String url, Map<String, String> param);

}
