package com.baogetv.app;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.lang.ref.SoftReference;

/**
 * Created by chalilayang on 2017/11/20.
 */

public abstract class ItemViewHolder<T>
        extends RecyclerView.ViewHolder implements View.OnClickListener {
    protected T mData;
    private int position;
    private View root;
    private SoftReference<ItemClickListener> mRef;
    public ItemViewHolder(View itemView) {
        super(itemView);
        root = itemView;
        if (root != null) {
            root.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View view) {
        if (mRef != null && mRef.get() != null) {
            mRef.get().onItemClick(mData, position);
        }
    }

    public void setData(T data, int pos) {
        mData = data;
        position = pos;
    }
    public abstract void bindData(T data);

    public void setItemClick(ItemClickListener listener) {
        if (listener != null) {
            mRef = new SoftReference<ItemClickListener>(listener);
        }
    }

    public interface ItemClickListener<T> {
        void onItemClick(T data, int position);
    }
}
