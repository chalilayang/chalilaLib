package com.baogetv.app;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baogetv.app.parcelables.PageData;


public class PagerFragment extends BaseFragment {
    public static final String PAGE_DATA = "PAGE_DATA";
    private PageData pageData;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private FragmentManager fragmentManager;

    private ViewPager mViewPager;

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_pager, container, false);
        init(root);
        return root;
    }

    public void init(View root) {
        TabLayout tabLayout = root.findViewById(R.id.tab_layout);
        mViewPager = root.findViewById(R.id.view_pager);
        if (pageData != null && pageData.getCount() > 0) {
            mSectionsPagerAdapter = new SectionsPagerAdapter(fragmentManager);
            mViewPager.setAdapter(mSectionsPagerAdapter);
            tabLayout.setupWithViewPager(mViewPager);
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
}