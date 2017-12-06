package com.baogetv.app.net;

import com.baogetv.app.bean.ResponseBean;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by chalilayang on 2017/11/25.
 */

public abstract class CustomCallBack<T> implements Callback<ResponseBean<T>> {
    @Override
    public final void onResponse(Call<ResponseBean<T>> call, Response<ResponseBean<T>> response) {
        ResponseBean<T> responseBean = response.body();
        if (responseBean != null && responseBean.getStatus() == 1) {
            onSuccess(responseBean.getData(), responseBean.getMsg(), responseBean.getStatus());
        } else {
            if (responseBean != null) {
                onFailed(responseBean.getMsg(), responseBean.getStatus());
            } else {
                onFailed("responseBean == null", responseBean.getStatus());
            }
        }
    }

    @Override
    public final void onFailure(Call<ResponseBean<T>> call, Throwable t) {
        onFailed(t.getMessage(), -1);
    }

    public abstract void onSuccess(T data, String msg, int state);
    public abstract void onFailed(String error, int state);
}
