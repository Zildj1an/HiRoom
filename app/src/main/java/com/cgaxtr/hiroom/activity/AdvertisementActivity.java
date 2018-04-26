package com.cgaxtr.hiroom.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.cgaxtr.hiroom.R;
import com.cgaxtr.hiroom.adapter.ViewPagerAdapter;

public class AdvertisementActivity extends AppCompatActivity {

    private ViewPager imageSwitcher;
    private ViewPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advertisement);

        imageSwitcher = findViewById(R.id.advImgSwitch);
        adapter = new ViewPagerAdapter(null, this);
        imageSwitcher.setAdapter(adapter);
    }
}
