package vn.chapp.vn24h.data.prefs;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import javax.inject.Inject;

import vn.chapp.vn24h.di.ApplicationContext;
import vn.chapp.vn24h.models.auth.UserDefault;

public class Preference implements PreferenceHelper {

    private final String BEARER_HEADER = "Bearer ";
    private SharedPreferences sharedPreferencesAccount;
    private SharedPreferences sharedPreferencesSetting;
    private String PREFS_ACCOUNT = "PREFS_ACCOUNT";
    private String PREFS_SETTING = "PREFS_SETTING";

    private final String KEY_USER_DEFAULT = "KEY_USER_DEFAULT";
    private final String KEY_IS_LOGIN = "KEY_IS_LOGIN";
    private final String KEY_FIREBASE_TOKEN = "KEY_FIREBASE_TOKEN";

    public static Preference instance;
    private Gson gason;

    public static Preference buildInstance(Context context) {
        if (instance == null) {
            instance = new Preference(context);
        }
        return instance;
    }

    @Inject
    public Preference(@ApplicationContext Context context) {
        sharedPreferencesAccount = context.getSharedPreferences(PREFS_ACCOUNT, Context.MODE_PRIVATE);
        sharedPreferencesSetting = context.getSharedPreferences(PREFS_SETTING, Context.MODE_PRIVATE);
        gason = new Gson();
    }

    @Override
    public void setUserDefault(UserDefault userDefault) {
        String jsonData = gason.toJson(userDefault);
        sharedPreferencesAccount.edit().putString(KEY_USER_DEFAULT,jsonData).apply();
    }

    @Override
    public UserDefault getUserDefault() {
        String jsonData = sharedPreferencesAccount.getString(KEY_USER_DEFAULT,"");
        if (!TextUtils.isEmpty(jsonData)) {
            try {
                return gason.fromJson(jsonData,new TypeToken<UserDefault>(){}.getType());
            } catch (JsonSyntaxException e) {
                return null;
            }
        }
        return null;
    }

    @Override
    public void setIsLogin(boolean isLogin) {
        sharedPreferencesAccount.edit().putBoolean(KEY_IS_LOGIN,isLogin).apply();
    }

    @Override
    public boolean isLogin() {
        return sharedPreferencesAccount.getBoolean(KEY_IS_LOGIN,false);
    }

    @Override
    public void logout() {
        sharedPreferencesAccount.edit().clear().apply();
    }

    @Override
    public void setTokenFirebase(String tokenFirebase) {
        sharedPreferencesSetting.edit().putString(KEY_FIREBASE_TOKEN, tokenFirebase).apply();
    }

    @Override
    public String getTokenFirebase() {
        return sharedPreferencesSetting.getString(KEY_FIREBASE_TOKEN, "");
    }
}
