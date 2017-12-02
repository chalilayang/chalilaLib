package com.baogetv.app.model.usercenter.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baogetv.app.BaseItemAdapter;
import com.baogetv.app.ItemViewHolder;
import com.baogetv.app.R;
import com.baogetv.app.bean.ResponseMeBean;
import com.baogetv.app.model.usercenter.customview.ResponseMeView;

import java.lang.ref.SoftReference;

public class ResponseMeListAdapter
        extends BaseItemAdapter<ResponseMeBean, ResponseMeListAdapter.ViewHolder> implements ResponseMeView.OnCommentClickListener{
    public ResponseMeListAdapter(Context context) {
        super(context);
    }
    @Override
    public ViewHolder createItemViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.response_me_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
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
        @Override
        public void bindData(ResponseMeBean data, int pos) {
            thumbMeView.setData(data);
            thumbMeView.setCommentClickListener(ResponseMeListAdapter.this);
        }

        public ViewHolder(View view) {
            super(view);
            thumbMeView = view.findViewById(R.id.zan_me_view);
        }
    }
}
