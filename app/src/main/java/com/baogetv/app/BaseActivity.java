package com.baogetv.app;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.baogetv.app.customview.CustomToastUtil;
import com.bumptech.glide.Glide;
import com.umeng.analytics.MobclickAgent;
import com.umeng.message.PushAgent;
import com.xiasuhuei321.loadingdialog.view.LoadingDialog;

import org.greenrobot.eventbus.EventBus;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PushAgent.getInstance(this).onAppStart();
        setStatusBarColor(getResources().getColor(R.color.black));
        if (!useActionBar() && getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (useEventBus()) {
            EventBus.getDefault().register(this);
        }
    }

    protected boolean useActionBar() {
        return false;
    }
    public boolean useEventBus() {
        return false;
    }
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    protected void initState() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    static void translucentStatusBar(Activity activity, boolean hideStatusBarBackground) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            //添加Flag把状态栏设为可绘制模式
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            if (hideStatusBarBackground) {
                //如果为全透明模式，取消设置Window半透明的Flag
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                //设置状态栏为透明
                window.setStatusBarColor(Color.TRANSPARENT);
                //设置window的状态栏不可见
                window.getDecorView().setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            } else {
                //如果为半透明模式，添加设置Window半透明的Flag
                window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                //设置系统状态栏处于可见状态
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
            }
            //view不根据系统窗口来调整自己的布局
            ViewGroup mContentView = (ViewGroup) window.findViewById(Window.ID_ANDROID_CONTENT);
            View mChildView = mContentView.getChildAt(0);
            if (mChildView != null) {
                ViewCompat.setFitsSystemWindows(mChildView, false);
                ViewCompat.requestApplyInsets(mChildView);
            }
        }
    }

    void setStatusBarColor(int statusColor) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            //取消状态栏透明
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //添加Flag把状态栏设为可绘制模式
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            //设置状态栏颜色
            window.setStatusBarColor(statusColor);
            //设置系统状态栏处于可见状态
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
            //让view不根据系统窗口来调整自己的布局
            ViewGroup mContentView = (ViewGroup) window.findViewById(Window.ID_ANDROID_CONTENT);
            View mChildView = mContentView.getChildAt(0);
            if (mChildView != null) {
                ViewCompat.setFitsSystemWindows(mChildView, false);
                ViewCompat.requestApplyInsets(mChildView);
            }
        }
    }

    public void showShortToast(String msg) {
        CustomToastUtil.makeShort(getApplicationContext(), msg);
    }

    protected void showError() {
        View view = findViewById(R.id.error_view);
        if (view != null) {
            view.setVisibility(View.VISIBLE);
        }
    }

    private LoadingDialog loadingDialog;
    public void showLoadingDialog(String msg, String success) {
        if (loadingDialog != null) {
            loadingDialog.close();
        }
        loadingDialog = new LoadingDialog(this);
        loadingDialog.setLoadingText(msg)
                .setSuccessText(success)//显示加载成功时的文字
                //.setFailedText("加载失败")
//                .setInterceptBack(intercept_back_event)
//                .setLoadSpeed(speed)
//                .setRepeatCount(repeatTime)
//                .setDrawColor(color)
                .show();
    }

    public void hideLoadingDialog() {
        if (loadingDialog != null) {
            loadingDialog.close();
        }
    }

    public void loadSuccess() {
        if (loadingDialog != null) {
            loadingDialog.loadSuccess();
        }
    }
}
