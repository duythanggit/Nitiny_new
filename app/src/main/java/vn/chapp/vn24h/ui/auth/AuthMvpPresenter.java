package vn.chapp.vn24h.ui.auth;

import vn.chapp.vn24h.base.MvpPresenter;
import vn.chapp.vn24h.di.PerActivity;

@PerActivity
public interface AuthMvpPresenter<V extends AuthMvpView> extends MvpPresenter<V> {
}
