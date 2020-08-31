package vn.chapp.vn24h.ui.history;

import vn.chapp.vn24h.base.MvpPresenter;

public interface HistoryFrMvpPresent<V extends HistoryFrMvpView> extends MvpPresenter<V> {
    void doGetShopLinked();
    void getServices();
    void getUserHistory(String serviceId);
}
