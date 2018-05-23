package com.cgaxtr.hiroom.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.cgaxtr.hiroom.R;

import java.util.List;

public class ViewPagerAdapter extends PagerAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private List<String> sliderImg;

    public ViewPagerAdapter(List list, Context context){
        this.sliderImg = list;
        this.context = context;

        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return sliderImg != null? sliderImg.size() : 0;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

        View myImageLayout = layoutInflater.inflate(R.layout.slide_layout, container, false);
        ImageView myImage = myImageLayout.findViewById(R.id.slideImageView);
        Glide.with(context).load(sliderImg.get(position)).into(myImage);
        container.addView(myImageLayout, 0);
        return myImageLayout;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
