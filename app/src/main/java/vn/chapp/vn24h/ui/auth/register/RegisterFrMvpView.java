package vn.chapp.vn24h.ui.auth.register;


import vn.chapp.vn24h.base.MvpView;
import vn.chapp.vn24h.models.auth.UserDefault;

public interface RegisterFrMvpView extends MvpView {
    void didVerifyOTP(int code);
    void didRegister(UserDefault userDefault);
}
