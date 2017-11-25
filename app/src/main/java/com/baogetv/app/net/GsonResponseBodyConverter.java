package com.baogetv.app.net;

import com.baogetv.app.bean.ResponseBean;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Created by chalilayang on 2017/11/25.
 */

public class GsonResponseBodyConverter<T extends ResponseBean>
        implements Converter<ResponseBody, T> {
    @Override
    public T convert(ResponseBody value) throws IOException {
        return null;
    }
}
