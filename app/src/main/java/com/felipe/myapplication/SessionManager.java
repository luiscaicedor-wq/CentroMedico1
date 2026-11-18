package com.felipe.myapplication;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {

    private static final String PREF_NAME = "UserSession";
    private static final String KEY_USUARIO = "usuario";
    private static final String KEY_ROL = "rol";
    private static final String KEY_IS_LOGGED_IN = "is_logged_in";

    private final SharedPreferences pref;
    private final SharedPreferences.Editor editor;

    public SessionManager(Context context) {
        this.pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        this.editor = pref.edit();
    }

    public void guardarSesion(String usuario, String rol) {
        editor.putString(KEY_USUARIO, usuario);
        editor.putString(KEY_ROL, rol);
        editor.putBoolean(KEY_IS_LOGGED_IN, true);
        editor.apply();
    }

    public String obtenerUsuario() {
        return pref.getString(KEY_USUARIO, "");
    }

    public String obtenerRol() {
        return pref.getString(KEY_ROL, "");
    }

    public boolean isLoggedIn() {
        return pref.getBoolean(KEY_IS_LOGGED_IN, false);
    }

    public void cerrarSesion() {
        editor.clear();
        editor.apply();
    }

    public String getUsuario() {
        return obtenerUsuario();
    }

    public String getRol() {
        return obtenerRol();
    }

    public void logout() {
        cerrarSesion();
    }
}
