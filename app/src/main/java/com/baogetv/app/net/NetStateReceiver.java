package com.baogetv.app.net;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.baogetv.app.R;
import com.baogetv.app.customview.CustomToastUtil;
import com.baogetv.app.event.NetStateEvent;
import com.baogetv.app.util.CacheUtil;
import com.baogetv.app.util.SettingManager;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by chalilayang on 2017/12/7.
 */

public class NetStateReceiver extends BroadcastReceiver {
    private final static String ANDROID_NET_CHANGE_ACTION = "android.net.conn.CONNECTIVITY_CHANGE";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equalsIgnoreCase(ANDROID_NET_CHANGE_ACTION)) {
            NetWorkUtil.initNetworkType(context);
            EventBus.getDefault().post(new NetStateEvent());
            if (NetUtil.isNetworkConnected(context)) {
                if (NetUtil.isWifiConnected(context)) {
                    Log.d("same_info>>", "net wifi");
                    CacheUtil.resumeAllCaching(context.getApplicationContext());
                    CustomToastUtil.makeShort(context.getApplicationContext(),
                            context.getString(R.string.wifi_connect));
                } else {
                    if (!SettingManager.allowCacheWithMobile(context.getApplicationContext())) {
                        CacheUtil.pauseAllCaching(context.getApplicationContext());
                    }
                    Log.d("same_info>>", "net data");
                    CustomToastUtil.makeShort(context.getApplicationContext(),
                            context.getString(R.string.mobile_connect));
                }
            } else {
                Log.d("same_info>>", "net error");
                CacheUtil.pauseAllCaching(context.getApplicationContext());
                CustomToastUtil.makeShort(context.getApplicationContext(),
                        context.getString(R.string.net_error));
            }
        }
    }
}
