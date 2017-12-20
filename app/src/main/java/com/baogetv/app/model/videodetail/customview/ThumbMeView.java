package com.baogetv.app.model.videodetail.customview;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baogetv.app.R;
import com.baogetv.app.bean.ZanMeBean;
import com.baogetv.app.customview.LogoCircleImageView;
import com.baogetv.app.model.videodetail.entity.CommentData;
import com.baogetv.app.model.videodetail.entity.ReplyData;
import com.baogetv.app.util.TimeUtil;
import com.bumptech.glide.Glide;
import com.chalilayang.scaleview.ScaleFrameLayout;
import com.chalilayang.scaleview.ScaleTextView;

import java.util.List;

/**
 * Created by chalilayang on 2017/11/21.
 */

public class ThumbMeView extends ScaleFrameLayout {

    private ZanMeBean commentData;

    private LogoCircleImageView userLogoImage;
    private TextView userName;
    private TextView userDesc;
    private TextView commentTime;
    private TextView commentContent;

    public ThumbMeView(Context context) {
        this(context, null);
    }

    public ThumbMeView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ThumbMeView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        View root = LayoutInflater.from(context).inflate(R.layout.zan_me_item_layout, this);
        userLogoImage = root.findViewById(R.id.comment_user_icon);
        userName = root.findViewById(R.id.comment_name);
        userDesc = root.findViewById(R.id.comment_name_desc);
        commentTime = root.findViewById(R.id.comment_time);
        commentContent = root.findViewById(R.id.comment_content);
    }

    public void setData(ZanMeBean data) {
        commentData = data;
        if (commentData != null) {
            Glide.with(getContext()).load(data.getUserpic_url())
                    .error(R.mipmap.user_default_icon).into(userLogoImage);
            userLogoImage.setLogo(data.getGrade(), data.getLevel_id());
            userName.setText(data.getUsername());
            if (TextUtils.isEmpty(data.getLevel_medal())) {
                userDesc.setText(data.getLevel_medal());
            } else {
                userDesc.setVisibility(INVISIBLE);
            }
            commentContent.setText(data.getContent());
            commentTime.setText(TimeUtil.getTimeStateNew(data.getAdd_time()));
        }
    }
}
