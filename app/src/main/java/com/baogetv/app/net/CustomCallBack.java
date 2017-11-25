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
        if (responseBean != null) {
            onSuccess(responseBean.getData());
        } else {
            onFailed("responseBean == null");
        }
    }

    @Override
    public final void onFailure(Call<ResponseBean<T>> call, Throwable t) {
        onFailed(t.getMessage());
    }

    public abstract void onSuccess(T data);
    public abstract void onFailed(String error);
}
