package com.baogetv.app.model.usercenter.activity;

import android.os.Bundle;

import com.baogetv.app.BaseTitleActivity;
import com.baogetv.app.R;


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