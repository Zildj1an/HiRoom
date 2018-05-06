package com.cgaxtr.hiroom.fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.cgaxtr.hiroom.R;
import com.cgaxtr.hiroom.adapter.AdvertisementAdapter;
import com.cgaxtr.hiroom.model.Advertisement;
import com.cgaxtr.hiroom.network.VolleySingleton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListAdvertisementsFragment extends Fragment {
    private final static String KEY_URL = "KEY_URL";

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
        if(getArguments() != null){
            String urlLoadData;
            urlLoadData = getArguments().getString(KEY_URL);
            fetchDataFromServer(urlLoadData);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list_advertisements, container, false);

        mRecyclerView = rootView.findViewById(R.id.recycleView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new AdvertisementAdapter(getContext());
        mRecyclerView.setAdapter(adapter);
        //String urlLoadData;

       // if(getArguments() != null){
            //urlLoadData = getArguments().getString(KEY_URL);
            //fetchDataFromServer(urlLoadData);
        //}

        return rootView;
    }

    private void fetchDataFromServer(String urlLoadData){
        JsonArrayRequest rq = new JsonArrayRequest(Request.Method.GET, urlLoadData, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d("RESPONSE", response.toString());
                Gson gson = new Gson();
                list = gson.fromJson(response.toString(), new TypeToken<List<Advertisement>>(){}.getType());
                Log.d("RESPONSE", list.toString());
                adapter.setList(list);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("ERROR", error.toString());
                //TO IMPLEMENT
            }
        });

        VolleySingleton.getInstance(getContext()).getRequestQueue().add(rq);
    }

}
