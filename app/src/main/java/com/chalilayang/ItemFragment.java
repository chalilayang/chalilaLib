package com.chalilayang;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chalilayang.dummy.DummyContent;
import com.chalilayang.dummy.DummyContent.DummyItem;
import com.chalilayang.parcelables.PageItemData;


public class ItemFragment extends BaseItemFragment {

    private static final String PAGE_DATA = "PAGE_DATA";
    private PageItemData pageData;

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
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setAdapter(new ItemRecyclerViewAdapter(DummyContent.ITEMS));
        }
        return view;
    }
}
