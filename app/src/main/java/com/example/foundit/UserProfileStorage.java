package com.example.foundit;

import android.content.Context;
import android.content.SharedPreferences;

public class UserProfileStorage {
    private static final String PREFS_NAME = "user_profile_prefs";
    private static final String KEY_NAME = "name";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_IMAGE_URI = "image_uri";

    public static void saveUserProfile(Context context, UserProfile profile) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(KEY_NAME, profile.getName());
        editor.putString(KEY_EMAIL, profile.getEmail());
        editor.putString(KEY_IMAGE_URI, profile.getProfileImageUri());
        editor.apply();
    }

    public static UserProfile getUserProfile(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        UserProfile profile = new UserProfile();
        profile.setName(prefs.getString(KEY_NAME, ""));
        profile.setEmail(prefs.getString(KEY_EMAIL, ""));
        profile.setProfileImageUri(prefs.getString(KEY_IMAGE_URI, ""));
        return profile;
    }
}
