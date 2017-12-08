package com.baogetv.app;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baogetv.app.model.videodetail.event.PageSelectEvent;
import com.baogetv.app.parcelables.PageData;
import com.chalilayang.scaleview.ScaleCalculator;

import org.greenrobot.eventbus.EventBus;

import java.lang.reflect.Field;


public class PagerFragment extends BaseFragment implements ViewPager.OnPageChangeListener {
    private static final String TAG = "PagerFragment";
    public static final String PAGE_DATA = "PAGE_DATA";
    protected PageData pageData;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private FragmentManager fragmentManager;
    private View mContentView;
    private Drawable tabSelectedDrawble;

    protected TabLayout tabLayout;
    protected ViewPager mViewPager;
    public PagerFragment() {
        // Required empty public constructor
    }

    public static PagerFragment newInstance(PageData data) {
        PagerFragment fragment = new PagerFragment();
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            fragmentManager = getChildFragmentManager();
        } else {
            fragmentManager = getFragmentManager();
        }
        tabSelectedDrawble = getTabSelected();
    }

    private Drawable getTabSelected() {
        int outRadius = ScaleCalculator.getInstance(getActivity()).scaleWidth(25);
        float[] outerRadii = {
                outRadius, outRadius,
                outRadius, outRadius,
                outRadius, outRadius,
                outRadius, outRadius};
        RoundRectShape roundRectShape = new RoundRectShape(outerRadii, null, null);
        ShapeDrawable drawable = new ShapeDrawable(roundRectShape);
        drawable.getPaint().setColor(getResources().getColor(R.color.reshape_red));
        drawable.getPaint().setFlags(Paint.ANTI_ALIAS_FLAG);
        drawable.getPaint().setStyle(Paint.Style.FILL);
        return new ShapeDrawable(roundRectShape);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (mContentView == null) {
            mContentView = inflater.inflate(R.layout.fragment_pager, container, false);
            init(mContentView);
        }
        return mContentView;
    }

    public void init(View root) {
        tabLayout = root.findViewById(R.id.tab_layout);
        mViewPager = root.findViewById(R.id.view_pager);
        if (pageData != null && pageData.getCount() > 0) {
            mSectionsPagerAdapter = new SectionsPagerAdapter(fragmentManager);
            mViewPager.setAdapter(mSectionsPagerAdapter);
            mViewPager.setOnPageChangeListener(this);
            tabLayout.setupWithViewPager(mViewPager);
            if (pageData.getTabStyle() != 0) {
                tabLayout.setSelectedTabIndicatorHeight(0);
                for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
                    TabLayout.Tab tab = tabLayout.getTabAt(i);//获得每一个tab
                    tab.setCustomView(R.layout.round_bg_tab);//给每一个tab设置view
                    TextView textView = (TextView) tab.getCustomView().findViewById(R.id.tab_tv);
                    if (i == 0) {
                        textView.setBackgroundResource(R.drawable.login_btn_bg);
                        textView.setTextColor(getResources().getColor(R.color.white));
                    }
                    textView.setText(mSectionsPagerAdapter.getPageTitle(i));//设置tab上的文字
                }
                tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                    @Override
                    public void onTabSelected(TabLayout.Tab tab) {
                        Log.i(TAG, "onTabSelected: ");
                        TextView tv = tab.getCustomView().findViewById(R.id.tab_tv);
                        tv.setBackgroundResource(R.drawable.login_btn_bg);
                        tv.setTextColor(getResources().getColor(R.color.white));
                        mViewPager.setCurrentItem(tab.getPosition());
                    }

                    @Override
                    public void onTabUnselected(TabLayout.Tab tab) {
                        Log.i(TAG, "onTabUnselected: ");
                        TextView tv = tab.getCustomView().findViewById(R.id.tab_tv);
                        tv.setTextColor(getResources().getColor(R.color.reply_time));
                        tv.setBackground(null);
                    }

                    @Override
                    public void onTabReselected(TabLayout.Tab tab) {

                    }
                });
            }
        }
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return createFragment(position);
        }

        @Override
        public int getCount() {
            if (pageData == null) {
                return 0;
            } else {
                return pageData.getCount();
            }
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return pageData.getItemDataList().get(position).getTitle();
        }
    }

    public BaseFragment createFragment(int pageIndex) {
        return ItemFragment.newInstance(pageData.getItemDataList().get(pageIndex));
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        Log.i(TAG, "onPageScrolled: ");
    }

    @Override
    public void onPageSelected(int position) {
        Log.i(TAG, "onPageSelected: ");
        EventBus.getDefault().post(new PageSelectEvent(position));
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        Log.i(TAG, "onPageScrollStateChanged: ");
    }


    public static void setIndicator(Context context, TabLayout tabs, int leftDip, int rightDip) {
        Class<?> tabLayout = tabs.getClass();
        Field tabStrip = null;
        try {
            tabStrip = tabLayout.getDeclaredField("mTabStrip");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        tabStrip.setAccessible(true);
        LinearLayout ll_tab = null;
        try {
            ll_tab = (LinearLayout) tabStrip.get(tabs);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        int left = leftDip;
        int right = rightDip;

        for (int i = 0; i < ll_tab.getChildCount(); i++) {
            View child = ll_tab.getChildAt(i);
            child.setPadding(0, 0, 0, 0);
            LinearLayout.LayoutParams params
                    = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
            params.leftMargin = left;
            params.rightMargin = right;
            child.setLayoutParams(params);
            child.invalidate();
        }
    }
}
