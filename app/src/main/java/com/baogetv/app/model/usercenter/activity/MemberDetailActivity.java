package com.baogetv.app.model.usercenter.activity;

import android.app.FragmentTransaction;
import android.os.Bundle;

import com.baogetv.app.BaseTitleActivity;
import com.baogetv.app.ItemFragment;
import com.baogetv.app.R;
import com.baogetv.app.model.usercenter.entity.MemberItemData;
import com.baogetv.app.parcelables.PageItemData;

public class MemberDetailActivity extends BaseTitleActivity {

    public static final String KEY_MEMBER_ID = "MEMBER_ID";
    private ItemFragment videoListFragment;
    private String uid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        uid = getIntent().getStringExtra(KEY_MEMBER_ID);
        setTitleActivity(getString(R.string.member_detail));
        showPlayerFragment();
    }

    @Override
    protected int getRootView() {
        return R.layout.activity_member_detail;
    }
    private void showPlayerFragment() {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        if (videoListFragment == null) {
            MemberItemData memberItemData
                    = new MemberItemData("", PageItemData.TYPE_MEMBER_COLLECT, uid);
            videoListFragment = ItemFragment.newInstance(memberItemData);
        }
        transaction.replace(R.id.fragment_container, videoListFragment).commit();
    }
}
