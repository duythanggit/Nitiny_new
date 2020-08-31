package vn.chapp.vn24h.ui.auth.forgot;

import vn.chapp.vn24h.base.MvpPresenter;


public interface ForgotPasswordFrMvpPresent<V extends ForgotPasswordFrMvpView> extends MvpPresenter<V> {
    void doGetOTP(String phone);
    void doChangePassword(String phone, String password);
    void doLogin(String phone, String password);
}
