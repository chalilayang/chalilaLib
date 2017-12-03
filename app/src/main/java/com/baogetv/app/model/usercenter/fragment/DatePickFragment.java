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
import com.baogetv.app.apiinterface.UserApiService;
import com.baogetv.app.bean.ResponseBean;
import com.baogetv.app.model.usercenter.LoginManager;
import com.baogetv.app.model.usercenter.event.DateSelectEvent;
import com.baogetv.app.model.usercenter.event.SexSelectEvent;
import com.baogetv.app.net.CustomCallBack;
import com.baogetv.app.net.RetrofitManager;
import com.chalilayang.scaleview.ScaleCalculator;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

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

    public void setSelectEvent(DateSelectEvent event) {
        Bundle args = new Bundle();
        args.putParcelable(KEY_DATE_INFO, event);
        setArguments(args);
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
        Log.i(TAG, "init: " + year+ " "+month + " "+day);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            selectEvent = getArguments().getParcelable(KEY_DATE_INFO);
            Log.i(TAG, "onCreate: " + selectEvent.year + selectEvent.month + selectEvent.day);
        }
        init();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_date_pick, container, false);
        initView(root);
        return root;
    }

    private void initView(View view) {
        Log.i(TAG, "initView: ");
        confirmBtn = view.findViewById(R.id.confirm_btn);
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "onClick: " + yearList.get(year) + " " + monthList.get(month) + " " +dayList.get(day));
                String yearStr = yearList.get(year);
                String monthStr = monthList.get(month);
                String dayStr = dayList.get(day);
                modifyUserInfo(yearStr, monthStr, dayStr);
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

    private void modifyUserInfo(final String year, final String month, final String day) {
        UserApiService userApiService
                = RetrofitManager.getInstance().createReq(UserApiService.class);
        String token = LoginManager.getUserToken(mActivity);
        String date = year + "-" + month + "-" + day;
        Call<ResponseBean<List<Object>>> call = userApiService.editUserDetail(
                null, null, null, date, null, null, null, null, null, null, token);
        if (call != null) {
            Log.i(TAG, "modifyUserInfo: call.enqueue");
            call.enqueue(new CustomCallBack<List<Object>>() {
                @Override
                public void onSuccess(List<Object> data) {
                    Log.i(TAG, "onSuccess: ");
                    showShortToast("success");
                    EventBus.getDefault().post(new DateSelectEvent(year, month, day));
                }

                @Override
                public void onFailed(String error) {
                    showShortToast(error);
                }
            });
        }
    }
}
