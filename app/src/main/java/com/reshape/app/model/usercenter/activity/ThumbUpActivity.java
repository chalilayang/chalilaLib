package com.reshape.app.model.usercenter.activity;

import android.os.Bundle;

import com.reshape.app.BaseTitleActivity;
import com.reshape.app.R;


public class ThumbUpActivity extends BaseTitleActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleActivity(getString(R.string.user_thumb_up));
    }

    @Override
    protected int getRootView() {
        return R.layout.activity_thumb_up;
    }
}
