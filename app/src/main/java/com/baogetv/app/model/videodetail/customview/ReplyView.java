package com.baogetv.app.model.videodetail.customview;

import android.content.Context;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baogetv.app.R;
import com.baogetv.app.model.videodetail.entity.CommentData;
import com.baogetv.app.model.videodetail.entity.ReplyData;
import com.chalilayang.scaleview.ScaleCalculator;
import com.chalilayang.scaleview.ScaleLinearLayout;

import java.lang.ref.SoftReference;

/**
 * Created by chalilayang on 2017/11/21.
 */

public class ReplyView extends ScaleLinearLayout {

    private static final String TAG = ReplyView.class.getSimpleName();
    private TextView contentTv;
    private TextView time;
    private TextView replyBtn;

    private int nameColor;
    private int contentColor;
    private int replyColor;
    private int timeColor;
    private int replyBtnColor;

    private int contentSize;
    private int timeSize;

    private CommentData commentData;

    private String replyFormat;
    private String replyFormatNoTo;

    public ReplyView(Context context) {
        this(context, null);
    }

    public ReplyView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ReplyView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }
    private void init(Context context) {
        nameColor = getResources().getColor(R.color.reply_name);
        contentColor = getResources().getColor(R.color.reply_content);
        replyColor = getResources().getColor(R.color.reply_name);
        timeColor = getResources().getColor(R.color.reply_time);
        replyBtnColor = getResources().getColor(R.color.reply_time);

        contentSize = ScaleCalculator.getInstance(context).scaleTextSize(28);
        timeSize = ScaleCalculator.getInstance(context).scaleTextSize(24);

        replyFormat = getResources().getString(R.string.reply_format);
        replyFormatNoTo = getResources().getString(R.string.reply_format_no_to);

        setOrientation(VERTICAL);
        contentTv = new TextView(context);
        contentTv.setTextSize(TypedValue.COMPLEX_UNIT_PX, contentSize);
        LinearLayout.LayoutParams llp
                = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        addView(contentTv, llp);

        RelativeLayout bottomContainer = new RelativeLayout(context);
        RelativeLayout.LayoutParams rlp
                = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        time = new TextView(context);
        time.setTextSize(TypedValue.COMPLEX_UNIT_PX, timeSize);
        time.setTextColor(timeColor);
        time.setText(getResources().getString(R.string.reply));
        rlp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        bottomContainer.addView(time, rlp);
        replyBtn = new TextView(context);
        replyBtn.setTextSize(TypedValue.COMPLEX_UNIT_PX, timeSize);
        replyBtn.setTextColor(replyBtnColor);
        replyBtn.setText(getResources().getString(R.string.reply));
        rlp = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        rlp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        bottomContainer.addView(replyBtn, rlp);

        addView(bottomContainer, llp);
    }

    public void setReply(final ReplyData data) {
        if (data != null) {
            contentTv.setMovementMethod(LinkMovementMethod.getInstance());
            contentTv.setHighlightColor(Color.TRANSPARENT);
            if (data.getReplyTo() != null) {
                String replyName = data.getReplyer().getNickName();
                String replyToName = data.getReplyTo().getNickName();
                String replyContent = data.getContent();
                String content = String.format(replyFormat, replyName, replyToName, replyContent);

                SpannableString spanString = new SpannableString (content);
                int start = 0;
                int nameEnd = start + replyName.length();
                spanString.setSpan(new ClickableSpan() {

                    @Override
                    public void updateDrawState (TextPaint ds) {
                        ds.setUnderlineText (false);
                        ds.setColor(nameColor);
                    }

                    @Override
                    public void onClick (final View widget) {
                        Log.i(TAG, "onClick: ");
                        if (mRef != null && mRef.get() != null) {
                            mRef.get().onReplyerClick(data);
                        }
                    }
                }, start, nameEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                start = nameEnd;
                nameEnd = start + 2;
                spanString.setSpan(
                        new ForegroundColorSpan(contentColor),
                        start, nameEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                start = nameEnd;
                nameEnd = start + replyToName.length();
                spanString.setSpan(new ClickableSpan() {

                    @Override
                    public void updateDrawState (TextPaint ds) {
                        ds.setUnderlineText (false);
                        ds.setColor(nameColor);
                    }

                    @Override
                    public void onClick (final View widget) {
                        if (mRef != null && mRef.get() != null) {
                            mRef.get().onReplyToClick(data);
                        }
                    }
                }, start, nameEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                start = nameEnd;
                nameEnd = start + 1 + replyContent.length(); // 加1是为了将冒号也包括在内
                spanString.setSpan(new ClickableSpan() {

                    @Override
                    public void updateDrawState (TextPaint ds) {
                        ds.setUnderlineText (false);
                        ds.setColor(contentColor);
                    }

                    @Override
                    public void onClick (final View widget) {
                        if (mRef != null && mRef.get() != null) {
                            mRef.get().onReplyClick(data);
                        }
                    }
                }, start, nameEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                contentTv.setText(spanString);
            }
        }
    }

    private SoftReference<OnReplyClickListener> mRef;
    public void setReplyClickListener(OnReplyClickListener listener) {
        if (listener != null) {
            mRef = new SoftReference<OnReplyClickListener>(listener);
        }
    }

    public interface OnReplyClickListener {
        void onReplyerClick(ReplyData data);
        void onReplyToClick(ReplyData data);
        void onReplyClick(ReplyData data);
    }
}
