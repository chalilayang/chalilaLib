package com.reshape.app.customview;


import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.reshape.app.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pinger on 2016/10/6.
 */

public class FlowLayout extends ViewGroup {
    /**
     * 存储行的集合，管理行
     */
    private List<Line> mLines = new ArrayList<>();

    /**
     * 水平和竖直的间距
     */
    private float vertical_space;
    private float horizontal_space;

    private Line mCurrentLine;
    private int mMaxWidth;

    public FlowLayout(Context context) {
        this(context, null);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.FlowLayout);
        horizontal_space = array.getDimension(R.styleable.FlowLayout_width_space, 0);
        vertical_space = array.getDimension(R.styleable.FlowLayout_height_space, 0);
        array.recycle();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // 每次测量之前都先清空集合，不让会覆盖掉以前
        mLines.clear();
        mCurrentLine = null;

        int width = MeasureSpec.getSize(widthMeasureSpec);
        mMaxWidth = width - getPaddingLeft() - getPaddingRight();

        int childCount = this.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            measureChild(childView, widthMeasureSpec, heightMeasureSpec);
            if (mCurrentLine == null) {
                mCurrentLine = new Line(mMaxWidth, horizontal_space);
                mCurrentLine.addView(childView);
                mLines.add(mCurrentLine);

            } else {
                if (mCurrentLine.canAddView(childView)) {
                    mCurrentLine.addView(childView);
                } else {
                    mCurrentLine = new Line(mMaxWidth, horizontal_space);
                    mCurrentLine.addView(childView);
                    mLines.add(mCurrentLine);
                }
            }
        }
        int height = getPaddingTop() + getPaddingBottom();
        for (int i = 0; i < mLines.size(); i++) {
            height += mLines.get(i).height;
        }
        height += (mLines.size() - 1) * vertical_space;
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        l = getPaddingLeft();
        t = getPaddingTop();
        for (int i = 0; i < mLines.size(); i++) {
            Line line = mLines.get(i);
            line.layout(t, l);
            t += line.height;
            if (i != mLines.size() - 1) {
                t += vertical_space;
            }
        }
    }

    /**
     * 内部类，行管理器，管理每一行的孩子
     */
    public class Line {
        // 定义一个行的集合来存放子View
        private List<View> views = new ArrayList<>();
        // 行的最大宽度
        private int maxWidth;
        // 行中已经使用的宽度
        private int usedWidth;
        // 行的高度
        private int height;
        // 孩子之间的距离
        private float space;

        // 通过构造初始化最大宽度和边距
        public Line(int maxWidth, float horizontalSpace) {
            this.maxWidth = maxWidth;
            this.space = horizontalSpace;
        }

        /**
         * 往集合里添加孩子
         */
        public void addView(View view) {
            int childWidth = view.getMeasuredWidth();
            int childHeight = view.getMeasuredHeight();

            // 更新行的使用宽度和高度
            if (views.size() == 0) {
                // 集合里没有孩子的时候
                if (childWidth > maxWidth) {
                    usedWidth = maxWidth;
                    height = childHeight;
                } else {
                    usedWidth = childWidth;
                    height = childHeight;
                }
            } else {
                usedWidth += childWidth + space;
                height = childHeight > height ? childHeight : height;
            }

            // 添加孩子到集合
            views.add(view);
        }


        /**
         * 判断当前的行是否能添加孩子
         *
         * @return
         */
        public boolean canAddView(View view) {
            // 集合里没有数据可以添加
            if (views.size() == 0) {
                return true;
            }

            // 最后一个孩子的宽度大于剩余宽度就不添加
            if (view.getMeasuredWidth() > (maxWidth - usedWidth - space)) {
                return false;
            }

            // 默认可以添加
            return true;
        }

        /**
         * 指定孩子显示的位置
         *
         * @param t
         * @param l
         */
        public void layout(int t, int l) {
            // 平分剩下的空间
            int avg = (maxWidth - usedWidth) / views.size();

            // 循环指定孩子位置
            for (View view : views) {
                // 获取宽高
                int measuredWidth = view.getMeasuredWidth();
                int measuredHeight = view.getMeasuredHeight();
                // 重新测量
                view.measure(MeasureSpec.makeMeasureSpec(measuredWidth + avg, MeasureSpec.EXACTLY),
                        MeasureSpec.makeMeasureSpec(measuredHeight, MeasureSpec.EXACTLY));
                // 重新获取宽度值
                measuredWidth = view.getMeasuredWidth();


                int top = t;
                int left = l;
                int right = measuredWidth + left;
                int bottom = measuredHeight + top;
                // 指定位置
                view.layout(left, top, right, bottom);

                // 更新数据
                l += measuredWidth + space;
            }
        }
    }
}