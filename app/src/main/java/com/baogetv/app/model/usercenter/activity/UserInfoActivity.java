package com.baogetv.app.model.usercenter.activity;

import android.app.FragmentManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.app.FragmentTransaction;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import com.baogetv.app.BaseFragment;
import com.baogetv.app.BaseTitleActivity;
import com.baogetv.app.R;
import com.baogetv.app.apiinterface.FileUploadService;
import com.baogetv.app.apiinterface.UserApiService;
import com.baogetv.app.bean.GradeBean;
import com.baogetv.app.bean.ImageUploadBean;
import com.baogetv.app.bean.ResponseBean;
import com.baogetv.app.bean.UserDetailBean;
import com.baogetv.app.constant.AppConstance;
import com.baogetv.app.model.usercenter.LoginManager;
import com.baogetv.app.model.usercenter.event.BodyInfoSelectEvent;
import com.baogetv.app.model.usercenter.event.DateSelectEvent;
import com.baogetv.app.model.usercenter.event.ImageSelectEvent;
import com.baogetv.app.model.usercenter.customview.MineLineItemView;
import com.baogetv.app.model.usercenter.event.SexSelectEvent;
import com.baogetv.app.model.usercenter.fragment.BodyInfoSelectFragment;
import com.baogetv.app.model.usercenter.fragment.DatePickFragment;
import com.baogetv.app.model.usercenter.fragment.ImageGetFragment;
import com.baogetv.app.model.usercenter.fragment.SexSelectFragment;
import com.baogetv.app.net.CustomCallBack;
import com.baogetv.app.net.RetrofitManager;
import com.baogetv.app.util.TimeUtil;
import com.bumptech.glide.Glide;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;

import static com.baogetv.app.constant.AppConstance.KEY_LEVEL_LIST;
import static com.baogetv.app.constant.AppConstance.KEY_USER_DETAIL_BEAN;
import static com.baogetv.app.constant.AppConstance.REQUEST_CODE_LOGIN_ACTIVITY;
import static com.baogetv.app.model.usercenter.activity.NameModifyActivity.REQUEST_CODE_NAME_MODIFY;

public class UserInfoActivity extends BaseTitleActivity implements View.OnClickListener {

    private static final String TAG = UserInfoActivity.class.getSimpleName();
    private MineLineItemView userIconLine;
    private MineLineItemView userGradeLine;
    private MineLineItemView userNickNameLine;
    private MineLineItemView userSexLine;
    private MineLineItemView userBirthdayLine;
    private MineLineItemView userBodyLine;
    private MineLineItemView userSignatureLine;

