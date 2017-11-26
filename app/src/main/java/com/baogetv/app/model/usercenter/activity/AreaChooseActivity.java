package com.baogetv.app.model.usercenter.activity;

import android.os.Bundle;
import android.view.WindowManager;

import com.baogetv.app.BaseActivity;
import com.baogetv.app.R;

public class AreaChooseActivity extends BaseActivity {

    public static final int REQUEST_CODE_AREA_CHOOSE = 1245;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_area_choose);
    }
}
