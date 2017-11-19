package com.baogetv.app.model.usercenter.customview;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
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

import java.lang.ref.SoftReference;

/**
 * Created by chalilayang on 2017/11/19.
 */

public class VerifyCodeInputView extends ScaleFrameLayout {

    private TextView titleTv;
    private LinearLayout inputContainer;

    private EditText inputEdit;
    private View baseLine;
    private TextView verifyTip;
    private String verifyCountFormat;
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
        verifyCountFormat = getResources().getString(R.string.fetch_verify_code_count);
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
        verifyTip = findViewById(R.id.verify_code_image);
        countNum = 60;
        verifyTip.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (reference != null && reference.get() != null) {
                    reference.get().onFetchClick();
                }
            }
        });
    }

    public String getInputText() {
        if (inputEdit != null) {
            return inputEdit.getText().toString();
        } else {
            return "";
        }
    }

    private int countNum;
    public void startCountDown(int count) {
        countNum = count;
        verifyTip.setAlpha(0.6f);
        verifyTip.setEnabled(false);
        handler.obtainMessage(MSG_COUNT_START).sendToTarget();
    }

    public static final int MSG_COUNT_START = 1000;
    public static final int MSG_COUNT_DOWN = 1001;
    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_COUNT_START:
                    verifyTip.setText(String.format(verifyCountFormat, countNum));
                    Message m = obtainMessage(MSG_COUNT_DOWN);
                    sendMessageDelayed(m, 1000);
                    break;
                case MSG_COUNT_DOWN:
                    countNum --;
                    if (countNum == 0) {
                        countNum = 60;
                        verifyTip.setText(getResources().getString(R.string.fetch_verify_code));
                        verifyTip.setAlpha(1f);
                        verifyTip.setEnabled(true);
                    } else {
                        verifyTip.setText(String.format(verifyCountFormat, countNum));
                        m = obtainMessage(MSG_COUNT_DOWN);
                        sendMessageDelayed(m, 1000);
                    }
                    break;

            }
        }
    };

    private SoftReference<VerifyCallBack> reference;
    public void setVerifyCallBack(VerifyCallBack back) {
        reference = new SoftReference<VerifyCallBack>(back);
    }
    public interface VerifyCallBack {
        void onFetchClick();
    }
}
