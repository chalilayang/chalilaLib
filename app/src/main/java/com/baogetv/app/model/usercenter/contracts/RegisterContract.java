package com.baogetv.app.model.usercenter.contracts;

import android.content.Context;
import android.content.Intent;

import com.baogetv.app.BasePresenter;
import com.baogetv.app.BaseView;


/**
 * Created by chalilayang on 2016/12/14.
 */

public interface RegisterContract {

    interface View extends BaseView<Presenter> {
        void showStepOne();
        void showStepComplete();
        void startVerifyCountDown();
    }

    interface Presenter extends BasePresenter {
        void init(Context context, Intent intent);
        void getVerifyNum(String mobileNum);
        void register(String mobileNum, String verifyCode, String password, String nick);
        int nextStep();
        int previousStep();
    }
}
