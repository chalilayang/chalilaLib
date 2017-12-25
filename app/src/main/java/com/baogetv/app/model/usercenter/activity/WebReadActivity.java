package com.baogetv.app.model.usercenter.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.baogetv.app.BaseTitleActivity;
import com.baogetv.app.R;

public class WebReadActivity extends BaseTitleActivity {

    public static final int REQUEST_CODE_WEB = 2313;
    public static final String KEY_WEB_TITLE = "WEB_TITLE";
    public static final String KEY_URL = "URL";
    public static final String KEY_WITH_TITLE = "WITH_TITLE";
    private WebView webView;
    private boolean withTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleActivity(getIntent().getStringExtra(KEY_WEB_TITLE));
        String url = getIntent().getStringExtra(KEY_URL);
        withTitle = getIntent().getBooleanExtra(KEY_WITH_TITLE, true);
        if (!TextUtils.isEmpty(url)) {
            webView = (WebView) findViewById(R.id.web_view);
            webView.setWebChromeClient(new WebChromeClient());
            WebSettings webSettings = webView.getSettings();
            webSettings.setJavaScriptEnabled(true);
//            webSettings.setUseWideViewPort(true);
//            webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
//            webSettings.setLoadWithOverviewMode(true);
            webSettings.setAllowFileAccess(true); //设置可以访问文件
            webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
            webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
            webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式
            webView.loadUrl(url);
        }
        if (!withTitle) {
            findViewById(R.id.title_container).setVisibility(View.GONE);
        }
    }
    @Override
    protected int getRootView() {
        return R.layout.activity_web_read;
    }
}
