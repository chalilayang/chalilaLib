package com.reshape.app.model.usercenter.activity;

import android.os.Bundle;
import android.view.View;

import com.reshape.app.BaseTitleActivity;
import com.reshape.app.R;
import com.reshape.app.model.usercenter.customview.MineLineItemView;

public class UserInfoActivity extends BaseTitleActivity implements View.OnClickListener {

    private MineLineItemView userIconLine;
    private MineLineItemView userGradeLine;
    private MineLineItemView userNickNameLine;
    private MineLineItemView userSexLine;
    private MineLineItemView userBirthdayLine;
    private MineLineItemView userBodyLine;
    private MineLineItemView userSignatureLine;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleActivity(getString(R.string.user_info));
        init();
    }

    private void init() {
        userIconLine = findViewById(R.id.user_icon);
        userIconLine.setOnClickListener(this);
        userGradeLine = findViewById(R.id.user_grade);
        userGradeLine.setOnClickListener(this);
        userNickNameLine = findViewById(R.id.user_nick_name);
        userNickNameLine.setOnClickListener(this);
        userSexLine = findViewById(R.id.user_sex);
        userSexLine.setOnClickListener(this);
        userBirthdayLine = findViewById(R.id.user_birthday);
        userBirthdayLine.setOnClickListener(this);
        userBodyLine = findViewById(R.id.user_body_info);
        userBodyLine.setOnClickListener(this);
        userSignatureLine = findViewById(R.id.user_signature);
        userSignatureLine.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.user_icon:
                break;
            case R.id.user_grade:
                break;
            case R.id.user_nick_name:
                break;
            case R.id.user_sex:
                break;
            case R.id.user_birthday:
                break;
            case R.id.user_body_info:
                break;
            case R.id.user_signature:
                break;
        }
    }

    @Override
    protected int getRootView() {
        return R.layout.activity_user_info;
    }
}
