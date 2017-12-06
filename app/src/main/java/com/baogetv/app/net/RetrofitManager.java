package com.baogetv.app.net;

import android.content.Context;
import android.util.Log;

import com.baogetv.app.constant.AppConstance;
import com.baogetv.app.constant.UrlConstance;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static anet.channel.util.Utils.context;

/**
 * Created by chalilayang on 2017/10/18.
 */

public class RetrofitManager {

    public static RetrofitManager getInstance() {
        return instance;
    }
    private static volatile RetrofitManager instance;
    public static void init(Context context) {
        instance = new RetrofitManager(context);
    }

    private Retrofit mRetrofit;
    private RetrofitManager(Context context) {
        initRetrofit(context);
    }

    private void initRetrofit(Context mContext) {
        HttpLoggingInterceptor LoginInterceptor
                = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.i("chalilayang","OkHttp====Message:"+message);
            }
        });
        LoginInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if (AppConstance.DEBUG) {
            builder.addInterceptor(LoginInterceptor); //添加retrofit日志打印
        }
        builder.connectTimeout(15, TimeUnit.SECONDS);
        builder.readTimeout(20, TimeUnit.SECONDS);
        builder.writeTimeout(20, TimeUnit.SECONDS);
        builder.retryOnConnectionFailure(true);
        File cacheDir = new File(mContext.getCacheDir(), "response");
        Cache cache = new Cache(cacheDir, 1024 * 1024 * 10);
        builder.cache(cache);
        OkHttpClient client = builder.build();

        mRetrofit = new Retrofit.Builder()
                .baseUrl(UrlConstance.BASE_URL)
                .addConverterFactory(StringConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
    }

    public <T> T createReq(Class<T> reqServer) {
        return mRetrofit.create(reqServer);
    }
}
