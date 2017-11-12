package com.reshape.app.model.usercenter.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.reshape.app.BaseFragment;
import com.reshape.app.R;
import com.reshape.app.model.usercenter.LoginManager;
import com.reshape.app.model.usercenter.customview.MineBodyInfoView;
import com.reshape.app.model.usercenter.customview.UpgradeProgress;

/**
 * A simple {@link Fragment} subclass.
 */
public class MineFragment extends BaseFragment {

    private View contentView;
    private View hasLoginView;
    private View notLoginView;
    private MineBodyInfoView mineBodyInfoView;
    private UpgradeProgress upgradeProgress;
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
        notLoginView = view.findViewById(R.id.no_login_view);
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
    }
}
