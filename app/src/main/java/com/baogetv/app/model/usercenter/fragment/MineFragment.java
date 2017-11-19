package com.baogetv.app.model.usercenter.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baogetv.app.BaseFragment;
import com.baogetv.app.R;
import com.baogetv.app.model.usercenter.LoginManager;
import com.baogetv.app.model.usercenter.activity.CacheActivity;
import com.baogetv.app.model.usercenter.activity.MyCollectActivity;
import com.baogetv.app.model.usercenter.activity.PlayHistoryActivity;
import com.baogetv.app.model.usercenter.activity.ResponseActivity;
import com.baogetv.app.model.usercenter.activity.SettingActivity;
import com.baogetv.app.model.usercenter.activity.SystemNotifyAcitvity;
import com.baogetv.app.model.usercenter.activity.ThumbUpActivity;
import com.baogetv.app.model.usercenter.activity.UserGradeDescActivity;
import com.baogetv.app.model.usercenter.activity.UserInfoActivity;
import com.baogetv.app.model.usercenter.activity.VideoLoginActivity;
import com.baogetv.app.model.usercenter.customview.MineBodyInfoView;
import com.baogetv.app.model.usercenter.customview.MineLineItemView;
import com.baogetv.app.model.usercenter.customview.UpgradeProgress;

/**
 * A simple {@link Fragment} subclass.
 */
public class MineFragment extends BaseFragment implements View.OnClickListener {

    private View contentView;
    private View hasLoginView;
    private View notLoginView;
    private MineBodyInfoView mineBodyInfoView;
    private UpgradeProgress upgradeProgress;
    private MineLineItemView userGradeItemView;
    private MineLineItemView myCacheItemView;
    private MineLineItemView myCollectItemView;
    private MineLineItemView playHistoryItemView;
    private MineLineItemView responseItemView;
    private MineLineItemView thumbUpItemView;
    private MineLineItemView systemNotifyItemView;
    private MineLineItemView settingItemView;
    public static MineFragment newInstance() {
        MineFragment fragment = new MineFragment();
        return fragment;
    }
    public MineFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (contentView == null) {
            View view = inflater.inflate(R.layout.fragment_mine, container, false);
            init(view);
            contentView = view;
        }
        return contentView;
    }

    private void init(View view) {
        hasLoginView = view.findViewById(R.id.has_login_view);
        hasLoginView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), UserInfoActivity.class);
                startActivity(intent);
            }
        });
        notLoginView = view.findViewById(R.id.no_login_view);
        notLoginView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), VideoLoginActivity.class);
                startActivity(intent);
            }
        });
        if (LoginManager.hasLogin()) {
            hasLoginView.setVisibility(View.VISIBLE);
            notLoginView.setVisibility(View.GONE);
        } else {
            hasLoginView.setVisibility(View.GONE);
            notLoginView.setVisibility(View.VISIBLE);
        }
        mineBodyInfoView = view.findViewById(R.id.body_info_view);
        mineBodyInfoView.setHeight(170);
        mineBodyInfoView.setBodyWeight(60);
        mineBodyInfoView.setBodyFat(20);
        upgradeProgress = view.findViewById(R.id.user_grade_progress);
        upgradeProgress.setUpGradeProgress(30);

        userGradeItemView = view.findViewById(R.id.user_grade);
        userGradeItemView.setOnClickListener(this);
        myCacheItemView = view.findViewById(R.id.my_cache);
        myCacheItemView.setOnClickListener(this);
        myCollectItemView = view.findViewById(R.id.my_collect);
        myCollectItemView.setOnClickListener(this);
        playHistoryItemView = view.findViewById(R.id.play_history);
        playHistoryItemView.setOnClickListener(this);
        responseItemView = view.findViewById(R.id.response);
        responseItemView.setOnClickListener(this);
        thumbUpItemView = view.findViewById(R.id.thumb_up);
        thumbUpItemView.setOnClickListener(this);
        systemNotifyItemView = view.findViewById(R.id.system_notify);
        systemNotifyItemView.setOnClickListener(this);
        settingItemView = view.findViewById(R.id.setting);
        settingItemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        Intent intent = null;
        switch (id) {
            case R.id.user_grade:
                intent = new Intent(this.getActivity(), UserGradeDescActivity.class);
                startActivity(intent);
                break;
            case R.id.my_cache:
                intent = new Intent(this.getActivity(), CacheActivity.class);
                startActivity(intent);
                break;
            case R.id.my_collect:
                intent = new Intent(this.getActivity(), MyCollectActivity.class);
                startActivity(intent);
                break;
            case R.id.play_history:
                intent = new Intent(this.getActivity(), PlayHistoryActivity.class);
                startActivity(intent);
                break;
            case R.id.response:
                intent = new Intent(this.getActivity(), ResponseActivity.class);
                startActivity(intent);
                break;
            case R.id.thumb_up:
                intent = new Intent(this.getActivity(), ThumbUpActivity.class);
                startActivity(intent);
                break;
            case R.id.system_notify:
                intent = new Intent(this.getActivity(), SystemNotifyAcitvity.class);
                startActivity(intent);
                break;
            case R.id.setting:
                intent = new Intent(this.getActivity(), SettingActivity.class);
                startActivity(intent);
                break;
        }
    }
}