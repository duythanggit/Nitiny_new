package vn.chapp.vn24h.ui.profile;

import vn.chapp.vn24h.base.MvpPresenter;

public interface ProfileFrMvpPresent<V extends ProfileFrMvpView> extends MvpPresenter<V> {
    void getUserDefault();
}
