package com.baogetv.app.model.usercenter.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;

import com.baogetv.app.CustomDialog;
import com.baogetv.app.ItemViewHolder;
import com.baogetv.app.OnLoadMoreListener;
import com.baogetv.app.apiinterface.UserApiService;
import com.baogetv.app.bean.CollectBean;
import com.baogetv.app.bean.ResponseBean;
import com.baogetv.app.model.usercenter.LoginManager;
import com.baogetv.app.model.videodetail.activity.VideoDetailActivity;
import com.baogetv.app.net.CustomCallBack;
import com.baogetv.app.net.RetrofitManager;
import com.chalilayang.customview.RecyclerViewDivider;
import com.chalilayang.scaleview.ScaleCalculator;
import com.baogetv.app.BaseTitleActivity;
import com.baogetv.app.R;
import com.baogetv.app.model.usercenter.adapter.CollectListAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

import static com.baogetv.app.constant.AppConstance.KEY_VIDEO_ID;


public class MyCollectActivity extends BaseTitleActivity
        implements SwipeRefreshLayout.OnRefreshListener,
        ItemViewHolder.ItemClickListener<CollectBean>,
        ItemViewHolder.ItemDeleteListener<CollectBean>,
        OnLoadMoreListener.DataLoadingSubject  {
    private static final String TAG = "MyCollectActivity";
    private static final int LOAD_PAGE_SIZE = 20;
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private CollectListAdapter recyclerViewAdapter;
    private OnLoadMoreListener onLoadMoreListener;
    private List<CollectBean> collectBeanList;
    private CustomDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleActivity(getString(R.string.user_collect));
        setRightTitle(getString(R.string.all_delete));
        init();
    }

    @Override
    public void onRightClick() {
        if (dialog == null) {
            CustomDialog.Builder builder = new CustomDialog.Builder(this);
            builder.setMessage(R.string.confirm_delete_all).setNegativeButton(R.string.cancel,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            if (dialog != null) {
                                dialog.cancel();
                            }
                        }
                    }).setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    delete(null, 0);
                    if (dialog != null) {
                        dialog.cancel();
                    }
                }
            });
            dialog = builder.create();
        }
        dialog.show();
    }

    @Override
    protected int getRootView() {
        return R.layout.activity_my_collect;
    }

    public void init() {
        collectBeanList = new ArrayList<>();
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.collect_list_container);
        recyclerView = (RecyclerView) findViewById(R.id.collect_list);
        RecyclerViewDivider divider
                = new RecyclerViewDivider(this,
                LinearLayoutManager.HORIZONTAL, 1,
                getResources().getColor(R.color.channel_list_divider));
        int margin_30px = ScaleCalculator.getInstance(getApplicationContext()).scaleWidth(30);
        divider.setMargin(margin_30px);
        recyclerView.addItemDecoration(divider);
        layoutManager = new LinearLayoutManager(this);
        recyclerViewAdapter = new CollectListAdapter(this);
        recyclerViewAdapter.setItemClick(this);
        recyclerViewAdapter.setItemDeleteListener(this);
        onLoadMoreListener = new OnLoadMoreListener(layoutManager, this) {
            @Override
            public void onLoadMore(int totalItemCount) {
                Log.i(TAG, "onLoadMore: " + totalItemCount);
                int more = collectBeanList.size() % LOAD_PAGE_SIZE;
                int pageNum = collectBeanList.size() / LOAD_PAGE_SIZE + 1;
                if (more != 0) {
                    recyclerViewAdapter.setHasMoreData(false);
                } else {
                    getCollectList(pageNum, LOAD_PAGE_SIZE);
                }
            }
        };
        recyclerView.addOnScrollListener(onLoadMoreListener);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(recyclerViewAdapter);
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setRefreshing(true);
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

    private void getCollectList(final int pageNum, final int pageSize) {
        if (LoginManager.hasLogin(getApplicationContext())) {
            UserApiService userApiService
                    = RetrofitManager.getInstance().createReq(UserApiService.class);
            String token = LoginManager.getUserToken(getApplicationContext());
            Call<ResponseBean<List<CollectBean>>> call = userApiService.getCollectList(
                    token, null, String.valueOf(pageNum), String.valueOf(pageSize));
            if (call != null) {
                refreshLayout.setRefreshing(true);
                isLoadingData = true;
                call.enqueue(new CustomCallBack<List<CollectBean>>() {
                    @Override
                    public void onSuccess(List<CollectBean> data, String msg, int state) {
                        if (pageNum <= 1) {
                            collectBeanList.clear();
                        }
                        if (data != null) {
                            if (data.size() <= 0) {
                                recyclerViewAdapter.setHasMoreData(false);
                            } else {
                                for (int index = 0, count = data.size(); index < count; index ++) {
                                    CollectBean bean = data.get(index);
                                    collectBeanList.add(bean);
                                }
                            }
                        }
                        recyclerViewAdapter.update(collectBeanList);
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
    }

    @Override
    public void onItemClick(CollectBean data, int position) {
        Log.i(TAG, "onItemClick: ");
        Intent intent = new Intent(this, VideoDetailActivity.class);
        intent.putExtra(KEY_VIDEO_ID, data.getVideo_id());
        startActivity(intent);
    }

    @Override
    public void onDelete(CollectBean data, int pos) {
        Log.i(TAG, "onDelete: " + pos);
        delete(data, pos);
    }

    private void delete(final CollectBean data, final int pos) {
        UserApiService userApiService
                = RetrofitManager.getInstance().createReq(UserApiService.class);
        String token = LoginManager.getUserToken(getApplicationContext());
        String id = "";
        if (data != null) {
            id = data.getVideo_id();
        } else {
            List<CollectBean> list = recyclerViewAdapter.getDataList();
            for (int index = 0, count = list.size(); index < count; index ++) {
                if (index == 0) {
                    id = list.get(index).getVideo_id();
                } else {
                    id = id + "," + list.get(index).getVideo_id();
                }
            }
        }
        if (!TextUtils.isEmpty(id)) {
            Call<ResponseBean<List<Object>>> call
                    = userApiService.deleteCollect(token, id);
            if (call != null) {
                refreshLayout.setRefreshing(true);
                isLoadingData = true;
                call.enqueue(new CustomCallBack<List<Object>>() {
                    @Override
                    public void onSuccess(List<Object> result, String msg, int state) {
                        Log.i(TAG, "onSuccess: ");
                        refreshLayout.setRefreshing(false);
                        isLoadingData = false;
                        if (data == null) {
                            getCollectList(0, 2 * collectBeanList.size());
                        } else {
                            collectBeanList.remove(pos);
                            recyclerViewAdapter.removeItem(pos);
                        }
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
    }

    @Override
    public void onRefresh() {
        Log.i(TAG, "onRefresh: ");
        getCollectList(0, LOAD_PAGE_SIZE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (dialog != null) {
            dialog.cancel();
        }
    }
}
