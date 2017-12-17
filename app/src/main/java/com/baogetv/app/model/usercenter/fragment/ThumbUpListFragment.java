package com.baogetv.app.model.usercenter.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baogetv.app.BaseFragment;
import com.baogetv.app.BaseItemAdapter;
import com.baogetv.app.ItemViewHolder;
import com.baogetv.app.OnLoadMoreListener;
import com.baogetv.app.R;
import com.baogetv.app.apiinterface.UserApiService;
import com.baogetv.app.bean.CollectBean;
import com.baogetv.app.bean.ResponseBean;
import com.baogetv.app.bean.ResponseMeBean;
import com.baogetv.app.bean.ZanMeBean;
import com.baogetv.app.model.usercenter.LoginManager;
import com.baogetv.app.model.usercenter.adapter.ThumbUpListAdapter;
import com.baogetv.app.net.CustomCallBack;
import com.baogetv.app.net.RetrofitManager;
import com.chalilayang.customview.RecyclerViewDivider;
import com.chalilayang.scaleview.ScaleCalculator;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;


public class ThumbUpListFragment extends BaseFragment
        implements SwipeRefreshLayout.OnRefreshListener,
        ItemViewHolder.ItemClickListener<ZanMeBean>, OnLoadMoreListener.DataLoadingSubject {

    private static final String TAG = "ThumbUpListFragment";
    private static final int LOAD_PAGE_SIZE = 20;
    private SwipeRefreshLayout refreshLayout;
    private View contentView;
    private LinearLayoutManager layoutManager;
    private ThumbUpListAdapter recyclerViewAdapter;
    private OnLoadMoreListener onLoadMoreListener;
    private RecyclerView recyclerView;
    private List<ZanMeBean> historyBeanList;

    public static ThumbUpListFragment newInstance() {
        ThumbUpListFragment fragment = new ThumbUpListFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        historyBeanList = new ArrayList<>();
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerViewAdapter = new ThumbUpListAdapter(getActivity());
        recyclerViewAdapter.setItemClick(this);
        onLoadMoreListener = new OnLoadMoreListener(layoutManager, this) {
            @Override
            public void onLoadMore(int totalItemCount) {
                Log.i(TAG, "onLoadMore: " + totalItemCount);
                int more = historyBeanList.size() % LOAD_PAGE_SIZE;
                int pageNum = historyBeanList.size() / LOAD_PAGE_SIZE + 1;
                if (more != 0) {
                    recyclerViewAdapter.setHasMoreData(false);
                } else {
                    getList(pageNum, LOAD_PAGE_SIZE);
                }
            }
        };
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (contentView == null) {
            View view = inflater.inflate(R.layout.fragment_comment_list, container, false);
            RecyclerViewDivider divider
                    = new RecyclerViewDivider(getActivity(),
                    LinearLayoutManager.HORIZONTAL, 1,
                    getResources().getColor(R.color.channel_list_divider));
            int margin_30px = ScaleCalculator.getInstance(getActivity()).scaleWidth(30);
            divider.setMargin(margin_30px);
            refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.srl);
            recyclerView = refreshLayout.findViewById(R.id.list);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.addItemDecoration(divider);
            recyclerView.addOnScrollListener(onLoadMoreListener);
            recyclerView.setAdapter(recyclerViewAdapter);
            refreshLayout.setOnRefreshListener(this);
            contentView = view;
        }
        return contentView;
    }

    @Override
    public boolean isLoading() {
        return isLoadingData;
    }

    private boolean isLoadingData;
    @Override
    public boolean needLoadMore() {
        return true;
    }

    private void getList(final int pageNum, final int pageSize) {
        UserApiService userApiService
                = RetrofitManager.getInstance().createReq(UserApiService.class);
        String token = LoginManager.getUserToken(mActivity);
        Call<ResponseBean<List<ZanMeBean>>> call = userApiService.getZanMeList(
                token, String.valueOf(pageNum), String.valueOf(pageSize));
        if (call != null) {
            refreshLayout.setRefreshing(true);
            isLoadingData = true;
            call.enqueue(new CustomCallBack<List<ZanMeBean>>() {
                @Override
                public void onSuccess(List<ZanMeBean> data, String msg, int state) {
                    if (pageNum <= 1) {
                        historyBeanList.clear();
                    }
                    if (data != null) {
                        if (data.size() <= 0) {
                            recyclerViewAdapter.setHasMoreData(false);
                        } else {
                            for (int index = 0, count = data.size(); index < count; index ++) {
                                ZanMeBean bean = data.get(index);
                                historyBeanList.add(bean);
                            }
                        }
                    }
                    recyclerViewAdapter.update(historyBeanList);
                    refreshLayout.setRefreshing(false);
                    isLoadingData = false;
                }

                @Override
                public void onFailed(String error, int state) {
                    showShortToast(error);
                    refreshLayout.setRefreshing(false);
                    isLoadingData = false;
                }
            });
        }
    }

    @Override
    public void onRefresh() {
        getList(0, LOAD_PAGE_SIZE);
    }

    @Override
    public void onItemClick(ZanMeBean data, int position) {

    }
}
