package com.cgaxtr.hiroom.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.cgaxtr.hiroom.R;
import com.cgaxtr.hiroom.utils.SessionManager;
import com.cgaxtr.hiroom.model.User;
import com.cgaxtr.hiroom.network.VolleySingleton;
import com.cgaxtr.hiroom.utils.UrlsAPI;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity {

    private static final String KEY_USER = "key_user";

    private Toolbar toolbar;
    private FloatingActionButton fab;
    private SessionManager sessionManager;
    private TextView name, city, description, phone, age;
    private ImageView gender, smoker, avatar;
    private ProgressBar partying, organized, athlete, freak, sociable, active;
    private User user;
    private Button report;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        toolbar = findViewById(R.id.proToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(getResources().getString(R.string.title_activity_profile));

        sessionManager = new SessionManager(getApplicationContext());
        id = getIntent().getIntExtra(KEY_USER,sessionManager.getId());

        fab = findViewById(R.id.proFab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), EditProfileActivity.class);
                i.putExtra(KEY_USER, user);
                startActivity(i);
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
        phone = findViewById(R.id.proPhone);
        age = findViewById(R.id.proAge);
        report = findViewById(R.id.proReport);
        avatar = findViewById(R.id.proBackground);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadUserData(id);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    private void loadUserData(int id){
        String url = UrlsAPI.GET_USER.replace("{id}", Integer.toString(id));

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                User u = new Gson().fromJson(response.toString(), User.class);
                user = u;
                populateData(u);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Log.d("ERROR", error.getCause().toString());
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
        RequestOptions options = new RequestOptions().error(R.drawable.user);
        Glide.with(this).load(user.getPathImg()).apply(options).into(avatar);

        String surname = user.getSurname() == null ? "" : user.getSurname();
        name.setText(String.format(getResources().getConfiguration().locale, "%s %s", user.getName(), surname));
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
        phone.setText(String.format(getResources().getConfiguration().locale,"%d", user.getPhoneNumber()));

        if(user.getBirthDate() != null)
            age.setText(String.format(getResources().getConfiguration().locale, "%d %s", getAge(user.getBirthDate()), getResources().getString(R.string.age)));

        if(sessionManager.getId() == user.getId()){
            report.setVisibility(View.INVISIBLE);
            fab.setVisibility(View.VISIBLE);
        }else{
            fab.setVisibility(View.INVISIBLE);
        }
    }

    private int getAge(String date){
        String format = "dd/MM/yyyy";
        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();
        SimpleDateFormat sdf = new  SimpleDateFormat(format);
        Date d = null;

        try {
            d = sdf.parse(date);
        } catch (ParseException e) {
            //TODO implement
            Log.d("DATE_ERROR", e.toString());
        }

        dob.setTime(d);
        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);
        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)){
            age--;
        }

        return age;
    }
}
