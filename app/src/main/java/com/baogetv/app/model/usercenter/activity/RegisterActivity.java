package com.baogetv.app.model.usercenter.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;

import com.baogetv.app.BaseActivity;
import com.baogetv.app.R;
import com.baogetv.app.bean.UserDetailBean;
import com.baogetv.app.customview.CustomToastUtil;
import com.baogetv.app.model.usercenter.LoginManager;
import com.baogetv.app.model.usercenter.contracts.RegisterContract;
import com.baogetv.app.model.usercenter.customview.TitleInputView;
import com.baogetv.app.model.usercenter.customview.VerifyCodeInputView;
import com.baogetv.app.model.usercenter.present.RegisterPresenter;

import static com.baogetv.app.constant.AppConstance.KEY_USER_DETAIL_BEAN;
import static com.baogetv.app.model.usercenter.activity.AreaChooseActivity.REQUEST_CODE_AREA_CHOOSE;

public class RegisterActivity extends BaseActivity
        implements RegisterContract.View, VerifyCodeInputView.VerifyCallBack {

    private RegisterPresenter registerPresenter;
    private View stepOne;
    private View stepComplete;
    private TitleInputView mobileNumView;
    private VerifyCodeInputView verifyCodeView;
    private TitleInputView nickNameView;
    private TitleInputView passwordView;
    private TitleInputView passwordConfirmView;
    private View nextStep;
    private View completeBtn;
    private int currentStep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_register);
        initView();
        registerPresenter = new RegisterPresenter(this);
        registerPresenter.init(getApplicationContext(), getIntent());
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
        mobileNumView.setOnAreaCallBack(new TitleInputView.OnAreaChooseCallback() {
            @Override
            public void onAreaClick() {
                Intent intent = new Intent(RegisterActivity.this, AreaChooseActivity.class);
                RegisterActivity.this.startActivityForResult(intent, REQUEST_CODE_AREA_CHOOSE);
            }
        });
        verifyCodeView = (VerifyCodeInputView) findViewById(R.id.verify_code_view);
        verifyCodeView.setVerifyCallBack(this);
        nextStep = findViewById(R.id.next_step_tv);
        nextStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentStep == 1) {
                    String mobile = mobileNumView.getInputText();
                    if (TextUtils.isEmpty(mobile)) {
                        CustomToastUtil.makeShort(
                                getApplicationContext(), getString(R.string.mobile_num_null));
                        return;
                    } else if (!LoginManager.isMobileNO(mobile)) {
                        CustomToastUtil.makeShort(
                                getApplicationContext(), getString(R.string.mobile_num_invalid));
                        return;
                    }
                    String verifyNum = verifyCodeView.getInputText();
                    if (!TextUtils.isEmpty(verifyNum)) {
                        registerPresenter.nextStep();
                    } else {
                        CustomToastUtil.makeShort(
                                getApplicationContext(), getString(R.string.verify_code_error));
                    }
                }
            }
        });
        nickNameView = (TitleInputView) findViewById(R.id.nick_name_tv);
        passwordView = (TitleInputView) findViewById(R.id.password_tv);
        passwordConfirmView = (TitleInputView) findViewById(R.id.password_confirm_tv);
        stepComplete = findViewById(R.id.register_step_two);
        completeBtn = findViewById(R.id.complete_tv);
        completeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tryRegister();
            }
        });
    }

    @Override
    public void showStepOne() {
        currentStep = 1;
        if (stepOne != null) {
            stepOne.setVisibility(View.VISIBLE);
        }
        if (stepComplete != null) {
            stepComplete.setVisibility(View.GONE);
        }
    }

    @Override
    public void showStepComplete() {
        currentStep = 2;
        if (stepOne != null) {
            stepOne.setVisibility(View.GONE);
        }
        if (stepComplete != null) {
            stepComplete.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void startVerifyCountDown() {
        mobileNumView.enableInput(false);
        verifyCodeView.startCountDown(60);
    }

    @Override
    public void onFetchClick() {
        String mobile = mobileNumView.getInputText();
        if (TextUtils.isEmpty(mobile)) {
            CustomToastUtil.makeShort(getApplicationContext(), getString(R.string.mobile_num_null));
        } else if (!LoginManager.isMobileNO(mobile)) {
            CustomToastUtil.makeShort(getApplicationContext(), getString(R.string.mobile_num_invalid));
        } else {
            registerPresenter.getVerifyNum(mobile);
        }
    }

    @Override
    public void onBackPressed() {
        int cur = registerPresenter.previousStep();
        if (cur == 0) {
            super.onBackPressed();
        }
    }

    private void tryRegister() {
        String mobile = mobileNumView.getInputText();
        String verifyNum = verifyCodeView.getInputText();
        String nickName = nickNameView.getInputText();
        String password = passwordView.getInputText();
        String passConfirm = passwordConfirmView.getInputText();
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
        if (TextUtils.isEmpty(nickName)) {
            CustomToastUtil.makeShort(
                    getApplicationContext(), getString(R.string.nick_name_null));
            return;
        }
        if (TextUtils.isEmpty(password) || password.length() <= 6) {
            CustomToastUtil.makeShort(
                    getApplicationContext(), getString(R.string.password_too_short));
            return;
        }
        if (!LoginManager.isPasswordValid(password)) {
            CustomToastUtil.makeShort(
                    getApplicationContext(), getString(R.string.password_invalid));
            return;
        }
        if (!password.equals(passConfirm)) {
            CustomToastUtil.makeShort(
                    getApplicationContext(), getString(R.string.password_not_same));
            return;
        }
        if (registerPresenter.register(mobile, verifyNum, password, nickName)) {
            stepComplete.setAlpha(0.4f);
            stepComplete.setEnabled(false);
        }
    }

    @Override
    public void showTip(String msg) {
        showShortToast(msg);
    }

    @Override
    public void registerFailed(String msg) {
        stepComplete.setAlpha(1f);
        stepComplete.setEnabled(true);
        showTip(msg);
    }

    @Override
    public void showSuccess(UserDetailBean bean) {
        Intent intent = new Intent();
        intent.putExtra(KEY_USER_DETAIL_BEAN, bean);
        setResult(RESULT_OK, intent);
        finish();
    }
}
