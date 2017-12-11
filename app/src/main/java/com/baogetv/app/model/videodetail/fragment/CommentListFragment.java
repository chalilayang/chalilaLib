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

import com.baogetv.app.BaseItemFragment;
import com.baogetv.app.ItemViewHolder;
import com.baogetv.app.OnLoadMoreListener;
import com.baogetv.app.R;
import com.baogetv.app.apiinterface.UserApiService;
import com.baogetv.app.bean.AddItemBean;
import com.baogetv.app.bean.CommentListBean;
import com.baogetv.app.bean.ResponseBean;
import com.baogetv.app.model.usercenter.LoginManager;
import com.baogetv.app.model.usercenter.activity.MemberDetailActivity;
import com.baogetv.app.model.usercenter.event.ReportEvent;
import com.baogetv.app.model.videodetail.activity.CommentDetailActivity;
import com.baogetv.app.model.videodetail.adapter.CommentListAdapter;
import com.baogetv.app.model.videodetail.customview.CommentView;
import com.baogetv.app.model.videodetail.entity.CommentData;
import com.baogetv.app.model.videodetail.entity.ReplyData;
import com.baogetv.app.model.videodetail.entity.VideoDetailData;
import com.baogetv.app.model.videodetail.event.CommentCountEvent;
import com.baogetv.app.model.videodetail.event.InputSendEvent;
import com.baogetv.app.model.videodetail.event.NeedCommentEvent;
import com.baogetv.app.model.videodetail.event.NeedReplyEvent;
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


