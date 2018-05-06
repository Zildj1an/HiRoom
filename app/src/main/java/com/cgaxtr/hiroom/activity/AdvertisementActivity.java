package com.cgaxtr.hiroom.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.cgaxtr.hiroom.R;
import com.cgaxtr.hiroom.adapter.ViewPagerAdapter;
import com.cgaxtr.hiroom.model.Advertisement;

import java.util.Locale;

public class AdvertisementActivity extends AppCompatActivity {

    private static final String KEY_USER = "key_user";

    private ViewPager imageSwitcher;
    private ViewPagerAdapter adapter;
    private TextView title, price, size, floor, description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Locale current = getResources().getConfiguration().locale;

        setContentView(R.layout.activity_advertisement);

        Advertisement ad = getIntent().getParcelableExtra(KEY_USER);

        imageSwitcher = findViewById(R.id.advImgSwitch);
        //TODO pass images list to adapter
        adapter = new ViewPagerAdapter(null, this);
        imageSwitcher.setAdapter(adapter);

        title = findViewById(R.id.advTitle);
        price = findViewById(R.id.advPrice);
        size = findViewById(R.id.advSize);
        floor = findViewById(R.id.advFloor);
        description = findViewById(R.id.advDescription);

        title.setText(String.format(current, "%s %s", getResources().getString(R.string.room), ad.getAddress()));
        price.setText(String.format(current, "%d %s", ad.getPrice(), getResources().getString(R.string.price_month)));
        size.setText(String.format(current, "%d %s", ad.getSize(), getResources().getString(R.string.meter)));
        floor.setText(String.format(current,"%d%s", ad.getFloor(), getResources().getString(R.string.floor)));
        description.setText(ad.getDescription());


    }
}
