package com.chalilayang;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chalilayang.dummy.DummyContent;
import com.chalilayang.parcelables.PageItemData;


public class ItemFragment extends BaseItemFragment implements SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "ItemFragment";
    private static final String PAGE_DATA = "PAGE_DATA";
    private PageItemData pageData;
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView.LayoutManager layoutManager;
    private ItemRecyclerViewAdapter recyclerViewAdapter;

    public ItemFragment() {
    }

    @Override
    public String getTitle() {
        return pageData.getTitle();
    }

    public static ItemFragment newInstance(PageItemData data) {
        ItemFragment fragment = new ItemFragment();
        Bundle args = new Bundle();
        args.putParcelable(PAGE_DATA, data);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            pageData = getArguments().getParcelable(PAGE_DATA);
            layoutManager = new LinearLayoutManager(getActivity());
            recyclerViewAdapter = new ItemRecyclerViewAdapter(DummyContent.ITEMS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
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
        return view;
    }

    @Override
    public void onRefresh() {
        Log.i(TAG, "onRefresh: ");
    }
}
