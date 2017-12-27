package com.baogetv.app.model.usercenter.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.baogetv.app.BaseActivity;
import com.baogetv.app.R;
import com.baogetv.app.apiinterface.UserApiService;
import com.baogetv.app.bean.HistoryBean;
import com.baogetv.app.bean.ResponseBean;
import com.baogetv.app.bean.UserDetailBean;
import com.baogetv.app.customview.CustomToastUtil;
import com.baogetv.app.customview.PressImageView;
import com.baogetv.app.db.entity.HistoryItemEntity;
import com.baogetv.app.model.usercenter.HistoryManager;
import com.baogetv.app.model.usercenter.LoginManager;
import com.baogetv.app.model.usercenter.customview.PasswordInputView;
import com.baogetv.app.model.usercenter.customview.TitleInputView;
import com.baogetv.app.net.CustomCallBack;
import com.baogetv.app.net.RetrofitManager;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.utils.SocializeUtils;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import retrofit2.Call;

import static com.baogetv.app.constant.AppConstance.KEY_USER_DETAIL_BEAN;
import static com.baogetv.app.constant.AppConstance.REQUEST_CODE_FIND_PASSWORD_ACTIVITY;
import static com.baogetv.app.constant.AppConstance.REQUEST_CODE_REGISTER_ACTIVITY;
import static com.umeng.socialize.bean.SHARE_MEDIA.QQ;
import static com.umeng.socialize.bean.SHARE_MEDIA.SINA;
import static com.umeng.socialize.bean.SHARE_MEDIA.WEIXIN;

