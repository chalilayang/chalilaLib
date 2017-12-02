package com.baogetv.app.model.usercenter.activity;

import android.app.FragmentTransaction;
import android.os.Bundle;

import com.baogetv.app.BaseTitleActivity;
import com.baogetv.app.R;
import com.baogetv.app.model.usercenter.fragment.ThumbUpListFragment;


public class ThumbUpActivity extends BaseTitleActivity {

    private ThumbUpListFragment fragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleActivity(getString(R.string.user_thumb_up));
        showFragment();
    }

    @Override
    protected int getRootView() {
        return R.layout.activity_thumb_up;
    }

    private void showFragment() {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        if (fragment == null) {
            fragment = ThumbUpListFragment.newInstance();
        }
        transaction.replace(R.id.root_container, fragment).commit();
    }
}
