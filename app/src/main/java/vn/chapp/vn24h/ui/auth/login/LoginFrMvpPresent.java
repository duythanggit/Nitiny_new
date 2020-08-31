package vn.chapp.vn24h.ui.auth.login;

import vn.chapp.vn24h.base.MvpPresenter;


public interface LoginFrMvpPresent<V extends LoginFrMvpView> extends MvpPresenter<V> {
    boolean isValidateLogin(String phone, String password);
    void doLogin(String phone, String password);
    boolean isValidateLoginGoogle(String email, String ggid, String name);
    void doLoginGoogle(String email, String ggid, String name, String refCode);
    void doForgetPassword(String phone);
}
