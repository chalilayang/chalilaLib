package com.baogetv.app.model.usercenter.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.baogetv.app.BaseActivity;
import com.baogetv.app.R;
import com.baogetv.app.apiinterface.UserApiService;
import com.baogetv.app.bean.LoginBean;
import com.baogetv.app.bean.ResponseBean;
import com.baogetv.app.model.usercenter.LoginManager;
import com.baogetv.app.model.usercenter.customview.PasswordInputView;
import com.baogetv.app.model.usercenter.customview.TitleInputView;
import com.baogetv.app.net.CustomCallBack;
import com.baogetv.app.net.RetrofitManager;

import retrofit2.Call;

public class LoginActivity extends BaseActivity {
    private static final String TAG = LoginActivity.class.getSimpleName();
    public static final String KEY_LOGIN_BEAN = "KEY_LOGIN_BEAN";
    private TitleInputView mobileNumView;
    private PasswordInputView passwordView;
    private View loginBtn;
    private View wechatBtn;
    private View sinaBtn;
    private View qqBtn;
    private boolean isLogining;

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
        loginBtn = findViewById(R.id.login_tv);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tryLogin();
            }
        });
    }

    private void tryLogin() {
        if (isLogining) {
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
        Call<ResponseBean<LoginBean>> beanCall
                = listService.login(mobile, password, token);
        if (beanCall != null) {
            isLogining = true;
            beanCall.enqueue(new CustomCallBack<LoginBean>() {
                @Override
                public void onSuccess(LoginBean data) {
                    isLogining = false;
                    if (data != null) {
                        LoginManager.putUserToken(getApplicationContext(), data.getToken());
                        loginSuccess(data);
                    } else {
                        loginFailed("LoginBean null");
                    }
                }
                @Override
                public void onFailed(String error) {
                    isLogining = false;
                    loginFailed("Login fail " + error);
                }
            });
        }
    }

    private void loginSuccess(LoginBean bean) {
        Intent intent = new Intent();
        intent.putExtra(KEY_LOGIN_BEAN, bean);
        setResult(RESULT_OK, intent);
        finish();
    }

    private void loginFailed(String msg) {
        showShortToast(msg);
    }
}
