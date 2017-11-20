package com.baogetv.app.model.videodetail.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.baogetv.app.BaseItemAdapter;
import com.baogetv.app.ItemViewHolder;
import com.baogetv.app.R;

import java.util.List;

public class VideoListAdapter extends BaseItemAdapter<String, VideoListAdapter.ViewHolder> {

    public VideoListAdapter(List<String> items) {
        super(items);
    }

    @Override
    public ViewHolder createItemViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item, parent, false);
        ViewGroup.LayoutParams vlp = view.getLayoutParams();
        vlp.height = 400;
        return new ViewHolder(view);
    }

    public class ViewHolder extends ItemViewHolder<String> {
        public final ImageView mContentView;

        public ViewHolder(View view) {
            super(view);
            mContentView = (ImageView) view.findViewById(R.id.img);
        }

        @Override
        public void bindData(String data) {

        }
    }
}
