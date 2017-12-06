package com.baogetv.app.model.usercenter.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.baogetv.app.BaseTitleActivity;
import com.baogetv.app.R;
import com.baogetv.app.apiinterface.UserApiService;
import com.baogetv.app.bean.AdviceBean;
import com.baogetv.app.bean.ResponseBean;
import com.baogetv.app.model.usercenter.LoginManager;
import com.baogetv.app.net.CustomCallBack;
import com.baogetv.app.net.RetrofitManager;

import java.util.List;

import retrofit2.Call;

public class AdviceActivity extends BaseTitleActivity {

    public static final int REQUEST_CODE_ADVICE = 2313;
    private TextView adviceBtn;
    private TextView bugBtn;
    private EditText introEdit;
    private View save;
    private int adType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleActivity(getString(R.string.advice));
        init();
    }

    private void init() {
        adviceBtn = (TextView) findViewById(R.id.advice_type);
        adviceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adviceBtn.setBackgroundResource(R.color.reshape_red);
                adviceBtn.setTextColor(getResources().getColor(R.color.white));
                bugBtn.setBackgroundResource(R.color.search_label_bg);
                bugBtn.setTextColor(getResources().getColor(R.color.black_90_percent));
                adType = 1;
            }
        });
        bugBtn = (TextView) findViewById(R.id.bug_type);
        bugBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bugBtn.setBackgroundResource(R.color.reshape_red);
                bugBtn.setTextColor(getResources().getColor(R.color.white));
                adviceBtn.setBackgroundResource(R.color.search_label_bg);
                adviceBtn.setTextColor(getResources().getColor(R.color.black_90_percent));
                adType = 2;
            }
        });
        adviceBtn.setBackgroundResource(R.color.reshape_red);
        adviceBtn.setTextColor(getResources().getColor(R.color.white));
        bugBtn.setBackgroundResource(R.color.search_label_bg);
        bugBtn.setTextColor(getResources().getColor(R.color.black_90_percent));
        adType = 1;
        introEdit = (EditText) findViewById(R.id.intro_edit);
        save = findViewById(R.id.save_btn);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String intro = introEdit.getText().toString();
                if (TextUtils.isEmpty(intro)) {
                    showShortToast(getString(R.string.intro_null));
                    return;
                }
                upload(adType+"", intro);
            }
        });
    }
    @Override
    protected int getRootView() {
        return R.layout.activity_advice;
    }

    private void upload(String type, String intro) {
        UserApiService userApiService
                = RetrofitManager.getInstance().createReq(UserApiService.class);
        String token = LoginManager.getUserToken(getApplicationContext());
        Call<ResponseBean<AdviceBean>> call = userApiService.uploadAdvice(token, type, intro);
        if (call != null) {
            call.enqueue(new CustomCallBack<AdviceBean>() {
                @Override
                public void onSuccess(AdviceBean data, String msg, int state) {
                    showShortToast("success");
                }

                @Override
                public void onFailed(String error, int state) {
                    showShortToast(error);
                }
            });
        }
    }
}
