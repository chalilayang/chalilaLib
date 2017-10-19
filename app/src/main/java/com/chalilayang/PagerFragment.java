package com.chalilayang;

import android.app.FragmentManager;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class PagerFragment extends Fragment {
    public static final String ARG_PARAM1 = "param1";
    private int mParam1;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private FragmentManager fragmentManager;

    private ViewPager mViewPager;
    private OnFragmentInteractionListener mListener;

    public PagerFragment() {
        // Required empty public constructor
    }

    public static PagerFragment newInstance(String param1) {
        PagerFragment fragment = new PagerFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getInt(ARG_PARAM1);
        }
        if (mParam1 == 0) {
            mParam1 = 4;
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
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_pager, container, false);
        init(root);
        return root;
    }

    public void init(View root) {
        mSectionsPagerAdapter = new SectionsPagerAdapter(fragmentManager, mParam1);
        mViewPager = root.findViewById(R.id.view_pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        private int count;
        public SectionsPagerAdapter(FragmentManager fm, int pageCount) {
            super(fm);
            count = pageCount;
        }

        @Override
        public Fragment getItem(int position) {
            return ItemFragment.newInstance(7);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return count;
        }
    }
}
