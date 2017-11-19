package com.baogetv.app.model.usercenter.customview;

import android.content.Context;
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

public class VerifyCodeInputView extends ScaleFrameLayout {

    private TextView titleTv;
    private LinearLayout inputContainer;

    private EditText inputEdit;
    private View baseLine;
    public VerifyCodeInputView(Context context) {
        this(context, null);
    }

    public VerifyCodeInputView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VerifyCodeInputView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        View root = LayoutInflater.from(context).inflate(R.layout.verify_code_input_layout, this);
        titleTv = root.findViewById(R.id.input_title);
        titleTv.setTextColor(getResources().getColor(R.color.white));
        titleTv.setTextSize(TypedValue.COMPLEX_UNIT_PX, 36);
        titleTv.setGravity(Gravity.LEFT);

        inputContainer = root.findViewById(R.id.input_edit_container);
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
