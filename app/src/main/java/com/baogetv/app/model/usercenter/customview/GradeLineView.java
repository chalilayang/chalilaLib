package com.baogetv.app.model.usercenter.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.baogetv.app.R;
import com.chalilayang.scaleview.ScaleFrameLayout;

/**
 * Created by chalilayang on 2017/11/19.
 */

public class GradeLineView extends ScaleFrameLayout {

    private static final String TAG = "GradeLineView";
    private TextView titleTv;
    private TextView descTv;
    private TextView scoreTv;

    public GradeLineView(Context context) {
        this(context, null);
    }

    public GradeLineView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GradeLineView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        View root = LayoutInflater.from(context).inflate(R.layout.grade_line_layout, this);
        titleTv = root.findViewById(R.id.grade_title);
        descTv = root.findViewById(R.id.grade_desc);
        scoreTv = root.findViewById(R.id.grade_score);
    }

    public void setText(String title, String des, String score) {
        titleTv.setText(title);
        descTv.setText(des);
        scoreTv.setText(score);
    }
}
