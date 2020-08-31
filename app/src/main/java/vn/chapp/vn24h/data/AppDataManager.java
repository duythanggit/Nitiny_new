

package vn.chapp.vn24h.data;


import android.content.Context;

import javax.inject.Inject;
import javax.inject.Singleton;

import vn.chapp.vn24h.data.prefs.Preference;
import vn.chapp.vn24h.di.ApplicationContext;
import vn.chapp.vn24h.models.auth.UserDefault;

@Singleton
public class AppDataManager implements DataManager {

    private static final String TAG = AppDataManager.class.getSimpleName();

    private final Context mContext;
    private final Preference mPreferencesHelper;

    @Inject
    public AppDataManager(@ApplicationContext Context context,
                          Preference preferencesHelper) {
        mContext = context;
        mPreferencesHelper = preferencesHelper;
    }

    @Override
    public void setUserDefault(UserDefault userDefault) {
        mPreferencesHelper.setUserDefault(userDefault);
    }

    @Override
    public UserDefault getUserDefault() {
        return mPreferencesHelper.getUserDefault();
    }

    @Override
    public void setIsLogin(boolean isLogin) {
        mPreferencesHelper.setIsLogin(isLogin);
    }

    @Override
    public boolean isLogin() {
        return mPreferencesHelper.isLogin();
    }

    @Override
    public void logout() {
        mPreferencesHelper.logout();
    }

    @Override
    public void setTokenFirebase(String tokenFirebase) {
        mPreferencesHelper.setTokenFirebase(tokenFirebase);
    }

    @Override
    public String getTokenFirebase() {
        return mPreferencesHelper.getTokenFirebase();
    }
}
