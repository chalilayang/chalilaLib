package com.baogetv.app.model.usercenter.activity;

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
import com.baogetv.app.bean.ResponseBean;
import com.baogetv.app.bean.UserDetailBean;
import com.baogetv.app.customview.CustomToastUtil;
import com.baogetv.app.model.usercenter.LoginManager;
import com.baogetv.app.model.usercenter.customview.PasswordInputView;
import com.baogetv.app.model.usercenter.customview.TitleInputView;
import com.baogetv.app.net.CustomCallBack;
import com.baogetv.app.net.RetrofitManager;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.utils.SocializeUtils;

import java.util.Map;

import retrofit2.Call;

import static com.baogetv.app.constant.AppConstance.KEY_USER_DETAIL_BEAN;
import static com.baogetv.app.constant.AppConstance.REQUEST_CODE_FIND_PASSWORD_ACTIVITY;
import static com.baogetv.app.constant.AppConstance.REQUEST_CODE_REGISTER_ACTIVITY;

public class LoginActivity extends BaseActivity
        implements PasswordInputView.OnForgetClickListener, UMAuthListener {
    private static final String TAG = LoginActivity.class.getSimpleName();
    private TitleInputView mobileNumView;
    private PasswordInputView passwordView;
    private View loginBtn;
    private View wechatBtn;
    private View sinaBtn;
    private View qqBtn;
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
        wechatBtn = findViewById(R.id.wechat_login);
        wechatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wxLogin();
            }
        });
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


    //微信登录
    public void wxLogin() {
        UMShareAPI.get(getApplication()).doOauthVerify(LoginActivity.this, SHARE_MEDIA.WEIXIN, this);
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
        showShortToast("成功了");
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
