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
import com.baogetv.app.model.videodetail.activity.VideoDetailActivity;
import com.baogetv.app.model.videodetail.adapter.CommentListAdapter;
import com.baogetv.app.model.videodetail.adapter.VideoListAdapter;
import com.baogetv.app.model.videodetail.entity.CommentData;

import java.util.ArrayList;
import java.util.List;


public class CommentListFragment extends BaseItemFragment
        implements SwipeRefreshLayout.OnRefreshListener, ItemViewHolder.ItemClickListener<String>{

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
            list.add(new CommentData());
        }
        recyclerViewAdapter = new CommentListAdapter(list);
        recyclerViewAdapter.setItemClick(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (contentView == null) {
            View view = inflater.inflate(R.layout.fragment_item_list, container, false);
            if (view instanceof RecyclerView) {
                RecyclerView recyclerView = (RecyclerView) view;
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(recyclerViewAdapter);
            } else if (view instanceof SwipeRefreshLayout ){
                refreshLayout = (SwipeRefreshLayout) view;
                RecyclerView child = refreshLayout.findViewById(R.id.list);
                child.setLayoutManager(layoutManager);
                child.setAdapter(recyclerViewAdapter);
                refreshLayout.setOnRefreshListener(this);
            }
            contentView = view;
        }
        return contentView;
    }

    @Override
    public void onRefresh() {
        Log.i(TAG, "onRefresh: ");
    }

    @Override
    public void onItemClick(String data, int position) {
        Log.i(TAG, "onItemClick: " + position);
        Intent intent = new Intent(getActivity(), VideoDetailActivity.class);
        getActivity().startActivity(intent);
    }
}
