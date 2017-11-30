package com.baogetv.app.model.usercenter.activity;

import android.app.FragmentManager;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.app.FragmentTransaction;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import com.baogetv.app.BaseFragment;
import com.baogetv.app.BaseTitleActivity;
import com.baogetv.app.R;
import com.baogetv.app.model.usercenter.ImageSelectEvent;
import com.baogetv.app.model.usercenter.customview.MineLineItemView;
import com.baogetv.app.model.usercenter.fragment.ImageGetFragment;

import org.greenrobot.eventbus.Subscribe;

public class UserInfoActivity extends BaseTitleActivity implements View.OnClickListener {

    private static final String TAG = UserInfoActivity.class.getSimpleName();
    private MineLineItemView userIconLine;
    private MineLineItemView userGradeLine;
    private MineLineItemView userNickNameLine;
    private MineLineItemView userSexLine;
    private MineLineItemView userBirthdayLine;
    private MineLineItemView userBodyLine;
    private MineLineItemView userSignatureLine;

    private BaseFragment curFloatingFragment;
    private ImageGetFragment imageSelectFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleActivity(getString(R.string.user_info));
        init();
    }

    private void init() {
        userIconLine = (MineLineItemView) findViewById(R.id.user_icon);
        userIconLine.setOnClickListener(this);
        userGradeLine = (MineLineItemView) findViewById(R.id.user_grade);
        userGradeLine.setOnClickListener(this);
        userNickNameLine = (MineLineItemView) findViewById(R.id.user_nick_name);
        userNickNameLine.setOnClickListener(this);
        userSexLine = (MineLineItemView) findViewById(R.id.user_sex);
        userSexLine.setOnClickListener(this);
        userBirthdayLine = (MineLineItemView) findViewById(R.id.user_birthday);
        userBirthdayLine.setOnClickListener(this);
        userBodyLine = (MineLineItemView) findViewById(R.id.user_body_info);
        userBodyLine.setOnClickListener(this);
        userSignatureLine = (MineLineItemView) findViewById(R.id.user_signature);
        userSignatureLine.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.user_icon:
                showOrHideImageSelectFragment();
                break;
            case R.id.user_grade:
                break;
            case R.id.user_nick_name:
                break;
            case R.id.user_sex:
                break;
            case R.id.user_birthday:
                break;
            case R.id.user_body_info:
                break;
            case R.id.user_signature:
                break;
        }
    }

    private void showOrHideImageSelectFragment() {
        if (imageSelectFragment != null && imageSelectFragment.isAdded()) {
            FragmentManager manager = getFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
            transaction.remove(imageSelectFragment);
            transaction.commit();
            curFloatingFragment = null;
        } else {
            if (imageSelectFragment == null) {
                imageSelectFragment = ImageGetFragment.newInstance();
            }
            FragmentManager manager = getFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            transaction.replace(R.id.floating_fragment_container, imageSelectFragment);
            transaction.commit();
            curFloatingFragment = imageSelectFragment;
        }
    }

    private void hideFloatingView() {
        if (curFloatingFragment != null) {
            FragmentManager manager = getFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
            transaction.remove(curFloatingFragment);
            transaction.commit();
            curFloatingFragment = null;
        }
    }

    @Override
    public void onBackPressed() {
        if (curFloatingFragment != null) {
            hideFloatingView();
            curFloatingFragment = null;
        } else {
            super.onBackPressed();
        }
    }
    public boolean useEventBus() {
        return true;
    }
    public static final int PERMISSIONS_REQUEST_READ_CONTACTS = 8;
    @Override
    public void onRequestPermissionsResult(
            int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST_READ_CONTACTS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (imageSelectFragment != null) {
                    imageSelectFragment.onRequestPermissionsSuccess();
                }
            }
        }
    }

    @Subscribe
    public void onImageEvent(ImageSelectEvent event) {
        Log.i(TAG, "onImageEvent: " + event.img);
    }

    @Override
    protected int getRootView() {
        return R.layout.activity_user_info;
    }
}
