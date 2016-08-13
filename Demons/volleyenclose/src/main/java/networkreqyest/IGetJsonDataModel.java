package networkreqyest;

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
    void getJsonDataGet(String url, JSONObject jsonObject);

    /**
     * Post 方式获取
     * @param url
     */
    void getJsonDataPost(String url, JSONObject jsonObject);

}
