package com.baogetv.app.model.search;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;

import com.baogetv.app.BaseActivity;
import com.baogetv.app.BaseTitleActivity;
import com.baogetv.app.PagerFragment;
import com.baogetv.app.R;
import com.baogetv.app.parcelables.PageData;
import com.baogetv.app.parcelables.PageItemData;

import java.util.ArrayList;
import java.util.List;

public class SearchResultActivity extends BaseTitleActivity {

    public static final String KEY_SEARCH = "KEY_SEARCH";
    private String key;
    private PagerFragment searchResultFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        key = intent.getStringExtra(KEY_SEARCH);
        setTitleActivity(key);
        showHomeFragment();
    }

    @Override
    protected int getRootView() {
        return R.layout.activity_search_result;
    }

    private void showHomeFragment() {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        if (searchResultFragment == null) {
            List<PageItemData> list = new ArrayList<>(2);
            SearchItemData pageItemData = new SearchItemData(
                    getString(R.string.search_relative),
                    PageItemData.TYPE_SEARCH_RELATIVE,
                    key);
            list.add(pageItemData);
            pageItemData = new SearchItemData(
                    getString(R.string.search_play_most),
                    PageItemData.TYPE_SEARCH_PLAY_MOST,
                    key);
            list.add(pageItemData);
            pageItemData = new SearchItemData(
                    getString(R.string.search_latest_publish),
                    PageItemData.TYPE_SEARCH_LATEST_PUBLISH,
                    key);
            list.add(pageItemData);
            PageData pageData = new PageData(list);
            pageData.setTabStyle(1);
            searchResultFragment = PagerFragment.newInstance(pageData);
        }
        transaction.replace(R.id.root_container, searchResultFragment).commit();
    }
}
