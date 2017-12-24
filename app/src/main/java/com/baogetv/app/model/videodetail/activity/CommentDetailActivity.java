package com.baogetv.app.model.videodetail.activity;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.baogetv.app.BaseTitleActivity;
import com.baogetv.app.R;
import com.baogetv.app.bean.CommentListBean;
import com.baogetv.app.model.usercenter.LoginManager;
import com.baogetv.app.model.usercenter.event.ReportEvent;
import com.baogetv.app.model.videodetail.entity.CommentData;
import com.baogetv.app.model.videodetail.entity.ReplyData;
import com.baogetv.app.model.videodetail.entity.VideoDetailData;
import com.baogetv.app.model.videodetail.event.InputSendDetailEvent;
import com.baogetv.app.model.videodetail.event.InputSendEvent;
import com.baogetv.app.model.videodetail.event.NeedCommentDetailEvent;
import com.baogetv.app.model.videodetail.event.NeedCommentEvent;
import com.baogetv.app.model.videodetail.event.NeedReplyDetailEvent;
import com.baogetv.app.model.videodetail.event.NeedReplyEvent;
import com.baogetv.app.model.videodetail.fragment.CommentDetailFragment;
import com.baogetv.app.util.InputUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import static com.baogetv.app.PagerFragment.PAGE_DATA;
import static com.baogetv.app.constant.AppConstance.KEY_VIDEO_ID;

public class CommentDetailActivity extends BaseTitleActivity {
    private static final String TAG = "CommentDetailActivity";
    private CommentDetailFragment fragment;
    private String videoID;
    public static final String KEY_COMMENT_ID = "COMMENT_ID";
    private EditText editText;
    private View sendBtn;
    private View editContainer;
    private String commentId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleActivity(getString(R.string.comment_detail));
        initView();
        commentId = getIntent().getStringExtra(KEY_COMMENT_ID);
        videoID = getIntent().getStringExtra(KEY_VIDEO_ID);
        showFragment(videoID, commentId);
    }

    public void initView() {
        editText = (EditText) findViewById(R.id.comment_edit_text);
        sendBtn = findViewById(R.id.comment_send_btn);
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!LoginManager.hasCommentRight(getApplicationContext())) {
                    if (!LoginManager.hasLogin(getApplicationContext())) {
                        LoginManager.startLogin(CommentDetailActivity.this);
                    } else if (LoginManager.hasMobile(getApplicationContext())) {
                        LoginManager.startChangeMobile(CommentDetailActivity.this);
                    } else {
                        showShortToast(getString(R.string.no_comment_right));
                    }
                } else {
                    String content = editText.getText().toString();
                    if (TextUtils.isEmpty(content)) {
                        showShortToast("评论不能为空");
                    } else {
                        InputSendDetailEvent event = new InputSendDetailEvent(content);
                        event.commentEvent = commentEvent;
                        event.replyEvent = replyEvent;
                        commentEvent = null;
                        replyEvent = null;
                        editText.setText("");
                        editText.setHint(getResources().getString(R.string.send_edit_hint));
                        EventBus.getDefault().post(event);
                    }
                    editText.clearFocus();
                    InputUtil.HideKeyboard(editText);
                }
            }
        });
        editContainer = findViewById(R.id.comment_edit_container);
        editContainer.setVisibility(View.GONE);
    }

    @Override
    public boolean useEventBus() {
        return true;
    }

    private NeedReplyDetailEvent replyEvent;
    private NeedCommentDetailEvent commentEvent;
    @Subscribe
    public void handleCommentEvent(NeedCommentDetailEvent event) {
        Log.i(TAG, "handleCommentEvent: dd ");
        if (replyEvent != null || commentEvent != null) {
            return;
        }
        commentEvent = event;
        CommentData data = event.commentData;
        CommentListBean bean = data.getBean();
        editText.setHint("@"+bean.getUsername());
        editContainer.setVisibility(View.VISIBLE);
        editText.requestFocus();
        InputUtil.ShowKeyboard(editText);
    }

    @Subscribe
    public void handleReplyEvent(NeedReplyDetailEvent event) {
        if (replyEvent != null || commentEvent != null) {
            return;
        }
        Log.i(TAG, "handleReplyEvent: ");
        replyEvent = event;
        ReplyData data = event.replyData;
        CommentListBean.DataBean bean = data.getBean();
        editText.setHint("@"+bean.getUsername());
        editContainer.setVisibility(View.VISIBLE);
        editText.requestFocus();
        InputUtil.ShowKeyboard(editText);
    }

    @Override
    protected int getRootView() {
        return R.layout.activity_comment_detail;
    }
    private void showFragment(String v, String c) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        if (fragment == null) {
            fragment = CommentDetailFragment.newInstance(v, c);
        }
        transaction.replace(R.id.root_container, fragment).commit();
    }
}
