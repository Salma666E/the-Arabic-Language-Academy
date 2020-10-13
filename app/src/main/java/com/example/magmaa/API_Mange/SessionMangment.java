package com.example.magmaa.API_Mange;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.magmaa.pages.Login.LoginActivity;

import java.util.HashMap;

public class SessionMangment {

    private SharedPreferences mSharedPreferences;
    private  SharedPreferences.Editor mEditor;
    private Context mContext;
    private int PRIVATE_MODE = 0;
    private Intent mIntent;

    private static final String PREF_NAME = "MyApp";

    public static final String IS_LOGIN = "IsLoggedIn";
    public static final String KEY_FNAME = "key_fname";
    public static final String KEY_EMAIL = "key_email";
    public static final String KEY_IMAGE = "key_image";
    public static final String KEY_IMAGEPATH = "key_image_path";
    /**
     * Constructor
     **/
    public SessionMangment(Context context) {
        this.mContext = context;
        mSharedPreferences = mContext.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        mEditor = mSharedPreferences.edit();
    }
    /**
     * Save User Details
     **/
    public void createLoginSession(Boolean status, String uname, String email) {
        mEditor.putBoolean(IS_LOGIN, status);
        mEditor.putString(KEY_FNAME, uname);
        mEditor.putString(KEY_EMAIL, email);
        mEditor.commit();
    }
    public void saveEdit(String image)
    {
        mEditor.putString(KEY_IMAGE, image);
        mEditor.commit();
    }
    /**
     * Get stored session data
     */
    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> users = new HashMap<String, String>();
        users.put(KEY_FNAME, mSharedPreferences.getString(KEY_FNAME, null));
        users.put(KEY_EMAIL, mSharedPreferences.getString(KEY_EMAIL, null));
        users.put(KEY_IMAGE, mSharedPreferences.getString(KEY_IMAGE, null));
        users.put(KEY_IMAGEPATH, mSharedPreferences.getString(KEY_IMAGEPATH, null));
        return users;
    }


    public void clearData(){
        mEditor.clear();
        mEditor.commit();
    }

    public void logoutUser() {
        mEditor.clear();
        mEditor.commit();
        mIntent = new Intent(mContext, LoginActivity.class);
        mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(mIntent);
    }

    public boolean isLoggedIn() {
        return mSharedPreferences.getBoolean(IS_LOGIN, false);
    }
}
