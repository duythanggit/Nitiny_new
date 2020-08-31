package vn.chapp.vn24h.ui.main;

import vn.chapp.vn24h.base.MvpPresenter;
import vn.chapp.vn24h.di.PerActivity;
import vn.chapp.vn24h.models.auth.UserDefault;

@PerActivity
public interface MainMvpPresenter<V extends MainMvpView> extends MvpPresenter<V> {
    void doLogout();
    UserDefault getUserDefault();
}
