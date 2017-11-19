package com.baogetv.app.model.usercenter.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baogetv.app.R;
import com.chalilayang.scaleview.ScaleFrameLayout;

/**
 * Created by chalilayang on 2017/11/19.
 */

public class TitleInputView extends ScaleFrameLayout {

    private TextView titleTv;
    private String title;
    private LinearLayout inputContainer;

    private boolean isMobileType;
    private TextView areaNum;
    private EditText inputEdit;
    private View baseLine;
    public TitleInputView(Context context) {
        this(context, null);
    }

    public TitleInputView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TitleInputView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.TitleInputViewAttr);

        title = ta.getString(R.styleable.TitleInputViewAttr_input_title);
        isMobileType = ta.getBoolean(R.styleable.TitleInputViewAttr_mobile_type, false);
        ta.recycle();
        init(context);
    }

    private void init(Context context) {
        View root = LayoutInflater.from(context).inflate(R.layout.input_layout, this);
        titleTv = root.findViewById(R.id.input_title);
        titleTv.setText(title);
        titleTv.setTextColor(getResources().getColor(R.color.white));
        titleTv.setTextSize(TypedValue.COMPLEX_UNIT_PX, 36);
        titleTv.setGravity(Gravity.LEFT);

        inputContainer = root.findViewById(R.id.input_edit_container);
        inputContainer.setOrientation(LinearLayout.HORIZONTAL);
        inputContainer.setGravity(Gravity.CENTER_VERTICAL);
        if (isMobileType) {
            areaNum = root.findViewById(R.id.area_num);
            areaNum.setVisibility(VISIBLE);
            areaNum.setTextColor(getResources().getColor(R.color.white));
            areaNum.setTextSize(TypedValue.COMPLEX_UNIT_PX, 32);
        }
        inputEdit = root.findViewById(R.id.input_edit);
        inputEdit.setTextColor(getResources().getColor(R.color.white));
        inputEdit.setTextSize(TypedValue.COMPLEX_UNIT_PX, 36);
        inputEdit.setCursorVisible(true);
        inputEdit = findViewById(R.id.input_edit);
        baseLine = findViewById(R.id.base_line);
        baseLine.setBackgroundColor(getResources().getColor(R.color.white_40_percent));
        inputEdit.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (baseLine != null) {
                    if (b) {
                        baseLine.setBackgroundColor(getResources().getColor(R.color.white));
                    } else {
                        baseLine.setBackgroundColor(getResources().getColor(R.color.white_40_percent));
                    }
                }
            }
        });
        inputEdit.clearFocus();
    }

    public String getInputText() {
        if (inputEdit != null) {
            return inputEdit.getText().toString();
        } else {
            return "";
        }
    }
}
