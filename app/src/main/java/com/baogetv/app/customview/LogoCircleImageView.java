package com.baogetv.app.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import com.baogetv.app.R;
import com.chalilayang.scaleview.ScaleCalculator;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by chalilayang on 2017/11/21.
 */

public class LogoCircleImageView extends CircleImageView {
    private Drawable proLogo;
    private Drawable one;
    private Drawable two;
    private Drawable three;
    private Drawable four;
    private Drawable five;
    private Drawable six;
    private Drawable seven;
    private Drawable eight;
    private Drawable nine;
    private Drawable oneYellow;
    private Drawable twoYellow;
    private Drawable threeYellow;
    private Drawable fourYellow;
    private Drawable fiveYellow;
    private Drawable sixYellow;
    private Drawable sevenYellow;
    private Drawable eightYellow;
    private Drawable nineYellow;

    private Paint circlePaint;
    public LogoCircleImageView(Context context) {
        this(context, null);
    }

    public LogoCircleImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LogoCircleImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        one = context.getResources().getDrawable(R.mipmap.user_grade_one_icon);
        two = context.getResources().getDrawable(R.mipmap.user_grade_two_icon);
        three = context.getResources().getDrawable(R.mipmap.user_grade_three_icon);
        four = context.getResources().getDrawable(R.mipmap.user_grade_four_icon);
        five = context.getResources().getDrawable(R.mipmap.user_grade_five_icon);
        six = context.getResources().getDrawable(R.mipmap.user_grade_six_icon);
        seven = context.getResources().getDrawable(R.mipmap.user_grade_seven_icon);
        eight = context.getResources().getDrawable(R.mipmap.user_grade_eight_icon);
        nine = context.getResources().getDrawable(R.mipmap.user_grade_nine_icon);
        oneYellow = context.getResources().getDrawable(R.mipmap.user_grade_one_yellow_icon);
        twoYellow = context.getResources().getDrawable(R.mipmap.user_grade_two_yellow_icon);
        threeYellow = context.getResources().getDrawable(R.mipmap.user_grade_three_yellow_icon);
        fourYellow = context.getResources().getDrawable(R.mipmap.user_grade_four_yellow_icon);
        fiveYellow = context.getResources().getDrawable(R.mipmap.user_grade_five_yellow_icon);
        sixYellow = context.getResources().getDrawable(R.mipmap.user_grade_six_icon_yellow);
        sevenYellow = context.getResources().getDrawable(R.mipmap.user_grade_sevent_icon_yellow);
        eightYellow = context.getResources().getDrawable(R.mipmap.user_grade_eight_icon_yellow);
        nineYellow = context.getResources().getDrawable(R.mipmap.user_grade_nine_icon_yellow);
        circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        circlePaint.setColor(getResources().getColor(R.color.reshape_red));
        int paintWidth = ScaleCalculator.getInstance(context).scaleWidth(4);
        circlePaint.setStrokeWidth(paintWidth);
        circlePaint.setStyle(Paint.Style.STROKE);
    }

    public void setLogo(int grade, int level) {
        switch (level) {
            case 1:
                proLogo = oneYellow;
                if (grade == 3) {
                    proLogo = one;
                }
                break;
            case 2:
                proLogo = twoYellow;
                if (grade == 3) {
                    proLogo = two;
                }
                break;
            case 3:
                proLogo = threeYellow;
                if (grade == 3) {
                    proLogo = three;
                }
                break;
            case 4:
                proLogo = fourYellow;
                if (grade == 3) {
                    proLogo = four;
                }
                break;
            case 5:
                proLogo = fiveYellow;
                if (grade == 3) {
                    proLogo = five;
                }
                break;
            case 6:
                proLogo = sixYellow;
                if (grade == 3) {
                    proLogo = six;
                }
                break;
            case 7:
                proLogo = sevenYellow;
                if (grade == 3) {
                    proLogo = seven;
                }
                break;
            case 8:
                proLogo = eightYellow;
                if (grade == 3) {
                    proLogo = eight;
                }
                break;
            case 9:
                proLogo = nineYellow;
                if (grade == 3) {
                    proLogo = nine;
                }
                break;
            default:
                proLogo = null;
                break;

        }
        if (grade == 3) {
            circlePaint.setColor(getResources().getColor(R.color.reshape_red));
        } else {
            circlePaint.setColor(getResources().getColor(R.color.reshape_yellow));
        }
        invalidate();
    }

    public int getProLogo(String gradeStr, String levelStr) {
        int grade = Integer.parseInt(gradeStr);
        int level = Integer.parseInt(levelStr);
        int result = -1;
        switch (level) {
            case 1:
                result = R.mipmap.user_grade_one_yellow_icon;
                if (grade == 3) {
                    result = R.mipmap.user_grade_one_icon;
                }
                break;
            case 2:
                result = R.mipmap.user_grade_two_yellow_icon;
                if (grade == 3) {
                    result = R.mipmap.user_grade_two_icon;
                }
                break;
            case 3:
                result = R.mipmap.user_grade_three_yellow_icon;
                if (grade == 3) {
                    result = R.mipmap.user_grade_three_icon;
                }
                break;
            case 4:
                result = R.mipmap.user_grade_four_yellow_icon;
                if (grade == 3) {
                    result = R.mipmap.user_grade_four_icon;
                }
                break;
            case 5:
                result = R.mipmap.user_grade_five_yellow_icon;
                if (grade == 3) {
                    result = R.mipmap.user_grade_five_icon;
                }
                break;
            default:
                break;
        }
        return result;
    }

    public void setLogo(String gradeStr, String levelStr) {
        int grade = Integer.parseInt(gradeStr);
        int level = Integer.parseInt(levelStr);
        setLogo(grade, level);
    }

    public void setLogo(String levelStr) {
        int grade = 0;
        int level = Integer.parseInt(levelStr);
        setLogo(grade, level);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = canvas.getWidth();
        int height = canvas.getHeight();
        if (proLogo != null && width > 0 && height > 0) {
            float cx = width / 2.0f;
            float cy = height / 2.0f;
            canvas.drawCircle(cx, cy, cx - circlePaint.getStrokeWidth()/2, circlePaint);
            int left = width - proLogo.getIntrinsicWidth();
            int top = height - proLogo.getIntrinsicHeight();
            int right = width;
            int bottom = height;
            proLogo.setBounds(left, top, right, bottom);
            proLogo.draw(canvas);
        }
    }
}
