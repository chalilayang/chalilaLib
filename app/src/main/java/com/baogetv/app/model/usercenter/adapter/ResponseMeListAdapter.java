package com.baogetv.app.model.usercenter.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.baogetv.app.BaseItemAdapter;
import com.baogetv.app.ItemViewHolder;
import com.baogetv.app.R;
import com.baogetv.app.bean.ResponseMeBean;
import com.baogetv.app.model.usercenter.customview.ResponseMeView;
import com.chalilayang.scaleview.ScaleCalculator;

import java.lang.ref.SoftReference;

public class ResponseMeListAdapter
        extends BaseItemAdapter<ResponseMeBean, ResponseMeListAdapter.ViewHolder> implements ResponseMeView.OnCommentClickListener{
    public ResponseMeListAdapter(Context context) {
        super(context);
        setNeedLoadMore(true);
    }
    @Override
    public ViewHolder createItemViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.response_me_item, parent, false);
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

    private SoftReference<ResponseMeView.OnCommentClickListener> mRef;
    public void setCommentClickListener(ResponseMeView.OnCommentClickListener listener) {
        if (listener != null) {
            mRef = new SoftReference<ResponseMeView.OnCommentClickListener>(listener);
        }
    }

    @Override
    public void onTitleClick(ResponseMeBean data) {
        if (mRef != null && mRef.get() != null) {
            mRef.get().onTitleClick(data);
        }
    }

    public class ViewHolder extends ItemViewHolder<ResponseMeBean> {

        public final ResponseMeView thumbMeView;
        public final TextView loadMoreTip;
        @Override
        public void bindData(ResponseMeBean data, int pos) {
            if (loadMoreTip != null) {
                loadMoreTip.setText(hasMoreData?loadingMore : noMoreData);
            } else {
                thumbMeView.setData(data);
                thumbMeView.setCommentClickListener(ResponseMeListAdapter.this);
            }
        }

        public ViewHolder(View view) {
            super(view);
            loadMoreTip = view.findViewById(R.id.load_more_tip);
            thumbMeView = view.findViewById(R.id.zan_me_view);
        }
    }
}
