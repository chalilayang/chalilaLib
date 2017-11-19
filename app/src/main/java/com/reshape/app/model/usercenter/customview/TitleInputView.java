package com.reshape.app.model.usercenter.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chalilayang.scaleview.ScaleFrameLayout;
import com.reshape.app.R;

/**
 * Created by chalilayang on 2017/11/19.
 */

public class TitleInputView extends ScaleFrameLayout {

    private TextView title;
    private LinearLayout inputContainer;

    private boolean isMobileType = true;
    private TextView areaNum;
    private EditText input;
    public TitleInputView(Context context) {
        this(context, null);
    }

    public TitleInputView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TitleInputView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        View root = LayoutInflater.from(context).inflate(R.layout.input_layout, this);
        title = root.findViewById(R.id.input_title);
        title.setTextColor(getResources().getColor(R.color.white));
        title.setTextSize(TypedValue.COMPLEX_UNIT_PX, 36);
        title.setGravity(Gravity.LEFT);

        inputContainer = root.findViewById(R.id.input_edit_container);
        inputContainer.setOrientation(LinearLayout.HORIZONTAL);
        inputContainer.setGravity(Gravity.CENTER_VERTICAL);
        if (isMobileType) {
            areaNum = root.findViewById(R.id.area_num);
            areaNum.setVisibility(VISIBLE);
            areaNum.setTextColor(getResources().getColor(R.color.white));
            areaNum.setTextSize(TypedValue.COMPLEX_UNIT_PX, 32);
        }
        input = root.findViewById(R.id.input_edit);
        input.setTextColor(getResources().getColor(R.color.white));
        input.setTextSize(TypedValue.COMPLEX_UNIT_PX, 36);
        input.setCursorVisible(true);
    }
}
