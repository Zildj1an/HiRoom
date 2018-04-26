package com.cgaxtr.hiroom.fragment;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.cgaxtr.hiroom.R;

import java.util.List;


public class SearchFragment extends Fragment {

    private Button search, addAdvertisement;
    private EditText where;


    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v  = inflater.inflate(R.layout.fragment_search, container, false);

        search = v.findViewById(R.id.seaSearchButton);
        addAdvertisement = v.findViewById(R.id.seaAddAdvertisement);
        where = v.findViewById(R.id.seaSearch);

        return v;
    }

}
