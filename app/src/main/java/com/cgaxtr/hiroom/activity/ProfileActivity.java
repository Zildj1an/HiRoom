package com.cgaxtr.hiroom.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.cgaxtr.hiroom.R;
import com.cgaxtr.hiroom.SessionManager;
import com.cgaxtr.hiroom.model.User;
import com.cgaxtr.hiroom.network.VolleySingleton;
import com.cgaxtr.hiroom.utils.UrlsAPI;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private FloatingActionButton fab;
    private SessionManager sessionManager;
    private TextView name, city, description;
    private ImageView gender, smoker;
    private ProgressBar partying, organized, athlete, freak, sociable, active;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        toolbar = findViewById(R.id.proToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sessionManager = new SessionManager(getApplicationContext());

        fab = findViewById(R.id.proFab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        name = findViewById(R.id.proName);
        city = findViewById(R.id.proCity);
        gender = findViewById(R.id.proGender);
        smoker = findViewById(R.id.proSmoker);
        description = findViewById(R.id.proDescription);
        partying = findViewById(R.id.proProgressBarPartying);
        organized = findViewById(R.id.proProgressBarOrganized);
        athlete = findViewById(R.id.proProgressBarAthlete);
        freak = findViewById(R.id.proProgressBarFreak);
        sociable = findViewById(R.id.proProgressBarSociable);
        active = findViewById(R.id.proProgressBarActive);

        loadUserData(sessionManager.getId());
    }

    private void loadUserData(int id){
        String url = UrlsAPI.GET_USER.replace("{id}", Integer.toString(id));
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                User u = new Gson().fromJson(response.toString(), User.class);
                populateData(u);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("ERROR", error.getCause().toString());
            }
        }) {
            @Override
            public Map<String, String> getHeaders(){
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + sessionManager.getJWT());
                return headers;
            }

        };
        VolleySingleton.getInstance(getApplicationContext()).getRequestQueue().add(request);
    }

    private void populateData(User user){

        getSupportActionBar().setTitle(user.getName());

        name.setText(user.getName());
        city.setText(user.getCity());

        if(user.getGender() != null){
            switch (user.getGender()){
                case "male":
                    gender.setImageDrawable(getDrawable(R.drawable.ic_male));
                    break;
                case "female":
                    gender.setImageDrawable(getDrawable(R.drawable.ic_female));
                    break;
            }
        }

        if(user.getSmoker() != null) {
            if (user.getSmoker())
                smoker.setImageDrawable(getDrawable(R.drawable.ic_smoker_black_24dp));
            else
                smoker.setImageDrawable(getDrawable(R.drawable.ic_no_smoker_red_24dp));
        }

        description.setText(user.getDescription());
        partying.setProgress(user.getPartying());
        organized.setProgress(user.getOrganized());
        athlete.setProgress(user.getAthlete());
        freak.setProgress(user.getFreak());
        sociable.setProgress(user.getSociable());
        active.setProgress(user.getActive());
    }
}
