package com.cgaxtr.hiroom.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cgaxtr.hiroom.R;
import com.cgaxtr.hiroom.activity.AdvertisementActivity;
import com.cgaxtr.hiroom.activity.ProfileActivity;
import com.cgaxtr.hiroom.model.Advertisement;

import java.util.List;
import java.util.Locale;

public class AdvertisementAdapter extends RecyclerView.Adapter<AdvertisementAdapter.MyViewHolder> {

    private static final String KEY_USER = "key_user";
    private static final String KEY_ADVERTISEMENT = "key_advertisement";

    private List<Advertisement> list;
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder{

        private ImageView image;
        private TextView title, user, phone, price, numPic;

        public MyViewHolder(View itemView) {
            super(itemView);

            this.image = itemView.findViewById(R.id.cardAdvImgage);
            this.title = itemView.findViewById(R.id.cardAdvTitle);
            this.user = itemView.findViewById(R.id.cardAdvUser);
            this.phone = itemView.findViewById(R.id.cardAdvPhone);
            this.price = itemView.findViewById(R.id.cardAdvPrice);
            this.numPic = itemView.findViewById(R.id.cardAdvNumPic);
        }
    }

    public AdvertisementAdapter(Context context){
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_advertisement, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        final Advertisement ad = list.get(position);

        holder.user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, ProfileActivity.class);
                i.putExtra(KEY_USER, list.get(position).getOwnerId());
                context.startActivity(i);
            }
        });

        holder.phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_DIAL);
                i.setData(Uri.parse("tel:" + ad.getOwnerPhone()));
                context.startActivity(i);
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, AdvertisementActivity.class);
                i.putExtra(KEY_ADVERTISEMENT, list.get(position));
                context.startActivity(i);
            }
        });


        holder.title.setText(ad.getTitle());
        holder.price.setText(String.format(Locale.getDefault(),"%d %s", ad.getPrice(), holder.itemView.getContext().getResources().getString(R.string.coin)));
        holder.phone.setText(String.format(Locale.getDefault(), "%d", ad.getOwnerPhone()));
        holder.user.setText(ad.getOwnerName());
        holder.numPic.setText(String.format(Locale.getDefault(), "%d", ad.getImages().size()));

        if(ad.getImages().size() != 0)
            Glide.with(context).load(ad.getImages().get(0)).into(holder.image);
        else
            holder.image.setImageResource(R.drawable.image_not_found);
    }

    @Override
    public int getItemCount() {
        return (list != null ? list.size() : 0);
    }

    public void setList(List<Advertisement> l){
        this.list = l;
        notifyDataSetChanged();
    }

}
