package com.baogetv.app;

import android.app.Application;
import android.util.Log;

import com.baogetv.app.constant.AppConstance;
import com.baogetv.app.downloader.DownloadService;
import com.baogetv.app.model.usercenter.LoginManager;
import com.baogetv.app.net.RetrofitManager;
import com.umeng.analytics.MobclickAgent;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.MsgConstant;
import com.umeng.message.PushAgent;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.common.QueuedWork;
import com.xiasuhuei321.loadingdialog.manager.StyleManager;
import com.xiasuhuei321.loadingdialog.view.LoadingDialog;

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
        RetrofitManager.init(this.getApplicationContext());
        PushAgent mPushAgent = PushAgent.getInstance(this);
        mPushAgent.setDebugMode(AppConstance.DEBUG);
        mPushAgent.setNotificationPlaySound(MsgConstant.NOTIFICATION_PLAY_SDK_ENABLE);
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

        //mPushAgent.setNotificationPlaySound(MsgConstant.NOTIFICATION_P
        Config.DEBUG = AppConstance.DEBUG;
        QueuedWork.isUseThreadPool = false;
        UMShareAPI.get(this);

        PlatformConfig.setWeixin(AppConstance.WE_CHAT_APP_ID, AppConstance.WE_CHAT_APP_SECRET);
        PlatformConfig.setQQZone(AppConstance.QQ_APP_ID, AppConstance.QQ_APP_KEY);
        PlatformConfig.setSinaWeibo(AppConstance.SINA_APP_ID, AppConstance.SINA_APP_SECRET, "https://www.baoge.tv/callback");

        MobclickAgent.setScenarioType(getApplicationContext(), MobclickAgent.EScenarioType.E_UM_NORMAL);
        StyleManager s = new StyleManager();
        s.Anim(false).repeatTime(0).contentSize(-1).intercept(true);
        LoadingDialog.initStyle(s);
        try {
            com.baogetv.app.downloader.config.Config config = new com.baogetv.app.downloader.config.Config();
            //set download quantity at the same time.
            config.setDownloadThread(1);
            //set each download info thread number
            config.setEachDownloadThread(1);
            config.setConnectTimeout(10000);
            config.setReadTimeout(10000);
            DownloadService.getDownloadManager(getApplicationContext(), config);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
