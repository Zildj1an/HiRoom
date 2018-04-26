package com.cgaxtr.hiroom.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cgaxtr.hiroom.R;
import com.cgaxtr.hiroom.adapter.AdvertisementAdapter;
import com.cgaxtr.hiroom.model.Advertisement;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListAdvertisementsFragment extends Fragment {

    private List<Advertisement> list;
    private RecyclerView mRecyclerView;
    private AdvertisementAdapter adapter;


    public ListAdvertisementsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //load data from server
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list_advertisements, container, false);

        mRecyclerView = rootView.findViewById(R.id.recycleView);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        list = new ArrayList<>();
        adapter = new AdvertisementAdapter(getContext(),list);
        mRecyclerView.setAdapter(adapter);

        for(int i = 0; i <=10; i++){
            list.add(new Advertisement());
        }
        adapter.notifyDataSetChanged();

        return rootView;
    }

}
