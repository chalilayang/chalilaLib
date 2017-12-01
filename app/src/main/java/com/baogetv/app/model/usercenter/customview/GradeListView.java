package com.baogetv.app.model.usercenter.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baogetv.app.R;
import com.chalilayang.scaleview.ScaleFrameLayout;

/**
 * Created by chalilayang on 2017/11/19.
 */

public class GradeListView extends ScaleFrameLayout {

    private static final String TAG = "GradeLineView";
    private LinearLayout container;

    public GradeListView(Context context) {
        this(context, null);
    }

    public GradeListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GradeListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        View root = LayoutInflater.from(context).inflate(R.layout.grade_list_layout, this);
        container = root.findViewById(R.id.list_container);
    }

    public void add(String title, String des, String score) {
        GradeLineView lineView = new GradeLineView(this.getContext());
        lineView.setText(title, des, score);
        LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        container.addView(lineView, llp);
    }
}
