package com.baogetv.app.util;

import android.content.Context;

import com.chalilayang.util.SPUtils;

/**
 * Created by chalilayang on 2017/12/7.
 */

public class SettingManager {
    public static final String KEY_ALLOW_CACHE_3G_4G = "ALLOW_CACHE_3G_4G";
    public static final String KEY_ALLOW_NOTIFY_ZAN = "ALLOW_NOTIFY_ZAN";
    public static final String KEY_ALLOW_NOTIFY_COMMENT = "ALLOW_NOTIFY_COMMENT";

    public static void putAllowCacheWithMobile(Context context, boolean allow) {
        SPUtils.put(context, KEY_ALLOW_CACHE_3G_4G, allow);
    }

    public static void putAllowZanNotify(Context context, boolean allow) {
        SPUtils.put(context, KEY_ALLOW_NOTIFY_ZAN, allow);
    }

    public static void putAllowCommentNotify(Context context, boolean allow) {
        SPUtils.put(context, KEY_ALLOW_NOTIFY_COMMENT, allow);
    }

    public static boolean allowCacheWithMobile(Context context) {
        return (Boolean) SPUtils.get(context, KEY_ALLOW_CACHE_3G_4G, false);
    }

    public static boolean allowZanNotify(Context context) {
        return (Boolean) SPUtils.get(context, KEY_ALLOW_NOTIFY_ZAN, false);
    }

    public static boolean allowCommentNotify(Context context) {
        return (Boolean) SPUtils.get(context, KEY_ALLOW_NOTIFY_COMMENT, false);
    }
}
