package com.baogetv.app.model.usercenter.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.baogetv.app.BaseActivity;
import com.baogetv.app.R;
import com.baogetv.app.apiinterface.UserApiService;
import com.baogetv.app.bean.ResponseBean;
import com.baogetv.app.bean.UserDetailBean;
import com.baogetv.app.customview.CustomToastUtil;
import com.baogetv.app.customview.VideoPlayerView;
import com.baogetv.app.model.homepage.HomePageActivity;
import com.baogetv.app.model.usercenter.LoginManager;
import com.baogetv.app.net.CustomCallBack;
import com.baogetv.app.net.RetrofitManager;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import retrofit2.Call;

import static com.baogetv.app.constant.AppConstance.KEY_USER_DETAIL_BEAN;
import static com.baogetv.app.constant.AppConstance.REQUEST_CODE_FIND_PASSWORD_ACTIVITY;
import static com.baogetv.app.constant.AppConstance.REQUEST_CODE_LOGIN_ACTIVITY;
import static com.baogetv.app.constant.AppConstance.REQUEST_CODE_REGISTER_ACTIVITY;

public class VideoLoginActivity extends BaseActivity implements UMAuthListener {

    private static final String TAG = "VideoLoginActivity";
    private View loginBtn;
    private View registerBtn;
    private View wechatBtn;
    private View sinaBtn;
    private View qqBtn;
    private VideoPlayerView playerView;
    private boolean isFetchingResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
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
        setContentView(R.layout.activity_video_login);
        init();
    }

    private void init() {
        playerView = (VideoPlayerView) findViewById(R.id.player_view);
        loginBtn = findViewById(R.id.login_tv);
        if (loginBtn != null) {
            loginBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    LoginManager.startLogin(VideoLoginActivity.this);
                }
            });
        }
        registerBtn = findViewById(R.id.register_tv);
        if (registerBtn != null) {
            registerBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    LoginManager.startRegister(VideoLoginActivity.this);
                }
            });
        }
        findViewById(R.id.skip_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startHomeActivity(null);
            }
        });
        wechatBtn = findViewById(R.id.wechat_login);
        wechatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wxLogin();
            }
        });
        sinaBtn = findViewById(R.id.sina_login);
        sinaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sinaLogin();
            }
        });
        qqBtn = findViewById(R.id.qq_login);
        qqBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                qqLogin();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_LOGIN_ACTIVITY) {
            if (resultCode == RESULT_OK) {
                UserDetailBean bean = data.getParcelableExtra(KEY_USER_DETAIL_BEAN);
                startHomeActivity(bean);
            }
        } else if (requestCode == REQUEST_CODE_REGISTER_ACTIVITY) {
            if (resultCode == RESULT_OK) {
                UserDetailBean bean = data.getParcelableExtra(KEY_USER_DETAIL_BEAN);
                startHomeActivity(bean);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (playerView != null) {
            playerView.release();
        }
        UMShareAPI.get(this).release();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        UMShareAPI.get(this).onSaveInstanceState(outState);
    }

    private void startHomeActivity(UserDetailBean bean) {
        Intent intent = new Intent(this, HomePageActivity.class);
        if (bean != null) {
            intent.putExtra(KEY_USER_DETAIL_BEAN, bean);
        }
        startActivity(intent);
        finish();
    }

    private void loginPartner(String type, String openid, String name, String url) {
        UserApiService listService
                = RetrofitManager.getInstance().createReq(UserApiService.class);
        Call<ResponseBean<UserDetailBean>> beanCall
                = listService.loginPartner(type, openid, name, url);
        if (beanCall != null) {
            showLoadingDialog("正在登录", "");
            isFetchingResult = true;
            beanCall.enqueue(new CustomCallBack<UserDetailBean>() {
                @Override
                public void onSuccess(UserDetailBean data, String msg, int state) {
                    isFetchingResult = false;
                    if (data != null) {
                        LoginManager.updateDetailBean(getApplicationContext(), data);
                        loginSuccess(data);
                    } else {
                        loginFailed("LoginBean null");
                    }
                    hideLoadingDialog();
                }
                @Override
                public void onFailed(String error, int state) {
                    isFetchingResult = false;
                    loginFailed("Login fail " + error);
                    hideLoadingDialog();
                }
            });
        }
    }

    private void loginSuccess(UserDetailBean bean) {
        startHomeActivity(bean);
    }

    private void loginFailed(String error) {

    }

    //微信登录
    public void wxLogin() {
        UMShareAPI.get(getApplication()).doOauthVerify(this, SHARE_MEDIA.WEIXIN, this);
    }
    //QQ登录
    public void qqLogin() {
        UMShareAPI.get(getApplication()).doOauthVerify(this, SHARE_MEDIA.QQ, this);
    }
    //sina登录
    public void sinaLogin() {
        UMShareAPI.get(getApplication()).doOauthVerify(this, SHARE_MEDIA.SINA, this);
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
        UMShareAPI.get(getApplicationContext()).getPlatformInfo(this,
                platform, new UMAuthListener() {
                    @Override
                    public void onStart(SHARE_MEDIA share_media) {
                        Log.i(TAG, "onStart: ");
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
                                String openid = map.get("openid");
                                String nickName = map.get("name");
                                String pic = map.get("iconurl");
                                loginPartner(LoginManager.KEY_WECHAT, openid, nickName, pic);
                                break;
                            case QQ:
                                openid = map.get("openid");
                                nickName = map.get("name");
                                pic = map.get("iconurl");
                                loginPartner(LoginManager.KEY_QQ, openid, nickName, pic);
                                break;
                            case SINA:
                                openid = map.get("uid");
                                nickName = map.get("name");
                                pic = map.get("iconurl");
                                loginPartner(LoginManager.KEY_SINA, openid, nickName, pic);
                                break;
                        }
                    }

                    @Override
                    public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
                        Log.i(TAG, "onError: ");
                    }

                    @Override
                    public void onCancel(SHARE_MEDIA share_media, int i) {
                        Log.i(TAG, "onCancel: ");
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
}
