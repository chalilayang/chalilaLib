package com.baogetv.app.model.videodetail.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baogetv.app.BaseItemAdapter;
import com.baogetv.app.BaseItemFragment;
import com.baogetv.app.ItemViewHolder;
import com.baogetv.app.R;
import com.baogetv.app.model.usercenter.entity.UserData;
import com.baogetv.app.model.videodetail.activity.VideoDetailActivity;
import com.baogetv.app.model.videodetail.adapter.CommentListAdapter;
import com.baogetv.app.model.videodetail.entity.CommentData;
import com.baogetv.app.model.videodetail.entity.ReplyData;
import com.chalilayang.customview.RecyclerViewDivider;
import com.chalilayang.scaleview.ScaleCalculator;

import java.util.ArrayList;
import java.util.List;


public class CommentListFragment extends BaseItemFragment
        implements SwipeRefreshLayout.OnRefreshListener, ItemViewHolder.ItemClickListener<CommentData>{

    private static final String TAG = "CommentListFragment";
    private static final String PAGE_DATA = "PAGE_DATA";
    private List<CommentData> commentDataList;
    private SwipeRefreshLayout refreshLayout;
    private View contentView;
    private RecyclerView.LayoutManager layoutManager;
    private BaseItemAdapter recyclerViewAdapter;

    public CommentListFragment() {
    }

    @Override
    public String getTitle() {
        return getString(R.string.comment);
    }

    public static CommentListFragment newInstance() {
        CommentListFragment fragment = new CommentListFragment();
//        Bundle args = new Bundle();
//        args.putParcelable(PAGE_DATA, data);
//        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layoutManager = new LinearLayoutManager(getActivity());
        List<CommentData> list = new ArrayList<>();
        for (int index = 0; index < 10; index ++) {
            CommentData data = new CommentData();
            UserData replyer = new UserData();
            replyer.setNickName("防腐层");
            replyer.setDesc("ddddd");
            replyer.setGrage(index);
            data.setOwner(replyer);
            data.setContent("瓦尔特VRTV让他 ");
            data.setTime(System.currentTimeMillis());
            ReplyData replyData = new ReplyData();
            replyData.setReplyer(replyer);
            replyData.setReplyTo(replyer);
            replyData.setContent("饿哦日女偶俄如女儿");
            List<ReplyData> list1 = new ArrayList<>();
            list1.add(replyData);
            data.setReplyList(list1);
            list.add(data);
        }
        recyclerViewAdapter = new CommentListAdapter(list);
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
            contentView = view;
        }
        return contentView;
    }

    @Override
    public void onRefresh() {
        Log.i(TAG, "onRefresh: ");
    }

    @Override
    public void onItemClick(CommentData data, int position) {
        Log.i(TAG, "onItemClick: " + position);
        Intent intent = new Intent(getActivity(), VideoDetailActivity.class);
        getActivity().startActivity(intent);
    }
}
