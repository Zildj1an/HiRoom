package com.cgaxtr.hiroom.activity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.SeekBar;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.cgaxtr.hiroom.R;
import com.cgaxtr.hiroom.SessionManager;
import com.cgaxtr.hiroom.model.User;
import com.cgaxtr.hiroom.network.VolleySingleton;
import com.cgaxtr.hiroom.utils.UrlsAPI;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class EditProfileActivity extends AppCompatActivity {

    private static final String KEY_USER = "key_user";

    private EditText name, surname, date, city, phone, description, email;
    private SeekBar partying, organized, athlete, freak, sociable, active;
    private RadioGroup gender, smoker;
    private Button save;
    private SessionManager sessionManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        Toolbar toolbar = findViewById(R.id.editToolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        User user = getIntent().getParcelableExtra(KEY_USER);
        sessionManager = new SessionManager(getApplicationContext());

        final Calendar calendar = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener dpd = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "dd/MM/yyyy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, getResources().getConfiguration().locale);
                date.setText(sdf.format(calendar.getTime()));


            }
        };

        date = findViewById(R.id.editBirthdate);

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(EditProfileActivity.this, dpd, calendar
                        .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });

        name = findViewById(R.id.editName);
        surname = findViewById(R.id.editSurname);
        email = findViewById(R.id.editEmail);
        date = findViewById(R.id.editBirthdate);
        phone = findViewById(R.id.editPhone);
        city = findViewById(R.id.editCity);
        gender = findViewById(R.id.editRadioGroupGender);
        smoker = findViewById(R.id.editRadioGroupSmoker);
        partying = findViewById(R.id.editPartying);
        organized = findViewById(R.id.editOrganized);
        athlete = findViewById(R.id.editAthlete);
        freak = findViewById(R.id.editFreak);
        sociable = findViewById(R.id.editSociable);
        active = findViewById(R.id.editActive);
        description = findViewById(R.id.editAboutMe);
        save = findViewById(R.id.editSave);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    sendToServer();
                } catch (JSONException e) {
                    Snackbar.make(v,getResources().getString(R.string.editError), Snackbar.LENGTH_LONG).show();
                }
            }
        });

        populateUserView(user);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    private void populateUserView(User u){
        name.setText(u.getName());
        surname.setText(u.getSurname());
        email.setText(u.getEmail());
        date.setText(u.getBirthDate());
        phone.setText(String.format(getResources().getConfiguration().locale, "%d", u.getPhoneNumber()));
        city.setText(u.getCity());

        if(u.getGender() != null) {
            switch (u.getGender()) {
                case "male":
                    gender.check(R.id.male);
                    break;
                case "female":
                    gender.check(R.id.female);
                    break;
            }
        }

        if (u.getSmoker()){
            smoker.check(R.id.smoker);
        }else{
            smoker.check(R.id.noSmoker);
        }

        partying.setProgress(u.getPartying());
        organized.setProgress(u.getOrganized());
        athlete.setProgress(u.getAthlete());
        freak.setProgress(u.getFreak());
        sociable.setProgress(u.getSociable());
        active.setProgress(u.getActive());
        description.setText(u.getDescription());
    }

    private void sendToServer() throws JSONException {
        String url = UrlsAPI.UPDATE_USER.replace("{id}", Integer.toString(sessionManager.getId()));
        final User u = getDataFromForm();
        final Gson gson = new Gson();
        JSONObject jsonUser = new JSONObject(gson.toJson(u));

        JsonObjectRequest rq = new JsonObjectRequest(Request.Method.PUT, url, jsonUser, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                sessionManager.updateSession(u);
                Snackbar.make(findViewById(android.R.id.content), "Usuario actualizado", Snackbar.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Snackbar.make(findViewById(android.R.id.content), "Ups algo salió mal", Snackbar.LENGTH_LONG).show();
            }
        }) {
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

        VolleySingleton.getInstance(getApplicationContext()).getRequestQueue().add(rq);

    }

    private User getDataFromForm(){
        User u = new User();

        u.setName(name.getText().toString());
        u.setSurname(surname.getText().toString());
        u.setEmail(email.getText().toString());
        u.setBirthDate(date.getText().toString());
        u.setPhoneNumber(Integer.parseInt(phone.getText().toString()));
        u.setCity(city.getText().toString());

        switch (gender.getCheckedRadioButtonId()){
            case R.id.male:
                u.setGender("male");
                break;
            case R.id.female:
                u.setGender("female");
                break;
        }

        if(smoker.getCheckedRadioButtonId() == R.id.smoker){
            u.setSmoker(true);
        }else{
            u.setSmoker(false);
        }

        u.setPartying(partying.getProgress());
        u.setOrganized(organized.getProgress());
        u.setAthlete(athlete.getProgress());
        u.setFreak(freak.getProgress());
        u.setSociable(sociable.getProgress());
        u.setActive(active.getProgress());
        u.setDescription(description.getText().toString());

        return u;
    }
}
