package com.baogetv.app.model.usercenter.activity;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.widget.TextView;

import com.baogetv.app.BaseTitleActivity;
import com.baogetv.app.ItemFragment;
import com.baogetv.app.R;
import com.baogetv.app.apiinterface.UserApiService;
import com.baogetv.app.bean.ResponseBean;
import com.baogetv.app.bean.UserDetailBean;
import com.baogetv.app.customview.LogoCircleImageView;
import com.baogetv.app.model.usercenter.LoginManager;
import com.baogetv.app.model.usercenter.customview.MineBodyInfoView;
import com.baogetv.app.model.usercenter.entity.MemberItemData;
import com.baogetv.app.net.CustomCallBack;
import com.baogetv.app.net.RetrofitManager;
import com.baogetv.app.parcelables.PageItemData;
import com.bumptech.glide.Glide;

import retrofit2.Call;

public class MemberDetailActivity extends BaseTitleActivity {

    public static final String KEY_MEMBER_ID = "MEMBER_ID";
    private ItemFragment videoListFragment;
    private String uid;
    private UserDetailBean userBean;

    private LogoCircleImageView userIcon;
    private TextView userName;
    private TextView gradeDesc;
    private TextView tip;
    private MineBodyInfoView mineBodyInfoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        uid = getIntent().getStringExtra(KEY_MEMBER_ID);
        setTitleActivity(getString(R.string.user_detail));
        showPlayerFragment();
        userIcon = (LogoCircleImageView) findViewById(R.id.mine_icon);
        userName = (TextView) findViewById(R.id.mine_name);
        gradeDesc = (TextView) findViewById(R.id.user_medal);
        tip = (TextView) findViewById(R.id.lazy_man);
        mineBodyInfoView = (MineBodyInfoView) findViewById(R.id.body_info_view);
        fetchUserInfo(uid);
    }

    private void updateInfo() {
        if (userBean != null) {
            Glide.with(this)
                    .load(userBean.getPic_url())
                    .placeholder(R.mipmap.user_default_icon)
                    .error(R.mipmap.user_default_icon)
                    .dontAnimate().into(userIcon);
            userIcon.setLogo(userBean.getGrade(), userBean.getLevel_id());
            userName.setText(userBean.getUsername());
            gradeDesc.setText(userBean.getMedal());
            int height = 0;
            try {
                height = Integer.parseInt(userBean.getHeight());
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            mineBodyInfoView.setHeight(height);
            int weight = 0;
            try {
                weight = Integer.parseInt(userBean.getWeight());
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            mineBodyInfoView.setBodyWeight(weight);
            int bodyFat = 0;
            try {
                int fBodyFat = Integer.parseInt(userBean.getBfr());
                bodyFat = Math.min(fBodyFat, 100);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            mineBodyInfoView.setBodyFat(bodyFat);
        }
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

    private void fetchUserInfo(String uid) {
        UserApiService userApiService
                = RetrofitManager.getInstance().createReq(UserApiService.class);
        Call<ResponseBean<UserDetailBean>> call
                = userApiService.getUserDetail(null, uid);
        if (call != null) {
            call.enqueue(new CustomCallBack<UserDetailBean>() {
                @Override
                public void onSuccess(UserDetailBean data, String msg, int state) {
                    userBean = data;
                    updateInfo();
                }

                @Override
                public void onFailed(String error, int state) {
                    showShortToast(error);
                }
            });
        }
    }
}
