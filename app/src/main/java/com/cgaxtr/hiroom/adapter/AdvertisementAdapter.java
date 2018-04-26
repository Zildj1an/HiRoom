package com.cgaxtr.hiroom.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cgaxtr.hiroom.R;
import com.cgaxtr.hiroom.activity.AdvertisementActivity;
import com.cgaxtr.hiroom.model.Advertisement;

import java.util.List;

public class AdvertisementAdapter extends RecyclerView.Adapter<AdvertisementAdapter.MyViewHodler> {

    private List<Advertisement> list;
    private Context context;

    public class MyViewHodler extends RecyclerView.ViewHolder{

        private ImageView image;
        private TextView title, user, phone, price;

        public MyViewHodler(View itemView) {
            super(itemView);

            this.image = itemView.findViewById(R.id.cardAdvImgage);
            this.title = itemView.findViewById(R.id.cardAdvTitle);
            this.user = itemView.findViewById(R.id.cardAdvUser);
            this.phone = itemView.findViewById(R.id.cardAdvPhone);
            this.price = itemView.findViewById(R.id.cardAdvPrice);
        }
    }

    public AdvertisementAdapter(Context context, List<Advertisement> list){
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHodler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_advertisement, parent, false);
        return new MyViewHodler(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHodler holder, int position) {
        //fetch data from api and set into cardview items.

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, AdvertisementActivity.class);
                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return (list != null ? list.size() : 0);
    }

}
