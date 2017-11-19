package com.baogetv.app.model.usercenter.present;

import android.content.Context;
import android.content.Intent;

import com.baogetv.app.model.usercenter.contracts.RegisterContract;

/**
 * Created by chalilayang on 2017/11/19.
 */

public class RegisterPresenter implements RegisterContract.Presenter {
    private RegisterContract.View registerView;
    private int currentStep;

    public RegisterPresenter(RegisterContract.View view) {
        registerView = view;
    }
    @Override
    public void init(Context context, Intent intent) {
        registerView.showStepOne();
        currentStep = 1;
    }

    @Override
    public void getVerifyNum(String mobileNum) {
        registerView.startVerifyCountDown();
    }

    @Override
    public void register(String mobileNum, String verifyCode) {
        registerView.showStepComplete();
        currentStep = 2;
    }

    @Override
    public int previousStep() {
        currentStep --;
        if (currentStep == 1) {
            registerView.showStepOne();
        }
        return currentStep;
    }
}
