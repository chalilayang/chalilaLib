package com.baogetv.app.model.usercenter.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.baogetv.app.BaseItemAdapter;
import com.baogetv.app.ItemViewHolder;
import com.baogetv.app.R;
import com.baogetv.app.bean.ZanMeBean;
import com.baogetv.app.model.videodetail.customview.ThumbMeView;
import com.chalilayang.scaleview.ScaleCalculator;

public class ThumbUpListAdapter
        extends BaseItemAdapter<ZanMeBean, ThumbUpListAdapter.ViewHolder> {
    public ThumbUpListAdapter(Context context) {
        super(context);
        setNeedLoadMore(true);
    }
    @Override
    public ViewHolder createItemViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.zan_me_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }
    @Override
    public ViewHolder createMoreViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.footer, parent, false);
        view.getLayoutParams().height = ScaleCalculator.getInstance(mContext).scaleWidth(60);
        return new ViewHolder(view);
    }
    public class ViewHolder extends ItemViewHolder<ZanMeBean> {

        public final ThumbMeView thumbMeView;
        public final TextView loadMoreTip;
        @Override
        public void bindData(ZanMeBean data, int pos) {
            if (loadMoreTip != null) {
                loadMoreTip.setText(hasMoreData?loadingMore : noMoreData);
            } else {
                thumbMeView.setData(data);
            }
        }

        public ViewHolder(View view) {
            super(view);
            loadMoreTip = view.findViewById(R.id.load_more_tip);
            thumbMeView = view.findViewById(R.id.zan_me_view);
        }

        @Override
        public void onClick(View view) {
            super.onClick(view);
        }
    }
}
