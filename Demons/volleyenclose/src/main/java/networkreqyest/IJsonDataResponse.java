package networkreqyest;

/**
 * Created by heyulong on 8/13/2016.
 */

public interface IJsonDataResponse<T> {

    /**
     * 获取数据成功
     * @param data
     */
    void onSuccess(RequestMsg.RequestType reqType, T data);

    /**
     * 获取数据失败
     * @param errorCode
     * @param errorMsg
     */
    void onError(RequestMsg.RequestType reqType, String errorCode, String errorMsg);
}
