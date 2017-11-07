package com.reshape.app.model.homepage;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.chalilayang.parcelables.PageData;
import com.chalilayang.parcelables.PageItemData;
import com.reshape.app.BaseActivity;
import com.reshape.app.R;
import com.reshape.app.model.homepage.fragment.HomeFragment;

import java.util.ArrayList;
import java.util.List;

public class HomePageActivity extends BaseActivity {

    private FrameLayout fragmentContainer;
    private HomeFragment homeFragment;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    showHomeFragment();
                    return true;
                case R.id.navigation_dashboard:
                    return true;
                case R.id.navigation_notifications:
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        setContentView(R.layout.activity_home_page);
        fragmentContainer = findViewById(R.id.fragment_container);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        showHomeFragment();
    }

    private void showHomeFragment() {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        if (homeFragment == null) {
            List<PageItemData> list = new ArrayList<>(2);
            PageItemData pageItemData = new PageItemData(getString(R.string.all), 0);
            list.add(pageItemData);
            pageItemData = new PageItemData(getString(R.string.ranking), 0);
            list.add(pageItemData);
            homeFragment = HomeFragment.newInstance(new PageData(list));
        }
        transaction.replace(R.id.fragment_container, homeFragment).commit();
    }
}
