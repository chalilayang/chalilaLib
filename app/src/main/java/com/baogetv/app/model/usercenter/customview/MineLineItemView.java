package com.baogetv.app.model.usercenter.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chalilayang.scaleview.ScaleCalculator;
import com.chalilayang.scaleview.ScaleTextView;
import com.baogetv.app.R;

import java.lang.ref.SoftReference;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by chalilayang on 2017/11/11.
 */

public class MineLineItemView extends RelativeLayout {
    private TextView textView;
    private TextView versionTv;
    private TextView newLogoView;
    private ImageView moreView;
    private ImageView rightImageView;
    private int size_10px;
    private int size_20px;
    private int size_30px;
    private int size_110px;
    private String title;
    private Drawable resID;
    private boolean checkType;
    private boolean open;
    private boolean rightVisible;
    public MineLineItemView(Context context) {
        this(context, null);
    }

    public MineLineItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MineLineItemView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.MineLineItemViewAttr);

        title = ta.getString(R.styleable.MineLineItemViewAttr_title);
        resID = (BitmapDrawable) ta.getDrawable(R.styleable.MineLineItemViewAttr_item_icon);
        checkType = ta.getBoolean(R.styleable.MineLineItemViewAttr_check_type, false);
        rightVisible = ta.getBoolean(R.styleable.MineLineItemViewAttr_right_image_visible, false);
        ta.recycle();
        init(context);
    }
    private void init(Context context) {
        setBackgroundResource(R.color.white);
        size_10px = ScaleCalculator.getInstance(context).scaleWidth(10);
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
        RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        rlp.addRule(ALIGN_PARENT_LEFT);
        rlp.addRule(CENTER_VERTICAL);
        rlp.leftMargin = size_30px;
        addView(textView, rlp);

        moreView = new ImageView(context);
        moreView.setId(R.id.more_view_id);
        moreView.setImageResource(checkType ? R.mipmap.closed_icon : R.mipmap.more_arrow_icon);
        open = false;
        rlp = new RelativeLayout.LayoutParams(
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

        versionTv = new TextView(context);
        versionTv.setTextSize(TypedValue.COMPLEX_UNIT_PX, size_30px);
        versionTv.setTextColor(getResources().getColor(R.color.search_label_text));
        rlp = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        rlp.addRule(RelativeLayout.LEFT_OF, moreView.getId());
        rlp.addRule(CENTER_VERTICAL);
        rlp.rightMargin = size_30px;
        addView(versionTv, rlp);

        newLogoView = new TextView(context);
        newLogoView.setTextSize(TypedValue.COMPLEX_UNIT_PX, size_20px);
        newLogoView.setTextColor(getResources().getColor(R.color.white));
        newLogoView.setBackgroundDrawable(getRoundSelected());
        newLogoView.setPadding(size_20px, size_10px/3, size_20px, size_10px/3);
        newLogoView.setText("12");
        rlp = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        rlp.addRule(RelativeLayout.LEFT_OF, moreView.getId());
        rlp.addRule(CENTER_VERTICAL);
        rlp.rightMargin = size_30px;
        addView(newLogoView, rlp);
        newLogoView.setVisibility(INVISIBLE);

        if (rightVisible) {
            rightImageView = new CircleImageView(context);
            rightImageView.setImageResource(R.mipmap.user_default_icon);
            rlp = new RelativeLayout.LayoutParams(size_110px, size_110px);
            rlp.addRule(RelativeLayout.LEFT_OF, moreView.getId());
            rlp.addRule(CENTER_VERTICAL);
            rlp.rightMargin = size_30px;
            rlp.bottomMargin = size_30px;
            rlp.topMargin = size_30px;
            addView(rightImageView, rlp);
        }
    }

    private Drawable getRoundSelected() {
        int outRadius = size_20px;
        float[] outerRadii = {
                outRadius, outRadius,
                outRadius, outRadius,
                outRadius, outRadius,
                outRadius, outRadius};
        RoundRectShape roundRectShape = new RoundRectShape(outerRadii, null, null);
        ShapeDrawable drawable = new ShapeDrawable(roundRectShape);
        drawable.getPaint().setColor(getResources().getColor(R.color.reshape_red));
        drawable.getPaint().setFlags(Paint.ANTI_ALIAS_FLAG);
        drawable.getPaint().setStyle(Paint.Style.FILL);
        return drawable;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpenState(boolean openState) {
        if (openState) {
            moreView.setImageResource(R.mipmap.closed_icon);
        } else {
            moreView.setImageResource(R.mipmap.opened_icon);
        }
        open = openState;
    }

    public void setNewLogoText(String content) {
        if (TextUtils.isEmpty(content)) {
            newLogoView.setVisibility(INVISIBLE);
        } else {
            newLogoView.setText(content);
            newLogoView.setVisibility(VISIBLE);
        }
    }

    public void setVersion(String version) {
        versionTv.setText(version);
        versionTv.setVisibility(VISIBLE);
    }

    public ImageView getRightImageView() {
        return rightImageView;
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
