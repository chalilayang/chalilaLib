package com.baogetv.app.model.usercenter.activity;

import android.app.FragmentTransaction;
import android.os.Bundle;

import com.baogetv.app.BaseTitleActivity;
import com.baogetv.app.R;
import com.baogetv.app.model.usercenter.fragment.ResponseMeListFragment;


public class ResponseActivity extends BaseTitleActivity {

    private ResponseMeListFragment fragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleActivity(getString(R.string.user_response));
        showFragment();
    }

    @Override
    protected int getRootView() {
        return R.layout.activity_response;
    }
    private void showFragment() {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        if (fragment == null) {
            fragment = ResponseMeListFragment.newInstance();
        }
        transaction.replace(R.id.root_container, fragment).commit();
    }
}
