package com.baogetv.app.model.usercenter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.baogetv.app.bean.UserDetailBean;
import com.baogetv.app.model.usercenter.activity.FindPasswordActivity;
import com.baogetv.app.model.usercenter.activity.LoginActivity;
import com.baogetv.app.model.usercenter.activity.MobileChangeActivity;
import com.baogetv.app.model.usercenter.activity.RegisterActivity;
import com.baogetv.app.util.SettingManager;
import com.chalilayang.util.SPUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.baogetv.app.constant.AppConstance.REQUEST_CODE_CHANGE_MOBILE_ACTIVITY;
import static com.baogetv.app.constant.AppConstance.REQUEST_CODE_CHANGE_PASSWORD_ACTIVITY;
import static com.baogetv.app.constant.AppConstance.REQUEST_CODE_FIND_PASSWORD_ACTIVITY;
import static com.baogetv.app.constant.AppConstance.REQUEST_CODE_LOGIN_ACTIVITY;
import static com.baogetv.app.constant.AppConstance.REQUEST_CODE_REGISTER_ACTIVITY;

/**
 * Created by chalilayang on 2017/11/12.
 */

public class LoginManager {
    private static final String KEY_DEVICE_TOKEN = "DEVICE_TOKEN";
    private static final String KEY_USER_TOKEN = "USER_TOKEN";
    private static final String KEY_USER_ID = "USER_ID";

    private static UserDetailBean detailBean;
    public static boolean hasLogin(Context context) {
        String token = getUserToken(context);
        if (!TextUtils.isEmpty(token)) {
            return true;
        } else {
            return false;
        }
    }

    public static void updateDetailBean(Context context, UserDetailBean bean) {
        detailBean = bean;
        LoginManager.putUserToken(context, bean.getToken());
        LoginManager.putUserID(context, bean.getUser_id());
        boolean allowComment;
        try {
            allowComment = Integer.parseInt(bean.getIs_push_comments()) > 0;
            SettingManager.putAllowCommentNotify(context, allowComment);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        boolean allowZan;
        try {
            allowZan = Integer.parseInt(bean.getIs_push_likes()) > 0;
            SettingManager.putAllowZanNotify(context, allowZan);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    public static void startLogin(Activity activity) {
        Intent intent = new Intent(activity, LoginActivity.class);
        activity.startActivityForResult(intent, REQUEST_CODE_LOGIN_ACTIVITY);
    }

    public static void startRegister(Activity activity) {
        Intent intent = new Intent(activity, RegisterActivity.class);
        activity.startActivityForResult(intent, REQUEST_CODE_REGISTER_ACTIVITY);
    }

    public static void startChangeMobile(Activity activity) {
        Intent intent = new Intent(activity, MobileChangeActivity.class);
        activity.startActivityForResult(intent, REQUEST_CODE_CHANGE_MOBILE_ACTIVITY);
    }

    public static void startChangePassword(Activity activity) {
        Intent intent = new Intent(activity, MobileChangeActivity.class);
        activity.startActivityForResult(intent, REQUEST_CODE_CHANGE_PASSWORD_ACTIVITY);
    }

    public static void startFindPassword(Activity activity) {
        Intent intent = new Intent(activity, FindPasswordActivity.class);
        activity.startActivityForResult(intent, REQUEST_CODE_FIND_PASSWORD_ACTIVITY);
    }

    public static void putDeviceToken(Context context, String token) {
        SPUtils.put(context, KEY_DEVICE_TOKEN, token);
    }

    public static String getDeviceToken(Context context) {
        return (String) SPUtils.get(context, KEY_DEVICE_TOKEN, "");
    }

    public static void putUserToken(Context context, String token) {
        SPUtils.put(context, KEY_USER_TOKEN, token);
    }

    public static String getUserToken(Context context) {
        return (String) SPUtils.get(context, KEY_USER_TOKEN, "");
    }

    public static void putUserID(Context context, String uid) {
        SPUtils.put(context, KEY_USER_ID, uid);
    }

    public static String getUserID(Context context) {
        return (String) SPUtils.get(context, KEY_USER_ID, "");
    }

    public static void cleanUserToken(Context context) {
        detailBean = null;
        SPUtils.put(context, KEY_USER_TOKEN, "");
    }



    public static boolean isMobileNO(String mobiles) {
        //"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        String telRegex = "[1][3578]\\d{9}";
        if (TextUtils.isEmpty(mobiles)) {
            return false;
        } else {
            return mobiles.matches(telRegex);
        }
    }

    public static boolean isPasswordValid(String password) {
        if (password.length() > 0) {
            for (int t = 0; t < password.length(); t++) {
                String b = password.substring(t, t + 1);
                if (b.equals(" ")) {
                    return false;
                }
            }
            int count = 0;
            String regEx = "[\\u4e00-\\u9fa5]";
            Pattern p = Pattern.compile(regEx);
            Matcher m = p.matcher(password);
            while (m.find()) {
                for (int i = 0; i <= m.groupCount(); i++) {
                    count = count + 1;
                }
            }
            if (count > 0) {
                return false;
            }
            for (int i = 0; i < password.length(); i++) {
                char c = password.charAt(i);
                if (!Character.isLetterOrDigit(c)) {
                    return false;
                }
            }
        } else {
            return false;
        }
        return true;
    }
}
