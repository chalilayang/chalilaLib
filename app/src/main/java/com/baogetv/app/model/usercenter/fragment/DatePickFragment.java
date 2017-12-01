package com.baogetv.app.model.usercenter.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aigestudio.wheelpicker.WheelPicker;
import com.aigestudio.wheelpicker.widgets.WheelDatePicker;
import com.baogetv.app.BaseFragment;
import com.baogetv.app.R;
import com.baogetv.app.model.usercenter.event.DateSelectEvent;
import com.chalilayang.scaleview.ScaleCalculator;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class DatePickFragment extends BaseFragment implements WheelPicker.OnItemSelectedListener {

    private static final String TAG = DatePickFragment.class.getSimpleName();
    private static final String KEY_DATE_INFO = "DATE_INFO";
    private View confirmBtn;
    private View cancelBtn;
    private View root;

    private WheelPicker yearPicker;
    private WheelPicker monthPicker;
    private WheelPicker dayPicker;

    private List<String> yearList;
    private List<String> monthList;
    private List<String> dayList;

    private int year;
    private int month;
    private int day;

    private DateSelectEvent selectEvent;
    public static DatePickFragment newInstance(DateSelectEvent event) {
        DatePickFragment fragment = new DatePickFragment();
        Bundle args = new Bundle();
        args.putParcelable(KEY_DATE_INFO, event);
        fragment.setArguments(args);
        return fragment;
    }

    private void init() {
        yearList = new ArrayList<>();
        for (int index = 1900; index < 2500; index ++) {
            yearList.add(String.valueOf(index));
        }
        if (selectEvent != null && !TextUtils.isEmpty(selectEvent.year)) {
            int y = Integer.parseInt(selectEvent.year);
            year = y - Integer.parseInt(yearList.get(0));
        } else {
            year = 2020 - 1900;
        }
        monthList = new ArrayList<>();
        for (int index = 1; index <= 12; index ++) {
            monthList.add(String.valueOf(index));
        }
        if (selectEvent != null && !TextUtils.isEmpty(selectEvent.month)) {
            int y = Integer.parseInt(selectEvent.month);
            month = y - Integer.parseInt(monthList.get(0));
        } else {
            month = 0;
        }
        dayList = new ArrayList<>();
        for (int index = 1; index <= 31; index ++) {
            dayList.add(String.valueOf(index));
        }
        if (selectEvent != null && !TextUtils.isEmpty(selectEvent.day)) {
            int y = Integer.parseInt(selectEvent.day);
            day = y - Integer.parseInt(dayList.get(0));
        } else {
            day = 0;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            selectEvent = getArguments().getParcelable(KEY_DATE_INFO);
        }
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
        confirmBtn = view.findViewById(R.id.confirm_btn);
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().post(
                        new DateSelectEvent(
                                yearList.get(year), monthList.get(month), dayList.get(day)));
            }
        });
        cancelBtn = view.findViewById(R.id.cancel_btn);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectEvent == null) {
                    EventBus.getDefault().post(new DateSelectEvent(null, null, null));
                } else {
                    EventBus.getDefault().post(selectEvent);
                }
            }
        });
        int textSize = ScaleCalculator.getInstance(mActivity).scaleTextSize(36);
        yearPicker = (WheelPicker) view.findViewById(R.id.year_picker);
        yearPicker.setItemTextSize(textSize);
        yearPicker.setData(yearList);
        yearPicker.setOnItemSelectedListener(this);
        yearPicker.post(new Runnable() {
            @Override
            public void run() {
                yearPicker.setSelectedItemPosition(year);
            }
        });
        monthPicker = (WheelPicker) view.findViewById(R.id.month_picker);
        monthPicker.setItemTextSize(textSize);
        monthPicker.setData(monthList);
        monthPicker.setOnItemSelectedListener(this);
        monthPicker.post(new Runnable() {
            @Override
            public void run() {
                monthPicker.setSelectedItemPosition(month);
            }
        });
        dayPicker = (WheelPicker) view.findViewById(R.id.day_picker);
        dayPicker.setItemTextSize(textSize);
        dayPicker.setData(dayList);
        dayPicker.setOnItemSelectedListener(this);
        dayPicker.post(new Runnable() {
            @Override
            public void run() {
                dayPicker.setSelectedItemPosition(day);
            }
        });
    }

    @Override
    public void onItemSelected(WheelPicker picker, Object data, int position) {
        if (picker == yearPicker) {
            year = position;
        } else if (picker == monthPicker) {
            month = position;
        } else if (picker == dayPicker) {
            day = position;
        }
        Log.i(TAG, "onItemSelected: " + yearList.get(year) + " " + monthList.get(month) + " " + dayList.get(day));
    }
}
