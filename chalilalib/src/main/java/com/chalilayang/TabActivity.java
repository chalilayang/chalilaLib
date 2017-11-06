package com.chalilayang;

import android.app.Activity;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;

import com.chalilayang.parcelables.PageData;
import com.chalilayang.parcelables.PageItemData;

import java.util.ArrayList;
import java.util.List;

public class TabActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        List<PageItemData> list = new ArrayList<>(3);
        for (int index = 0; index < 3; index ++) {
            PageItemData pageItemData = new PageItemData(String.valueOf(index), 0);
            list.add(pageItemData);
        }
        PagerFragment pagerFragment = PagerFragment.newInstance(new PageData(list));
        transaction.replace(R.id.page_fragment_container, pagerFragment).commit();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
}
