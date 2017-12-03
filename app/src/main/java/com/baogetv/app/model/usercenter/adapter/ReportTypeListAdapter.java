package com.baogetv.app.model.usercenter.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.baogetv.app.BaseItemAdapter;
import com.baogetv.app.ItemViewHolder;
import com.baogetv.app.R;
import com.baogetv.app.bean.CollectBean;
import com.baogetv.app.model.usercenter.entity.ReportTypeData;
import com.bumptech.glide.Glide;
import com.chalilayang.scaleview.ScaleCalculator;

import java.lang.ref.SoftReference;

public class ReportTypeListAdapter
        extends BaseItemAdapter<ReportTypeData, ReportTypeListAdapter.ViewHolder> {

    public ReportTypeListAdapter(Context context) {
        super(context);
    }

    @Override
    public ViewHolder createItemViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.report_type_item, parent, false);
        view.getLayoutParams().height = ScaleCalculator.getInstance(mContext).scaleHeight(100);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    public class ViewHolder extends ItemViewHolder<ReportTypeData> {
        public final TextView title;
        private final View icon;
        @Override
        public void bindData(ReportTypeData data, int pos) {
            title.setText(data.bean.getName());
            icon.setVisibility(data.selected?View.VISIBLE:View.GONE);
        }

        public ViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.video_title);
            icon = view.findViewById(R.id.report_icon);
        }
    }
}
