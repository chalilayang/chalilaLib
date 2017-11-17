package com.baogetv.app;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by chalilayang on 2017/10/21.
 */

public class BaseFragment extends Fragment {
    public boolean useEventBus() {
        return false;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onStart() {
        super.onStart();
        if (useEventBus()) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (useEventBus()) {
            EventBus.getDefault().unregister(this);
        }
    }
}
