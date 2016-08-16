package net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Proxy;

/**
 * Created by zzz on 8/16/2016.
 */

public class NetworkManager {

    private static final String TAG = NetworkManager.class.getSimpleName();
    private static final boolean DEBUG = false;
    private String mApn;
    private String mProxy;
    private String mPort;
    private boolean mUseWap;
    private String mNetType;
    private int mSubType;
    private String mSubTypeName;

    public NetworkManager(Context var1) {
        this.checkNetworkType(var1);
    }

    private void checkApn(Context var1, NetworkInfo var2) {
        String var3;
        if(var2.getExtraInfo() != null) {
            var3 = var2.getExtraInfo().toLowerCase();
            if(var3 != null) {
                label54: {
                    if(!var3.startsWith("cmwap") && !var3.startsWith("uniwap") && !var3.startsWith("3gwap")) {
                        if(var3.startsWith("ctwap")) {
                            this.mUseWap = true;
                            this.mApn = var3;
                            this.mProxy = "10.0.0.200";
                            this.mPort = "80";
                            return;
                        }

                        if(!var3.startsWith("cmnet") && !var3.startsWith("uninet") && !var3.startsWith("ctnet") && !var3.startsWith("3gnet")) {
                            break label54;
                        }

                        this.mUseWap = false;
                        this.mApn = var3;
                        return;
                    }

                    this.mUseWap = true;
                    this.mApn = var3;
                    this.mProxy = "10.0.0.172";
                    this.mPort = "80";
                    return;
                }
            }
        }

        var3 = Proxy.getDefaultHost();
        int var4 = Proxy.getDefaultPort();
        if(var3 != null && var3.length() > 0) {
            this.mProxy = var3;
            if("10.0.0.172".equals(this.mProxy.trim())) {
                this.mUseWap = true;
                this.mPort = "80";
            } else if("10.0.0.200".equals(this.mProxy.trim())) {
                this.mUseWap = true;
                this.mPort = "80";
            } else {
                this.mUseWap = false;
                this.mPort = Integer.toString(var4);
            }
        } else {
            this.mUseWap = false;
        }

    }

    /**
     * 检测当前的网络是什么类型.wifi  wap
     * @param var1
     */
    private void checkNetworkType(Context var1) {
        ConnectivityManager var2 = (ConnectivityManager)var1.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo var3 = var2.getActiveNetworkInfo();
        if(var3 != null) {
            if("wifi".equals(var3.getTypeName().toLowerCase())) {
                this.mNetType = "wifi";
                this.mUseWap = false;
            } else {
                this.checkApn(var1, var3);
                this.mNetType = this.mApn;
            }

            this.mSubType = var3.getSubtype();
            this.mSubTypeName = var3.getSubtypeName();
        }

    }

    /**
     * 判断网络是否连接
     * @param var0
     * @return
     */
    public static boolean isNetworkConnected(Context var0) {
        ConnectivityManager var1 = (ConnectivityManager)var0.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo var2 = var1.getActiveNetworkInfo();
        return var2 != null?var2.isConnectedOrConnecting():false;
    }

    public boolean isWapNetwork() {
        return this.mUseWap;
    }

    public String getApn() {
        return this.mApn;
    }

    public String getProxy() {
        return this.mProxy;
    }

    public String getSubTypeName() {
        return this.mSubTypeName;
    }

    public int getSubType() {
        return this.mSubType;
    }

    public String getProxyPort() {
        return this.mPort;
    }

    public String getNetType() {
        return this.mNetType;
    }
}
