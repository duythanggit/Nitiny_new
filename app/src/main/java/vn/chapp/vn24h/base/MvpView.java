

package vn.chapp.vn24h.base;


import android.support.annotation.StringRes;

import vn.chapp.vn24h.utils.rx.RxBus;


public interface MvpView {

    void showLoading();

    void hideLoading();

    void showMessage(String message);

    void showMessage(@StringRes int resId);

    boolean isNetworkConnected();

    void hideKeyboard();

    void sendBuser(String key, Object values);

    void registerBuser(RxBus.OnMessageReceived onMessageReceived);

    void unRegisterBuser();

    void showDialogMessage(int type, String title, String message, String positive, String negative, boolean cancelable, BaseActivity.OnDialogMessageClick onDialogMessageClick);

    void forceLogout();

}
