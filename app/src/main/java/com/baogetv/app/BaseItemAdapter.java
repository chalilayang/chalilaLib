package com.baogetv.app;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseItemAdapter<T, D extends ItemViewHolder<T>>
        extends RecyclerView.Adapter<D> implements ItemViewHolder.ItemClickListener<T> {

    protected final List<T> mValues;
    protected Context mContext;
    protected SoftReference<ItemViewHolder.ItemClickListener> mRef;
    public void setItemClick(ItemViewHolder.ItemClickListener listener) {
        if (listener != null) {
            mRef = new SoftReference<ItemViewHolder.ItemClickListener>(listener);
        }
    }
    public BaseItemAdapter(Context context, List<T> items) {
        mContext = context;
        mValues = new ArrayList<>();
        update(items);
    }

    public BaseItemAdapter(Context context) {
        mContext = context;
        mValues = new ArrayList<>();
    }

    public void update(List<T> list) {
        mValues.clear();
        if (list != null && list.size() > 0) {
            mValues.addAll(list);
        }
        notifyDataSetChanged();
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
        if (mValues != null && position < mValues.size()) {
            holder.setData(mValues.get(position), position);
        }
    }

    @Override
    public void onItemClick(T data, int position) {
        if (mRef != null && mRef.get() != null) {
            mRef.get().onItemClick(data, position);
        }
    }

    public List<T> getDataList() {
        List<T> list = new ArrayList<>();
        if (mValues != null) {
            list.addAll(mValues);
        }
        return list;
    }
    @Override
    public int getItemCount() {
        return mValues.size();
    }
}
