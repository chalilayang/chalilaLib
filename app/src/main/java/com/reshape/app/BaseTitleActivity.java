package com.reshape.app;

import android.os.Bundle;
import android.widget.TextView;


public class BaseTitleActivity extends BaseActivity {

    protected TextView titleActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getRootView());
        titleActivity = findViewById(R.id.title);
    }

    protected int getRootView() {
        return R.layout.activity_base_title;
    }

    protected void setTitleActivity(String title) {
        if (titleActivity != null) {
            titleActivity.setText(title);
        }
    }
}
