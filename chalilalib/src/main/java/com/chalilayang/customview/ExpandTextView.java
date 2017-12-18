package com.chalilayang.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chalilayang.R;

import java.util.ArrayList;
import java.util.List;

public class ExpandTextView extends RelativeLayout {

    public static final int WORD_LENGTH = 26;//设置一个单词最长不能超过26个字符

    private String mString = "";

    private TextView mTextView;

    private FlowLayout mFlowLayout;

    private float mCharSize = 14;//文本字体大小

    private int mZoomRows = 2;//完全显示的是mZoomRows行，一共显示的是mZoomRows+1行
    private int mZoomChar = 9;//最后一行字符显示的个数

    private Context mContext;

    private float mDescSize = 14;//文本字体大小
    private int mDescColor = -1;//文本字体颜色

    private String mExpandTextOpen = null;
    private String mExpandTextClose = null;
    private int mExpandTextColor = -1;
    private float mExpandTextSize = 14;//展开/收缩文本大小

    private int mOpenResId;//展开右边的图片资源id
    private int mCloseResId;//收缩右边的图片资源id
    private DisplayMetrics displayMetrics;

	/*
     *
	 *  <attr name="desc" format="string" />
        <attr name="descSize" format="float" />
        <attr name="descColor" format="color" />
        <attr name="expandText" format="string" />
        <attr name="expandTextColor" format="color" />
        <attr name="expandTextSize" format="float" />
	 */

