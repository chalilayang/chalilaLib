package com.baogetv.app;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.lang.ref.SoftReference;
import java.util.List;

public abstract class BaseItemAdapter<T, D extends ItemViewHolder<T>>
        extends RecyclerView.Adapter<D> implements ItemViewHolder.ItemClickListener<T> {

    private final List<T> mValues;
    private SoftReference<ItemViewHolder.ItemClickListener> mRef;
    public void setItemClick(ItemViewHolder.ItemClickListener listener) {
        if (listener != null) {
            mRef = new SoftReference<ItemViewHolder.ItemClickListener>(listener);
        }
    }
    public BaseItemAdapter(List<T> items) {
        mValues = items;
    }

    @Override
    public D onCreateViewHolder(ViewGroup parent, int viewType) {
        D holder = createItemViewHolder(parent, viewType);
        holder.setItemClick(this);
        return holder;
    }

    public abstract D createItemViewHolder(ViewGroup parent, int viewType);

    @Override
    public void onBindViewHolder(final D holder, int position) {
        holder.setData(mValues.get(position), position);
    }

    @Override
    public void onItemClick(T data, int position) {
        if (mRef != null && mRef.get() != null) {
            mRef.get().onItemClick(data, position);
        }
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }
}
