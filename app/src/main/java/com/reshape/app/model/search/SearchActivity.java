package com.reshape.app.model.search;

import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chalilayang.scaleview.ScaleCalculator;
import com.nex3z.flowlayout.FlowLayout;
import com.reshape.app.BaseActivity;
import com.reshape.app.R;

public class SearchActivity extends BaseActivity {

    private static final String TAG = "SearchActivity";
    private FlowLayout flowLayout;
    /**
     * 显示的文字
     */
    private String[] mDatas = new String[]{"QQ",
            "视频",
            "放开那三国",
            "电子书",
            "酒店",
            "单机",
            "小说",
            "斗地主",
            "优酷",
            "网游",
            "WIFI万能钥匙",
            "播放器",
            "捕鱼达人2",
            "机票",
            "游戏",
            "熊出没之熊大快跑",
            "美图秀秀",
            "浏览器",};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        flowLayout = findViewById(R.id.search_label_layout);
        int space = ScaleCalculator.getInstance(getApplicationContext()).scaleWidth(20);
        flowLayout.setChildSpacing(space);
        flowLayout.setRowSpacing(space);
        addLabel();
    }

    private void addLabel() {
        // 循环添加TextView到容器
        int paddingLeft
                = ScaleCalculator.getInstance(getApplicationContext()).scaleTextSize(30);
        int paddingTop
                = ScaleCalculator.getInstance(getApplicationContext()).scaleTextSize(22);
        flowLayout.measure(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        int height = flowLayout.getMeasuredHeight();
        Log.i(TAG, "addLabel: " + height);
        for (int i = 0; i < mDatas.length; i++) {
            final TextView view = new TextView(this);
            view.setText(mDatas[i]);
            view.setTextColor(getResources().getColor(R.color.search_label_text));
            view.setGravity(Gravity.CENTER);
            view.setIncludeFontPadding(false);
            view.setPadding(paddingLeft, paddingTop, paddingLeft, paddingTop);
            view.setTextSize(
                    TypedValue.COMPLEX_UNIT_PX,
                    ScaleCalculator.getInstance(getApplicationContext()).scaleTextSize(26));
            view.setBackgroundResource(R.drawable.round_corner_rec);
            flowLayout.addView(view);
        }
    }
}
