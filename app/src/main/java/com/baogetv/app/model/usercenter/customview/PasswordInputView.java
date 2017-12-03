package com.baogetv.app.model.usercenter.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baogetv.app.R;
import com.chalilayang.scaleview.ScaleFrameLayout;

import java.lang.ref.SoftReference;

/**
 * Created by chalilayang on 2017/11/19.
 */

public class PasswordInputView extends ScaleFrameLayout {

    private static final String TAG = "PasswordInputView";
    private TextView titleTv;
    private EditText inputEdit;
    private View baseLine;

    public PasswordInputView(Context context) {
        this(context, null);
    }

    public PasswordInputView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PasswordInputView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        View root = LayoutInflater.from(context).inflate(R.layout.password_input_layout, this);
        titleTv = root.findViewById(R.id.input_title);
        titleTv.setTextColor(getResources().getColor(R.color.white));
        titleTv.setTextSize(TypedValue.COMPLEX_UNIT_PX, 36);
        titleTv.setGravity(Gravity.LEFT);
        baseLine = findViewById(R.id.base_line);
        baseLine.setBackgroundColor(getResources().getColor(R.color.white_40_percent));
        inputEdit = findViewById(R.id.input_edit);
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
        findViewById(R.id.forget_password).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mRef != null && mRef.get() != null) {
                    mRef.get().onForgetClick();
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

    private SoftReference<OnForgetClickListener> mRef;
    public void setForgetListener(OnForgetClickListener listener) {
        if (listener != null) {
            mRef = new SoftReference<OnForgetClickListener>(listener);
        }
    }
    public interface OnForgetClickListener {
        void onForgetClick();
    }
}
