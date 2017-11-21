package com.baogetv.app.model.videodetail.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baogetv.app.BaseItemAdapter;
import com.baogetv.app.ItemViewHolder;
import com.baogetv.app.R;
import com.baogetv.app.model.videodetail.customview.CommentView;
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
                .inflate(R.layout.comment_item, parent, false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends ItemViewHolder<CommentData> {
        public final CommentView mCommentView;

        public ViewHolder(View view) {
            super(view);
            mCommentView = (CommentView) view.findViewById(R.id.comment_view);
        }

        @Override
        public void bindData(CommentData data, int pos) {
            mCommentView.setCommentData(data);
        }
    }
}
