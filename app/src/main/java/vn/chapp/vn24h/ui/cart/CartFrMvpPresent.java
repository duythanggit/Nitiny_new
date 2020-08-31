package vn.chapp.vn24h.ui.cart;

import vn.chapp.vn24h.base.MvpPresenter;


public interface CartFrMvpPresent<V extends CartFrMvpView> extends MvpPresenter<V> {
    void doGetCart();
    void doDeleteCart(String productId);
    void doEditCart(String productId, String number);
}
