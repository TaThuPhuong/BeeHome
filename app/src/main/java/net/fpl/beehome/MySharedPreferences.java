package net.fpl.beehome;

import android.content.Context;
import android.content.SharedPreferences;

public class MySharedPreferences {
    // Ghi nhớ tài khoản, mật khẩu đăng nhập trước đó
    private static final String MSP = "MSP_EMAIL_PASSWORD";
    public static final String USER_KEY = "user";
    public static final String PASSWORD_KEY = "password";
    public static final String STATUS_KEY = "status";
    private Context context;


    public MySharedPreferences(Context context) {
        this.context = context;
    }
//
    public void saveUser(String key, String user) {
        SharedPreferences preferences = context.getSharedPreferences(MSP, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, user);
        editor.apply();
    }
    public void savePassword(String key, String pass) {
        SharedPreferences preferences = context.getSharedPreferences(MSP, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, pass);
        editor.apply();
    }


    public void saveStatus(String key, boolean b) {
        SharedPreferences preferences = context.getSharedPreferences(MSP, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key, b);
        editor.apply();
    }

    public String getUser(String key) {
        SharedPreferences preferences = context.getSharedPreferences(MSP, Context.MODE_PRIVATE);
        return preferences.getString(key, "");
    }
    public String getPassword(String key) {
        SharedPreferences preferences = context.getSharedPreferences(MSP, Context.MODE_PRIVATE);
        return preferences.getString(key, "");
    }
    public boolean getStatus(String key) {
        SharedPreferences preferences = context.getSharedPreferences(MSP, Context.MODE_PRIVATE);
        return preferences.getBoolean(key, false);
    }

    public void clear() {
        SharedPreferences preferences = context.getSharedPreferences(MSP, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();
    }
}
