package com.baogetv.app.model.usercenter.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baogetv.app.BaseFragment;
import com.baogetv.app.BaseItemAdapter;
import com.baogetv.app.ItemViewHolder;
import com.baogetv.app.R;
import com.baogetv.app.apiinterface.UserApiService;
import com.baogetv.app.bean.CollectBean;
import com.baogetv.app.bean.ResponseBean;
import com.baogetv.app.bean.ZanMeBean;
import com.baogetv.app.model.usercenter.LoginManager;
import com.baogetv.app.model.usercenter.adapter.ThumbUpListAdapter;
import com.baogetv.app.net.CustomCallBack;
import com.baogetv.app.net.RetrofitManager;
import com.chalilayang.customview.RecyclerViewDivider;
import com.chalilayang.scaleview.ScaleCalculator;

import java.util.List;

import retrofit2.Call;


public class ThumbUpListFragment extends BaseFragment
        implements SwipeRefreshLayout.OnRefreshListener, ItemViewHolder.ItemClickListener<ZanMeBean>{

    private static final String TAG = "ThumbUpListFragment";
    private List<ZanMeBean> commentDataList;
    private SwipeRefreshLayout refreshLayout;
    private View contentView;
    private LinearLayoutManager layoutManager;
    private ThumbUpListAdapter recyclerViewAdapter;

    public static ThumbUpListFragment newInstance() {
        ThumbUpListFragment fragment = new ThumbUpListFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerViewAdapter = new ThumbUpListAdapter(getActivity());
        recyclerViewAdapter.setItemClick(this);
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
            RecyclerView child = refreshLayout.findViewById(R.id.list);
            child.setLayoutManager(layoutManager);
            child.addItemDecoration(divider);
            child.setAdapter(recyclerViewAdapter);
            refreshLayout.setOnRefreshListener(this);
            refreshLayout.setEnabled(false);
            contentView = view;
            getZanList();
        }
        return contentView;
    }

    /**
     * video_id : 1
     * content : xxs
     * user_id : 3
     * username : 15913196454
     * userpic : http://localhost/test2/Uploads/Picture/2016-12-05/58451c922375d.png
     * id : 2
     * comments_id : 1
     * add_time : 2017-11-12 09:26:21
     * userpicid : 1
     */

    private void getZanList() {
        UserApiService userApiService
                = RetrofitManager.getInstance().createReq(UserApiService.class);
        String token = LoginManager.getUserToken(mActivity);
        Call<ResponseBean<List<ZanMeBean>>> call = userApiService.getZanMeList(token);
        if (call != null) {
            call.enqueue(new CustomCallBack<List<ZanMeBean>>() {
                @Override
                public void onSuccess(List<ZanMeBean> data, String msg, int state) {
                    recyclerViewAdapter.update(data);
                }

                @Override
                public void onFailed(String error, int state) {
                    showShortToast(error);
                }
            });
        }
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onItemClick(ZanMeBean data, int position) {

    }
}
