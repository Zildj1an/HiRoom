package com.cgaxtr.hiroom.fragment;


import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.cgaxtr.hiroom.R;


public class SearchFragment extends Fragment {

    public interface ButtonsListeners {
        void onSearchClickedEvent();
        void onAddClickedEvent();
    }


    private Button search, addAdvertisement;
    private EditText where;
    private ButtonsListeners mListener;

    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v  = inflater.inflate(R.layout.fragment_search, container, false);

        search = v.findViewById(R.id.seaSearchButton);
        addAdvertisement = v.findViewById(R.id.seaAddAdvertisement);
        where = v.findViewById(R.id.seaSearch);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onSearchClickedEvent();
            }
        });

        addAdvertisement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onAddClickedEvent();
            }
        });

        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mListener = (ButtonsListeners) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement ButtonsListeners");
        }
    }
}
