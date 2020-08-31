package vn.chapp.vn24h.ui.web;


import vn.chapp.vn24h.base.MvpPresenter;

public interface WebLoaderFrMvpPresent<V extends WebLoaderFrMvpView> extends MvpPresenter<V> {
    void getGuide();
}
