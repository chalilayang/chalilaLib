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
import com.baogetv.app.R;
import com.baogetv.app.apiinterface.UserApiService;
import com.baogetv.app.bean.ResponseBean;
import com.baogetv.app.bean.ResponseMeBean;
import com.baogetv.app.bean.ZanMeBean;
import com.baogetv.app.model.usercenter.LoginManager;
import com.baogetv.app.model.usercenter.adapter.ResponseMeListAdapter;
import com.baogetv.app.model.usercenter.adapter.ThumbUpListAdapter;
import com.baogetv.app.model.usercenter.customview.ResponseMeView;
import com.baogetv.app.net.CustomCallBack;
import com.baogetv.app.net.RetrofitManager;
import com.chalilayang.customview.RecyclerViewDivider;
import com.chalilayang.scaleview.ScaleCalculator;

import java.util.List;

import retrofit2.Call;


public class ResponseMeListFragment extends BaseFragment
        implements SwipeRefreshLayout.OnRefreshListener,
        ItemViewHolder.ItemClickListener<ResponseMeBean>, ResponseMeView.OnCommentClickListener {

    private static final String TAG = "ResponseMeListFragment";
    private List<ResponseMeBean> commentDataList;
    private SwipeRefreshLayout refreshLayout;
    private View contentView;
    private RecyclerView.LayoutManager layoutManager;
    private ResponseMeListAdapter recyclerViewAdapter;

    public static ResponseMeListFragment newInstance() {
        ResponseMeListFragment fragment = new ResponseMeListFragment();
//        Bundle args = new Bundle();
//        args.putParcelable(PAGE_DATA, data);
//        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerViewAdapter = new ResponseMeListAdapter(getActivity());
        recyclerViewAdapter.setItemClick(this);
        recyclerViewAdapter.setCommentClickListener(this);
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
    private void getZanList() {
        UserApiService userApiService
                = RetrofitManager.getInstance().createReq(UserApiService.class);
        String token = LoginManager.getUserToken(mActivity);
        Call<ResponseBean<List<ResponseMeBean>>> call = userApiService.getResponseMeList(token);
        if (call != null) {
            call.enqueue(new CustomCallBack<List<ResponseMeBean>>() {
                @Override
                public void onSuccess(List<ResponseMeBean> data) {
                    ResponseMeBean zanMeBean = new ResponseMeBean();
                    zanMeBean.setVideo_id("1");
                    zanMeBean.setContent("xxs");
                    zanMeBean.setTitle("测试");
                    zanMeBean.setType_name("频道1");
                    zanMeBean.setUsername("123223323443");
                    zanMeBean.setUserpic("http://localhost/test2/Uploads/Picture/2016-12-05/58451c922375d.png");
                    zanMeBean.setId("2");
                    zanMeBean.setAdd_time("2017-11-12 09:26:21");
                    zanMeBean.setUserpicid("1");
                    for (int index = 0; index < 10; index ++) {
                        data.add(zanMeBean);
                    }
                    recyclerViewAdapter.update(data);
                }

                @Override
                public void onFailed(String error) {
                    showShortToast(error);
                }
            });
        }
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onTitleClick(ResponseMeBean data) {
        Log.i(TAG, "onTitleClick: ");
    }

    @Override
    public void onItemClick(ResponseMeBean data, int position) {
        Log.i(TAG, "onItemClick: ");
    }
}
