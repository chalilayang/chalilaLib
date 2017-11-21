package com.baogetv.app.model.videodetail.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baogetv.app.BaseItemAdapter;
import com.baogetv.app.ItemViewHolder;
import com.baogetv.app.R;
import com.baogetv.app.customview.LogoImageView;
import com.baogetv.app.model.videodetail.customview.ReplyView;
import com.baogetv.app.model.videodetail.entity.CommentData;

import java.util.List;

public class CommentListAdapter extends BaseItemAdapter<CommentData, CommentListAdapter.ViewHolder> {

    public CommentListAdapter(List<CommentData> items) {
        super(items);
    }

    @Override
    public ViewHolder createItemViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.comment_list_item, parent, false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends ItemViewHolder<CommentData> {
        public final ReplyView mReplyView;

        public ViewHolder(View view) {
            super(view);
            mReplyView = (ReplyView) view.findViewById(R.id.replay_view);
        }

        @Override
        public void bindData(CommentData data, int pos) {
            mReplyView.setComment(data);
        }
    }
}
