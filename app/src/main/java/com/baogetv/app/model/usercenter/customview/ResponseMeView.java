package com.baogetv.app.model.usercenter.customview;

import android.content.Context;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.baogetv.app.R;
import com.baogetv.app.bean.ResponseMeBean;
import com.baogetv.app.customview.LogoCircleImageView;
import com.bumptech.glide.Glide;
import com.chalilayang.scaleview.ScaleFrameLayout;

import java.lang.ref.SoftReference;

/**
 * Created by chalilayang on 2017/11/21.
 */

public class ResponseMeView extends ScaleFrameLayout {

    private ResponseMeBean commentData;

    private LogoCircleImageView userLogoImage;
    private TextView userName;
    private TextView userDesc;
    private TextView commentTime;
    private TextView responseContent;
    private TextView titleContainer;
    private String commentFormat;

    private int contentColor;

    public ResponseMeView(Context context) {
        this(context, null);
    }

    public ResponseMeView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ResponseMeView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        View root = LayoutInflater.from(context).inflate(R.layout.zan_me_item_layout, this);
        contentColor = getResources().getColor(R.color.reshape_red);
        userLogoImage = root.findViewById(R.id.comment_user_icon);
        userName = root.findViewById(R.id.comment_name);
        userDesc = root.findViewById(R.id.comment_name_desc);
        responseContent = root.findViewById(R.id.zan_your_comment);
        commentTime = root.findViewById(R.id.comment_time);
        titleContainer = root.findViewById(R.id.comment_content);
        commentFormat = getResources().getString(R.string.xx_response_you_in);
    }

    public void setData(final ResponseMeBean data) {
        commentData = data;
        if (commentData != null) {
            Glide.with(getContext()).load(data.getUserpic())
                    .error(R.mipmap.user_default_icon).into(userLogoImage);
            userName.setText(data.getUsername());
            userDesc.setText("dddd");
            commentTime.setText(data.getAdd_time());
            responseContent.setText(data.getContent());
            titleContainer.setMovementMethod(LinkMovementMethod.getInstance());
            titleContainer.setHighlightColor(Color.TRANSPARENT);
            String content = String.format(commentFormat, data.getTitle());
            SpannableString spanString = new SpannableString (content);
            int start = 1;
            int nameEnd = start + data.getTitle().length();
            spanString.setSpan(new ClickableSpan() {

                @Override
                public void updateDrawState (TextPaint ds) {
                    ds.setUnderlineText (false);
                    ds.setColor(contentColor);
                }

                @Override
                public void onClick (final View widget) {
                    if (mRef != null && mRef.get() != null) {
                        mRef.get().onTitleClick(data);
                    }
                }
            }, start, nameEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            titleContainer.setText(spanString);
        }
    }

    private SoftReference<OnCommentClickListener> mRef;
    public void setCommentClickListener(OnCommentClickListener listener) {
        if (listener != null) {
            mRef = new SoftReference<OnCommentClickListener>(listener);
        }
    }
    public interface OnCommentClickListener {
        void onTitleClick(ResponseMeBean data);
    }
}
