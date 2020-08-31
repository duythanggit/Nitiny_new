package vn.chapp.vn24h.ui.sample;

import vn.chapp.vn24h.base.MvpPresenter;
import vn.chapp.vn24h.di.PerActivity;

@PerActivity
public interface SampleMvpPresenter<V extends SampleMvpView> extends MvpPresenter<V> {
}
