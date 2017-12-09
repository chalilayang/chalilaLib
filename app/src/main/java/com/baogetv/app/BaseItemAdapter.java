package com.baogetv.app;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chalilayang.scaleview.ScaleCalculator;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseItemAdapter<T, D extends ItemViewHolder<T>>
        extends RecyclerView.Adapter<D> implements ItemViewHolder.ItemClickListener<T> {

    protected static final int TYPE_LOAD_MORE = 112;
    protected static final int TYPE_NORMAL = 0;
    protected final List<T> mValues;
    protected Context mContext;
    private boolean needLoadMore;
    protected String loadingMore;
    protected String noMoreData;
    protected SoftReference<ItemViewHolder.ItemClickListener> mRef;
    protected boolean hasMoreData;
    public void setItemClick(ItemViewHolder.ItemClickListener listener) {
        if (listener != null) {
            mRef = new SoftReference<ItemViewHolder.ItemClickListener>(listener);
        }
    }

    public void setHasMoreData(boolean hasMoreData) {
        this.hasMoreData = hasMoreData;
    }

    public void setNeedLoadMore(boolean need) {
        needLoadMore = need;
    }

    public BaseItemAdapter(Context context, List<T> items) {
        this(context);
        update(items);
    }

    public BaseItemAdapter(Context context) {
        mContext = context;
        mValues = new ArrayList<>();
        loadingMore = mContext.getString(R.string.loading_more_data);
        noMoreData = mContext.getString(R.string.no_more_data);
        hasMoreData = true;
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
        if (viewType == TYPE_LOAD_MORE) {
            D holder = createMoreViewHolder(parent, viewType);
            return holder;
        } else {
            D holder = createItemViewHolder(parent, viewType);
            holder.setItemClick(this);
            return holder;
        }
    }

    public abstract D createItemViewHolder(ViewGroup parent, int viewType);
    public D createMoreViewHolder(ViewGroup parent, int viewType) {
        return createItemViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(final D holder, int position) {
        if (mValues != null) {
            if (position < mValues.size()) {
                holder.setData(mValues.get(position), position);
            } else {
                holder.setData(null, position);
            }
        }
    }

    @Override
    public void onItemClick(T data, int position) {
        if (mRef != null && mRef.get() != null && position < getDataCount()) {
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

    public int getDataCount() {
        return mValues.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (needLoadMore) {
            if (position < getDataCount()) {
                return TYPE_NORMAL;
            } else {
                return TYPE_LOAD_MORE;
            }
        } else {
            return super.getItemViewType(position);
        }
    }

    @Override
    public int getItemCount() {
        if (needLoadMore) {
            return mValues.size() + 1;
        } else {
            return mValues.size();
        }
    }
}
