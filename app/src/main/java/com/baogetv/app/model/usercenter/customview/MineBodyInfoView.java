package com.baogetv.app.model.usercenter.customview;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chalilayang.scaleview.ScaleCalculator;
import com.chalilayang.scaleview.ScaleTextView;
import com.baogetv.app.R;

/**
 * Created by chalilayang on 2017/11/11.
 */

public class MineBodyInfoView extends LinearLayout {
    private int size_24px;
    private int size_60px;
    private int size_110px;

    private int height;
    private int bodyWeight;
    private int bodyFat;

    private TextView heightTv;
    private TextView bodyWeightTv;
    private TextView bodyFatTv;
    public MineBodyInfoView(Context context) {
        this(context, null);
    }

    public MineBodyInfoView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MineBodyInfoView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }
    private void init(Context context) {
        size_24px = ScaleCalculator.getInstance(context).scaleTextSize(24);
        size_60px = ScaleCalculator.getInstance(context).scaleTextSize(60);
        size_110px = ScaleCalculator.getInstance(context).scaleWidth(110);
        Typeface fontFace
                = Typeface.createFromAsset(context.getAssets(), "fonts/DINEngschriftStd.ttf");

        LinearLayout.LayoutParams llp
                = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1);
        LinearLayout container = new LinearLayout(context);
        container.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);
        TextView textView = new TextView(context);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, size_24px);
        textView.setTextColor(getResources().getColor(R.color.search_label_text));
        textView.setText(getResources().getText(R.string.height) + " ");
        textView.setIncludeFontPadding(false);
        textView.setGravity(Gravity.BOTTOM);
        container.addView(textView);

        heightTv = new TextView(context);
        heightTv.setTextSize(TypedValue.COMPLEX_UNIT_PX, size_60px);
        heightTv.setIncludeFontPadding(false);
        heightTv.setGravity(Gravity.BOTTOM);
        heightTv.setTextColor(Color.BLACK);
        heightTv.setTypeface(fontFace);
        heightTv.setText(getResources().getText(R.string.height_format));
        container.addView(heightTv);

        textView = new ScaleTextView(context);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, 36);
        textView.setTextColor(getResources().getColor(R.color.search_label_text));
        textView.setText("cm");
        textView.setIncludeFontPadding(false);
        textView.setGravity(Gravity.BOTTOM);
        textView.setTextColor(Color.BLACK);
        textView.setTypeface(fontFace);
        container.addView(textView);

        addView(container, llp);

        container = new LinearLayout(context);
        container.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);

        textView = new TextView(context);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, size_24px);
        textView.setTextColor(getResources().getColor(R.color.search_label_text));
        textView.setText(getResources().getText(R.string.body_weight) + " ");
        textView.setIncludeFontPadding(false);
        textView.setGravity(Gravity.BOTTOM);
        container.addView(textView);

        bodyWeightTv = new TextView(context);
        bodyWeightTv.setTextSize(TypedValue.COMPLEX_UNIT_PX, size_60px);
        bodyWeightTv.setIncludeFontPadding(false);
        bodyWeightTv.setGravity(Gravity.BOTTOM);
        bodyWeightTv.setTextColor(Color.BLACK);
        bodyWeightTv.setTypeface(fontFace);
        bodyWeightTv.setText(getResources().getText(R.string.body_weight_format));
        container.addView(bodyWeightTv);

        textView = new ScaleTextView(context);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, 36);
        textView.setTextColor(getResources().getColor(R.color.search_label_text));
        textView.setText("kg");
        textView.setIncludeFontPadding(false);
        textView.setGravity(Gravity.BOTTOM);
        textView.setTextColor(Color.BLACK);
        textView.setTypeface(fontFace);
        container.addView(textView);

        addView(container, llp);

        container = new LinearLayout(context);
        container.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);
        textView = new TextView(context);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, size_24px);
        textView.setTextColor(getResources().getColor(R.color.search_label_text));
        textView.setText(getResources().getText(R.string.body_fat) + " ");
        textView.setIncludeFontPadding(false);
        textView.setGravity(Gravity.BOTTOM);
        container.addView(textView);

        bodyFatTv = new TextView(context);
        bodyFatTv.setTextSize(TypedValue.COMPLEX_UNIT_PX, size_60px);
        bodyFatTv.setIncludeFontPadding(false);
        bodyFatTv.setGravity(Gravity.BOTTOM);
        bodyFatTv.setTextColor(Color.BLACK);
        bodyFatTv.setTypeface(fontFace);
        bodyFatTv.setText(getResources().getText(R.string.body_fat_format));
        container.addView(bodyFatTv);

        textView = new ScaleTextView(context);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, 36);
        textView.setTextColor(getResources().getColor(R.color.search_label_text));
        textView.setText("%");
        textView.setIncludeFontPadding(false);
        textView.setGravity(Gravity.BOTTOM);
        textView.setTextColor(Color.BLACK);
        textView.setTypeface(fontFace);
        container.addView(textView);

        addView(container, llp);
    }

    public void setHeight(int h) {
        this.height = h;
        this.heightTv.setText(String.format(getResources().getString(R.string.height_format), height));
    }

    public void setBodyWeight(int h) {
        this.bodyWeight = h;
        this.bodyWeightTv.setText(String.format(getResources().getString(R.string.body_weight_format), bodyWeight));
    }

    public void setBodyFat(int h) {
        this.bodyFat = h;
        this.bodyFatTv.setText(String.format(getResources().getString(R.string.body_fat_format), bodyFat));
    }
}
