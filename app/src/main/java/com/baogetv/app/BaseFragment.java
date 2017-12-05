package com.baogetv.app;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.baogetv.app.customview.CustomToastUtil;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by chalilayang on 2017/10/21.
 */

public class BaseFragment extends Fragment {
    protected Activity mActivity;
    public boolean useEventBus() {
        return false;
    }

    /**
     * Called when a fragment is first attached to its context.
     * {@link #onCreate(Bundle)} will be called after this.
     *
     * @param context
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = getActivity();
    }

    /**
     * Called when the fragment is no longer attached to its activity.  This is called after
     * {@link #onDestroy()}, except in the cases where the fragment instance is retained across
     * Activity re-creation (see {@link #setRetainInstance(boolean)}), in which case it is called
     * after {@link #onStop()}.
     */
    @Override
    public void onDetach() {
        super.onDetach();
        mActivity = null;
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

    public void showShortToast(String msg) {
        if (mActivity != null && !mActivity.isFinishing()) {
            CustomToastUtil.makeShort(mActivity, msg);
        }
    }
}
