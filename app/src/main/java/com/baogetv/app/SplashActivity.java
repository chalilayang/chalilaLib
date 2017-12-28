package com.baogetv.app;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;

import com.baogetv.app.apiinterface.UserApiService;
import com.baogetv.app.bean.ResponseBean;
import com.baogetv.app.bean.UserDetailBean;
import com.baogetv.app.downloader.DownloadService;
import com.baogetv.app.downloader.config.Config;
import com.baogetv.app.model.homepage.HomePageActivity;
import com.baogetv.app.model.usercenter.LoginManager;
import com.baogetv.app.model.usercenter.activity.VideoLoginActivity;
import com.baogetv.app.net.CustomCallBack;
import com.baogetv.app.net.RetrofitManager;
import com.baogetv.app.util.SystemUtil;

import retrofit2.Call;

import static com.baogetv.app.constant.AppConstance.KEY_USER_DETAIL_BEAN;

public class SplashActivity extends BaseActivity {

    private static final String TAG = "SplashActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        String versionName = SystemUtil.getAppVersionName(getApplicationContext());
        ((TextView)findViewById(R.id.version_tv)).setText("版本号：" + versionName);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (TextUtils.isEmpty(LoginManager.getUserToken(getApplicationContext()))) {
            startVideoLoginActivity();
        } else {
            getUserDetail();
        }
    }

    private Handler mHandler = new Handler() {
        public static final int MSG_WRITE_SUCCESS = 57;
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_WRITE_SUCCESS:
                    break;
            }
        }
    };
    private void getUserDetail() {
        UserApiService userApiService
                = RetrofitManager.getInstance().createReq(UserApiService.class);
        Call<ResponseBean<UserDetailBean>> call
                = userApiService.getUserDetail(LoginManager.getUserToken(getApplicationContext()), null);
        if (call != null) {
            call.enqueue(new CustomCallBack<UserDetailBean>() {
                @Override
                public void onSuccess(UserDetailBean data, String msg, int state) {
                    if (data != null) {
                        LoginManager.updateDetailBean(getApplicationContext(), data);
                        startHomeActivity(data);
                    } else {
                        startVideoLoginActivity();
                    }
                }

                @Override
                public void onFailed(String error, int state) {
                    Log.i(TAG, "onFailed: " + state + " " + error);
                    showShortToast(error);
                    LoginManager.cleanUserToken(getApplicationContext());
                    startVideoLoginActivity();
                }
            });
        }
    }

    private void startVideoLoginActivity() {
        Intent intent = new Intent(this, VideoLoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void startHomeActivity(UserDetailBean bean) {
        Intent intent = new Intent(this, HomePageActivity.class);
        intent.putExtra(KEY_USER_DETAIL_BEAN, bean);
        startActivity(intent);
        finish();
    }
}
