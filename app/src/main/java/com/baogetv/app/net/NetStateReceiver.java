package com.baogetv.app.net;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.baogetv.app.R;
import com.baogetv.app.customview.CustomToastUtil;

/**
 * Created by chalilayang on 2017/12/7.
 */

public class NetStateReceiver extends BroadcastReceiver {
    private final static String ANDROID_NET_CHANGE_ACTION = "android.net.conn.CONNECTIVITY_CHANGE";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equalsIgnoreCase(ANDROID_NET_CHANGE_ACTION)) {
            if (NetUtil.isNetworkAvailable(context)) {
                //Wifi情况下
                if (NetUtil.isWifiConnected(context)) {
                    Log.d("same_info>>", "net wifi");
                    CustomToastUtil.makeShort(context.getApplicationContext(),
                            context.getString(R.string.wifi_connect));
                }
                //数据流量的情况下
                else {
                    Log.d("same_info>>", "net data");
                    CustomToastUtil.makeShort(context.getApplicationContext(),
                            context.getString(R.string.mobile_connect));
                }
            } else {
                Log.d("same_info>>", "net error");
                CustomToastUtil.makeShort(context.getApplicationContext(),
                        context.getString(R.string.net_error));
            }
        }
    }
}