    private BaseFragment curFloatingFragment;
    private ImageGetFragment imageSelectFragment;
    private DatePickFragment datePickFragment;
    private SexSelectFragment sexSelectFragment;
    private BodyInfoSelectFragment bodyInfoSelectFragment;
    private UserDetailBean userDetailBean;
    private List<GradeBean> gradeBeanList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleActivity(getString(R.string.user_info));
        userDetailBean = getIntent().getParcelableExtra(KEY_USER_DETAIL_BEAN);
        gradeBeanList = getIntent().getParcelableArrayListExtra(KEY_LEVEL_LIST);
        Log.i(TAG, "onCreate: " + gradeBeanList.size());
        init();
    }

    private void init() {
        userIconLine = (MineLineItemView) findViewById(R.id.user_icon);
        userIconLine.setOnClickListener(this);
        userGradeLine = (MineLineItemView) findViewById(R.id.user_grade);
        userGradeLine.setOnClickListener(this);
        userNickNameLine = (MineLineItemView) findViewById(R.id.user_nick_name);
        userNickNameLine.setOnClickListener(this);
        userSexLine = (MineLineItemView) findViewById(R.id.user_sex);
        userSexLine.setOnClickListener(this);
        userBirthdayLine = (MineLineItemView) findViewById(R.id.user_birthday);
        userBirthdayLine.setOnClickListener(this);
        userBirthdayLine.setVersion(userDetailBean.getBirthday());
        userBodyLine = (MineLineItemView) findViewById(R.id.user_body_info);
        userBodyLine.setOnClickListener(this);
        userSignatureLine = (MineLineItemView) findViewById(R.id.user_signature);
        userSignatureLine.setOnClickListener(this);
        freshInfo();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.user_icon:
                showOrHideImageSelectFragment(true);
                break;
            case R.id.user_grade:
                Intent intent = new Intent(this, UserGradeDescActivity.class);
                intent.putExtra(AppConstance.KEY_USER_DETAIL_BEAN, userDetailBean);
                startActivity(intent);
                break;
            case R.id.user_nick_name:
                startNameIntroActivity();
                break;
            case R.id.user_sex:
                showOrHideSexFragment(true);
                break;
            case R.id.user_birthday:
                showOrHideDatePickFragment(true);
                break;
            case R.id.user_body_info:
                showOrHideBodyFragment(true);
                break;
            case R.id.user_signature:
                startNameIntroActivity();
                break;
        }
    }

    private void showOrHideImageSelectFragment(boolean flag) {
        if (flag) {
            if (imageSelectFragment == null) {
                imageSelectFragment = ImageGetFragment.newInstance();
            }
            FragmentManager manager = getFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.floating_fragment_container, imageSelectFragment);
            transaction.commitAllowingStateLoss();
            curFloatingFragment = imageSelectFragment;
        } else {
            if (imageSelectFragment != null && imageSelectFragment.isAdded()) {
                FragmentManager manager = getFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.remove(imageSelectFragment);
                transaction.commitAllowingStateLoss();
                curFloatingFragment = null;
            }
        }
    }

    private void showOrHideDatePickFragment(boolean flag) {
        if (flag) {
            if (datePickFragment == null) {
                String year = TimeUtil.getYear(userDetailBean.getBirthday());
                String month = TimeUtil.getMonth(userDetailBean.getBirthday());
                String day = TimeUtil.getDay(userDetailBean.getBirthday());
                DateSelectEvent event = new DateSelectEvent(year, month, day);
                datePickFragment = DatePickFragment.newInstance(event);
            } else {
                String year = TimeUtil.getYear(userDetailBean.getBirthday());
                String month = TimeUtil.getMonth(userDetailBean.getBirthday());
                String day = TimeUtil.getDay(userDetailBean.getBirthday());
                DateSelectEvent event = new DateSelectEvent(year, month, day);
                datePickFragment.setSelectEvent(event);
            }
            FragmentManager manager = getFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.floating_fragment_container, datePickFragment);
            transaction.commitAllowingStateLoss();
            curFloatingFragment = datePickFragment;
        } else {
            if (datePickFragment != null && datePickFragment.isAdded()) {
                FragmentManager manager = getFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.remove(datePickFragment);
                transaction.commitAllowingStateLoss();
                curFloatingFragment = null;
            }
        }
    }

    private void showOrHideSexFragment(boolean flag) {
        if (flag) {
            SexSelectEvent event = new SexSelectEvent(userDetailBean.getSex());
            if (sexSelectFragment == null) {
                sexSelectFragment = SexSelectFragment.newInstance(event);
            } else {
                sexSelectFragment.setEvent(event);
            }
            FragmentManager manager = getFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.floating_fragment_container, sexSelectFragment);
            transaction.commitAllowingStateLoss();
            curFloatingFragment = sexSelectFragment;
        } else {
            if (sexSelectFragment != null && sexSelectFragment.isAdded()) {
                FragmentManager manager = getFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.remove(sexSelectFragment);
                transaction.commitAllowingStateLoss();
                curFloatingFragment = null;
            }
        }
    }

    private void showOrHideBodyFragment(boolean flag) {
        if (flag) {
            if (bodyInfoSelectFragment == null) {
                int height = Integer.parseInt(userDetailBean.getHeight());
                int weight = Integer.parseInt(userDetailBean.getWeight());
                int bodyFat = Integer.parseInt(userDetailBean.getBfr());
                BodyInfoSelectEvent event = new BodyInfoSelectEvent(height, weight, bodyFat);
                bodyInfoSelectFragment = BodyInfoSelectFragment.newInstance(event);
            } else {
                int height = Integer.parseInt(userDetailBean.getHeight());
                int weight = Integer.parseInt(userDetailBean.getWeight());
                int bodyFat = Integer.parseInt(userDetailBean.getBfr());
                BodyInfoSelectEvent event = new BodyInfoSelectEvent(height, weight, bodyFat);
                bodyInfoSelectFragment.setEvent(event);
            }
            FragmentManager manager = getFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.floating_fragment_container, bodyInfoSelectFragment);
            transaction.commitAllowingStateLoss();
            curFloatingFragment = bodyInfoSelectFragment;
        } else {
            if (bodyInfoSelectFragment != null && bodyInfoSelectFragment.isAdded()) {
                FragmentManager manager = getFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.remove(bodyInfoSelectFragment);
                transaction.commitAllowingStateLoss();
                curFloatingFragment = null;
            }
        }
    }

    private void startNameIntroActivity() {
        Intent intent = new Intent(this, NameModifyActivity.class);
        intent.putExtra(KEY_USER_DETAIL_BEAN, userDetailBean);
        startActivityForResult(intent, REQUEST_CODE_NAME_MODIFY);
    }

    private void hideFloatingView() {
        if (curFloatingFragment != null) {
            FragmentManager manager = getFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.remove(curFloatingFragment);
            transaction.commitAllowingStateLoss();
            curFloatingFragment = null;
        }
    }

    private void uploadFile(final String filePath) {
        File file = new File(filePath);
        RequestBody imageBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part imageBodyPart = MultipartBody.Part.createFormData("pic", file.getName(), imageBody);
        FileUploadService fileUploadService
                = RetrofitManager.getInstance().createReq(FileUploadService.class);
        Call<ResponseBean<ImageUploadBean>> call
                = fileUploadService.uploadImage(imageBodyPart);
        if (call != null) {
            call.enqueue(new CustomCallBack<ImageUploadBean>() {
                @Override
                public void onSuccess(ImageUploadBean data, String msg, int state) {
                    Log.i(TAG, "onSuccess: ");
                    showShortToast("图片上传success");
                    modifyUserInfo(data.getId());
                }

                @Override
                public void onFailed(String error, int state) {
                    showShortToast(error);
                }
            });
        }
    }

    private void modifyUserInfo(final String pic) {
        UserApiService userApiService
                = RetrofitManager.getInstance().createReq(UserApiService.class);
        String token = LoginManager.getUserToken(getApplicationContext());
        Call<ResponseBean<List<Object>>> call = userApiService.editUserDetail(
                pic, null, null, null, null, null, null, null, null, null, token);
        if (call != null) {
            Log.i(TAG, "modifyUserInfo: call.enqueue");
            call.enqueue(new CustomCallBack<List<Object>>() {
                @Override
                public void onSuccess(List<Object> data, String msg, int state) {
                    Log.i(TAG, "onSuccess: ");
                    showShortToast("头像修改success");
                    hideLoadingDialog();
                    fetchUserInfo();
                    setResult(RESULT_OK);
                }

                @Override
                public void onFailed(String error, int state) {
                    showShortToast(error);
                }
            });
        }
    }

    @Subscribe
    public void onImageEvent(ImageSelectEvent event) {
        Log.i(TAG, "onImageEvent: " + event.img);
        showOrHideImageSelectFragment(false);
        showLoadingDialog("uploading", "上传成功");
        uploadFile(event.img);
        Glide.with(this)
                .fromFile()
                .placeholder(R.mipmap.user_default_icon)
                .error(R.mipmap.user_default_icon)
                .load(new File(event.img))
                .dontAnimate()
                .into(userIconLine.getRightImageView());
    }

    @Subscribe
    public void onDateEvent(DateSelectEvent event) {
        showOrHideDatePickFragment(false);
        fetchUserInfo();
        setResult(RESULT_OK);
    }

    @Subscribe
    public void onSexEvent(SexSelectEvent event) {
        showOrHideSexFragment(false);
        fetchUserInfo();
        setResult(RESULT_OK);
    }

    @Subscribe
    public void onBodyFatEvent(BodyInfoSelectEvent event) {
        showOrHideBodyFragment(false);
        fetchUserInfo();
        setResult(RESULT_OK);
    }

    private void freshInfo() {
        if (!LoginManager.hasLogin(getApplicationContext())) {
            LoginManager.startLogin(this);
        } else {
            if (userDetailBean != null) {
                Log.i(TAG, "freshInfo: " + userDetailBean.getPic_url());
                Glide.with(this)
                        .load(userDetailBean.getPic_url())
                        .placeholder(R.mipmap.user_default_icon)
                        .error(R.mipmap.user_default_icon)
                        .dontAnimate()
                        .into(userIconLine.getRightImageView());
                userGradeLine.setUserLever(userDetailBean);
                userNickNameLine.setVersion(userDetailBean.getUsername());
                int sex = 0;
                try {
                    sex = Integer.parseInt(userDetailBean.getSex());
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
                switch (sex) {
                    case 0:
                        userSexLine.setVersion("保密");
                        break;
                    case 1:
                        userSexLine.setVersion("男");
                        break;
                    case 2:
                        userSexLine.setVersion("女");
                        break;
                }
                userBirthdayLine.setVersion(userDetailBean.getBirthday());
                String bodyInfo = userDetailBean.getHeight() + "cm-"
                        + userDetailBean.getWeight() + "kg-" + userDetailBean.getBfr() + "%bfr";
                userBodyLine.setVersion(bodyInfo);
            }
        }
    }

    @Override
    protected int getRootView() {
        return R.layout.activity_user_info;
    }
    @Override
    public void onBackPressed() {
        if (curFloatingFragment != null) {
            hideFloatingView();
            curFloatingFragment = null;
        } else {
            super.onBackPressed();
        }
    }
    public boolean useEventBus() {
        return true;
    }
    public static final int PERMISSIONS_REQUEST_READ_CONTACTS = 8;
    @Override
    public void onRequestPermissionsResult(
            int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST_READ_CONTACTS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (imageSelectFragment != null) {
                    imageSelectFragment.onRequestPermissionsSuccess();
                }
            }
        }
    }

    private void fetchUserInfo() {
        UserApiService userApiService
                = RetrofitManager.getInstance().createReq(UserApiService.class);
        String token = LoginManager.getUserToken(getApplicationContext());
        Call<ResponseBean<UserDetailBean>> call
                = userApiService.getUserDetail(token, null);
        if (call != null) {
            call.enqueue(new CustomCallBack<UserDetailBean>() {
                @Override
                public void onSuccess(UserDetailBean data, String msg, int state) {
                    userDetailBean = data;
                    freshInfo();
                    LoginManager.updateDetailBean(getApplicationContext(), data);
                }

                @Override
                public void onFailed(String error, int state) {
                    LoginManager.cleanUserToken(getApplicationContext());
                    userDetailBean = null;
                    showShortToast(error);
                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_NAME_MODIFY) {
            if (resultCode == RESULT_OK) {
                fetchUserInfo();
                setResult(RESULT_OK);
            }
        } else if (requestCode == REQUEST_CODE_LOGIN_ACTIVITY) {
            if (resultCode == RESULT_OK) {
                fetchUserInfo();
            }
        }
    }
}
