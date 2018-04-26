package com.cgaxtr.hiroom.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.cgaxtr.hiroom.R;
import com.cgaxtr.hiroom.SessionManager;
import com.cgaxtr.hiroom.network.VolleySingleton;
import com.cgaxtr.hiroom.model.Credential;
import com.cgaxtr.hiroom.model.User;
import com.cgaxtr.hiroom.utils.UrlsAPI;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    //debug stuff--------------------------------------------
    private final static String USER = "USER";
    private final static String PASS = "PASS";
    private final static String RESPONSE = "VOLLEY_RESPONSE";
    private final static String ERROR = "VOLLEY_ERROR";
    private final static String JSON = "JSON";
    private final static String PATH = "PATH";
    //--------------------------------------------------------

    private EditText username, password;
    private Button login;
    private TextView signUp;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sessionManager = new SessionManager(getApplicationContext());

        if (sessionManager.isLogedIn())
            startMainActivity();

        username = findViewById(R.id.logEmail);
        password = findViewById(R.id.logPassword);

        login = findViewById(R.id.logLogin);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder mBuilder = new AlertDialog.Builder(LoginActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.dialog_login, null);
                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();
                dialog.show();

                String user = username.getText().toString().toLowerCase();
                String pass = password.getText().toString();
/*
                if (validate(user, pass)){
                    Credential credential = new Credential(user, pass);
                    try {
                        login(credential);
                    } catch (JSONException e) {
                        Log.e(JSON, "Error parse JSON", e);
                    }
                }
*/



                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startMainActivity();
                        //dialog.dismiss();
                    }
                }, 2000);

            }
        });

        signUp = findViewById(R.id.logSignUp);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
               startActivity(i);
               finish();
            }
        });
    }

    //debug stuff------------------------------
    private void startMainActivity(){
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
        finish();
    }
//----------------------------------------------

    private void login(Credential credential) throws JSONException {

        Gson gson = new Gson();
        String jsonCredential = gson.toJson(credential);
        JSONObject j = new JSONObject(jsonCredential);

        Log.d(JSON, j.toString());
        Log.d(PATH, UrlsAPI.LOGIN_PATH);

        JsonObjectRequest loginRequest = new JsonObjectRequest
                (Request.Method.POST, UrlsAPI.LOGIN_PATH, j, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(RESPONSE, response.toString());
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(ERROR, "Error: " + error.getMessage());
                    }
                });

        VolleySingleton.getInstance(getApplicationContext()).getRequestQueue().add(loginRequest);
    }

    private boolean validate(String email, String pass){

        if( email == null ||email.isEmpty() || Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            username.setError(getResources().getString(R.string.error_email));
            return false;
        }

        if(pass.isEmpty() || pass.length() < 8){
            password.setError(getResources().getString(R.string.error_pass));
            return false;
        }

        return true;
    }

    private User retrieveDataUser(String JWT){





        return null;
    }
}
