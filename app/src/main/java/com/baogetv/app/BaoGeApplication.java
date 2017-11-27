package com.baogetv.app;

import android.app.Application;
import android.util.Log;

import com.baogetv.app.model.usercenter.LoginManager;
import com.baogetv.app.push.MyPushIntentService;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;

/**
 * Created by chalilayang on 2017/10/19.
 */

public class BaoGeApplication extends Application {
    private static final String TAG = BaoGeApplication.class.getSimpleName();
    /**
     * Called when the application is starting, before any activity, service,
     * or receiver objects (excluding content providers) have been created.
     * Implementations should be as quick as possible (for example using
     * lazy initialization of state) since the time spent in this function
     * directly impacts the performance of starting the first activity,
     * service, or receiver in a process.
     * If you override this method, be sure to call super.onCreate().
     */
    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "onCreate: ");
        PushAgent mPushAgent = PushAgent.getInstance(this);
        //注册推送服务，每次调用register方法都会回调该接口
        mPushAgent.register(new IUmengRegisterCallback() {

            @Override
            public void onSuccess(String deviceToken) {
                //注册成功会返回device token
                Log.i(TAG, "onSuccess: " + deviceToken);
                LoginManager.putDeviceToken(getApplicationContext(), deviceToken);
            }

            @Override
            public void onFailure(String s, String s1) {
                Log.i(TAG, "onFailure: " + s + " " + s1);

            }
        });
        mPushAgent.setPushIntentServiceClass(MyPushIntentService.class);
    }
}
