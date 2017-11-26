package com.baogetv.app.model.usercenter.present;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.baogetv.app.R;
import com.baogetv.app.apiinterface.UserApiService;
import com.baogetv.app.apiinterface.VideoListService;
import com.baogetv.app.bean.RegisterBean;
import com.baogetv.app.bean.ResponseBean;
import com.baogetv.app.bean.VideoListBean;
import com.baogetv.app.customview.CustomToastUtil;
import com.baogetv.app.model.usercenter.LoginManager;
import com.baogetv.app.model.usercenter.contracts.RegisterContract;
import com.baogetv.app.net.CustomCallBack;
import com.baogetv.app.net.RetrofitManager;

import java.util.List;

import retrofit2.Call;

/**
 * Created by chalilayang on 2017/11/19.
 */

public class RegisterPresenter implements RegisterContract.Presenter {
    private static final String TAG = "RegisterPresenter";
    private RegisterContract.View registerView;
    private int currentStep;
    private Context mContext;

    public RegisterPresenter(RegisterContract.View view) {
        registerView = view;
    }
    @Override
    public void init(Context context, Intent intent) {
        mContext = context;
        registerView.showStepOne();
        currentStep = 1;
    }

    @Override
    public void getVerifyNum(String mobileNum) {
        sendSMS(mobileNum);
    }

    @Override
    public void register(String mobileNum, String verifyCode, String password, String nick) {
        UserApiService listService
                = RetrofitManager.getInstance().createReq(UserApiService.class);
        String token = LoginManager.getKeyDeviceToken(mContext);
        Log.i(TAG, "register: " + mobileNum + " " + verifyCode + " " + password + " " + nick);
//        Call<ResponseBean<RegisterBean>> beanCall
//                = listService.register(mobileNum, );
//        if (listBeanCall != null) {
//
//        }
        registerView.showStepComplete();
        currentStep = 2;
    }

    @Override
    public int nextStep() {
        currentStep ++;
        if (currentStep >= 2) {
            currentStep = 2;
            registerView.showStepComplete();
        }
        return currentStep;
    }

    @Override
    public int previousStep() {
        currentStep --;
        if (currentStep == 1) {
            registerView.showStepOne();
        }
        return currentStep;
    }

    public void sendSMS(String mobile) {
        UserApiService listService
                = RetrofitManager.getInstance().createReq(UserApiService.class);
        Call<ResponseBean<List<Object>>> listBeanCall
                = listService.sendMobileSMS(mobile);
        listBeanCall.enqueue(new CustomCallBack<List<Object>>() {
            @Override
            public void onSuccess(List<Object> data) {
                Log.i(TAG, "onSuccess: ");
                registerView.startVerifyCountDown();
            }

            @Override
            public void onFailed(String error) {
                Log.i(TAG, "onFailed: ");
                CustomToastUtil.makeShort(
                        mContext, mContext.getString(R.string.verify_code_get_failed));
            }
        });
    }
}
