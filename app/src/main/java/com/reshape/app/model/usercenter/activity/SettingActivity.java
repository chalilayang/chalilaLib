package com.reshape.app.model.usercenter.activity;

import android.os.Bundle;

import com.reshape.app.BaseTitleActivity;
import com.reshape.app.R;


public class SettingActivity extends BaseTitleActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleActivity(getString(R.string.setting));
    }

    @Override
    protected int getRootView() {
        return R.layout.activity_setting;
    }
}