public class LoginActivity extends BaseActivity
        implements PasswordInputView.OnForgetClickListener, UMAuthListener, PressImageView.OnClickBack {
    private static final String TAG = LoginActivity.class.getSimpleName();
    private TitleInputView mobileNumView;
    private PasswordInputView passwordView;
    private View loginBtn;
    private PressImageView wechatBtn;
    private PressImageView sinaBtn;
    private PressImageView qqBtn;
    private View newUserRegister;
    private boolean isFetchingResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_login);
        UMShareAPI.get(this).fetchAuthResultWithBundle(this, savedInstanceState, new UMAuthListener() {
            @Override
            public void onStart(SHARE_MEDIA platform) {
                hideLoadingDialog();
            }

            @Override
            public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
                showShortToast("onRestoreInstanceState Authorize succeed");
                final Set<Map.Entry<String, String>> entries = data.entrySet();
                final Iterator<Map.Entry<String, String>> iterator = entries.iterator();
                while (iterator.hasNext()) {
                    final Map.Entry<String, String> next = iterator.next();
                    Log.e(TAG, "====" + next.getKey() + "=======" + next.getValue());
                }
                switch (platform) {
                    case WEIXIN:
                        String openid = data.get("openid");
                        String nickName = data.get("name");
                        String pic = data.get("iconurl");
                        loginPartner(LoginManager.KEY_WECHAT, openid, nickName, pic);
                        break;
                    case QQ:
                        openid = data.get("openid");
                        nickName = data.get("name");
                        pic = data.get("iconurl");
                        loginPartner(LoginManager.KEY_QQ, openid, nickName, pic);
                        break;
                    case SINA:
                        openid = data.get("uid");
                        nickName = data.get("name");
                        pic = data.get("iconurl");
                        loginPartner(LoginManager.KEY_SINA, openid, nickName, pic);
                        break;
                }
            }

            @Override
            public void onError(SHARE_MEDIA platform, int action, Throwable t) {
                showShortToast("onRestoreInstanceState Authorize onError");
            }

            @Override
            public void onCancel(SHARE_MEDIA platform, int action) {
                showShortToast("onRestoreInstanceState Authorize onCancel");
            }
        });
        initView();
    }

    private void initView() {
        findViewById(R.id.back_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        mobileNumView = (TitleInputView) findViewById(R.id.mobile_num_view);
        passwordView = (PasswordInputView) findViewById(R.id.password_tv);
        passwordView.setForgetListener(this);
        loginBtn = findViewById(R.id.login_tv);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tryLogin();
            }
        });
        newUserRegister = findViewById(R.id.new_user_register);
        newUserRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginManager.startRegister(LoginActivity.this);
            }
        });
        wechatBtn = (PressImageView) findViewById(R.id.wechat_login);
        wechatBtn.setOnClickBack(this);
        sinaBtn = (PressImageView) findViewById(R.id.sina_login);
        sinaBtn.setOnClickBack(this);
        qqBtn = (PressImageView) findViewById(R.id.qq_login);
        qqBtn.setOnClickBack(this);
    }

    @Override
    public void onClick(View view) {
        Log.i(TAG, "onClick: ");
        switch (view.getId()) {
            case R.id.wechat_login:
                wxLogin();
                break;
            case R.id.qq_login:
                qqLogin();
                break;
            case R.id.sina_login:
                sinaLogin();
                break;
        }
    }

    private void tryLogin() {
        if (isFetchingResult) {
            showShortToast(getString(R.string.is_login_ing));
            return;
        }
        String mobile = mobileNumView.getInputText();
        String password = passwordView.getInputText();
        if (TextUtils.isEmpty(mobile)) {
            showShortToast(getString(R.string.mobile_num_null));
            return;
        } else if (!LoginManager.isMobileNO(mobile)) {
            showShortToast(getString(R.string.mobile_num_invalid));
            return;
        }
        if (TextUtils.isEmpty(password) || password.length() <= 6) {
            showShortToast(getString(R.string.password_too_short));
            return;
        }
        if (!LoginManager.isPasswordValid(password)) {
            showShortToast(getString(R.string.password_invalid));
            return;
        }
        UserApiService listService
                = RetrofitManager.getInstance().createReq(UserApiService.class);
        String token = LoginManager.getDeviceToken(getApplicationContext());
        Log.i(TAG, "register: " + mobile + " " + password);
        Call<ResponseBean<UserDetailBean>> beanCall
                = listService.login(mobile, password, token);
        if (beanCall != null) {
            isFetchingResult = true;
            beanCall.enqueue(new CustomCallBack<UserDetailBean>() {
                @Override
                public void onSuccess(UserDetailBean data, String msg, int state) {
                    isFetchingResult = false;
                    if (data != null) {
                        LoginManager.updateDetailBean(getApplicationContext(), data);
                        uploadHistory(getApplicationContext(), data);
                        loginSuccess(data);
                    } else {
                        loginFailed("LoginBean null");
                    }
                }
                @Override
                public void onFailed(String error, int state) {
                    isFetchingResult = false;
                    loginFailed("Login fail " + error);
                }
            });
        }
    }

    @Override
    public void onForgetClick() {
        LoginManager.startFindPassword(this);
    }

    private void loginSuccess(UserDetailBean bean) {
        Intent intent = new Intent();
        intent.putExtra(KEY_USER_DETAIL_BEAN, bean);
        setResult(RESULT_OK, intent);
        finish();
    }

    private void loginFailed(String msg) {
        showShortToast(msg);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_FIND_PASSWORD_ACTIVITY) {
            if (resultCode == RESULT_OK) {
                showShortToast("请用新密码进行登录");
            }
        } else if (requestCode == REQUEST_CODE_REGISTER_ACTIVITY) {
            if (resultCode == RESULT_OK) {
                UserDetailBean bean = data.getParcelableExtra(KEY_USER_DETAIL_BEAN);
                loginSuccess(bean);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        UMShareAPI.get(this).release();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        UMShareAPI.get(this).onSaveInstanceState(outState);
    }

    private void loginPartner(String type, String openid, String name, String url) {
        UserApiService listService
                = RetrofitManager.getInstance().createReq(UserApiService.class);
        String deviceToken = LoginManager.getDeviceToken(getApplicationContext());
        Call<ResponseBean<UserDetailBean>> beanCall
                = listService.loginPartner(type, openid, name, url, deviceToken);
        if (beanCall != null) {
            isFetchingResult = true;
            beanCall.enqueue(new CustomCallBack<UserDetailBean>() {
                @Override
                public void onSuccess(UserDetailBean data, String msg, int state) {
                    isFetchingResult = false;
                    if (data != null) {
                        LoginManager.updateDetailBean(getApplicationContext(), data);
                        uploadHistory(getApplicationContext(), data);
                    } else {
                        loginFailed("LoginBean null");
                    }
                }
                @Override
                public void onFailed(String error, int state) {
                    isFetchingResult = false;
                    showShortToast(error);
                    loginFailed("Login fail " + error);
                }
            });
        }
    }

    //微信登录
    public void wxLogin() {
        UMShareAPI.get(getApplication()).doOauthVerify(LoginActivity.this, WEIXIN, this);
    }
    //QQ登录
    public void qqLogin() {
        UMShareAPI.get(getApplication()).doOauthVerify(this, QQ, this);
    }
    //sina登录
    public void sinaLogin() {
        UMShareAPI.get(getApplication()).doOauthVerify(this, SINA, this);
    }

    /**
     * @desc 授权开始的回调
     * @param platform 平台名称
     */
    @Override
    public void onStart(SHARE_MEDIA platform) {
        showLoadingDialog("三方登录...", "success");
    }

    /**
     * @desc 授权成功的回调
     * @param platform 平台名称
     * @param action 行为序号，开发者用不上
     * @param data 用户资料返回
     */
    @Override
    public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
        hideLoadingDialog();
        final Set<Map.Entry<String, String>> entries = data.entrySet();
        final Iterator<Map.Entry<String, String>> iterator = entries.iterator();
        while (iterator.hasNext()) {
            final Map.Entry<String, String> next = iterator.next();
            Log.e("TAG", "====" + next.getKey() + "=======" + next.getValue());
        }
        //当授权成功后，去获取用户信息的方法里面获取参数
        UMShareAPI.get(getApplicationContext()).getPlatformInfo(LoginActivity.this,
                platform, new UMAuthListener() {
            @Override
            public void onStart(SHARE_MEDIA share_media) {

            }

            @Override
            public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
                final Set<Map.Entry<String, String>> entries = map.entrySet();
                final Iterator<Map.Entry<String, String>> iterator = entries.iterator();
                while (iterator.hasNext()) {
                    final Map.Entry<String, String> next = iterator.next();
                    Log.e(TAG, "====" + next.getKey() + "=======" + next.getValue());
                }
                switch (share_media) {
                    case WEIXIN:
                        String openid = map.get("refreshToken");
                        String nickName = map.get("name");
                        String pic = map.get("iconurl");
                        loginPartner(LoginManager.KEY_WECHAT, openid, nickName, pic);
                        break;
                    case QQ:
                        openid = map.get("accessToken");
                        nickName = map.get("name");
                        pic = map.get("iconurl");
                        loginPartner(LoginManager.KEY_QQ, openid, nickName, pic);
                        break;
                    case SINA:
                        openid = map.get("accessToken");
                        nickName = map.get("name");
                        pic = map.get("iconurl");
                        loginPartner(LoginManager.KEY_SINA, openid, nickName, pic);
                        break;
                }
            }

            @Override
            public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {

            }

            @Override
            public void onCancel(SHARE_MEDIA share_media, int i) {

            }
        });
    }

    /**
     * @desc 授权失败的回调
     * @param platform 平台名称
     * @param action 行为序号，开发者用不上
     * @param t 错误原因
     */
    @Override
    public void onError(SHARE_MEDIA platform, int action, Throwable t) {
        hideLoadingDialog();
        Log.i(TAG, "onError: " + t.getMessage());
        CustomToastUtil.makeLongText(this, "失败：" + t.getMessage());
    }

    /**
     * @desc 授权取消的回调
     * @param platform 平台名称
     * @param action 行为序号，开发者用不上
     */
    @Override
    public void onCancel(SHARE_MEDIA platform, int action) {
        hideLoadingDialog();
        CustomToastUtil.makeLongText(this, "取消了");
    }

    public void uploadHistory(Context context, final UserDetailBean detailBean) {
        UserApiService userApiService
                = RetrofitManager.getInstance().createReq(UserApiService.class);
        String id = "";
        List<HistoryItemEntity> list
                = HistoryManager.getInstance(context).getHistoryList();
        if (list != null) {
            for (int index = 0, count = list.size(); index < count; index ++) {
                if (index == 0) {
                    id = list.get(index).getHistoryId();
                } else {
                    id = id + "," + list.get(index).getHistoryId();
                }
            }
        }
        Log.i(TAG, "uploadHistory: " + detailBean.getToken() + " " + id);
        if (!TextUtils.isEmpty(id)) {
            Call<ResponseBean<List<Object>>> call = userApiService.editHistory(detailBean.getToken(), id);
            if (call != null) {
                call.enqueue(new CustomCallBack<List<Object>>() {
                    @Override
                    public void onSuccess(List<Object> data, String msg, int state) {
                        HistoryManager.getInstance(getApplicationContext()).clearHistory();
                        loginSuccess(detailBean);
                    }

                    @Override
                    public void onFailed(String error, int state) {
                        showShortToast(error);
                        HistoryManager.getInstance(getApplicationContext()).clearHistory();
                        loginSuccess(detailBean);
                    }
                });
            }
        } else {
            loginSuccess(detailBean);
        }
    }
}
