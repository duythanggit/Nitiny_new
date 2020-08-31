package vn.chapp.vn24h.ui.auth.forgot;


import vn.chapp.vn24h.base.MvpView;
import vn.chapp.vn24h.models.auth.UserDefault;

public interface ForgotPasswordFrMvpView extends MvpView {
    void didGetOTP(int otp);
    void didLogin(UserDefault userDefault);
}
