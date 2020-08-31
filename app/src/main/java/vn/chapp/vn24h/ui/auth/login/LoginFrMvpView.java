package vn.chapp.vn24h.ui.auth.login;


import vn.chapp.vn24h.base.MvpView;
import vn.chapp.vn24h.models.auth.UserDefault;

public interface LoginFrMvpView extends MvpView {
    void didLogin(UserDefault userDefault);
    void didForgetPassword(String phone, int otp);
}
