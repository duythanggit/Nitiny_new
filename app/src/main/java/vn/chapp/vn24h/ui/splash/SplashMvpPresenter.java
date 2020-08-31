package vn.chapp.vn24h.ui.splash;

import vn.chapp.vn24h.base.MvpPresenter;
import vn.chapp.vn24h.di.PerActivity;

@PerActivity
public interface SplashMvpPresenter<V extends SplashMvpView> extends MvpPresenter<V> {
    boolean isLoggedIn();
}
