package com.baogetv.app.model.usercenter.activity;

import android.os.Bundle;
import android.widget.EditText;

import com.baogetv.app.BaseTitleActivity;
import com.baogetv.app.R;

public class NameModifyActivity extends BaseTitleActivity {

    public static final int REQUEST_CODE_NAME_MODIFY = 2312;
    private EditText nameEdit;
    private EditText introEdit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleActivity(getString(R.string.name_and_intro));
    }

    private void init() {
        nameEdit = (EditText) findViewById(R.id.name_edit);
        introEdit = (EditText) findViewById(R.id.intro_edit);
    }
    @Override
    protected int getRootView() {
        return R.layout.activity_name_modify;
    }
}
