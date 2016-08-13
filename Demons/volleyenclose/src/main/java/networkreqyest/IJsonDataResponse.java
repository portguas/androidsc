package networkreqyest;

/**
 * Created by heyulong on 8/13/2016.
 */

public interface IJsonDataResponse<T> {

    /**
     * 获取数据成功
     * @param data
     */
    void onSuccess(T data);

    /**
     * 获取数据失败
     * @param errorCode
     * @param errorMsg
     */
    void onError(String errorCode, String errorMsg);
}
