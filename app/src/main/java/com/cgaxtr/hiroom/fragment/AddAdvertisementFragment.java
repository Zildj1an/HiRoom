package com.cgaxtr.hiroom.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.cgaxtr.hiroom.R;
import com.cgaxtr.hiroom.SessionManager;
import com.cgaxtr.hiroom.model.Advertisement;
import com.cgaxtr.hiroom.network.VolleySingleton;
import com.cgaxtr.hiroom.utils.UrlsAPI;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class AddAdvertisementFragment extends Fragment {

    private EditText title, city, address, number, floor, size, price, description;
    private Spinner type;
    private Button button;
    public AddAdvertisementFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_advertisement, container, false);

        title = v.findViewById(R.id.addTitle);
        city = v.findViewById(R.id.addCity);
        address = v.findViewById(R.id.addAdress);
        number = v.findViewById(R.id.addNumber);
        floor = v.findViewById(R.id.addFloor);
        size = v.findViewById(R.id.addSize);
        price = v.findViewById(R.id.addPrice);
        description = v.findViewById(R.id.addDescription);
        type = v.findViewById(R.id.addType);
        button = v.findViewById(R.id.addSubmit);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()){
                    try {
                        sendToServer();
                    } catch (JSONException e) {
                        //TODO
                        e.printStackTrace();
                    }
                }
            }
        });

        return v;
    }

    private boolean validate(){
        ArrayList<EditText> toValidate = new ArrayList<>();
        toValidate.add(title);
        toValidate.add(city);
        toValidate.add(address);
        toValidate.add(number);
        toValidate.add(floor);
        toValidate.add(size);
        toValidate.add(price);
        toValidate.add(description);

        for (EditText editText : toValidate){
            if(editText.getText().toString().isEmpty()){
                editText.setError("prueba");
                return false;
            }
        }

        return true;
    }

    private void sendToServer() throws JSONException {
        Advertisement ad = populateAdvertisement();
        JSONObject jsonAd = new JSONObject(new Gson().toJson(ad));

        JsonObjectRequest rq = new JsonObjectRequest(Request.Method.PUT, UrlsAPI.ADVERTISEMENT, jsonAd, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //TODO
                Log.d("VOLLEY", "response");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //TODO
                Log.d("VOLLEY", "error");
            }
        }){
            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                try {
                    String json = new String(response.data, "UTF-8");

                    if (json.length() == 0) {
                        return Response.success(null, HttpHeaderParser.parseCacheHeaders(response));
                    } else {
                        return super.parseNetworkResponse(response);
                    }
                } catch (UnsupportedEncodingException e) {
                    return Response.error(new ParseError(e));
                }
            }
        };

        VolleySingleton.getInstance(getContext()).getRequestQueue().add(rq);
    }

    private Advertisement populateAdvertisement(){
        Advertisement ad = new Advertisement();

        ad.setOwnerId(new SessionManager(getContext()).getId());
        ad.setTitle(title.getText().toString());
        ad.setCity(city.getText().toString());
        ad.setAddress(address.getText().toString());
        ad.setNumber(Integer.parseInt(number.getText().toString()));
        ad.setFloor(Integer.parseInt(floor.getText().toString()));
        ad.setSize(Integer.parseInt(size.getText().toString()));
        ad.setPrice(Integer.parseInt(price.getText().toString()));
        ad.setDescription(description.getText().toString());
        ad.setType(type.getSelectedItem().toString());

        return ad;
    }
}
