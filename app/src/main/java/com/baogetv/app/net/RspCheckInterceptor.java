package com.baogetv.app.net;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * Created by chalilayang on 2017/10/18.
 */

public class RspCheckInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Response response = chain.proceed(chain.request());
//        try {
//            ResponseBody rspBody = response.body();
//            JSONObject jsonObject = new JSONObject(InterceptorUtils.getRspData(rspBody));
//            int status = jsonObject.getInt("status");
//            if (status < 200 || status >= 300){
//                throw new IOException(jsonObject.getString("msg"));
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//            throw new IOException("parase data error");
//        }catch (Exception e){
//            if (e instanceof IOException){
//                throw (IOException)e;
//            }
//        }
        return response;
    }
}
