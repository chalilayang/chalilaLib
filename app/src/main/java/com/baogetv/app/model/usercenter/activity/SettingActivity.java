package com.baogetv.app.model.usercenter.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.baogetv.app.BaseTitleActivity;
import com.baogetv.app.R;
import com.baogetv.app.model.usercenter.customview.MineLineItemView;


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
        netSet = (MineLineItemView) findViewById(R.id.net_set);
        netSet.setClickCallback(new MineLineItemView.ClickCallback() {
            @Override
            public void onMoreViewClick() {
                netSet.swichOpenState();
            }
        });
        clearCache = (MineLineItemView) findViewById(R.id.cache_clear);
        clearCache.setOnClickListener(this);
        thumbUpPush = (MineLineItemView) findViewById(R.id.thumb_up_push);
        thumbUpPush.setClickCallback(new MineLineItemView.ClickCallback() {
            @Override
            public void onMoreViewClick() {
                thumbUpPush.swichOpenState();
            }
        });
        commentPush = (MineLineItemView) findViewById(R.id.comment_push);
        commentPush.setClickCallback(new MineLineItemView.ClickCallback() {
            @Override
            public void onMoreViewClick() {
                commentPush.swichOpenState();
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
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.advice:
                Intent intent = new Intent(this, AdviceActivity.class);
                startActivity(intent);
                break;
        }

    }

    @Override
    protected int getRootView() {
        return R.layout.activity_setting;
    }
}
