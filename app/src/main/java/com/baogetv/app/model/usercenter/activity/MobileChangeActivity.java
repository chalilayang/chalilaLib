package com.baogetv.app.model.usercenter.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.baogetv.app.BaseActivity;
import com.baogetv.app.R;
import com.baogetv.app.apiinterface.UserApiService;
import com.baogetv.app.bean.ResponseBean;
import com.baogetv.app.customview.CustomToastUtil;
import com.baogetv.app.model.usercenter.LoginManager;
import com.baogetv.app.model.usercenter.customview.TitleInputView;
import com.baogetv.app.model.usercenter.customview.VerifyCodeInputView;
import com.baogetv.app.net.CustomCallBack;
import com.baogetv.app.net.RetrofitManager;

import java.util.List;

import retrofit2.Call;

public class MobileChangeActivity extends BaseActivity
        implements VerifyCodeInputView.VerifyCallBack{
    private static final String TAG = "MobileChangeActivity";
    private View stepOne;
    private TitleInputView mobileNumView;
    private VerifyCodeInputView verifyCodeView;
    private View nextStep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_change_mobile);
        initView();
    }

    private void initView() {
        findViewById(R.id.back_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        stepOne = findViewById(R.id.register_step_one);
        mobileNumView = (TitleInputView) findViewById(R.id.mobile_num_view);
        verifyCodeView = (VerifyCodeInputView) findViewById(R.id.verify_code_view);
        verifyCodeView.setVerifyCallBack(this);
        nextStep = findViewById(R.id.next_step_tv);
        nextStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mobile = mobileNumView.getInputText();
                if (TextUtils.isEmpty(mobile)) {
                    showShortToast(getString(R.string.mobile_num_null));
                    return;
                } else if (!LoginManager.isMobileNO(mobile)) {
                    showShortToast(getString(R.string.mobile_num_invalid));
                    return;
                }
                String verifyNum = verifyCodeView.getInputText();
                if (!TextUtils.isEmpty(verifyNum)) {
                    tryRegister();
                } else {
                    showShortToast(getString(R.string.verify_code_error));
                }
            }
        });
    }

    @Override
    public void onFetchClick() {
        String mobile = mobileNumView.getInputText();
        if (TextUtils.isEmpty(mobile)) {
            showShortToast(getString(R.string.mobile_num_null));
        } else if (!LoginManager.isMobileNO(mobile)) {
            showShortToast(getString(R.string.mobile_num_invalid));
        } else {
            sendSMS(mobile);
        }
    }

    public void sendSMS(String mobile) {
        UserApiService listService
                = RetrofitManager.getInstance().createReq(UserApiService.class);
        Call<ResponseBean<List<Object>>> listBeanCall
                = listService.sendMobileSMS(mobile);
        listBeanCall.enqueue(new CustomCallBack<List<Object>>() {
            @Override
            public void onSuccess(List<Object> data, String msg, int state) {
                Log.i(TAG, "onSuccess: ");
                verifyCodeView.startCountDown(60);
            }

            @Override
            public void onFailed(String error, int state) {
                Log.i(TAG, "onFailed: ");
                showShortToast(getString(R.string.verify_code_get_failed) + "：" + error);
            }
        });
    }

    private void tryRegister() {
        String mobile = mobileNumView.getInputText();
        String verifyNum = verifyCodeView.getInputText();
        if (TextUtils.isEmpty(mobile)) {
            CustomToastUtil.makeShort(
                    getApplicationContext(), getString(R.string.mobile_num_null));
            return;
        } else if (!LoginManager.isMobileNO(mobile)) {
            CustomToastUtil.makeShort(
                    getApplicationContext(), getString(R.string.mobile_num_invalid));
            return;
        }
        if (TextUtils.isEmpty(verifyNum)) {
            CustomToastUtil.makeShort(
                    getApplicationContext(), getString(R.string.verify_code_error));
            return;
        }
        UserApiService listService
                = RetrofitManager.getInstance().createReq(UserApiService.class);
        String token = LoginManager.getUserToken(getApplicationContext());
        Call<ResponseBean<List<Object>>> listBeanCall
                = listService.resetMobile(token, mobile, verifyNum);
        listBeanCall.enqueue(new CustomCallBack<List<Object>>() {
            @Override
            public void onSuccess(List<Object> data, String msg, int state) {
                Log.i(TAG, "onSuccess: ");
                showSuccess();
            }

            @Override
            public void onFailed(String error, int state) {
                Log.i(TAG, "onFailed: ");
                showShortToast(getString(R.string.verify_code_get_failed) + "：" + error);
            }
        });
    }

    private void showSuccess() {
        showShortToast(getString(R.string.mobile_change_success));
        setResult(RESULT_OK);
        finish();
    }
}