    public ExpandTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        displayMetrics = new DisplayMetrics();
        ((WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE))
                .getDefaultDisplay().getMetrics(displayMetrics);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ExpandTextView);
        mZoomRows = a.getInt(R.styleable.ExpandTextView_descZoomRows, 2);
        mString = a.getString(R.styleable.ExpandTextView_descText);
        if (TextUtils.isEmpty(mString)) {
            mString = "";
        }
        mDescSize = a.getFloat(R.styleable.ExpandTextView_descSize, 14);
        mCharSize = mDescSize;
        mDescColor = a.getColor(R.styleable.ExpandTextView_descColor, 0);
        mExpandTextOpen = a.getString(R.styleable.ExpandTextView_expandTextOpen);
        mExpandTextClose = a.getString(R.styleable.ExpandTextView_expandTextClose);
        mExpandTextColor = a.getColor(R.styleable.ExpandTextView_expandTextColor, 0);
        mExpandTextSize = a.getFloat(R.styleable.ExpandTextView_expandTextSize, 14);
        mOpenResId = a.getResourceId(R.styleable.ExpandTextView_expandTextOpenDrawable, 0);
        mCloseResId = a.getResourceId(R.styleable.ExpandTextView_expandTextCloseDrawable, 0);
        init(context);
    }

    public ExpandTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ExpandTextView(Context context) {
        this(context, null);
    }

    /**
     * 初始化
     *
     * @param context
     */
    private void init(Context context) {
        mFlowLayout = new FlowLayout(context);
        this.addView(mFlowLayout);
        mTextView = getTextView(mCharSize);
        mTextViewExpand = new TextView(mContext);
        mTextViewExpand.setTextSize(TypedValue.COMPLEX_UNIT_PX, mExpandTextSize);
        mTextViewExpand.setTextColor(mExpandTextColor);
        mTextViewExpand.setGravity(Gravity.CENTER_VERTICAL);
    }

    private boolean mIsOnce = true;

    private int mWidthSize;

    private TextView mTextViewExpand;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (mIsOnce) {
            //计算一行最大显示的宽度
            mWidthSize = MeasureSpec.getSize(widthMeasureSpec) - getPaddingLeft() -
					getPaddingRight();
            //默认为收缩状态
            expandText(false);
            //onMeasure可能会执行多次
            mIsOnce = false;
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    /**
     * @return 返回指定笔和指定字符串的长度
     */
    public float getFontLength(Paint paint, String str) {
        return paint.measureText(str);
    }

    public float getFontLength(Paint paint, char str) {
        TextView tv = new TextView(mContext);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, mCharSize);
        tv.setText(String.valueOf(str));
        tv.setIncludeFontPadding(false);
        int size = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        tv.measure(size, size);
        return tv.getMeasuredWidth();
    }

    /**
     * 是否展开文本
     *
     * @param isExpand
     */
    private void expandText(boolean isExpand) {
        int widthScreen = mWidthSize;
        float width = 0;//计算一行的宽度
        int index = 0;//记住每行开头的下标索引
        int rows = 0;//计算行数
        List<String> strings = new ArrayList<String>();
        for (int i = 0; i < mString.length(); i++) {
            float charWidth = getCharWidth(mTextView, (char) mString.charAt(i));
            if (width + charWidth <= widthScreen) {
                width += charWidth;
            } else {
                if (mString.length() != i) {
                    strings.add(mString.substring(index, i));
                    index = i;
                    width = 0;
                    width += charWidth;
                    rows++;
                }
            }
            //第一种情况：在收缩文本行数以内 （展开和收缩都是一样的，所以就没有收缩和展开）
            if (rows <= mZoomRows && !isExpand && mString.length() - 1 == i) {
                strings.add(mString.substring(index, i + 1));//加最后一行
                for (int j = 0; j < strings.size(); j++) {
                    TextView textView = new TextView(mContext);
                    textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, mCharSize);
                    textView.setTextColor(mDescColor);
                    textView.setText(strings.get(j));
                    //textView.setBackgroundColor(Color.RED);
                    mFlowLayout.addView(textView);
                }
                break;
            }
            //第二种情况：展开时，已经到最后一个字符
            if (isExpand && mString.length() - 1 == i) {
                strings.add(mString.substring(index, i + 1));//加最后一行
                for (int j = 0; j < strings.size(); j++) {
                    TextView textView = new TextView(mContext);
                    textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, mCharSize);
                    textView.setText(strings.get(j));
                    textView.setTextColor(mDescColor);
                    //textView.setBackgroundColor(Color.GREEN);
                    mFlowLayout.addView(textView);
                }

                mTextViewExpand.setText("  " + mExpandTextClose);
                setExpandTextDrawable(mCloseResId);
                mTextViewExpand.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        //Toast.makeText(mContext, "收缩", 0).show();
                        mFlowLayout.removeAllViews();
                        expandText(false);
                    }
                });
                mFlowLayout.addView(mTextViewExpand);
                break;
            }
            //第三种情况：收缩时，但是文本已经超出了收缩的个数，所以只保留收缩的文本字数
            if (rows == mZoomRows && index + mZoomChar == i && !isExpand) {
                strings.add(mString.substring(index, i + 1));//加最后一行
                strings.add("...");
                for (int j = 0; j < strings.size(); j++) {
                    TextView textView = new TextView(mContext);
                    textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, mCharSize);
                    textView.setText(strings.get(j));
                    textView.setTextColor(mDescColor);
                    //textView.setBackgroundColor(Color.BLUE);
                    mFlowLayout.addView(textView);
                }
                mTextViewExpand.setText("  " + mExpandTextOpen);
                setExpandTextDrawable(mOpenResId);
                mTextViewExpand.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        //Toast.makeText(mContext, "展开", 0).show();
                        mFlowLayout.removeAllViews();
                        expandText(true);
                    }
                });
                mFlowLayout.addView(mTextViewExpand);
                break;
            }
        }

    }

    /**
     * 计算每一个字符的宽度
     *
     * @param textView 初始化工作的文本控件，载体
     * @param c        字符
     * @return
     */
    private float getCharWidth(TextView textView, char c) {
        Paint paint = textView.getPaint();
        return getFontLength(paint, c) * displayMetrics.density;
    }

    /**
     * 临时文本控件，用于计算每个字符所占的空间大小，主要是宽度
     *
     * @param size
     * @return
     */
    private TextView getTextView(float size) {
        TextView textView = new TextView(mContext);
        textView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
        return textView;
    }

    /**
     * 切换图片
     *
     * @param resId
     */
    private void setExpandTextDrawable(int resId) {
        Drawable drawable = mContext.getResources().getDrawable(resId);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        mTextViewExpand.setCompoundDrawables(null, null, drawable, null);
    }

    /**
     * 请求重新布局绘制
     */
    private void requestLay() {
        mIsOnce = true;
        mFlowLayout.removeAllViews();
        requestLayout();
        invalidate();
    }

    /**
     * 设置显示的文本
     *
     * @param text
     */
    public void setText(String text) {
        mString = text;
        requestLay();
    }

    /**
     * 设置显示文本的颜色
     *
     * @param color
     */
    public void setTextColor(int color) {
        mDescColor = color;
        requestLay();
    }

    /**
     * 设置显示文本的大小
     *
     * @param size
     */
    public void setTextSize(float size) {
        mCharSize = mDescSize = size;
        requestLay();
    }

    /**
     * 设置显示的展开缩放文本
     */
    public void setExpandText(String open, String close) {
        mExpandTextOpen = open;
        mExpandTextClose = close;
        requestLay();
    }

    /**
     * 设置显示的展开缩放文本的指示图片
     */
    public void setExpandTextDrawable(int openResId, int closeResId) {
        mOpenResId = openResId;
        mCloseResId = closeResId;
        requestLay();
    }

    /**
     * 设置显示展开缩放文本的颜色
     *
     * @param color
     */
    public void setExpandTextColor(int color) {
        mExpandTextColor = color;
        requestLay();
    }

    /**
     * 设置显示展开缩放文本的大小
     *
     * @param size
     */
    public void setExpandTextSize(float size) {
        mExpandTextSize = size;
        mTextViewExpand.setTextSize(TypedValue.COMPLEX_UNIT_PX, mExpandTextSize);
        requestLay();
    }

    /**
     * 设置收缩时显示的行数
     *
     * @param rows
     */
    public void setDescZoomRows(int rows) {
        mZoomRows = rows;
        requestLay();
    }
}
