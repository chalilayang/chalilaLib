package com.baogetv.app.model.usercenter.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.baogetv.app.BaseFragment;
import com.baogetv.app.R;
import com.baogetv.app.apiinterface.UserApiService;
import com.baogetv.app.bean.ResponseBean;
import com.baogetv.app.bean.UserDetailBean;
import com.baogetv.app.constant.AppConstance;
import com.baogetv.app.customview.LogoCircleImageView;
import com.baogetv.app.model.usercenter.LoginManager;
import com.baogetv.app.model.usercenter.activity.MyCacheActivity;
import com.baogetv.app.model.usercenter.activity.MyCollectActivity;
import com.baogetv.app.model.usercenter.activity.PlayHistoryActivity;
import com.baogetv.app.model.usercenter.activity.ResponseActivity;
import com.baogetv.app.model.usercenter.activity.SettingActivity;
import com.baogetv.app.model.usercenter.activity.SystemNotifyAcitvity;
import com.baogetv.app.model.usercenter.activity.ThumbUpActivity;
import com.baogetv.app.model.usercenter.activity.UserGradeDescActivity;
import com.baogetv.app.model.usercenter.activity.UserInfoActivity;
import com.baogetv.app.model.usercenter.customview.MineBodyInfoView;
import com.baogetv.app.model.usercenter.customview.MineLineItemView;
import com.baogetv.app.model.usercenter.customview.UpgradeProgress;
import com.baogetv.app.net.CustomCallBack;
import com.baogetv.app.net.RetrofitManager;
import com.bumptech.glide.Glide;

import retrofit2.Call;

import static com.baogetv.app.constant.AppConstance.KEY_USER_DETAIL_BEAN;
import static com.baogetv.app.constant.AppConstance.REQUEST_CODE_SETTING_ACTIVITY;
import static com.baogetv.app.constant.AppConstance.REQUEST_CODE_USER_INFO_ACTIVITY;

/**
 * A simple {@link Fragment} subclass.
 */
public class MineFragment extends BaseFragment implements View.OnClickListener {

    private static final String TAG = "MineFragment";
    private static final String KEY_USER_DETAIL = "USER_DETAIL";
    private View contentView;
    private View hasLoginView;
    private View notLoginView;
    private View loginBtn;
    private View registerBtn;
    private LogoCircleImageView userIcon;
    private TextView userName;
    private ImageView gradeIcon;
    private TextView gradeDesc;
    private MineBodyInfoView mineBodyInfoView;
    private TextView scoreeDesc;
    private UpgradeProgress upgradeProgress;
    private MineLineItemView userGradeItemView;
    private MineLineItemView myCacheItemView;
    private MineLineItemView myCollectItemView;
    private MineLineItemView playHistoryItemView;
    private MineLineItemView responseItemView;
    private MineLineItemView thumbUpItemView;
    private MineLineItemView systemNotifyItemView;
    private MineLineItemView settingItemView;

