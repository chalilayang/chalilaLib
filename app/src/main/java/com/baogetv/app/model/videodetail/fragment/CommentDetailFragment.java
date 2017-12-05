package com.baogetv.app.model.videodetail.fragment;

import android.app.FragmentTransaction;
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
import com.baogetv.app.ItemViewHolder;
import com.baogetv.app.R;
import com.baogetv.app.apiinterface.UserApiService;
import com.baogetv.app.bean.AddItemBean;
import com.baogetv.app.bean.BeanConvert;
import com.baogetv.app.bean.CommentListBean;
import com.baogetv.app.bean.ResponseBean;
import com.baogetv.app.model.usercenter.LoginManager;
import com.baogetv.app.model.usercenter.activity.MemberDetailActivity;
import com.baogetv.app.model.usercenter.event.ReportEvent;
import com.baogetv.app.model.videodetail.adapter.CommentListAdapter;
import com.baogetv.app.model.videodetail.customview.CommentView;
import com.baogetv.app.model.videodetail.entity.CommentData;
import com.baogetv.app.model.videodetail.entity.ReplyData;
import com.baogetv.app.model.videodetail.entity.VideoDetailData;
import com.baogetv.app.model.videodetail.event.InputSendDetailEvent;
import com.baogetv.app.model.videodetail.event.NeedCommentDetailEvent;
import com.baogetv.app.model.videodetail.event.NeedReplyDetailEvent;
import com.baogetv.app.net.CustomCallBack;
import com.baogetv.app.net.RetrofitManager;
import com.chalilayang.customview.RecyclerViewDivider;
import com.chalilayang.scaleview.ScaleCalculator;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

import static com.baogetv.app.model.usercenter.activity.MemberDetailActivity.KEY_MEMBER_ID;
import static com.baogetv.app.model.videodetail.activity.CommentDetailActivity.KEY_COMMENT_DATA;


