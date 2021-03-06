package com.baogetv.app.apiinterface;

import com.baogetv.app.bean.ImageUploadBean;
import com.baogetv.app.bean.ResponseBean;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by chalilayang on 2017/11/27.
 */

public interface FileUploadService {
    /**
     * 上传一张图片
     * @return
     */
    @Multipart
    @POST("index.php?s=/File/uploadPicture")
    Call<ResponseBean<ImageUploadBean>>
    uploadImage(@Part MultipartBody.Part part);
}
