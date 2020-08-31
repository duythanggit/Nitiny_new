package vn.chapp.vn24h.ui.services;

import vn.chapp.vn24h.base.MvpPresenter;


public interface LinkServiceFrMvpPresent<V extends LinkServiceFrMvpView> extends MvpPresenter<V> {
    void loadService(int type);
    void loadServiceLinked(int type);
    void getBanners();
    void getWallet();
}
