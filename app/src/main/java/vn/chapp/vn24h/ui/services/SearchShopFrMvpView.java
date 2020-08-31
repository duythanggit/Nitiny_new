package vn.chapp.vn24h.ui.services;


import java.util.List;

import vn.chapp.vn24h.base.MvpView;
import vn.chapp.vn24h.models.service.Shop;

public interface SearchShopFrMvpView extends MvpView {
    void didRefresh();
    void didSearchShop(List<Shop> shops);
    void didAddShop(int idService, int shopId);
}