public class CommentListFragment extends BaseItemFragment
        implements SwipeRefreshLayout.OnRefreshListener,
        ItemViewHolder.ItemClickListener<CommentData>,
        CommentView.OnCommentListener, OnLoadMoreListener.DataLoadingSubject {
    private static final int LOAD_PAGE_SIZE = 10;
    private static final String TAG = "CommentListFragment";
    private static final String PAGE_DATA = "PAGE_DATA";
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private View contentView;
    private LinearLayoutManager layoutManager;
    private CommentListAdapter recyclerViewAdapter;
    private VideoDetailData videoDetailData;
    private OnLoadMoreListener onLoadMoreListener;
    private List<CommentData> commentList;

    public CommentListFragment() {
        commentList = new ArrayList<>();
    }

    @Override
    public String getTitle() {
        return getString(R.string.comment);
    }

    public static CommentListFragment newInstance(VideoDetailData data) {
        CommentListFragment fragment = new CommentListFragment();
        Bundle args = new Bundle();
        args.putParcelable(PAGE_DATA, data);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            videoDetailData = getArguments().getParcelable(PAGE_DATA);
        }
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerViewAdapter = new CommentListAdapter(getActivity());
        recyclerViewAdapter.setItemClick(this);
        recyclerViewAdapter.setOnCommentListener(this);
        onLoadMoreListener = new OnLoadMoreListener(layoutManager, this) {
            @Override
            public void onLoadMore(int totalItemCount) {
                Log.i(TAG, "onLoadMore: " + totalItemCount);
                int more = commentList.size() % LOAD_PAGE_SIZE;
                int pageNum = commentList.size() / LOAD_PAGE_SIZE + 1;
                if (more != 0) {
                    recyclerViewAdapter.setHasMoreData(false);
                } else {
                    getCommentList(videoDetailData, pageNum);
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
        if (commentList.size() == 0) {
            getCommentList(videoDetailData, 0);
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

    private CommentReportFragment fragment;
    private void showFragment(CommentData commentData) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        if (fragment == null) {
            fragment = CommentReportFragment.newInstance(commentData);
        }
        transaction.replace(R.id.floating_fragment_container, fragment).commit();
    }

    public void getCommentList(VideoDetailData videoDetailData, final int pageNum) {
        Log.i(TAG, "getCommentList: ");
        UserApiService userApiService
                = RetrofitManager.getInstance().createReq(UserApiService.class);
        String token = null;
        if (LoginManager.hasLogin(mActivity)) {
            token = LoginManager.getUserToken(mActivity);
        }
        Call<ResponseBean<List<CommentListBean>>> call = userApiService.getCommentList(
                        videoDetailData.videoDetailBean.getId(),
                token, String.valueOf(pageNum), String.valueOf(LOAD_PAGE_SIZE));
        if (call != null) {
            refreshLayout.setRefreshing(true);
            isLoadingData = true;
            call.enqueue(new CustomCallBack<List<CommentListBean>>() {
                @Override
                public void onSuccess(List<CommentListBean> bean, String msg, int state) {
                    Log.i(TAG, "onSuccess: " + bean.size() + " " + pageNum);
                    if (pageNum <= 1) {
                        commentList.clear();
                    }
                    if (bean != null) {
                        if (bean.size() <= 0) {
                            recyclerViewAdapter.setHasMoreData(false);
                        } else {
                            for (int index = 0, count = bean.size(); index < count; index ++) {
                                CommentData data = new CommentData();
                                CommentListBean listBean = bean.get(index);
                                data.setTime(listBean.getAdd_time());
                                data.setBean(listBean);
                                if (listBean.getChild() != null && listBean.getChild().size() > 0) {
                                    List<CommentListBean.DataBean> childList = listBean.getChild();
                                    List<ReplyData> list1 = new ArrayList<>(childList.size());
                                    for (int i = 0, childCount = childList.size(); i < childCount; i ++) {
                                        ReplyData replyData = new ReplyData();
                                        replyData.setBean(childList.get(i));
                                        list1.add(replyData);
                                    }
                                    data.setReplyList(list1);
                                }
                                commentList.add(data);
                            }
                        }
                    }
                    recyclerViewAdapter.update(commentList);
                    EventBus.getDefault().post(new CommentCountEvent(commentList.size()));
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

    @Subscribe
    public void handleReportEvent(ReportEvent event) {
        if (fragment != null && fragment.isAdded()) {
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.remove(fragment).commit();
        }
    }

    @Subscribe
    public void handleSendComment(InputSendEvent event) {
        if (!LoginManager.hasLogin(mActivity)) {
            LoginManager.startLogin(mActivity);
        } else {
            NeedCommentEvent commentEvent = event.commentEvent;
            NeedReplyEvent replyEvent = event.replyEvent;
            Log.i(TAG, "handleSendComment: " + commentEvent + " " + replyEvent);
            if (commentEvent != null) {
                String commentId = commentEvent.commentData.getBean().getId();
                addComment(event.content, videoDetailData.videoDetailBean.getId(), commentId, null);
            } else if (replyEvent != null) {
                String commentId = replyEvent.replyData.getBean().getReply_id();
                String uid = replyEvent.replyData.getBean().getUser_id();
                addComment(event.content, videoDetailData.videoDetailBean.getId(), commentId, uid);
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
        getCommentList(videoDetailData, 0);
    }

    @Override
    public void onItemClick(CommentData data, int position) {
        Log.i(TAG, "onItemClick: " + position);
        Log.i(TAG, "onMoreComment: ");
        Intent intent = new Intent(mActivity, CommentDetailActivity.class);
        intent.putExtra(PAGE_DATA, videoDetailData);
        intent.putExtra(KEY_COMMENT_DATA, data);
        mActivity.startActivity(intent);
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
        EventBus.getDefault().post(new NeedReplyEvent(data));
    }

    @Override
    public void onMoreComment(CommentData data) {
        Log.i(TAG, "onMoreComment: ");
        Intent intent = new Intent(mActivity, CommentDetailActivity.class);
        intent.putExtra(PAGE_DATA, videoDetailData);
        intent.putExtra(KEY_COMMENT_DATA, data);
        mActivity.startActivity(intent);
    }

    @Override
    public void onCommentClick(CommentData data) {
        EventBus.getDefault().post(new NeedCommentEvent(data));
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
                public void onSuccess(AddItemBean bean, String msg, int state) {
                    showShortToast("add comment success");
                    Log.i(TAG, "onSuccess: add comment success");
                    getCommentList(videoDetailData, 0);
                }

                @Override
                public void onFailed(String error, int state) {
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
                public void onSuccess(AddItemBean bean, String msg, int state) {
                    showShortToast("点赞 success");
                    Log.i(TAG, "onSuccess: add zan success");
                    getCommentList(videoDetailData, 0);
                }

                @Override
                public void onFailed(String error, int state) {
                    showShortToast(error);
                }
            });
        }
    }

    private void delZan(String vid, String comment_id) {
        UserApiService userApiService
                = RetrofitManager.getInstance().createReq(UserApiService.class);
        String token = LoginManager.getUserToken(mActivity);
        Call<ResponseBean<AddItemBean>> call = userApiService.delZan(
                token, vid, comment_id);
        if (call != null) {
            call.enqueue(new CustomCallBack<AddItemBean>() {
                @Override
                public void onSuccess(AddItemBean bean, String msg, int state) {
                    showShortToast("点赞 success");
                    Log.i(TAG, "onSuccess: add zan success");
                    getCommentList(videoDetailData, 0);
                }

                @Override
                public void onFailed(String error, int state) {
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
