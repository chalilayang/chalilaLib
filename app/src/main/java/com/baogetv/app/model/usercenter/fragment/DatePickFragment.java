package com.baogetv.app.model.usercenter.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aigestudio.wheelpicker.widgets.WheelDatePicker;
import com.baogetv.app.BaseFragment;
import com.baogetv.app.R;
import com.chalilayang.scaleview.ScaleCalculator;

import java.util.ArrayList;
import java.util.List;

public class DatePickFragment extends BaseFragment {

    private static final String TAG = DatePickFragment.class.getSimpleName();
    private View confirmBtn;
    private View cancelBtn;
    private WheelDatePicker wheelDatePicker;
    private View root;

    private List<String> yearList;
    private List<String> monthList;
    private List<String> dayList;
    public static DatePickFragment newInstance() {
        DatePickFragment fragment = new DatePickFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    private void init() {
        yearList = new ArrayList<>();
        for (int index = 1900; index < 2017; index ++) {
            yearList.add(String.valueOf(index));
        }
        monthList = new ArrayList<>();
        for (int index = 1; index <= 12; index ++) {
            monthList.add(String.valueOf(index));
        }
        dayList = new ArrayList<>();
        for (int index = 1; index <= 31; index ++) {
            dayList.add(String.valueOf(index));
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (root == null) {
            root = inflater.inflate(R.layout.fragment_date_pick, container, false);
            initView(root);
        }
        return root;
    }

    private void initView(View view) {

    }
}
