package com.baogetv.app.model.videodetail.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baogetv.app.R;
import com.baogetv.app.customview.LogoCircleImageView;
import com.baogetv.app.model.videodetail.entity.CommentData;
import com.baogetv.app.model.videodetail.entity.ReplyData;
import com.baogetv.app.util.TimeUtil;
import com.bumptech.glide.Glide;
import com.chalilayang.scaleview.ScaleFrameLayout;
import com.chalilayang.scaleview.ScaleTextView;

import org.w3c.dom.Text;

import java.lang.ref.SoftReference;
import java.util.List;

/**
 * Created by chalilayang on 2017/11/21.
 */

public class CommentView extends ScaleFrameLayout
        implements ReplyView.OnReplyClickListener {

    private static final String TAG = "CommentView";
    private CommentData commentData;

    private LogoCircleImageView userLogoImage;
    private TextView userName;
    private TextView userDesc;
    private TextView commentTime;
    private TextView commentContent;
    private LinearLayout replyContainer;
    private TextView moreReplyBtn;

    private View reportBtn;
    private TextView commentCount;
    private View zan;

    private String moreReplyFormat;

    public CommentView(Context context) {
        this(context, null);
    }

    public CommentView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CommentView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        moreReplyFormat = getResources().getString(R.string.more_reply_format);
        View root = LayoutInflater.from(context).inflate(R.layout.comment_layout, this);
        userLogoImage = root.findViewById(R.id.comment_user_icon);
        userName = root.findViewById(R.id.comment_name);
        userDesc = root.findViewById(R.id.comment_name_desc);
        commentTime = root.findViewById(R.id.comment_time);
        commentContent = root.findViewById(R.id.comment_content);
        replyContainer = root.findViewById(R.id.comment_reply_container);
        moreReplyBtn = new ScaleTextView(context);
        moreReplyBtn.setTextSize(TypedValue.COMPLEX_UNIT_PX, 28);
        moreReplyBtn.setTextColor(getResources().getColor(R.color.video_detail_name_link));

        reportBtn = root.findViewById(R.id.report_comment);
        commentCount = root.findViewById(R.id.comment_icon);
        zan = root.findViewById(R.id.comment_thumb_up);
    }

    public void setCommentData(final CommentData data) {
        commentData = data;
        replyContainer.setVisibility(GONE);
        replyContainer.removeAllViews();
        reportBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mRef != null && mRef.get() != null) {
                    mRef.get().onJuBaoClick(data);
                }
            }
        });
        if (data.getReplyList() != null) {
            commentCount.setText(data.getReplyList().size()+"");
        } else {
            commentCount.setText(0+"");
        }
        zan.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mRef != null && mRef.get() != null) {
                    mRef.get().onThumbUp(data);
                }
            }
        });
        if (commentData != null) {
            Glide.with(getContext()).load(data.getOwner()
                            .getIconUrl()).error(R.mipmap.user_default_icon).into(userLogoImage);
            userLogoImage.setLogo(data.getOwner().getGrage());
            userLogoImage.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mRef != null && mRef.get() != null) {
                        mRef.get().onIconClick(commentData);
                    }
                }
            });
            userName.setText(data.getOwner().getNickName());
            userDesc.setText(data.getOwner().getDesc());
            commentContent.setText(data.getContent());
            commentTime.setText(TimeUtil.getTimeStateNew(String.valueOf(data.getTime())));
            List<ReplyData> replyDataList = commentData.getReplyList();
            if (replyDataList != null) {
                int count = replyDataList.size();
                if (count > 0) {
                    replyContainer.setVisibility(VISIBLE);
                    for (int index = 0; index < count; index ++) {
                        if (index == 3) {
                            moreReplyBtn.setText(String.format(moreReplyFormat, count));
                            moreReplyBtn.setOnClickListener(new OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    if (mRef != null && mRef.get() != null) {
                                        mRef.get().onMoreComment(commentData);
                                    }
                                }
                            });
                            replyContainer.addView(moreReplyBtn, index);
                            break;
                        }
                        ReplyData replyData = replyDataList.get(index);
                        ReplyView replyView = new ReplyView(getContext());
                        replyView.setReplyClickListener(this);
                        replyView.setReply(replyData);
                        replyContainer.addView(replyView, index);
                    }
                }
            }
        }
    }

    @Override
    public void onReplyerClick(ReplyData data) {
        Log.i(TAG, "onReplyerClick: ");
        if (mRef != null && mRef.get() != null) {
            mRef.get().onReplyerClick(data);
        }
    }

    @Override
    public void onReplyToClick(ReplyData data) {
        Log.i(TAG, "onReplyToClick: ");
        if (mRef != null && mRef.get() != null) {
            mRef.get().onReplyToClick(data);
        }
    }

    @Override
    public void onReplyClick(ReplyData data) {
        Log.i(TAG, "onReplyClick: ");
        if (mRef != null && mRef.get() != null) {
            mRef.get().onReplyClick(data);
        }
    }

    private SoftReference<OnCommentListener> mRef;
    public void setOnCommentListener(OnCommentListener listener) {
        if (listener != null) {
            mRef = new SoftReference<OnCommentListener>(listener);
        }
    }

    public interface OnCommentListener extends ReplyView.OnReplyClickListener {
        void onIconClick(CommentData data);
        void onThumbUp(CommentData data);
        void onJuBaoClick(CommentData data);
        void onMoreComment(CommentData data);
    }
}
