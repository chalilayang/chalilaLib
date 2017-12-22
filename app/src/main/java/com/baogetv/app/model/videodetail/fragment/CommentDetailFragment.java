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
import com.baogetv.app.model.videodetail.adapter.CommentDetailAdapter;
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

import static com.baogetv.app.constant.AppConstance.KEY_VIDEO_ID;
import static com.baogetv.app.model.usercenter.activity.MemberDetailActivity.KEY_MEMBER_ID;
import static com.baogetv.app.model.videodetail.activity.CommentDetailActivity.KEY_COMMENT_ID;


public class CommentDetailFragment extends BaseFragment
        implements SwipeRefreshLayout.OnRefreshListener,
        ItemViewHolder.ItemClickListener<CommentData>, CommentView.OnCommentListener {

    private static final String TAG = "CommentListFragment";
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private View contentView;
    private RecyclerView.LayoutManager layoutManager;
    private CommentDetailAdapter recyclerViewAdapter;
    private String videoid;
    private String commentId;

    private List<CommentData> commentList;
    public CommentDetailFragment() {
        commentList = new ArrayList<>();
    }

    public static CommentDetailFragment newInstance(String data, String commentId) {
        CommentDetailFragment fragment = new CommentDetailFragment();
        Bundle args = new Bundle();
        args.putString(KEY_VIDEO_ID, data);
        args.putString(KEY_COMMENT_ID, commentId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            videoid = getArguments().getString(KEY_VIDEO_ID);
            commentId = getArguments().getString(KEY_COMMENT_ID);
        }
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerViewAdapter = new CommentDetailAdapter(getActivity());
        recyclerViewAdapter.setItemClick(this);
        recyclerViewAdapter.setHasMoreData(false);
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
            divider.setStartIndex(1);
            int margin_30px = ScaleCalculator.getInstance(getActivity()).scaleWidth(30);
            divider.setMargin(margin_30px);
            refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.srl);
            recyclerView = refreshLayout.findViewById(R.id.list);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.addItemDecoration(divider);
            recyclerView.setAdapter(recyclerViewAdapter);
            refreshLayout.setOnRefreshListener(this);
            contentView = view;
            getCommentList(videoid, commentId);
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
                String commentid = commentId;
                addComment(event.content, videoid, commentid, null);
            } else if (replyEvent != null) {
                String commentid = replyEvent.replyData.getBean().getReply_id();
                String uid = replyEvent.replyData.getBean().getUser_id();
                addComment(event.content, videoid, commentid, uid);
            } else {
                String commentid = commentId;
                addComment(event.content, videoid, commentid, null);
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
        getCommentList(videoid, commentId);
    }

    @Override
    public void onItemClick(CommentData data, int position) {
        Log.i(TAG, "onItemClick: " + position);
    }

    @Override
    public void onIconClick(CommentData data, int commentIndex) {
        Log.i(TAG, "onIconClick: ");
        startMemberActivity(data.getBean().getUser_id());
    }

    @Override
    public void onThumbUp(CommentData data, int commentIndex) {
        Log.i(TAG, "onThumbUp: ");
        int zan = 0;
        try {
            zan = Integer.parseInt(data.getBean().getIs_like());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        if (zan == 0) {
            addZan(videoid, data.getBean().getId(), commentIndex);
        } else {
            delZan(videoid, data.getBean().getId(), commentIndex);
        }
    }

    @Override
    public void onJuBaoClick(CommentData data, int commentIndex) {
        showFragment(data);
    }

    @Override
    public void onReplyerClick(ReplyData data, int commentIndex) {
    }

    @Override
    public void onReplyToClick(ReplyData data, int commentIndex) {
    }

    @Override
    public void onReplyClick(ReplyData data, int commentIndex) {
    }

    @Override
    public void onMoreComment(CommentData data, int commentIndex) {
        Log.i(TAG, "onMoreComment: ");
    }

    @Override
    public void onCommentClick(CommentData data, int commentIndex) {
        if (commentIndex == 0) {
            EventBus.getDefault().post(new NeedCommentDetailEvent(data));
        } else {
            ReplyData replyData = data.getReplyList().get(commentIndex);
            EventBus.getDefault().post(new NeedReplyDetailEvent(replyData));
        }
    }

    private void addComment(String content, String vid, String reply_id, String replay_user_id) {
        UserApiService userApiService
                = RetrofitManager.getInstance().createReq(UserApiService.class);
        String token = LoginManager.getUserToken(mActivity);
        Call<ResponseBean<CommentListBean>> call = userApiService.addComment(
                token, vid, reply_id, replay_user_id, content);
        if (call != null) {
            call.enqueue(new CustomCallBack<CommentListBean>() {
                @Override
                public void onSuccess(CommentListBean bean, String msg, int state) {
                    Log.i(TAG, "onSuccess: add comment success");
                    CommentData data = new CommentData();
                    CommentListBean listBean = bean;
                    data.setTime(listBean.getAdd_time());
                    data.setBean(listBean);
                    commentList.add(1, data);
                    recyclerViewAdapter.update(commentList);
                }

                @Override
                public void onFailed(String error, int state) {
                    showShortToast(error);
                }
            });
        }
    }

    private void addZan(String vid, String comment_id, final int commentIndex) {
        if (!LoginManager.hasCommentRight(mActivity.getApplicationContext())) {
            if (!LoginManager.hasLogin(mActivity.getApplicationContext())) {
                LoginManager.startLogin(mActivity);
            } else if (LoginManager.hasMobile(mActivity.getApplicationContext())) {
                LoginManager.startChangeMobile(mActivity);
            } else {
                showShortToast(getString(R.string.no_zan_right));
            }
        } else {
            UserApiService userApiService
                    = RetrofitManager.getInstance().createReq(UserApiService.class);
            String token = LoginManager.getUserToken(mActivity);
            Call<ResponseBean<AddItemBean>> call = userApiService.addZan(
                    token, vid, comment_id);
            if (call != null) {
                call.enqueue(new CustomCallBack<AddItemBean>() {
                    @Override
                    public void onSuccess(AddItemBean bean, String msg, int state) {
                        Log.i(TAG, "onSuccess: add zan success");
                        CommentData commentData = commentList.get(commentIndex);
                        commentData.getBean().setIs_like("1");
                        int zan = -1;
                        try {
                            zan = Integer.parseInt(commentData.getBean().getLikes());
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                        }
                        if (zan >= 0) {
                            zan ++;
                        }
                        commentData.getBean().setLikes(zan + "");
                        recyclerViewAdapter.updateItem(commentIndex);
                    }

                    @Override
                    public void onFailed(String error, int state) {
                        showShortToast(error);
                    }
                });
            }
        }
    }

    private void delZan(String vid, String comment_id, final int commentIndex) {
        if (!LoginManager.hasCommentRight(mActivity.getApplicationContext())) {
            if (!LoginManager.hasLogin(mActivity.getApplicationContext())) {
                LoginManager.startLogin(mActivity);
            } else if (LoginManager.hasMobile(mActivity.getApplicationContext())) {
                LoginManager.startChangeMobile(mActivity);
            } else {
                showShortToast(getString(R.string.no_zan_right));
            }
        } else {
            UserApiService userApiService
                    = RetrofitManager.getInstance().createReq(UserApiService.class);
            String token = LoginManager.getUserToken(mActivity);
            Call<ResponseBean<List<Object>>> call = userApiService.delZan(
                    token, vid, comment_id);
            if (call != null) {
                call.enqueue(new CustomCallBack<List<Object>>() {
                    @Override
                    public void onSuccess(List<Object> bean, String msg, int state) {
                        Log.i(TAG, "onSuccess: add zan success");
                        CommentData commentData = commentList.get(commentIndex);
                        commentData.getBean().setIs_like("0");
                        int zan = -1;
                        try {
                            zan = Integer.parseInt(commentData.getBean().getLikes());
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                        }
                        if (zan > 0) {
                            zan --;
                        }
                        commentData.getBean().setLikes(zan + "");
                        recyclerViewAdapter.updateItem(commentIndex);
                    }

                    @Override
                    public void onFailed(String error, int state) {
                        showShortToast(error);
                    }
                });
            }
        }
    }

    private void startMemberActivity(String uid) {
        Intent intent = new Intent(mActivity, MemberDetailActivity.class);
        intent.putExtra(KEY_MEMBER_ID, uid);
        mActivity.startActivity(intent);
    }

    public void getCommentList(String vid, String comment_id) {
        Log.i(TAG, "getCommentList: ");
        UserApiService userApiService
                = RetrofitManager.getInstance().createReq(UserApiService.class);
        String token = null;
        if (LoginManager.hasLogin(mActivity)) {
            token = LoginManager.getUserToken(mActivity);
        }
        Call<ResponseBean<List<CommentListBean>>> call = userApiService.getCommentList(
                vid, token, comment_id, null, null);
        if (call != null) {
            refreshLayout.setRefreshing(true);
            call.enqueue(new CustomCallBack<List<CommentListBean>>() {
                @Override
                public void onSuccess(List<CommentListBean> bean, String msg, int state) {
                    commentList.clear();
                    if (bean != null) {
                        if (bean.size() <= 0) {
                            recyclerViewAdapter.setHasMoreData(false);
                        } else {
                            CommentListBean listBean = bean.get(0);
                            CommentData data = new CommentData();
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
                            List<CommentListBean.DataBean> replyDataList = listBean.getChild();
                            if (replyDataList != null) {
                                List<CommentListBean> beanList = new ArrayList<>();
                                for (CommentListBean.DataBean dataBean: replyDataList) {
                                    CommentListBean commentBean = BeanConvert.getCommentListBean(dataBean);
                                    beanList.add(commentBean);
                                }
                                for (int i = 0; i < beanList.size(); i ++) {
                                    data = new CommentData();
                                    listBean = beanList.get(i);
                                    data.setTime(listBean.getAdd_time());
                                    data.setBean(listBean);
                                    commentList.add(data);
                                }
                                recyclerViewAdapter.update(commentList);
                            }
                        }
                    }
                    recyclerViewAdapter.update(commentList);
                    refreshLayout.setRefreshing(false);
                }

                @Override
                public void onFailed(String error, int state) {
                    showShortToast(error);
                    refreshLayout.setRefreshing(false);
                }
            });
        }
    }
}
