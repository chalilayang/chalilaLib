package com.baogetv.app.model.usercenter.fragment;


import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baogetv.app.BaseFragment;
import com.baogetv.app.R;
import com.baogetv.app.apiinterface.FileUploadService;
import com.baogetv.app.apiinterface.UserApiService;
import com.baogetv.app.bean.ImageUploadBean;
import com.baogetv.app.bean.ResponseBean;
import com.baogetv.app.model.usercenter.GlideImageLoader;
import com.baogetv.app.model.usercenter.LoginManager;
import com.baogetv.app.model.usercenter.event.DateSelectEvent;
import com.baogetv.app.model.usercenter.event.ImageSelectEvent;
import com.baogetv.app.net.CustomCallBack;
import com.baogetv.app.net.RetrofitManager;
import com.yancy.gallerypick.config.GalleryConfig;
import com.yancy.gallerypick.config.GalleryPick;
import com.yancy.gallerypick.inter.IHandlerCallBack;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;

import static com.baogetv.app.model.usercenter.activity.UserInfoActivity
        .PERMISSIONS_REQUEST_READ_CONTACTS;

public class ImageGetFragment extends BaseFragment implements IHandlerCallBack {

    private static final String TAG = ImageGetFragment.class.getSimpleName();
    private GalleryConfig galleryConfig;
    private List<String> path = new ArrayList<>();

    private View cameraBtn;
    private View chooseBtn;
    private View cancelBtn;
    private View root;
    public static ImageGetFragment newInstance() {
        ImageGetFragment fragment = new ImageGetFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    private void init() {
        galleryConfig = new GalleryConfig.Builder()
                .imageLoader(new GlideImageLoader())    // ImageLoader 加载框架（必填）
                .iHandlerCallBack(this)     // 监听接口（必填）
                .provider("com.baogetv.app.fileprovider")
                .pathList(path)                         // 记录已选的图片
                .multiSelect(false)                      // 是否多选   默认：false
                .crop(false)                             // 快捷开启裁剪功能，仅当单选 或直接开启相机时有效
                .crop(false, 1, 1, 500, 500)
                .isShowCamera(true)                     // 是否现实相机按钮  默认：false
                .filePath("/Gallery/Pictures")          // 图片存放路径
                .build();
    }

    public void onRequestPermissionsSuccess() {
        GalleryPick.getInstance().setGalleryConfig(galleryConfig).open(getActivity());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (root == null) {
            root = inflater.inflate(R.layout.fragment_image_get, container, false);
            initView(root);
        }
        return root;
    }

    private void initView(View root) {
        cameraBtn = root.findViewById(R.id.camera_btn);
        cameraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initPermissions();
            }
        });
        chooseBtn = root.findViewById(R.id.choose_btn);
        chooseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initPermissions();
            }
        });
        cancelBtn = root.findViewById(R.id.cancel_btn);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new ImageSelectEvent(null));
            }
        });
    }

    // 授权管理
    private void initPermissions() {
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                showShortToast("请在 设置-应用管理 中开启此应用的储存授权。");
            } else {
                ActivityCompat.requestPermissions(
                        getActivity(),
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSIONS_REQUEST_READ_CONTACTS);
            }
        } else {
            GalleryPick.getInstance().setGalleryConfig(galleryConfig).open(getActivity());
        }
    }

    @Override
    public void onSuccess(List<String> photoList) {
        Log.i(TAG, "onSuccess: ");
        if (photoList != null && photoList.size() > 0) {
            EventBus.getDefault().postSticky(new ImageSelectEvent(photoList.get(0)));
        }
    }

    @Override
    public void onCancel() {
        Log.i(TAG, "onCancel: ");
    }

    @Override
    public void onFinish() {
        Log.i(TAG, "onFinish: ");
    }

    @Override
    public void onError() {
        Log.i(TAG, "onError: ");
    }
}