    private UserDetailBean detailBean;
    public static MineFragment newInstance(UserDetailBean bean) {
        MineFragment fragment = new MineFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(KEY_USER_DETAIL, bean);
        fragment.setArguments(bundle);
        return fragment;
    }
    public MineFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            detailBean = getArguments().getParcelable(KEY_USER_DETAIL);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (contentView == null) {
            View view = inflater.inflate(R.layout.fragment_mine, container, false);
            init(view);
            contentView = view;
            updateInfo();
        }
        return contentView;
    }

    private void init(View view) {
        hasLoginView = view.findViewById(R.id.has_login_view);
        hasLoginView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mActivity, UserInfoActivity.class);
                intent.putExtra(KEY_USER_DETAIL_BEAN, detailBean);
                mActivity.startActivityForResult(intent, REQUEST_CODE_USER_INFO_ACTIVITY);
            }
        });
        notLoginView = view.findViewById(R.id.no_login_view);
        loginBtn = view.findViewById(R.id.login_btn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginManager.startLogin(getActivity());
            }
        });
        registerBtn = view.findViewById(R.id.register_btn);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginManager.startRegister(getActivity());
            }
        });
        if (LoginManager.hasLogin(getActivity())) {
            hasLoginView.setVisibility(View.VISIBLE);
            notLoginView.setVisibility(View.GONE);
        } else {
            hasLoginView.setVisibility(View.GONE);
            notLoginView.setVisibility(View.VISIBLE);
        }
        userName = view.findViewById(R.id.mine_name);
        userIcon = view.findViewById(R.id.mine_icon);
        view.findViewById(R.id.change_mobile_num).setOnClickListener(this);
        gradeIcon = view.findViewById(R.id.user_grade_icon);
        gradeDesc = view.findViewById(R.id.user_grade_desc);
        mineBodyInfoView = view.findViewById(R.id.body_info_view);
        scoreeDesc = view.findViewById(R.id.score_desc);
        upgradeProgress = view.findViewById(R.id.user_grade_progress);

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

    public void updateInfo() {
        if (LoginManager.hasLogin(getActivity())) {
            hasLoginView.setVisibility(View.VISIBLE);
            notLoginView.setVisibility(View.GONE);
        } else {
            hasLoginView.setVisibility(View.GONE);
            notLoginView.setVisibility(View.VISIBLE);
        }
        if (detailBean != null) {
            Log.i(TAG, "updateInfo: " + detailBean.getPic_url());
            Glide.with(this).load(detailBean.getPic_url()).into(userIcon);
            Glide.with(this).load(detailBean.getLevel_pic_url()).into(gradeIcon);
            userIcon.setLogo(detailBean.getGrade(), detailBean.getLevel_id());
            userName.setText(detailBean.getUsername());
            gradeDesc.setText(detailBean.getLevel_name());
            int height = 0;
            try {
                height = Integer.parseInt(detailBean.getHeight());
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            mineBodyInfoView.setHeight(height);
            int weight = 0;
            try {
                weight = Integer.parseInt(detailBean.getWeight());
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            mineBodyInfoView.setBodyWeight(weight);
            int bodyFat = 0;
            try {
                float fBodyFat = Float.parseFloat(detailBean.getBfr());
                bodyFat = Math.min((int) fBodyFat * 100, 100);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            mineBodyInfoView.setBodyFat(bodyFat);
            try {
                int max = Integer.parseInt(detailBean.getNext_level_score());
                if (max > 0) {
                    int score = Integer.parseInt(detailBean.getScore());
                    int scoreProgress = Math.min(100, score * 100 / max);
                    String scoreDes = String.format(
                            getString(R.string.upgrade_desc_format),
                            String.valueOf(max - score), "LV2");
                    scoreeDesc.setText(scoreDes);
                    upgradeProgress.setUpGradeProgress(scoreProgress);
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        Intent intent = null;
        if (!LoginManager.hasLogin(mActivity)) {
            LoginManager.startLogin(mActivity);
        } else {
            switch (id) {
                case R.id.user_grade:
                    intent = new Intent(this.getActivity(), UserGradeDescActivity.class);
                    intent.putExtra(AppConstance.KEY_USER_DETAIL_BEAN, detailBean);
                    startActivity(intent);
                    break;
                case R.id.my_cache:
                    intent = new Intent(this.getActivity(), MyCacheActivity.class);
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
                    intent = new Intent(mActivity, SettingActivity.class);
                    mActivity.startActivityForResult(intent, REQUEST_CODE_SETTING_ACTIVITY);
                    break;
                case R.id.change_mobile_num:
                    LoginManager.startChangeMobile(mActivity);
                    break;
            }
        }
    }

    public void freshUserInfo(UserDetailBean bean) {
        if (getActivity() != null) {
            if (bean != null) {
                detailBean = bean;
                updateInfo();
                hasLoginView.setVisibility(View.VISIBLE);
            } else {
                if (LoginManager.hasLogin(getActivity())) {
                    String token = LoginManager.getUserToken(getActivity());
                    if (TextUtils.isEmpty(token)) {
                        detailBean = null;
                        updateInfo();
                    } else {
                        fetchUserInfo(token);
                    }
                } else {
                    detailBean = null;
                    updateInfo();
                }
            }
        }
    }

    private void fetchUserInfo(String token) {
        UserApiService userApiService
                = RetrofitManager.getInstance().createReq(UserApiService.class);
        Call<ResponseBean<UserDetailBean>> call
                = userApiService.getUserDetail(token, null);
        if (call != null) {
            call.enqueue(new CustomCallBack<UserDetailBean>() {
                @Override
                public void onSuccess(UserDetailBean data) {
                    detailBean = data;
                    updateInfo();
                    hasLoginView.setVisibility(View.VISIBLE);
                }

                @Override
                public void onFailed(String error) {
                    showShortToast(error);
                }
            });
        }
    }
}
