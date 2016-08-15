package bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by heyulong on 8/13/2016.
 */

public class RequestBean implements Parcelable {
    String errCode;
    String errMsg;
    String body;



    public RequestBean(String errCode, String errMsg, String body) {
        this.errCode = errCode;
        this.errMsg = errMsg;
        this.body = body;
    }

    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * 写入到Parcel对象中
     * @param dest
     * @param flags
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.errCode);
        dest.writeString(this.errMsg);
        dest.writeString(this.body);
    }

    public RequestBean() {
    }

    // 读出的顺序必须和写入的顺序一致
    protected RequestBean(Parcel in) {
        this.errCode = in.readString();
        this.errMsg = in.readString();
        this.body = in.readString();
    }

    public static final Parcelable.Creator<RequestBean> CREATOR = new Parcelable.Creator<RequestBean>() {
        @Override
        public RequestBean createFromParcel(Parcel source) {
            return new RequestBean(source);
        }

        @Override
        public RequestBean[] newArray(int size) {
            return new RequestBean[size];
        }

    };
}
