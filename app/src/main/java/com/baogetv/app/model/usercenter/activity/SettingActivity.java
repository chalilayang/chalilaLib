package com.baogetv.app.model.usercenter.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.baogetv.app.BaseTitleActivity;
import com.baogetv.app.R;
import com.baogetv.app.apiinterface.UserApiService;
import com.baogetv.app.bean.ResponseBean;
import com.baogetv.app.model.usercenter.LoginManager;
import com.baogetv.app.model.usercenter.customview.MineLineItemView;
import com.baogetv.app.net.CustomCallBack;
import com.baogetv.app.net.RetrofitManager;
import com.baogetv.app.util.SettingManager;

import java.util.List;

import retrofit2.Call;


public class SettingActivity extends BaseTitleActivity implements View.OnClickListener {

    private MineLineItemView netSet;
    private MineLineItemView clearCache;
    private MineLineItemView thumbUpPush;
    private MineLineItemView commentPush;
    private MineLineItemView giveScore;
    private MineLineItemView advice;
    private MineLineItemView curVersion;
    private MineLineItemView videoIntroduce;
    private MineLineItemView userLicense;
    private MineLineItemView versionRight;
    private View loginOut;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleActivity(getString(R.string.setting));
        netSet = (MineLineItemView) findViewById(R.id.net_set);
        netSet.setOpenState(SettingManager.allowCacheWithMobile(getApplicationContext()));
        netSet.setClickCallback(new MineLineItemView.ClickCallback() {
            @Override
            public void onMoreViewClick() {
                netSet.setOpenState(!netSet.isOpen());
                SettingManager.putAllowCacheWithMobile(getApplicationContext(), netSet.isOpen());
            }
        });
        clearCache = (MineLineItemView) findViewById(R.id.cache_clear);
        clearCache.setOnClickListener(this);
        thumbUpPush = (MineLineItemView) findViewById(R.id.thumb_up_push);
        thumbUpPush.setOpenState(SettingManager.allowZanNotify(getApplicationContext()));
        thumbUpPush.setClickCallback(new MineLineItemView.ClickCallback() {
            @Override
            public void onMoreViewClick() {
                thumbUpPush.setOpenState(!thumbUpPush.isOpen());
                SettingManager.putAllowZanNotify(getApplicationContext(), thumbUpPush.isOpen());
            }
        });
        commentPush = (MineLineItemView) findViewById(R.id.comment_push);
        commentPush.setOpenState(SettingManager.allowCommentNotify(getApplicationContext()));
        commentPush.setClickCallback(new MineLineItemView.ClickCallback() {
            @Override
            public void onMoreViewClick() {
                commentPush.setOpenState(!commentPush.isOpen());
                SettingManager.putAllowCommentNotify(getApplicationContext(), commentPush.isOpen());
            }
        });
        giveScore = (MineLineItemView) findViewById(R.id.give_score);
        giveScore.setOnClickListener(this);
        advice = (MineLineItemView) findViewById(R.id.advice);
        advice.setOnClickListener(this);
        curVersion = (MineLineItemView) findViewById(R.id.current_version);
        curVersion.setOnClickListener(this);
        userLicense = (MineLineItemView) findViewById(R.id.user_license);
        userLicense.setOnClickListener(this);
        versionRight = (MineLineItemView) findViewById(R.id.version_right);
        versionRight.setOnClickListener(this);
        loginOut = findViewById(R.id.login_out);
        loginOut.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.advice:
                Intent intent = new Intent(this, AdviceActivity.class);
                startActivityForResult(intent, AdviceActivity.REQUEST_CODE_ADVICE);
                break;
            case R.id.net_set:
                break;
            case R.id.cache_clear:
                break;
            case R.id.thumb_up_push:
                break;
            case R.id.comment_push:
                break;
            case R.id.login_out:
                loginOut();
                break;
        }
    }

    private void loginOut() {
        UserApiService userApiService = RetrofitManager.getInstance().createReq(UserApiService.class);
        Call<ResponseBean<List<Object>>> call
                = userApiService.loginOut(LoginManager.getUserToken(getApplicationContext()));
        if (call != null) {
            call.enqueue(new CustomCallBack<List<Object>>() {
                @Override
                public void onSuccess(List<Object> data, String msg, int state) {
                    showShortToast("login out success");
                    LoginManager.cleanUserToken(getApplicationContext());
                    setResult(RESULT_OK);
                    finish();
                }

                @Override
                public void onFailed(String error, int state) {
                    showShortToast(error);
                }
            });
        }
    }
    @Override
    protected int getRootView() {
        return R.layout.activity_setting;
    }
}
