package com.reshape.app.model.usercenter.customview;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chalilayang.scaleview.ScaleCalculator;
import com.chalilayang.scaleview.ScaleTextView;
import com.reshape.app.R;

/**
 * Created by chalilayang on 2017/11/11.
 */

public class MineLineItemView extends RelativeLayout {
    private TextView textView;
    private ImageView moreView;
    private int size_30px;
    private int size_110px;
    public MineLineItemView(Context context) {
        this(context, null);
    }

    public MineLineItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MineLineItemView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }
    private void init(Context context) {
        size_30px = ScaleCalculator.getInstance(context).scaleWidth(30);
        size_110px = ScaleCalculator.getInstance(context).scaleWidth(110);
        textView = new ScaleTextView(context);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, 36);
        textView.setTextColor(Color.WHITE);
        textView.setText("我的缓存");
        RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        rlp.addRule(ALIGN_PARENT_LEFT);
        rlp.addRule(CENTER_VERTICAL);
        rlp.leftMargin = size_30px;
        addView(textView, rlp);

        moreView = new ImageView(context);
        moreView.setImageResource(R.mipmap.more_arrow_icon);
        moreView.setBackgroundColor(Color.RED);
        rlp = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        rlp.addRule(ALIGN_PARENT_RIGHT);
        rlp.addRule(CENTER_VERTICAL);
        rlp.rightMargin = size_30px;
        addView(moreView, rlp);
    }
}
