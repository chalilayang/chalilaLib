package com.reshape.app.model.usercenter.activity;

import android.os.Bundle;

import com.reshape.app.BaseTitleActivity;
import com.reshape.app.R;


public class MyCollectActivity extends BaseTitleActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleActivity(getString(R.string.user_collect));
    }

    @Override
    protected int getRootView() {
        return R.layout.activity_my_collect;
    }
}
