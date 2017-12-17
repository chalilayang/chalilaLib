package com.baogetv.app.model.usercenter.fragment;

import android.content.Intent;
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
import com.baogetv.app.bean.ResponseBean;
import com.baogetv.app.bean.ResponseMeBean;
import com.baogetv.app.bean.SystemInfoBean;
import com.baogetv.app.bean.ZanMeBean;
import com.baogetv.app.model.usercenter.LoginManager;
import com.baogetv.app.model.usercenter.activity.MemberDetailActivity;
import com.baogetv.app.model.usercenter.adapter.ResponseMeListAdapter;
import com.baogetv.app.model.usercenter.adapter.ThumbUpListAdapter;
import com.baogetv.app.model.usercenter.customview.ResponseMeView;
import com.baogetv.app.net.CustomCallBack;
import com.baogetv.app.net.RetrofitManager;
import com.chalilayang.customview.RecyclerViewDivider;
import com.chalilayang.scaleview.ScaleCalculator;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

import static com.baogetv.app.model.usercenter.activity.MemberDetailActivity.KEY_MEMBER_ID;


public class ResponseMeListFragment extends BaseFragment
        implements SwipeRefreshLayout.OnRefreshListener,
        ItemViewHolder.ItemClickListener<ResponseMeBean>,
        ResponseMeView.OnCommentClickListener,
        OnLoadMoreListener.DataLoadingSubject  {

    private static final String TAG = "ResponseMeListFragment";
    private static final int LOAD_PAGE_SIZE = 20;
    private List<ResponseMeBean> commentDataList;
    private SwipeRefreshLayout refreshLayout;
    private View contentView;
    private LinearLayoutManager layoutManager;
    private ResponseMeListAdapter recyclerViewAdapter;
    private OnLoadMoreListener onLoadMoreListener;
    private RecyclerView recyclerView;
    private List<ResponseMeBean> historyBeanList;

    public static ResponseMeListFragment newInstance() {
        ResponseMeListFragment fragment = new ResponseMeListFragment();
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        historyBeanList = new ArrayList<>();
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerViewAdapter = new ResponseMeListAdapter(getActivity());
        recyclerViewAdapter.setItemClick(this);
        recyclerViewAdapter.setCommentClickListener(this);
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

    /**
     * video_id : 1
     * pic_url : http://localhost/test2/Uploads/Picture/2016-12-05/58451c922375d.png
     * title : 测试
     * type_name : 频道1
     * username : 15913196454
     * userpic : http://localhost/test2/Uploads/Picture/2016-12-05/58451c922375d.png
     * id : 2
     * content : 内容
     * add_time : 2017-11-12 09:16:20
     * pic : 1
     * userpicid : 1
     */
    private void getList(final int pageNum, final int pageSize) {
        UserApiService userApiService
                = RetrofitManager.getInstance().createReq(UserApiService.class);
        String token = LoginManager.getUserToken(mActivity);
        Call<ResponseBean<List<ResponseMeBean>>> call = userApiService.getResponseMeList(
                token, String.valueOf(pageNum), String.valueOf(pageSize));
        if (call != null) {
            refreshLayout.setRefreshing(true);
            isLoadingData = true;
            call.enqueue(new CustomCallBack<List<ResponseMeBean>>() {
                @Override
                public void onSuccess(List<ResponseMeBean> data, String msg, int state) {
                    if (pageNum <= 1) {
                        historyBeanList.clear();
                    }
                    if (data != null) {
                        if (data.size() <= 0) {
                            recyclerViewAdapter.setHasMoreData(false);
                        } else {
                            for (int index = 0, count = data.size(); index < count; index ++) {
                                ResponseMeBean bean = data.get(index);
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
    public void onTitleClick(ResponseMeBean data) {
        Log.i(TAG, "onTitleClick: ");
//        startMemberActivity(data.getus);
    }

    @Override
    public void onItemClick(ResponseMeBean data, int position) {
        Log.i(TAG, "onItemClick: ");
    }

    private void startMemberActivity(String uid) {
        Intent intent = new Intent(mActivity, MemberDetailActivity.class);
        intent.putExtra(KEY_MEMBER_ID, uid);
        mActivity.startActivity(intent);
    }
}
