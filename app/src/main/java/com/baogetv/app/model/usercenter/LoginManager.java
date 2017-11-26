package com.baogetv.app.model.usercenter;

import android.content.Context;

import com.chalilayang.util.SPUtils;

/**
 * Created by chalilayang on 2017/11/12.
 */

public class LoginManager {
    private static final String KEY_DEVICE_TOKEN = "DEVICE_TOKEN";
    public static boolean hasLogin() {
        return false;
    }
    public static void putDeviceToken(Context context, String token) {
        SPUtils.put(context, KEY_DEVICE_TOKEN, token);
    }
    public static String getKeyDeviceToken(Context context) {
        return (String) SPUtils.get(context, KEY_DEVICE_TOKEN, "");
    }
}
