package com.baogetv.app.model.usercenter.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baogetv.app.BaseItemAdapter;
import com.baogetv.app.ItemViewHolder;
import com.baogetv.app.R;
import com.baogetv.app.bean.ZanMeBean;
import com.baogetv.app.model.videodetail.customview.ThumbMeView;

public class ThumbUpListAdapter
        extends BaseItemAdapter<ZanMeBean, ThumbUpListAdapter.ViewHolder> {
    public ThumbUpListAdapter(Context context) {
        super(context);
    }
    @Override
    public ViewHolder createItemViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.zan_me_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    public class ViewHolder extends ItemViewHolder<ZanMeBean> {

        public final ThumbMeView thumbMeView;
        @Override
        public void bindData(ZanMeBean data, int pos) {
            thumbMeView.setData(data);
        }

        public ViewHolder(View view) {
            super(view);
            thumbMeView = view.findViewById(R.id.zan_me_view);
        }

        @Override
        public void onClick(View view) {
            super.onClick(view);
        }
    }
}
