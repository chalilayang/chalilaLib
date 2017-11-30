package com.baogetv.app.model.homepage;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.baogetv.app.bean.LoginBean;
import com.baogetv.app.bean.RegisterBean;
import com.chalilayang.scaleview.ScaleCalculator;
import com.baogetv.app.BaseActivity;
import com.baogetv.app.R;
import com.baogetv.app.model.channel.fragment.ChannelListFragment;
import com.baogetv.app.model.homepage.fragment.HomeFragment;
import com.baogetv.app.model.usercenter.fragment.MineFragment;
import com.baogetv.app.parcelables.PageData;
import com.baogetv.app.parcelables.PageItemData;

import java.util.ArrayList;
import java.util.List;

import static com.baogetv.app.constant.AppConstance.REQUEST_CODE_LOGIN_ACTIVITY;
import static com.baogetv.app.constant.AppConstance.REQUEST_CODE_REGISTER_ACTIVITY;
import static com.baogetv.app.model.usercenter.activity.LoginActivity.KEY_LOGIN_BEAN;
import static com.baogetv.app.model.usercenter.activity.RegisterActivity.KEY_REGISTER_BEAN;

public class HomePageActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "HomePageActivity";
    private FrameLayout fragmentContainer;
    private HomeFragment homeFragment;
    private ChannelListFragment channelListFragment;
    private MineFragment mineFragment;
    private TextView homeBtn;
    private TextView channelBtn;
    private TextView userCenterBtn;
    private Drawable homeDrawablePositive;
    private Drawable homeDrawableNormal;
    private Drawable channelDrawablePositive;
    private Drawable channelDrawableNormal;
    private Drawable userCenterDrawablePositive;
    private Drawable userCenterDrawableNormal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        setContentView(R.layout.activity_home_page);
        fragmentContainer = (FrameLayout) findViewById(R.id.fragment_container);

        homeDrawablePositive = getResources().getDrawable(R.mipmap.home_page_positive);
        int width = ScaleCalculator.getInstance(this).scaleWidth(48);
        int height = (int) (homeDrawablePositive.getIntrinsicHeight()
                * width * 1.0f / homeDrawablePositive.getIntrinsicWidth());
        homeDrawablePositive.setBounds(0, 0, width, height);
        homeDrawableNormal = getResources().getDrawable(R.mipmap.home_page_normal);
        homeDrawableNormal.setBounds(0, 0, width, height);

        channelDrawablePositive = getResources().getDrawable(R.mipmap.channel_positive);
        width = ScaleCalculator.getInstance(this).scaleWidth(48);
        height = (int) (channelDrawablePositive.getIntrinsicHeight()
                * width * 1.0f / channelDrawablePositive.getIntrinsicWidth());
        channelDrawablePositive.setBounds(0, 0, width, height);
        channelDrawableNormal = getResources().getDrawable(R.mipmap.channel_normal);
        channelDrawableNormal.setBounds(0, 0, width, height);

        userCenterDrawablePositive = getResources().getDrawable(R.mipmap.user_center_positive);
        width = ScaleCalculator.getInstance(this).scaleWidth(48);
        height = (int) (userCenterDrawablePositive.getIntrinsicHeight()
                * width * 1.0f / userCenterDrawablePositive.getIntrinsicWidth());
        userCenterDrawablePositive.setBounds(0, 0, width, height);
        userCenterDrawableNormal = getResources().getDrawable(R.mipmap.user_center_normal);
        userCenterDrawableNormal.setBounds(0, 0, width, height);
        int marginDrawble = ScaleCalculator.getInstance(this).scaleWidth(12);
        homeBtn = (TextView) findViewById(R.id.home_btn);
        homeBtn.setCompoundDrawablePadding(marginDrawble);
        homeBtn.setOnClickListener(this);
        channelBtn = (TextView) findViewById(R.id.channel_btn);
        channelBtn.setOnClickListener(this);
        channelBtn.setCompoundDrawablePadding(marginDrawble);
        userCenterBtn = (TextView) findViewById(R.id.user_center_btn);
        userCenterBtn.setOnClickListener(this);
        userCenterBtn.setCompoundDrawablePadding(marginDrawble);
        homeBtn.performClick();
    }

    @Override
    public void onClick(View view) {
        int viewId = view.getId();
        switch (viewId) {
            case R.id.home_btn:
                showHomeFragment();
                homeBtn.setCompoundDrawables(null, homeDrawablePositive, null, null);
                homeBtn.setTextColor(getResources().getColor(R.color.reshape_red));
                channelBtn.setCompoundDrawables(null, channelDrawableNormal, null, null);
                channelBtn.setTextColor(getResources().getColor(R.color.reshape_gray));
                userCenterBtn.setCompoundDrawables(null, userCenterDrawableNormal, null, null);
                userCenterBtn.setTextColor(getResources().getColor(R.color.reshape_gray));
                break;
            case R.id.channel_btn:
                showChannelListFragment();
                homeBtn.setCompoundDrawables(null, homeDrawableNormal, null, null);
                homeBtn.setTextColor(getResources().getColor(R.color.reshape_gray));
                channelBtn.setCompoundDrawables(null, channelDrawablePositive, null, null);
                channelBtn.setTextColor(getResources().getColor(R.color.reshape_red));
                userCenterBtn.setCompoundDrawables(null, userCenterDrawableNormal, null, null);
                userCenterBtn.setTextColor(getResources().getColor(R.color.reshape_gray));
                break;
            case R.id.user_center_btn:
                showUserCenterFragment();
                homeBtn.setCompoundDrawables(null, homeDrawableNormal, null, null);
                homeBtn.setTextColor(getResources().getColor(R.color.reshape_gray));
                channelBtn.setCompoundDrawables(null, channelDrawableNormal, null, null);
                channelBtn.setTextColor(getResources().getColor(R.color.reshape_gray));
                userCenterBtn.setCompoundDrawables(null, userCenterDrawablePositive, null, null);
                userCenterBtn.setTextColor(getResources().getColor(R.color.reshape_red));
                break;
        }
    }

    private void showHomeFragment() {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        if (homeFragment == null) {
            List<PageItemData> list = new ArrayList<>(2);
            PageItemData pageItemData = new PageItemData(getString(R.string.all), PageItemData.TYPE_ALL_VIDEO);
            list.add(pageItemData);
            pageItemData = new PageItemData(getString(R.string.ranking), PageItemData.TYPE_RANK_VIDEO);
            list.add(pageItemData);
            homeFragment = HomeFragment.newInstance(new PageData(list));
        }
        transaction.replace(R.id.fragment_container, homeFragment).commit();
    }

    private void showChannelListFragment() {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        if (channelListFragment == null) {
            channelListFragment = ChannelListFragment.newInstance();
        }
        transaction.replace(R.id.fragment_container, channelListFragment).commit();
    }

    private void showUserCenterFragment() {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        if (mineFragment == null) {
            mineFragment = MineFragment.newInstance();
        }
        transaction.replace(R.id.fragment_container, mineFragment).commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_LOGIN_ACTIVITY) {
            if (resultCode == RESULT_OK) {
                LoginBean loginBean = data.getParcelableExtra(KEY_LOGIN_BEAN);
                if (mineFragment != null && mineFragment.isVisible()) {
                    mineFragment.freshUserInfo(loginBean);
                }
            }
        } else if (requestCode == REQUEST_CODE_REGISTER_ACTIVITY) {
            if (resultCode == RESULT_OK) {
                RegisterBean registerBean = data.getParcelableExtra(KEY_REGISTER_BEAN);
                if (mineFragment != null && mineFragment.isVisible()) {
                    mineFragment.freshUserInfo(registerBean);
                }
            }
        }
    }
}
