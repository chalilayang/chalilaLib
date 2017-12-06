package com.baogetv.app.model.usercenter.activity;

import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.baogetv.app.BaseTitleActivity;
import com.baogetv.app.R;
import com.baogetv.app.apiinterface.UserApiService;
import com.baogetv.app.bean.GradeBean;
import com.baogetv.app.bean.ResponseBean;
import com.baogetv.app.bean.UserDetailBean;
import com.baogetv.app.constant.AppConstance;
import com.baogetv.app.model.usercenter.Level;
import com.baogetv.app.model.usercenter.LevelUtil;
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
    private GradeListView gradeListView;
    private List<Grade> grades;
    private UserDetailBean userDetailBean;
    private ImageView userImage;
    private TextView scoreTv;
    private UpgradeProgress upgradeProgress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleActivity(getString(R.string.my_grade));
        init();
        getGradeList();
    }

    private void init() {
        userDetailBean = getIntent().getParcelableExtra(AppConstance.KEY_USER_DETAIL_BEAN);
        userImage = (ImageView) findViewById(R.id.user_icon);
        scoreTv = (TextView) findViewById(R.id.level_score_desc);
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
                public void onSuccess(List<GradeBean> data, String msg, int state) {
                    Log.i(TAG, "onSuccess: " + data.size());
                    if (data != null) {
                        Level nextLevel = LevelUtil.getNextLevel(userDetailBean, data);
                        if (nextLevel != null) {
                            int score = Integer.parseInt(userDetailBean.getScore());
                            int nextScore = Integer.parseInt(data.get(nextLevel.index).getScore());
                            String content = score + "/" + nextScore;
                            int progress = Math.min((int)(score * 100.0 / nextScore), 100);
                            upgradeProgress.setUpGradeProgress(progress);
                            Paint paint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
                            paint.setTextSize(scoreTv.getTextSize());
                            float textWidth = paint.measureText(content);
                            float margin
                                    = upgradeProgress.getLayoutParams().width * progress / 100.0f;
                            SpannableString spanString = new SpannableString (content);
                            int start = 0;
                            int nameEnd = start + String.valueOf(score).length();
                            spanString.setSpan(new ClickableSpan() {

                                @Override
                                public void updateDrawState (TextPaint ds) {
                                    ds.setUnderlineText (false);
                                    ds.setColor(getResources().getColor(R.color.reshape_red));
                                }

                                @Override
                                public void onClick (final View widget) {
                                }
                            }, start, nameEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                            margin = Math.max(0, margin - textWidth / 2);
                            scoreTv.setPadding((int)margin, 0, 0, 0);
                            scoreTv.setText(spanString);
                        }
                        for (int index = 0, count = data.size(); index < count; index ++) {
                            int score = Integer.parseInt(data.get(index).getScore());
                            grades.add(new Grade(score, index));
                        }
                        Collections.sort(grades);
                        int userScore = Integer.parseInt(userDetailBean.getScore());
                        int pos = 0;
                        for (int index = 0, count = grades.size(); index < count; index ++) {
                            if (userScore >= grades.get(index).score) {
                                pos = index;
                            }
                        }
                        for (int index = 0, count = grades.size(); index < count; index ++) {
                            GradeBean bean = data.get(grades.get(index).index);
                            gradeListView.add(
                                    bean.getName(),
                                    bean.getPic_url(),
                                    bean.getScore(),
                                    index == pos,
                                    index == count-1);
                        }
                    }
                }

                @Override
                public void onFailed(String error, int state) {

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
