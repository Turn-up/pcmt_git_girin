package com.pcm.pcmmanager.manager;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.pcm.pcmmanager.MyApplication;
import com.pcm.pcmmanager.data.CommonCodeList;
import com.pcm.pcmmanager.data.CommonRegionList;

import java.util.List;

/**
 * Created by LG on 2016-06-09.
 */
public class PropertyManager {
    private static PropertyManager instance;
    public static List<CommonCodeList> commonCodeList;
    public static List<CommonRegionList> commonRegionLists;
    private static Boolean expertCheck;
    private static Boolean first_user = false;
    private static Boolean pushYN = true;

    public static PropertyManager getInstance() {
        if (instance == null) {
            instance = new PropertyManager();
        }
        return instance;
    }

    SharedPreferences mPrefs;
    SharedPreferences.Editor mEditor;

    private PropertyManager() {
        mPrefs = PreferenceManager.getDefaultSharedPreferences(MyApplication.getContext());
        mEditor = mPrefs.edit();
    }

    private static final String FIELD_EMAIL = "email";

    public void setEmail(String email) {
        mEditor.putString(FIELD_EMAIL, email);
        mEditor.commit();
    }

    public String getEmail() {
        return mPrefs.getString(FIELD_EMAIL, "");
    }

    private static final String FIELD_PASSWORD = "password";

    public void setPassword(String password) {
        mEditor.putString(FIELD_PASSWORD, password);
        mEditor.commit();
    }

    public String getPassword() {
        return mPrefs.getString(FIELD_PASSWORD, "");
    }

    private static final String FIELD_AUTHORIZATION_TOKEN = "authorization";

    public void setAuthorizationToken(String authorizationToken) {
        mEditor.putString(FIELD_AUTHORIZATION_TOKEN, authorizationToken);
        mEditor.commit();
    }

    public String getAuthorizationToken() {
        return mPrefs.getString(FIELD_AUTHORIZATION_TOKEN, "");
    }

    public static List<CommonRegionList> getCommonRegionLists() {
        return commonRegionLists;
    }

    public static List<CommonCodeList> getCommonCodeList() {
        return commonCodeList;
    }


    private static final String FIELD_REGISTRATION_TOKEN = "registrationToken";

    public void setRegistrationToken(String registrationToken) {
        mEditor.putString(FIELD_REGISTRATION_TOKEN, registrationToken);
        mEditor.commit();
    }

    public String getRegistrationToken() {
        return mPrefs.getString(FIELD_REGISTRATION_TOKEN, "");
    }

    public void setExpertCheck(Boolean check) {
        expertCheck = check;
    }

    public Boolean getExpertCheck() {
        return expertCheck;
    }

    public void setFirstUser(Boolean check) {
        first_user = check;
    }

    public Boolean getFirstUser() {
        return first_user;
    }

    private static final String FIELD_PUSH = "push";

    public Boolean getPushYN() {
        return mPrefs.getBoolean(FIELD_PUSH, true);
    }

    public void setPushYN(Boolean pushYN) {
        mEditor.putBoolean(FIELD_PUSH, pushYN);
        mEditor.commit();
    }
}

