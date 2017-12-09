package com.baogetv.app;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by chalilayang on 2017/12/9.
 */

public abstract class OnLoadMoreListener extends RecyclerView.OnScrollListener {

    private final LinearLayoutManager layoutManager;
    private final DataLoadingSubject dataLoading;

    public OnLoadMoreListener(@NonNull LinearLayoutManager layoutManager,
                                  @NonNull DataLoadingSubject dataLoading) {
        this.layoutManager = layoutManager;
        this.dataLoading = dataLoading;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        if (dy < 0 || dataLoading.isLoading() || !dataLoading.needLoadMore()) {
            return;
        }
        final int totalItemCount = layoutManager.getItemCount();
        final int lastVisibleItem = layoutManager.findLastCompletelyVisibleItemPosition();
        if (lastVisibleItem >= (totalItemCount - 1)) {
            onLoadMore(totalItemCount - 1);
        }
    }

    public abstract void onLoadMore(int totalItemCount);

    public interface DataLoadingSubject {
        boolean isLoading();
        boolean needLoadMore();
    }
}
