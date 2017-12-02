package com.baogetv.app;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


public class BaseTitleActivity extends BaseActivity  {

    protected TextView titleActivity;
    protected TextView rightTitle;
    protected ImageView backView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getRootView());
        titleActivity = (TextView) findViewById(R.id.title);
        rightTitle = (TextView) findViewById(R.id.right_title);
        if (rightTitle!= null) {
            rightTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onRightClick();
                }
            });
        }
        backView = (ImageView) findViewById(R.id.back_icon);
        if (backView != null) {
            backView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBackPressed();
                }
            });
        }

    }

    public void onRightClick() {

    }

    protected int getRootView() {
        return R.layout.activity_base_title;
    }

    protected void setTitleActivity(String title) {
        if (titleActivity != null) {
            titleActivity.setText(title);
        }
    }

    protected void setRightTitle(String title) {
        if (rightTitle != null) {
            rightTitle.setText(title);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
