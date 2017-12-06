package com.baogetv.app.model.videodetail.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baogetv.app.BaseFragment;
import com.baogetv.app.ItemViewHolder;
import com.baogetv.app.R;
import com.baogetv.app.apiinterface.UserApiService;
import com.baogetv.app.bean.AddItemBean;
import com.baogetv.app.bean.ReportTypeBean;
import com.baogetv.app.bean.ResponseBean;
import com.baogetv.app.model.usercenter.LoginManager;
import com.baogetv.app.model.usercenter.adapter.ReportTypeListAdapter;
import com.baogetv.app.model.usercenter.entity.ReportTypeData;
import com.baogetv.app.model.usercenter.event.ReportEvent;
import com.baogetv.app.model.videodetail.entity.CommentData;
import com.baogetv.app.net.CustomCallBack;
import com.baogetv.app.net.RetrofitManager;
import com.chalilayang.customview.RecyclerViewDivider;
import com.chalilayang.scaleview.ScaleCalculator;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

public class CommentReportFragment extends BaseFragment implements ItemViewHolder.ItemClickListener<ReportTypeData>{

    private static final String TAG = CommentReportFragment.class.getSimpleName();
    private static final String KEY_COMMENT_DATA = "COMMENT_DATA";
    private CommentData commentData;
    private int curSelect;
    private View root;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private ReportTypeListAdapter recyclerViewAdapter;

    public static CommentReportFragment newInstance(CommentData event) {
        CommentReportFragment fragment = new CommentReportFragment();
        Bundle args = new Bundle();
        args.putParcelable(KEY_COMMENT_DATA, event);
        fragment.setArguments(args);
        return fragment;
    }

    private void init() {
        typeDatas = new ArrayList<>();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            commentData = getArguments().getParcelable(KEY_COMMENT_DATA);
        }
        init();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_comment_report, container, false);
        initView(root);
        getReportList();
        return root;
    }

    private void initView(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.report_list);
        RecyclerViewDivider divider
                = new RecyclerViewDivider(mActivity,
                LinearLayoutManager.HORIZONTAL, 1,
                getResources().getColor(R.color.channel_list_divider));
        recyclerView.addItemDecoration(divider);
        layoutManager = new LinearLayoutManager(mActivity);
        recyclerViewAdapter = new ReportTypeListAdapter(mActivity);
        recyclerViewAdapter.setItemClick(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(recyclerViewAdapter);
        view.findViewById(R.id.complete_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (typeDatas != null && typeDatas.size() > curSelect) {
                    String type = typeDatas.get(curSelect).bean.getId();
                    String content = typeDatas.get(curSelect).bean.getName();
                    String videoId = commentData.getBean().getVideo_id();
                    String userId = commentData.getBean().getUser_id();
                    report(type, content, videoId, userId);
                } else {
                    showShortToast("error");
                }
            }
        });
    }

    @Override
    public void onItemClick(ReportTypeData data, int position) {
        for (int index = 0; index < typeDatas.size(); index ++) {
            ReportTypeData reportTypeData = typeDatas.get(index);
            reportTypeData.selected = (index == position);
        }
        curSelect = position;
        recyclerViewAdapter.update(typeDatas);
    }

    private List<ReportTypeData> typeDatas;
    private void getReportList() {
        UserApiService userApiService
                = RetrofitManager.getInstance().createReq(UserApiService.class);
        Call<ResponseBean<List<ReportTypeBean>>> call = userApiService.getReportTypeList();
        if (call != null) {
            call.enqueue(new CustomCallBack<List<ReportTypeBean>>() {
                @Override
                public void onSuccess(List<ReportTypeBean> data, String msg, int state) {
                    if (data != null && data.size() > 0) {
                        for (int index = 0; index < data.size(); index ++) {
                            ReportTypeData reportTypeData = new ReportTypeData();
                            reportTypeData.bean = data.get(index);
                            reportTypeData.selected = (index == 0);
                            typeDatas.add(reportTypeData);
                        }
                    }
                    recyclerViewAdapter.update(typeDatas);
                }

                @Override
                public void onFailed(String error, int state) {
                    showShortToast(error);
                }
            });
        }
    }

    /**
     * 举报
     *
     * @param type_id：（举报类型：1.广告 2.暴力言论）
     * @param content：（举报内容）
     * @param video_id：（举报视频ID）
     * @param be_user_id：（被举报用户ID）
     * @return
     */
    private void report(String type_id, String content, String video_id, String be_user_id) {
        UserApiService userApiService
                = RetrofitManager.getInstance().createReq(UserApiService.class);
        String token = LoginManager.getUserToken(mActivity);
        Call<ResponseBean<AddItemBean>> call = userApiService.uploadReport(
                token, type_id, content, video_id, be_user_id);
        if (call != null) {
            call.enqueue(new CustomCallBack<AddItemBean>() {
                @Override
                public void onSuccess(AddItemBean data, String msg, int state) {
                    showShortToast("已举报");
                    EventBus.getDefault().post(new ReportEvent());
                }

                @Override
                public void onFailed(String error, int state) {
                    showShortToast(error);
                }
            });
        }
    }
}
