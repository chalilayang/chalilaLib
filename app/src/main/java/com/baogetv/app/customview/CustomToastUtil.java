package com.baogetv.app.customview;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.baogetv.app.R;

/**
 * Created by chalilayang on 2017/7/30.
 */

public class CustomToastUtil {
    private static Toast shortToast = null;

    public static Toast makeShortText(Context context, CharSequence text) {
        Toast result = new Toast(context);
        LayoutInflater inflate = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflate.inflate(R.layout.qsdk_custom_toast, null);
        TextView tv = (TextView) v.findViewById(R.id.toast_tip);
        tv.setText(text);
        result.setView(v);
        result.setDuration(Toast.LENGTH_SHORT);
        result.setGravity(Gravity.CENTER, 0, 0);
        return result;
    }

    public static Toast makeLongText(Context context, CharSequence text) {
        Toast result = new Toast(context);
        LayoutInflater inflate = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflate.inflate(R.layout.qsdk_custom_toast, null);
        TextView tv = (TextView) v.findViewById(R.id.toast_tip);
        tv.setText(text);
        result.setView(v);
        result.setDuration(Toast.LENGTH_LONG);
        result.setGravity(Gravity.CENTER, 0, 0);
        return result;
    }

    public static void makeShort(Context context, CharSequence text) {
        LayoutInflater inflate = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflate.inflate(R.layout.qsdk_custom_toast, null);
        TextView tv = (TextView) v.findViewById(R.id.toast_tip);
        tv.setText(text);
        if (shortToast == null) {
            shortToast = new Toast(context);
            shortToast.setView(v);
            shortToast.setDuration(Toast.LENGTH_SHORT);
            shortToast.setGravity(Gravity.CENTER, 0, 0);
        } else {
            shortToast.setView(v);
        }
        shortToast.show();
    }
}
