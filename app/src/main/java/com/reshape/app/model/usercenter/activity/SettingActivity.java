package com.reshape.app.model.usercenter.activity;

import android.os.Bundle;
import android.view.View;

import com.reshape.app.BaseTitleActivity;
import com.reshape.app.R;
import com.reshape.app.model.usercenter.customview.MineLineItemView;


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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleActivity(getString(R.string.setting));
        netSet = findViewById(R.id.net_set);
        netSet.setClickCallback(new MineLineItemView.ClickCallback() {
            @Override
            public void onMoreViewClick() {
                netSet.swichOpenState();
            }
        });
        clearCache = findViewById(R.id.cache_clear);
        clearCache.setOnClickListener(this);
        thumbUpPush = findViewById(R.id.thumb_up_push);
        thumbUpPush.setClickCallback(new MineLineItemView.ClickCallback() {
            @Override
            public void onMoreViewClick() {
                thumbUpPush.swichOpenState();
            }
        });
        commentPush = findViewById(R.id.comment_push);
        commentPush.setClickCallback(new MineLineItemView.ClickCallback() {
            @Override
            public void onMoreViewClick() {
                commentPush.swichOpenState();
            }
        });
        giveScore = findViewById(R.id.give_score);
        giveScore.setOnClickListener(this);
        advice = findViewById(R.id.advice);
        advice.setOnClickListener(this);
        curVersion = findViewById(R.id.current_version);
        curVersion.setOnClickListener(this);
        userLicense = findViewById(R.id.user_license);
        userLicense.setOnClickListener(this);
        versionRight = findViewById(R.id.version_right);
        versionRight.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    protected int getRootView() {
        return R.layout.activity_setting;
    }
}