package vn.chapp.vn24h.ui.main;

import vn.chapp.vn24h.base.MvpPresenter;
import vn.chapp.vn24h.di.PerActivity;

@PerActivity
public interface BackableMvpPresenter<V extends BackableMvpView> extends MvpPresenter<V> {
}
