package com.baogetv.app.model.usercenter.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.TextView;

import com.baogetv.app.BaseTitleActivity;
import com.baogetv.app.R;
import com.baogetv.app.apiinterface.UserApiService;
import com.baogetv.app.bean.AdviceBean;
import com.baogetv.app.bean.ResponseBean;
import com.baogetv.app.model.usercenter.LoginManager;
import com.baogetv.app.net.CustomCallBack;
import com.baogetv.app.net.RetrofitManager;

import retrofit2.Call;

public class WebReadActivity extends BaseTitleActivity {

    public static final int REQUEST_CODE_WEB = 2313;
    public static final String KEY_WEB_TITLE = "WEB_TITLE";
    public static final String KEY_URL = "URL";
    private WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleActivity(getIntent().getStringExtra(KEY_WEB_TITLE));
        String url = getIntent().getStringExtra(KEY_URL);
        if (!TextUtils.isEmpty(url)) {
            webView = (WebView) findViewById(R.id.web_view);
            webView.setWebViewClient(new MyWebViewClient());
            webView.getSettings().setJavaScriptEnabled(true);
            webView.setInitialScale(80);
            webView.loadUrl(url);
        }
    }
    @Override
    protected int getRootView() {
        return R.layout.activity_web_read;
    }

    private class MyWebViewClient extends WebViewClient
    {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url)
        {
            view.loadUrl(url);
            return true;
        }
    }
}
