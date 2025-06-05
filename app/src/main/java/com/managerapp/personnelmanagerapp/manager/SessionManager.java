package com.managerapp.personnelmanagerapp.manager;

import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SessionManager {
    private static final String PREF_NAME = "session_prefs";
    private static final String KEY_ROLE = "user_role";

    private final SharedPreferences sharedPreferences;

    @Inject
    public SessionManager (Context context) {
        this.sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public void saveRole(String role) {
        sharedPreferences.edit().putString(KEY_ROLE, role).apply();
    }

    public String getRole() {
        return sharedPreferences.getString(KEY_ROLE, null);
    }

    public boolean hasRole(String checkRole) {
        String role = getRole();
        return role != null && role.equalsIgnoreCase(checkRole);
    }
    public void clearSession() {
        sharedPreferences.edit().clear().apply();
    }
}
