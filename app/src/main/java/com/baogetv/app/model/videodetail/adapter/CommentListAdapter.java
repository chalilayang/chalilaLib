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
import com.baogetv.app.model.videodetail.customview.ReplyView;
import com.baogetv.app.model.videodetail.entity.CommentData;
import com.baogetv.app.model.videodetail.entity.ReplyData;
import com.chalilayang.scaleview.ScaleCalculator;

import java.lang.ref.SoftReference;
import java.util.List;

public class CommentListAdapter
        extends BaseItemAdapter<CommentData, CommentListAdapter.ViewHolder> implements CommentView.OnCommentListener {

    public CommentListAdapter(Context context) {
        super(context);
    }

    private static final int TYPE_NORMAL = 0;
    private static final int TYPE_FOOT = 1;
    @Override
    public ViewHolder createItemViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_NORMAL) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.comment_item, parent, false);
            return new ViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.footer, parent, false);
            view.getLayoutParams().height = ScaleCalculator.getInstance(mContext).scaleWidth(120);
            return new ViewHolder(view);
        }
    }

    private SoftReference<CommentView.OnCommentListener> mRef;
    public void setOnCommentListener(CommentView.OnCommentListener listener) {
        if (listener != null) {
            mRef = new SoftReference<CommentView.OnCommentListener>(listener);
        }
    }
    public class ViewHolder extends ItemViewHolder<CommentData> {
        public final CommentView mCommentView;

        public ViewHolder(View view) {
            super(view);
            mCommentView = (CommentView) view.findViewById(R.id.comment_view);
        }

        @Override
        public void bindData(CommentData data, int pos) {
            if (mCommentView != null) {
                mCommentView.setCommentData(data);
                mCommentView.setOnCommentListener(CommentListAdapter.this);
            }
        }
    }

    @Override
    public void onIconClick(CommentData data) {
        if (mRef != null && mRef.get() != null) {
            mRef.get().onIconClick(data);
        }
    }

    @Override
    public void onThumbUp(CommentData data) {
        if (mRef != null && mRef.get() != null) {
            mRef.get().onThumbUp(data);
        }
    }

    @Override
    public void onJuBaoClick(CommentData data) {
        if (mRef != null && mRef.get() != null) {
            mRef.get().onJuBaoClick(data);
        }
    }

    @Override
    public void onReplyerClick(ReplyData data) {
        if (mRef != null && mRef.get() != null) {
            mRef.get().onReplyerClick(data);
        }
    }

    @Override
    public void onReplyToClick(ReplyData data) {
        if (mRef != null && mRef.get() != null) {
            mRef.get().onReplyToClick(data);
        }
    }

    @Override
    public void onReplyClick(ReplyData data) {
        if (mRef != null && mRef.get() != null) {
            mRef.get().onReplyClick(data);
        }
    }

    @Override
    public void onMoreComment(CommentData data) {
        if (mRef != null && mRef.get() != null) {
            mRef.get().onMoreComment(data);
        }
    }

    @Override
    public void onCommentClick(CommentData data) {
        if (mRef != null && mRef.get() != null) {
            mRef.get().onCommentClick(data);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position < getItemCount() - 1) {
            return TYPE_NORMAL;
        } else {
            return TYPE_FOOT;
        }
    }

    public int getDataCount() {
        return mValues.size();
    }

    @Override
    public int getItemCount() {
        return super.getItemCount() + 1;
    }
}
