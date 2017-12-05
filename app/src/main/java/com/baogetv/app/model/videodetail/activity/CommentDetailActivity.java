package com.baogetv.app.model.videodetail.activity;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.baogetv.app.BaseTitleActivity;
import com.baogetv.app.R;
import com.baogetv.app.model.videodetail.entity.CommentData;
import com.baogetv.app.model.videodetail.entity.VideoDetailData;
import com.baogetv.app.model.videodetail.event.InputSendEvent;
import com.baogetv.app.model.videodetail.event.NeedCommentEvent;
import com.baogetv.app.model.videodetail.event.NeedReplyEvent;
import com.baogetv.app.model.videodetail.fragment.CommentDetailFragment;
import com.baogetv.app.util.InputUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import static com.baogetv.app.PagerFragment.PAGE_DATA;

public class CommentDetailActivity extends BaseTitleActivity {
    private static final String TAG = "CommentDetailActivity";
    private CommentDetailFragment fragment;
    private CommentData commentData;
    private VideoDetailData videoDetailData;
    public static final String KEY_COMMENT_DATA = "COMMENT_DATA";
    private EditText editText;
    private View sendBtn;
    private View editContainer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleActivity(getString(R.string.comment_detail));
        commentData = getIntent().getParcelableExtra(KEY_COMMENT_DATA);
        videoDetailData = getIntent().getParcelableExtra(PAGE_DATA);
        showFragment(videoDetailData, commentData);
    }

    public void init() {
        editText = (EditText) findViewById(R.id.comment_edit_text);
        sendBtn = findViewById(R.id.comment_send_btn);
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String content = editText.getText().toString();
                if (TextUtils.isEmpty(content)) {
                    showShortToast("评论不能为空");
                } else {
                    InputSendEvent event = new InputSendEvent(content);
                    event.commentEvent = commentEvent;
                    event.replyEvent = replyEvent;
                    commentEvent = null;
                    replyEvent = null;
                    EventBus.getDefault().post(event);
                }
                editText.clearFocus();
                InputUtil.HideKeyboard(editText);
                editContainer.setVisibility(View.GONE);
            }
        });
        editContainer = findViewById(R.id.comment_edit_container);
        editContainer.setVisibility(View.GONE);
    }

    private NeedReplyEvent replyEvent;
    private NeedCommentEvent commentEvent;
    @Subscribe
    public void handleCommentEvent(NeedCommentEvent event) {
        if (replyEvent != null || commentEvent != null) {
            return;
        }
        EventBus.getDefault().cancelEventDelivery(event);
        commentEvent = event;
        editText.setHint("@"+event.commentData.getBean().getUsername());
//        editContainer.setVisibility(View.VISIBLE);
//        editText.requestFocus();
//        InputUtil.ShowKeyboard(editText);
    }

    @Subscribe
    public void handleReplyEvent(NeedReplyEvent event) {
        if (replyEvent != null || commentEvent != null) {
            return;
        }
        EventBus.getDefault().cancelEventDelivery(event);
        replyEvent = event;
        Log.i(TAG, "handleReplyEvent: ");
        editText.setHint("@"+event.replyData.getReplyer().getNickName());
//        editContainer.setVisibility(View.VISIBLE);
//        editText.requestFocus();
//        InputUtil.ShowKeyboard(editText);
    }
    
    @Override
    protected int getRootView() {
        return R.layout.activity_comment_detail;
    }
    private void showFragment(VideoDetailData v, CommentData c) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        if (fragment == null) {
            fragment = CommentDetailFragment.newInstance(v, c);
        }
        transaction.replace(R.id.root_container, fragment).commit();
    }
}
