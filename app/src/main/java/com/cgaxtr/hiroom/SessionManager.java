package com.cgaxtr.hiroom;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.cgaxtr.hiroom.model.User;

public class SessionManager {

    private static final String KEY_LOGGEDIN = "logedIn";
    private static final String KEY_JWT = "jwt";
    private static final String KEY_ID = "id";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_NAME = "name";
    private static final String KEY_IMG_PATH = "userImg";

    private SharedPreferences sharedPreferences;
    private Context context;
    private Editor editor;

    public SessionManager(Context context){
        this.context = context;
        sharedPreferences = this.context.getSharedPreferences("name", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void setLogin(User user, String jwt){

        if (jwt == null ||user == null)
            throw new IllegalStateException("User not set");

        editor.putBoolean(KEY_LOGGEDIN, true);
        editor.putString(KEY_JWT, jwt);
        editor.putInt(KEY_ID, user.getId());
        editor.putString(KEY_NAME, user.getName());
        editor.putString(KEY_EMAIL, user.getEmail());
        editor.putString(KEY_IMG_PATH, user.getPathImg());

        editor.commit();
    }

    public boolean isLogedIn(){
        return sharedPreferences.getBoolean(KEY_LOGGEDIN,false);
    }

    public String getJWT(){
        return sharedPreferences.getString(KEY_JWT, null);
    }

    public int getId(){
        return sharedPreferences.getInt(KEY_ID, 0);
    }

    public void logOut(){
        editor.clear();
        editor.commit();
    }

    public User getUserData(){

        String name = sharedPreferences.getString(KEY_NAME,null);
        String email = sharedPreferences.getString(KEY_EMAIL, null);
        String path = sharedPreferences.getString(KEY_IMG_PATH, null);
        int id = sharedPreferences.getInt(KEY_ID, 0);

        User u = new User(name, email, path);
        u.setId(id);

        return u;
    }

    public void updateSession(User user){
        editor.putInt(KEY_ID, user.getId());
        editor.putString(KEY_NAME, user.getName());
        editor.putString(KEY_EMAIL, user.getEmail());
        editor.putString(KEY_IMG_PATH, user.getPathImg());

        editor.commit();
    }
}
