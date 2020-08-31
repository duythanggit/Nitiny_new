package vn.chapp.vn24h.ui.shop;

import vn.chapp.vn24h.base.MvpPresenter;

public interface ShopDetailFrMvpPresent<V extends ShopDetailFrMvpView> extends MvpPresenter<V> {
    void doGetListShop(String idService, String type);
    void doGetShopDetail(String shopId);
    void getBanners();
}
