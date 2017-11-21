package com.baogetv.app.model.videodetail.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.baogetv.app.R;
import com.baogetv.app.model.usercenter.entity.UserData;
import com.baogetv.app.model.videodetail.entity.CommentData;
import com.baogetv.app.model.videodetail.entity.ReplyData;
import com.chalilayang.scaleview.ScaleCalculator;
import com.chalilayang.scaleview.ScaleLinearLayout;
import com.chalilayang.scaleview.ScaleTextView;

import java.lang.ref.SoftReference;

/**
 * Created by chalilayang on 2017/11/21.
 */

public class ReplyView extends ScaleLinearLayout {

    private ScaleTextView contentTv;
    private ScaleTextView time;
    private ScaleTextView replyBtn;

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
        contentTv = new ScaleTextView(context);
        contentTv.setTextSize(contentSize);
        LinearLayout.LayoutParams llp
                = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        addView(contentTv, llp);

        RelativeLayout bottomContainer = new RelativeLayout(context);
        RelativeLayout.LayoutParams rlp
                = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        time = new ScaleTextView(context);
        time.setTextSize(timeSize);
        time.setTextColor(timeColor);
        time.setText(getResources().getString(R.string.reply));
        rlp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        bottomContainer.addView(time, rlp);
        replyBtn = new ScaleTextView(context);
        replyBtn.setTextSize(timeSize);
        replyBtn.setTextColor(replyBtnColor);
        replyBtn.setText(getResources().getString(R.string.reply));
        rlp = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        rlp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        bottomContainer.addView(replyBtn, rlp);

        addView(bottomContainer, llp);
    }

    public void setComment(CommentData data) {
        this.commentData = data;
        ReplyData replyData = new ReplyData();
        UserData replyer = new UserData();
        replyer.setNickName("防腐层");
        UserData replyTo = new UserData();
        replyTo.setNickName("吃软饭");
        replyData.setReplyer(replyer);
        replyData.setReplyTo(replyTo);
        replyData.setContent("让他VRTV让他VRTV");
        setReply(replyData);
    }

    public void setReply(ReplyData data) {
        if (data != null) {
            if (data.getReplyTo() != null) {
                String replyName = data.getReplyer().getNickName();
                String replyToName = data.getReplyTo().getNickName();
                String replyContent = data.getContent();
                String content = String.format(replyFormat, replyName, replyToName, replyContent);
                contentTv.setText(content);
            }
        }
    }

    private SoftReference<OnCommentClickListener> mRef;
    public void setCommentClickListener(OnCommentClickListener listener) {
        if (listener != null) {
            mRef = new SoftReference<OnCommentClickListener>(listener);
        }
    }
    public interface OnCommentClickListener {
        void onReplyerClick(CommentData data);
        void onReplyToClick(CommentData data);
        void onReplyClick(CommentData data);
    }
}
