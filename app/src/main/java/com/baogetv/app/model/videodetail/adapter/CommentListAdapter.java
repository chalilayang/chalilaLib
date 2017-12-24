package com.baogetv.app.model.videodetail.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.baogetv.app.BaseItemAdapter;
import com.baogetv.app.ItemViewHolder;
import com.baogetv.app.R;
import com.baogetv.app.model.videodetail.customview.CommentView;
import com.baogetv.app.model.videodetail.entity.CommentData;
import com.baogetv.app.model.videodetail.entity.ReplyData;
import com.chalilayang.scaleview.ScaleCalculator;

import java.lang.ref.SoftReference;

public class CommentListAdapter
        extends BaseItemAdapter<CommentData, CommentListAdapter.ViewHolder> implements CommentView.OnCommentListener {

    public CommentListAdapter(Context context) {
        super(context);
        setNeedLoadMore(true);
    }

    @Override
    public ViewHolder createItemViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.comment_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public ViewHolder createMoreViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.footer, parent, false);
        view.getLayoutParams().height = ScaleCalculator.getInstance(mContext).scaleWidth(360);
        return new ViewHolder(view);
    }

    private SoftReference<CommentView.OnCommentListener> mRef;
    public void setOnCommentListener(CommentView.OnCommentListener listener) {
        if (listener != null) {
            mRef = new SoftReference<CommentView.OnCommentListener>(listener);
        }
    }
    public class ViewHolder extends ItemViewHolder<CommentData> {
        public final CommentView mCommentView;

        public final TextView loadMoreTip;

        public ViewHolder(View view) {
            super(view);
            loadMoreTip = view.findViewById(R.id.load_more_tip);
            mCommentView = (CommentView) view.findViewById(R.id.comment_view);
        }

        @Override
        public void bindData(CommentData data, int pos) {
            if (loadMoreTip != null) {
                loadMoreTip.setText(hasMoreData?loadingMore : noMoreData);
            } else {
                if (mCommentView != null) {
                    mCommentView.setCommentData(data, pos);
                    mCommentView.setOnCommentListener(CommentListAdapter.this);
                }
            }
        }
    }

    @Override
    public void onIconClick(CommentData data, int commentIndex) {
        if (mRef != null && mRef.get() != null) {
            mRef.get().onIconClick(data, commentIndex);
        }
    }

    @Override
    public void onThumbUp(CommentData data, int commentIndex) {
        if (mRef != null && mRef.get() != null) {
            mRef.get().onThumbUp(data, commentIndex);
        }
    }

    @Override
    public void onJuBaoClick(CommentData data, int commentIndex) {
        if (mRef != null && mRef.get() != null) {
            mRef.get().onJuBaoClick(data, commentIndex);
        }
    }

    @Override
    public void onReplyNameClick(ReplyData data, int commentIndex) {
        if (mRef != null && mRef.get() != null) {
            mRef.get().onReplyNameClick(data, commentIndex);
        }
    }

    @Override
    public void onReplyToClick(ReplyData data, int commentIndex) {
        if (mRef != null && mRef.get() != null) {
            mRef.get().onReplyToClick(data, commentIndex);
        }
    }

    @Override
    public void onReplyClick(ReplyData data, int commentIndex) {
        if (mRef != null && mRef.get() != null) {
            mRef.get().onReplyClick(data, commentIndex);
        }
    }

    @Override
    public void onMoreComment(CommentData data, int commentIndex) {
        if (mRef != null && mRef.get() != null) {
            mRef.get().onMoreComment(data, commentIndex);
        }
    }

    @Override
    public void onCommentClick(CommentData data, int commentIndex) {
        if (mRef != null && mRef.get() != null) {
            mRef.get().onCommentClick(data, commentIndex);
        }
    }
}
