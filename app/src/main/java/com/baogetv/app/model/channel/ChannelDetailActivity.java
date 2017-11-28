package com.baogetv.app.model.channel;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.baogetv.app.PagerFragment;
import com.baogetv.app.R;
import com.baogetv.app.parcelables.PageData;
import com.baogetv.app.parcelables.PageItemData;

import java.util.ArrayList;
import java.util.List;

public class ChannelDetailActivity extends AppCompatActivity {
    private PagerFragment searchResultFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        showHomeFragment();
    }

    private void showHomeFragment() {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        if (searchResultFragment == null) {
            List<PageItemData> list = new ArrayList<>(2);
            PageItemData pageItemData = new PageItemData(getString(R.string.search_relative), PageItemData.TYPE_SEARCH_RELATIVE);
            list.add(pageItemData);
            pageItemData = new PageItemData(getString(R.string.search_play_most), PageItemData.TYPE_SEARCH_PLAY_MOST);
            list.add(pageItemData);
            pageItemData = new PageItemData(getString(R.string.search_latest_publish), PageItemData.TYPE_SEARCH_LATEST_PUBLISH);
            list.add(pageItemData);
            PageData pageData = new PageData(list);
            pageData.setTabStyle(1);
            searchResultFragment = PagerFragment.newInstance(pageData);
        }
        transaction.replace(R.id.fragment_container, searchResultFragment).commit();
    }
}
