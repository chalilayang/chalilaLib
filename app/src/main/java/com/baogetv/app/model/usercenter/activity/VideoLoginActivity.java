package com.baogetv.app.model.usercenter.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.baogetv.app.BaseActivity;
import com.baogetv.app.R;
import com.baogetv.app.model.homepage.HomePageActivity;
import com.baogetv.app.model.usercenter.LoginManager;

import static com.baogetv.app.constant.AppConstance.REQUEST_CODE_LOGIN_ACTIVITY;
import static com.baogetv.app.constant.AppConstance.REQUEST_CODE_REGISTER_ACTIVITY;

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
                    LoginManager.startLogin(VideoLoginActivity.this);
                }
            });
        }
        registerBtn = findViewById(R.id.register_tv);
        if (registerBtn != null) {
            registerBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    LoginManager.startRegister(VideoLoginActivity.this);
                }
            });
        }
        findViewById(R.id.skip_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startHomeActivity();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_LOGIN_ACTIVITY) {
            if (resultCode == RESULT_OK) {
                startHomeActivity();
            }
        } else if (requestCode == REQUEST_CODE_REGISTER_ACTIVITY) {
            if (resultCode == RESULT_OK) {
                startHomeActivity();
            }
        }
    }

    private void startHomeActivity() {
        Intent intent = new Intent(VideoLoginActivity.this, HomePageActivity.class);
        startActivity(intent);
        finish();
    }
}
