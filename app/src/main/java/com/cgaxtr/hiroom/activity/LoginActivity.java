package com.cgaxtr.hiroom.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.cgaxtr.hiroom.R;
import com.cgaxtr.hiroom.SessionManager;
import com.cgaxtr.hiroom.model.Credential;
import com.cgaxtr.hiroom.model.User;
import com.cgaxtr.hiroom.network.VolleySingleton;
import com.cgaxtr.hiroom.utils.UrlsAPI;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    private static final String KEY_DIALOG = "DIALOG";

    private EditText username, password;
    private Button login;
    private TextView signUp;
    private SessionManager sessionManager;
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if(savedInstanceState != null && savedInstanceState.getBoolean(KEY_DIALOG))
            createDialog();

        sessionManager = new SessionManager(getApplicationContext());

        if (sessionManager.isLogedIn())
            startMainActivity();

        username = findViewById(R.id.logEmail);
        password = findViewById(R.id.logPassword);

        login = findViewById(R.id.logLogin);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = username.getText().toString().toLowerCase();
                String pass = password.getText().toString();
                if (validate(user, pass)){
                    createDialog();
                    Credential credential = new Credential(user, pass);
                    try {
                        login(credential);
                    } catch (JSONException e) {
                        Snackbar.make(findViewById(android.R.id.content), getResources().getString(R.string.jsonError), Snackbar.LENGTH_LONG).show();
                    }
                }
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

    private void startMainActivity(){
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
        finish();
    }

    private void login(Credential credential) throws JSONException {
        final Gson gson = new Gson();
        String jsonCredential = gson.toJson(credential);
        JSONObject j = new JSONObject(jsonCredential);

        JsonObjectRequest loginRequest = new JsonObjectRequest
                (Request.Method.POST, UrlsAPI.LOGIN_PATH, j, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String token = response.getString("token");
                            String user = response.getString("user");
                            User u = gson.fromJson(user, User.class);
                            sessionManager.setLogin(u, token);
                            startMainActivity();
                        } catch (JSONException e) {
                            Snackbar.make(findViewById(android.R.id.content), getResources().getString(R.string.jsonError), Snackbar.LENGTH_LONG).show();
                        }
                        dialog.dismiss();
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        NetworkResponse networkResponse = error.networkResponse;
                        if(networkResponse != null && networkResponse.data != null) {
                            switch (networkResponse.statusCode) {
                                case 401:
                                    Snackbar.make(findViewById(android.R.id.content), getResources().getString(R.string.invalidUser), Snackbar.LENGTH_LONG).show();
                                    break;
                                default:
                                    Snackbar.make(findViewById(android.R.id.content), getResources().getString(R.string.networkError), Snackbar.LENGTH_LONG).show();
                                 break;
                            }
                        }
                        dialog.dismiss();
                    }
                });

        VolleySingleton.getInstance(getApplicationContext()).getRequestQueue().add(loginRequest);
    }

    private boolean validate(String email, String pass){

        if( email == null || email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            username.setError(getResources().getString(R.string.error_email));
            return false;
        }

        if(pass.isEmpty() || pass.length() < 4){
            password.setError(getResources().getString(R.string.error_pass));
            return false;
        }

        return true;
    }

    private void createDialog(){
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(LoginActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.dialog_login, null);
        mBuilder.setView(mView);
        dialog = mBuilder.create();
        dialog.show();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if (dialog != null && dialog.isShowing()){
            outState.putBoolean(KEY_DIALOG, true);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(dialog != null)
            dialog.dismiss();
    }
}
