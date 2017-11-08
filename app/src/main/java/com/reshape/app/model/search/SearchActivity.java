package com.reshape.app.model.search;

import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.TextView;

import com.chalilayang.scaleview.ScaleCalculator;
import com.nex3z.flowlayout.FlowLayout;
import com.reshape.app.BaseActivity;
import com.reshape.app.R;

public class SearchActivity extends BaseActivity {

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
            "浏览器",
            "单机游戏",
            "我的世界",
            "电影电视",
            "QQ空间",
            "旅游",
            "免费游戏",
            "2048",
            "刀塔传奇",
            "壁纸",
            "节奏大师",
            "锁屏",
            "装机必备",
            "天天动听",
            "备份",
            "网盘",
            "海淘网",
            "大众点评",
            "爱奇艺视频",
            "腾讯手机管家",
            "百度地图",
            "猎豹清理大师",
            "谷歌地图",
            "hao123上网导航",
            "京东",
            "有你",
            "万年历-农历黄历",
            "支付宝钱包"};

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
        for (int i = 0; i < mDatas.length; i++) {
            final TextView view = new TextView(this);
            view.setText(mDatas[i]);
            view.setTextColor(getResources().getColor(R.color.search_label_text));
            view.setGravity(Gravity.CENTER);
            view.setPadding(paddingLeft, paddingTop, paddingLeft, paddingTop);
            view.setTextSize(
                    TypedValue.COMPLEX_UNIT_PX,
                    ScaleCalculator.getInstance(getApplicationContext()).scaleTextSize(26));
            view.setBackgroundResource(R.drawable.round_corner_rec);
            flowLayout.addView(view);
        }
    }
}
