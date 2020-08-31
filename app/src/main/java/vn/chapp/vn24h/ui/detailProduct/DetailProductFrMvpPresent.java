package vn.chapp.vn24h.ui.detailProduct;

import vn.chapp.vn24h.base.MvpPresenter;

public interface DetailProductFrMvpPresent<V extends DetailProductFrMvpView> extends MvpPresenter<V> {
    void doAddProductToCart(String productId);
}
