package com.baogetv.app.net;

import com.baogetv.app.constant.AppConstance;
import com.baogetv.app.constant.UrlConstance;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by chalilayang on 2017/10/18.
 */

public class RetrofitManager {
    private static class Holder {
        public static RetrofitManager INSTANCE = new RetrofitManager();
    }

    public static RetrofitManager getInstance() {
        return Holder.INSTANCE;
    }

    private Retrofit mRetrofit;

    private RetrofitManager() {
        initRetrofit();
    }

    private void initRetrofit() {
        HttpLoggingInterceptor LoginInterceptor = new HttpLoggingInterceptor();
        LoginInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if (AppConstance.DEBUG) {
            builder.addInterceptor(LoginInterceptor); //添加retrofit日志打印
        }
        builder.connectTimeout(15, TimeUnit.SECONDS);
        builder.readTimeout(20, TimeUnit.SECONDS);
        builder.writeTimeout(20, TimeUnit.SECONDS);
        builder.retryOnConnectionFailure(true);
        OkHttpClient client = builder.build();

        mRetrofit = new Retrofit.Builder()
                .baseUrl(UrlConstance.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
    }

    public <T> T createReq(Class<T> reqServer) {
        return mRetrofit.create(reqServer);
    }
}