public class CommentDetailFragment extends BaseFragment
        implements SwipeRefreshLayout.OnRefreshListener,
        ItemViewHolder.ItemClickListener<CommentData>, CommentView.OnCommentListener {

    private static final String TAG = "CommentListFragment";
    private static final String PAGE_DATA = "PAGE_DATA";
    private CommentData commentData;
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private View contentView;
    private RecyclerView.LayoutManager layoutManager;
    private CommentListAdapter recyclerViewAdapter;
    private VideoDetailData videoDetailData;

    public CommentDetailFragment() {
    }

    public static CommentDetailFragment newInstance(VideoDetailData data, CommentData commentData) {
        CommentDetailFragment fragment = new CommentDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(PAGE_DATA, data);
        args.putParcelable(KEY_COMMENT_DATA, commentData);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            videoDetailData = getArguments().getParcelable(PAGE_DATA);
            commentData = getArguments().getParcelable(KEY_COMMENT_DATA);
        }
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerViewAdapter = new CommentListAdapter(getActivity());
        recyclerViewAdapter.setItemClick(this);
        recyclerViewAdapter.setOnCommentListener(this);
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
            recyclerView.setAdapter(recyclerViewAdapter);
            refreshLayout.setOnRefreshListener(this);
            refreshLayout.setEnabled(false);
            contentView = view;
            getCommentList(videoDetailData);
        }
        return contentView;
    }


    private CommentReportFragment fragment;
    private void showFragment(CommentData commentData) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        if (fragment == null) {
            fragment = CommentReportFragment.newInstance(commentData);
        }
        transaction.replace(R.id.floating_fragment_container, fragment).commit();
    }

    public void getCommentList(VideoDetailData videoDetailData) {
        UserApiService userApiService
                = RetrofitManager.getInstance().createReq(UserApiService.class);
        Call<ResponseBean<List<CommentListBean>>> call
                = userApiService.getCommentList(videoDetailData.videoDetailBean.getId());
        if (call != null) {
            call.enqueue(new CustomCallBack<List<CommentListBean>>() {
                @Override
                public void onSuccess(List<CommentListBean> bean) {
                    for (int index = 0, count = bean.size(); index < count; index ++) {
                        if (bean.get(index).getId().equals(commentData.getBean().getId())) {
                            List<CommentListBean.DataBean> replyDataList
                                    = bean.get(index).getChild();
                            if (replyDataList != null) {
                                List<CommentListBean> beanList = new ArrayList<CommentListBean>();
                                for (CommentListBean.DataBean dataBean: replyDataList) {
                                    CommentListBean commentBean
                                            = BeanConvert.getCommentListBean(dataBean);
                                    beanList.add(commentBean);
                                }
                                List<CommentData> list = new ArrayList<>();
                                for (int i = 0; i < beanList.size(); i ++) {
                                    CommentData data = new CommentData();
                                    CommentListBean listBean = bean.get(i);
                                    data.setTime(listBean.getAdd_time());
                                    data.setBean(listBean);
                                    list.add(data);
                                }
                                recyclerViewAdapter.update(list);
                            }
                            break;
                        }
                    }
                }

                @Override
                public void onFailed(String error) {
                    showShortToast(error);
                }
            });
        }
    }

    @Subscribe
    public void handleReportEvent(ReportEvent event) {
        if (fragment != null && fragment.isAdded()) {
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.remove(fragment).commit();
        }
    }

    @Subscribe
    public void handleSendComment(InputSendDetailEvent event) {
        if (!LoginManager.hasLogin(mActivity)) {
            LoginManager.startLogin(mActivity);
        } else {
            EventBus.getDefault().cancelEventDelivery(event);
            NeedCommentDetailEvent commentEvent = event.commentEvent;
            NeedReplyDetailEvent replyEvent = event.replyEvent;
            Log.i(TAG, "handleSendComment: " + commentEvent + " " + replyEvent);
            if (commentEvent != null) {
                String commentid = commentEvent.commentData.getBean().getId();
//                String uid = commentEvent.commentData.getBean().getUser_id();
                addComment(event.content, videoDetailData.videoDetailBean.getId(), commentid, null);
            } else if (replyEvent != null) {
                String commentid = replyEvent.replyData.getBean().getReply_id();
                String uid = replyEvent.replyData.getBean().getUser_id();
                addComment(event.content, videoDetailData.videoDetailBean.getId(), commentid, uid);
            } else {
                addComment(event.content, videoDetailData.videoDetailBean.getId(), null, null);
            }
        }
        Log.i(TAG, "handleSendComment: " + event.content);
    }

    @Override
    public boolean useEventBus() {
        return true;
    }

    @Override
    public void onRefresh() {
        Log.i(TAG, "onRefresh: ");
    }

    @Override
    public void onItemClick(CommentData data, int position) {
        Log.i(TAG, "onItemClick: " + position);
    }

    @Override
    public void onIconClick(CommentData data) {
        Log.i(TAG, "onIconClick: ");
        startMemberActivity(data.getBean().getUser_id());
    }

    @Override
    public void onThumbUp(CommentData data) {
        Log.i(TAG, "onThumbUp: ");
        addZan(videoDetailData.videoDetailBean.getId(), data.getBean().getId());
    }

    @Override
    public void onJuBaoClick(CommentData data) {
        Log.i(TAG, "onJuBaoClick: ");
        showFragment(data);
    }

    @Override
    public void onReplyerClick(ReplyData data) {
        Log.i(TAG, "onReplyerClick: ");
        startMemberActivity(data.getBean().getUser_id());
    }

    @Override
    public void onReplyToClick(ReplyData data) {
        Log.i(TAG, "onReplyToClick: ");
        startMemberActivity(data.getBean().getUser_id());
    }

    @Override
    public void onReplyClick(ReplyData data) {
        Log.i(TAG, "onReplyClick: ");
        EventBus.getDefault().post(new NeedReplyDetailEvent(data));
    }

    @Override
    public void onMoreComment(CommentData data) {
        Log.i(TAG, "onMoreComment: ");
    }

    @Override
    public void onCommentClick(CommentData data) {
        EventBus.getDefault().post(new NeedCommentDetailEvent(data));
    }

    private void addComment(String content, String vid, String reply_id, String replay_user_id) {
        UserApiService userApiService
                = RetrofitManager.getInstance().createReq(UserApiService.class);
        String token = LoginManager.getUserToken(mActivity);
        Call<ResponseBean<AddItemBean>> call = userApiService.addComment(
                token, vid, reply_id, replay_user_id, content);
        if (call != null) {
            call.enqueue(new CustomCallBack<AddItemBean>() {
                @Override
                public void onSuccess(AddItemBean bean) {
                    showShortToast("add comment success");
                    Log.i(TAG, "onSuccess: add comment success");
                    getCommentList(videoDetailData);
                }

                @Override
                public void onFailed(String error) {
                    showShortToast(error);
                }
            });
        }
    }

    private void addZan(String vid, String comment_id) {
        UserApiService userApiService
                = RetrofitManager.getInstance().createReq(UserApiService.class);
        String token = LoginManager.getUserToken(mActivity);
        Call<ResponseBean<AddItemBean>> call = userApiService.addZan(
                token, vid, comment_id);
        if (call != null) {
            call.enqueue(new CustomCallBack<AddItemBean>() {
                @Override
                public void onSuccess(AddItemBean bean) {
                    showShortToast("点赞 success");
                    Log.i(TAG, "onSuccess: add zan success");
                    getCommentList(videoDetailData);
                }

                @Override
                public void onFailed(String error) {
                    showShortToast(error);
                }
            });
        }
    }

    private void startMemberActivity(String uid) {
        Intent intent = new Intent(mActivity, MemberDetailActivity.class);
        intent.putExtra(KEY_MEMBER_ID, uid);
        mActivity.startActivity(intent);
    }
}
