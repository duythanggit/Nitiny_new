package vn.chapp.vn24h.ui.auth;

import vn.chapp.vn24h.base.MvpPresenter;


public interface ChangePasswordFrMvpPresent<V extends ChangePasswordFrMvpView> extends MvpPresenter<V> {

    boolean isValidate(String oldPassword, String newPassword, String rePassword);
    void doChangePassword(String oldPassword, String newPassword, String rePassword);


}
