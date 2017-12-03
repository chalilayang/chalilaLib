package com.baogetv.app.model.usercenter.contracts;

import android.content.Context;
import android.content.Intent;

import com.baogetv.app.BasePresenter;
import com.baogetv.app.BaseView;
import com.baogetv.app.bean.UserDetailBean;


/**
 * Created by chalilayang on 2016/12/14.
 */

public interface FindPasswordContract {

    interface View extends BaseView<Presenter> {
        void showStepOne();
        void showStepComplete();
        void startVerifyCountDown();
        void showTip(String msg);
        void showSuccess();
        void registerFailed(String msg);
    }

    interface Presenter extends BasePresenter {
        void init(Context context, Intent intent);
        void getVerifyNum(String mobileNum);
        boolean find(String mobileNum, String verifyCode, String password);
        int nextStep();
        int previousStep();
    }
}
