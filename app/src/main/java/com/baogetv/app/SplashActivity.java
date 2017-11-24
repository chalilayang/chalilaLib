package com.baogetv.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.baogetv.app.downloader.DownloadService;
import com.baogetv.app.downloader.config.Config;
import com.baogetv.app.model.homepage.HomePageActivity;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        try {
            Config config = new Config();
            //set download quantity at the same time.
            config.setDownloadThread(3);
            //set each download info thread number
            config.setEachDownloadThread(2);
            config.setConnectTimeout(10000);
            config.setReadTimeout(10000);
            DownloadService.getDownloadManager(this.getApplicationContext(), config);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = new Intent(this, HomePageActivity.class);
        startActivity(intent);
        finish();
    }
}
