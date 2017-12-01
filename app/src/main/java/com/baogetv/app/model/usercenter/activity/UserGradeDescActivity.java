package com.baogetv.app.model.usercenter.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.ImageView;

import com.baogetv.app.BaseTitleActivity;
import com.baogetv.app.R;
import com.baogetv.app.apiinterface.UserApiService;
import com.baogetv.app.bean.GradeBean;
import com.baogetv.app.bean.ResponseBean;
import com.baogetv.app.bean.UserDetailBean;
import com.baogetv.app.model.usercenter.customview.GradeListView;
import com.baogetv.app.model.usercenter.customview.UpgradeProgress;
import com.baogetv.app.net.CustomCallBack;
import com.baogetv.app.net.RetrofitManager;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;

public class UserGradeDescActivity extends BaseTitleActivity {
    private static final String TAG = UserGradeDescActivity.class.getSimpleName();
    private static final String KEY_USER_DETAIL = "USER_DETAIL";
    private GradeListView gradeListView;
    private List<Grade> grades;
    private UserDetailBean userDetailBean;
    private ImageView userImage;
    private UpgradeProgress upgradeProgress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleActivity(getString(R.string.my_grade));
        init();
        getGradeList();
    }

    private void init() {
        userDetailBean = getIntent().getParcelableExtra(KEY_USER_DETAIL);
        userImage = (ImageView) findViewById(R.id.user_icon);
        upgradeProgress = (UpgradeProgress) findViewById(R.id.user_grade_progress);
        if (userDetailBean != null) {
            Glide.with(this).load(userDetailBean.getPic_url()).into(userImage);
            upgradeProgress.setUpGradeProgress(Integer.parseInt(userDetailBean.getScore()));
        }
        gradeListView = (GradeListView) findViewById(R.id.grade_list_view);
        grades = new ArrayList<>();
    }

    protected int getRootView() {
        return R.layout.activity_user_grade_desc;
    }

    private void getGradeList() {
        UserApiService userApiService
                = RetrofitManager.getInstance().createReq(UserApiService.class);
        Call<ResponseBean<List<GradeBean>>> call = userApiService.getGradeList(null);
        if (call != null) {
            call.enqueue(new CustomCallBack<List<GradeBean>>() {
                @Override
                public void onSuccess(List<GradeBean> data) {
                    Log.i(TAG, "onSuccess: " + data.size());
                    if (data != null) {
                        for (int index = 0, count = data.size(); index < count; index ++) {
                            int score = Integer.parseInt(data.get(index).getScore());
                            grades.add(new Grade(score, index));
                        }
                        Collections.sort(grades);
                        for (int index = 0, count = grades.size(); index < count; index ++) {
                            GradeBean bean = data.get(grades.get(index).index);
                            gradeListView.add(bean.getName(), bean.getMedal(), bean.getScore());
                        }
                    }
                }

                @Override
                public void onFailed(String error) {

                }
            });
        }
    }

    public class Grade implements Comparable<Grade> {
        int score;
        int index;
        public Grade(int s, int i) {
            score = s;
            index = i;
        }

        @Override
        public int compareTo(@NonNull Grade o) {
            return score - o.score;
        }
    }
}
