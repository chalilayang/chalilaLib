package com.reshape.app.model.usercenter.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chalilayang.scaleview.ScaleCalculator;
import com.chalilayang.scaleview.ScaleTextView;
import com.reshape.app.R;

import java.lang.ref.SoftReference;

/**
 * Created by chalilayang on 2017/11/11.
 */

public class SettingLineItemView extends RelativeLayout {
    private TextView textView;
    private ImageView moreView;
    private int size_20px;
    private int size_30px;
    private int size_110px;
    private String title;
    private Drawable resID;
    public SettingLineItemView(Context context) {
        this(context, null);
    }

    public SettingLineItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SettingLineItemView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.MineLineItemViewAttr);

        title = ta.getString(R.styleable.MineLineItemViewAttr_title);
        resID = (BitmapDrawable) ta.getDrawable(R.styleable.MineLineItemViewAttr_item_icon);
        ta.recycle();
        init(context);
    }
    private void init(Context context) {
        size_20px = ScaleCalculator.getInstance(context).scaleWidth(20);
        size_30px = ScaleCalculator.getInstance(context).scaleWidth(30);
        size_110px = ScaleCalculator.getInstance(context).scaleWidth(110);
        textView = new ScaleTextView(context);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, 30);
        textView.setTextColor(getResources().getColor(R.color.search_label_text));
        textView.setText(title);
        if (resID != null) {
            resID.setBounds(0, 0, size_30px, size_30px);
            textView.setCompoundDrawables(resID, null, null, null);
            textView.setCompoundDrawablePadding(size_20px);
        }
        LayoutParams rlp = new LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        rlp.addRule(ALIGN_PARENT_LEFT);
        rlp.addRule(CENTER_VERTICAL);
        rlp.leftMargin = size_30px;
        addView(textView, rlp);

        moreView = new ImageView(context);
        moreView.setImageResource(R.mipmap.more_arrow_icon);
        rlp = new LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        rlp.addRule(ALIGN_PARENT_RIGHT);
        rlp.addRule(CENTER_VERTICAL);
        rlp.rightMargin = size_30px;
        moreView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mRef != null && mRef.get() != null) {
                    mRef.get().onMoreViewClick();
                }
            }
        });
        addView(moreView, rlp);
    }

    private SoftReference<ClickCallback> mRef;
    public void setClickCallback(ClickCallback back) {
        if (back != null) {
            mRef = new SoftReference<ClickCallback>(back);
        }
    }
    public interface ClickCallback {
        void onMoreViewClick();
    }
}
