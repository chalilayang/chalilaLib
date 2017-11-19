package com.baogetv.app.model.usercenter.activity;

import android.os.Bundle;

import com.baogetv.app.BaseTitleActivity;
import com.baogetv.app.R;

public class UserGradeDescActivity extends BaseTitleActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleActivity(getString(R.string.my_grade));
    }

    protected int getRootView() {
        return R.layout.activity_user_grade_desc;
    }
}
