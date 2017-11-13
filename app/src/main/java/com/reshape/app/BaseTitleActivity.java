package com.reshape.app;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


public class BaseTitleActivity extends BaseActivity {

    protected TextView titleActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getRootView());
        titleActivity = findViewById(R.id.title);
        findViewById(R.id.back_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    protected int getRootView() {
        return R.layout.activity_base_title;
    }

    protected void setTitleActivity(String title) {
        if (titleActivity != null) {
            titleActivity.setText(title);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
