package vn.chapp.vn24h.ui.customer;

import vn.chapp.vn24h.base.MvpPresenter;

public interface CustomerFrMvpPresent<V extends CustomerFrMvpView> extends MvpPresenter<V> {
    void doGetShopLinked();
}
