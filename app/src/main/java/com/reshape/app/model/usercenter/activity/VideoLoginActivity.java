package com.reshape.app.model.usercenter.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.reshape.app.BaseActivity;
import com.reshape.app.R;

import static com.reshape.app.constant.AppConstance.REQUEST_CODE_LOGIN_ACTIVITY;

public class VideoLoginActivity extends BaseActivity {

    private View loginBtn;
    private View registerBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_video_login);
        init();
    }

    private void init() {
        loginBtn = findViewById(R.id.login_tv);
        if (loginBtn != null) {
            loginBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(VideoLoginActivity.this, LoginActivity.class);
                    startActivityForResult(intent, REQUEST_CODE_LOGIN_ACTIVITY);
                }
            });
        }
    }
}
