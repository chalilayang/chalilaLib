package com.baogetv.app.model.usercenter.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.baogetv.app.BaseTitleActivity;
import com.baogetv.app.R;
import com.baogetv.app.apiinterface.UserApiService;
import com.baogetv.app.bean.ResponseBean;
import com.baogetv.app.model.usercenter.LoginManager;
import com.baogetv.app.net.CustomCallBack;
import com.baogetv.app.net.RetrofitManager;

import java.util.List;

import retrofit2.Call;

public class NameModifyActivity extends BaseTitleActivity {

    public static final int REQUEST_CODE_NAME_MODIFY = 2312;
    private EditText nameEdit;
    private EditText introEdit;
    private View save;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleActivity(getString(R.string.name_and_intro));
        init();
    }

    private void init() {
        nameEdit = (EditText) findViewById(R.id.name_edit);
        introEdit = (EditText) findViewById(R.id.intro_edit);
        save = findViewById(R.id.save_btn);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameEdit.getText().toString();
                String intro = introEdit.getText().toString();
                if (TextUtils.isEmpty(name)) {
                    showShortToast(getString(R.string.nick_name_null));
                    return;
                }
                if (TextUtils.isEmpty(intro)) {
                    showShortToast(getString(R.string.intro_null));
                    return;
                }
                modifyUserInfo(name, intro);
            }
        });
    }
    @Override
    protected int getRootView() {
        return R.layout.activity_name_modify;
    }

    private void modifyUserInfo(String name, String intro) {
        UserApiService userApiService
                = RetrofitManager.getInstance().createReq(UserApiService.class);
        String token = LoginManager.getUserToken(getApplicationContext());
        Call<ResponseBean<List<Object>>> call = userApiService.editUserDetail(
                null, name, null, null, intro, null, null, null, null, null, token);
        if (call != null) {
            call.enqueue(new CustomCallBack<List<Object>>() {
                @Override
                public void onSuccess(List<Object> data, String msg, int state) {
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
