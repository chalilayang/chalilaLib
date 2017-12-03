package com.baogetv.app.model.usercenter.activity;

import android.os.Bundle;

import com.baogetv.app.BaseTitleActivity;
import com.baogetv.app.R;

public class MemberDetailActivity extends BaseTitleActivity {

    public static final String KEY_MEMBER_ID = "MEMBER_ID";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleActivity(getString(R.string.member_detail));
    }

    @Override
    protected int getRootView() {
        return R.layout.activity_member_detail;
    }
}
