package vn.chapp.vn24h.data.prefs;


import vn.chapp.vn24h.models.auth.UserDefault;

public interface PreferenceHelper {
    void setUserDefault(UserDefault userDefault);
    UserDefault getUserDefault();

    void setIsLogin(boolean isLogin);
    boolean isLogin();

    void logout();

    void setTokenFirebase(String tokenFirebase);
    String getTokenFirebase();
}
