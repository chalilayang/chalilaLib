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
    public static int REQUEST_CODE_USER_INFO_ACTIVITY = REQUEST_CODE_FIND_PASSWORD_ACTIVITY + 1;

    public static final String UMENG_PUSH_API_KEY = "5a0e54ddf29d9817aa0000d7";
    public static final String UMENG_PUSH_MESSAGE_SECRET = "367df8049e6d8f1cb4a9ef2a2e0cbf38";
    public static final String UMENG_PUSH_MASTER_SECRET = "8djpfliumffpjnmy4bshylvjrkvjd6qr";

    public static final String KEY_USER_DETAIL_BEAN = "USER_DETAIL_BEAN";
    public static final String KEY_VIDEO_ID = "KEY_VIDEO_ID";
    public static final String KEY_VIDEO_DETAIL = "KEY_VIDEO_DETAIL";
    public static final String KEY_LEVEL_LIST = "KEY_LEVEL_LIST";

    public static final String QQ_APP_ID = "1106573350";
    public static final String QQ_APP_KEY = "lRbPqYJ3y7nICy8I";

    public static final String WEICHAT_APP_ID = "wx5f458bd391d3d86f";
    public static final String WEICHAT_APP_SECRET = "82a20462f78cd6f099d587026713b6e4";

    public static final String SINA_APP_ID = "wx5f458bd391d3d86f";
    public static final String SINA_APP_SECRET = null;
}
