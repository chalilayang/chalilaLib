package com.baogetv.app.constant;

/**
 * Created by chalilayang on 2017/10/18.
 */

public class AppConstance {
    public static final boolean DEBUG = true;


    public static int REQUEST_CODE_LOGIN_ACTIVITY = 1000;
    public static int REQUEST_CODE_REGISTER_ACTIVITY = REQUEST_CODE_LOGIN_ACTIVITY + 1;
    public static int REQUEST_CODE_SETTING_ACTIVITY = REQUEST_CODE_REGISTER_ACTIVITY + 1;
    public static int REQUEST_CODE_CHANGE_MOBILE_ACTIVITY = REQUEST_CODE_SETTING_ACTIVITY + 1;
    public static int REQUEST_CODE_CHANGE_PASSWORD_ACTIVITY = REQUEST_CODE_CHANGE_MOBILE_ACTIVITY + 1;
    public static int REQUEST_CODE_FIND_PASSWORD_ACTIVITY = REQUEST_CODE_CHANGE_PASSWORD_ACTIVITY + 1;

    public static final String UMENG_PUSH_API_KEY = "5a0e54ddf29d9817aa0000d7";
    public static final String UMENG_PUSH_MESSAGE_SECRET = "367df8049e6d8f1cb4a9ef2a2e0cbf38";
    public static final String UMENG_PUSH_MASTER_SECRET = "8djpfliumffpjnmy4bshylvjrkvjd6qr";

    public static final String KEY_USER_DETAIL_BEAN = "USER_DETAIL_BEAN";
}
