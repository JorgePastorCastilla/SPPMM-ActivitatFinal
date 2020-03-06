package com.example.activitatfinalandroid;

import android.content.Context;
import android.content.SharedPreferences;

public class Preferencies {
    private static final String NOM_PREFERENCIES = "PreferenciesQuepassaEh";
    private final static String CLAU_CODIUSUARI = "id";
    private final static String CLAU_USER = "email";
    private final static String CLAU_PASSWD = "passwd";
    private final static String TOKEN = "token";
    private String codiusuari;
    private String user;
    private String password;
    private String token;
    private SharedPreferences prefs;

    public Preferencies(Context ctx) {
        this.prefs = ctx.getSharedPreferences(NOM_PREFERENCIES, ctx.MODE_PRIVATE);
        this.codiusuari = this.prefs.getString(CLAU_CODIUSUARI, "-1");
        this.user = this.prefs.getString(CLAU_USER, "");
        this.password = this.prefs.getString(CLAU_PASSWD, "");
        this.token = this.prefs.getString(TOKEN, "");
    }

    public String getCodiusuari() {
        return codiusuari;
    }

    public void setCodiusuari(String idempleat) {
        this.codiusuari = idempleat;
        SharedPreferences.Editor editor = this.prefs.edit();
        editor.putString(CLAU_CODIUSUARI, idempleat);
        editor.commit();
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
        SharedPreferences.Editor editor = this.prefs.edit();
        editor.putString(CLAU_USER, user);
        editor.commit();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
        SharedPreferences.Editor editor = this.prefs.edit();
        editor.putString(CLAU_PASSWD, password);
        editor.commit();
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
        SharedPreferences.Editor editor = this.prefs.edit();
        editor.putString(TOKEN, token);
        editor.commit();
    }
}