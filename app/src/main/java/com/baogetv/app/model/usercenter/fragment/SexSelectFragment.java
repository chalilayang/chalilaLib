package com.baogetv.app.model.usercenter.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aigestudio.wheelpicker.WheelPicker;
import com.aigestudio.wheelpicker.widgets.WheelDatePicker;
import com.baogetv.app.BaseFragment;
import com.baogetv.app.R;
import com.baogetv.app.model.usercenter.event.DateSelectEvent;
import com.baogetv.app.model.usercenter.event.SexSelectEvent;
import com.chalilayang.scaleview.ScaleCalculator;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class SexSelectFragment extends BaseFragment implements WheelPicker.OnItemSelectedListener {

    private static final String TAG = SexSelectFragment.class.getSimpleName();
    private static final String KEY_SEX_INFO = "SEX_INFO";
    private View confirmBtn;
    private View cancelBtn;
    private View root;

    private WheelPicker yearPicker;
    private WheelPicker monthPicker;
    private WheelPicker dayPicker;

    private List<String> sexList;

    private int sex;

    private SexSelectEvent selectEvent;
    public static SexSelectFragment newInstance(SexSelectEvent event) {
        SexSelectFragment fragment = new SexSelectFragment();
        Bundle args = new Bundle();
        args.putParcelable(KEY_SEX_INFO, event);
        fragment.setArguments(args);
        return fragment;
    }

    private void init() {
        sexList = new ArrayList<>();
        sexList.add(getString(R.string.secret));
        sexList.add(getString(R.string.sex_men));
        sexList.add(getString(R.string.sex_women));
        if (selectEvent != null) {
            sex = selectEvent.sex;
        } else {
            sex = 0;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            selectEvent = getArguments().getParcelable(KEY_SEX_INFO);
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
                EventBus.getDefault().post(new SexSelectEvent(sex));
            }
        });
        cancelBtn = view.findViewById(R.id.cancel_btn);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectEvent == null) {
                    EventBus.getDefault().post(new SexSelectEvent(0));
                } else {
                    EventBus.getDefault().post(selectEvent);
                }
            }
        });
        int textSize = ScaleCalculator.getInstance(mActivity).scaleTextSize(36);
        yearPicker = (WheelPicker) view.findViewById(R.id.year_picker);
        yearPicker.setVisibility(View.INVISIBLE);
        monthPicker = (WheelPicker) view.findViewById(R.id.month_picker);
        monthPicker.setItemTextSize(textSize);
        monthPicker.setData(sexList);
        monthPicker.setOnItemSelectedListener(this);
        monthPicker.post(new Runnable() {
            @Override
            public void run() {
                monthPicker.setSelectedItemPosition(sex);
            }
        });
        dayPicker = (WheelPicker) view.findViewById(R.id.day_picker);
        dayPicker.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onItemSelected(WheelPicker picker, Object data, int position) {
        sex = position;
    }
}
