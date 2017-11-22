package com.baogetv.app.model.usercenter.activity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.baogetv.app.BaseActivity;
import com.baogetv.app.R;
import com.baogetv.app.model.usercenter.contracts.RegisterContract;
import com.baogetv.app.model.usercenter.customview.TitleInputView;
import com.baogetv.app.model.usercenter.customview.VerifyCodeInputView;
import com.baogetv.app.model.usercenter.present.RegisterPresenter;

public class RegisterActivity extends BaseActivity
        implements RegisterContract.View, VerifyCodeInputView.VerifyCallBack {

    private RegisterPresenter registerPresenter;

    private View stepOne;
    private View stepComplete;
    private TitleInputView mobileNumView;
    private VerifyCodeInputView verifyCodeView;
    private View nextStep;
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
        verifyCodeView = (VerifyCodeInputView) findViewById(R.id.verify_code_view);
        verifyCodeView.setVerifyCallBack(this);
        stepComplete = findViewById(R.id.register_step_two);
        nextStep = findViewById(R.id.next_step_tv);
        nextStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerPresenter.register("", "");
            }
        });
    }

    @Override
    public void showStepOne() {
        currentStep = 0;
        if (stepOne != null) {
            stepOne.setVisibility(View.VISIBLE);
        }
        if (stepComplete != null) {
            stepComplete.setVisibility(View.GONE);
        }
    }

    @Override
    public void showStepComplete() {
        currentStep = 1;
        if (stepOne != null) {
            stepOne.setVisibility(View.GONE);
        }
        if (stepComplete != null) {
            stepComplete.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void startVerifyCountDown() {
        verifyCodeView.startCountDown(60);
    }

    @Override
    public void onFetchClick() {
        registerPresenter.getVerifyNum("");
    }

    @Override
    public void onBackPressed() {
        int cur = registerPresenter.previousStep();
        if (cur == 0) {
            super.onBackPressed();
        }
    }
}
