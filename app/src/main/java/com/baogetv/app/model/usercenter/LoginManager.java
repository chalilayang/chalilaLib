package com.baogetv.app.model.usercenter;

import android.content.Context;
import android.text.TextUtils;

import com.chalilayang.util.SPUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
