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
import com.cgaxtr.hiroom.model.User;
import com.cgaxtr.hiroom.network.VolleySingleton;
import com.cgaxtr.hiroom.utils.UrlsAPI;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {

    private static final String KEY_DIALOG = "DIALOG";

    private EditText name, email, password;
    private TextView login;
    private Button register;
    private SessionManager sessionManager;
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        if(savedInstanceState != null && savedInstanceState.getBoolean(KEY_DIALOG))
            createDialog();

        sessionManager = new SessionManager(getApplicationContext());

        name = findViewById(R.id.regName);
        email = findViewById(R.id.regEmail);
        password = findViewById(R.id.regPassword);

        login = findViewById(R.id.regSignIn);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(i);
                finish();
            }
        });

        register = findViewById(R.id.regRegister);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createDialog();
                String user = name.getText().toString();
                String mail = email.getText().toString();
                String pass = password.getText().toString();
                if(validate(user, mail, pass)){
                    User u = new User();
                    u.setName(user);
                    u.setEmail(mail);
                    u.setPass(pass);
                    try {
                        register(u);
                    } catch (JSONException e) {
                        Snackbar.make(findViewById(android.R.id.content), getResources().getString(R.string.jsonError), Snackbar.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    private boolean validate(String name, String email, String pass){
        if(name == null || name.isEmpty()){
            this.name.setError(getResources().getString(R.string.error_name));
            return false;
        }

        if( email == null || email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            this.email.setError(getResources().getString(R.string.error_email));
            return false;
        }

        if(pass.isEmpty() || pass.length() < 4){
            this.password.setError(getResources().getString(R.string.error_pass));
            return false;
        }

        return true;
    }

    private void register(User u) throws JSONException {

        final Gson gson = new Gson();
        String user = gson.toJson(u);
        JSONObject data = new JSONObject(user);

        JsonObjectRequest registerRequest = new JsonObjectRequest(Request.Method.POST, UrlsAPI.REGISTER_PATH, data, new Response.Listener<JSONObject>() {
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
                        case 409:
                            Snackbar.make(findViewById(android.R.id.content), getResources().getString(R.string.alreadyExists), Snackbar.LENGTH_LONG).show();
                            break;
                        default:
                            Snackbar.make(findViewById(android.R.id.content), getResources().getString(R.string.networkError), Snackbar.LENGTH_LONG).show();
                    }
                }
                dialog.dismiss();
            }
        });

        VolleySingleton.getInstance(getApplicationContext()).getRequestQueue().add(registerRequest);
    }

    private void startMainActivity(){
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
        finish();
    }

    private void createDialog(){
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(RegisterActivity.this);
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
