package vn.chapp.vn24h.ui.shopLinked;

import vn.chapp.vn24h.base.MvpPresenter;


public interface ShopLinkedFrMvpPresent<V extends ShopLinkedFrMvpView> extends MvpPresenter<V> {
    void doGetShopLinked(int serviceId, int type);
    void doUnLink(String shopId);
}
