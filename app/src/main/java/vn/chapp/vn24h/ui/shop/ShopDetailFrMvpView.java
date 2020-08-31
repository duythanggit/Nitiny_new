package vn.chapp.vn24h.ui.shop;

import java.util.List;

import vn.chapp.vn24h.base.MvpView;
import vn.chapp.vn24h.models.service.ListProducts;
import vn.chapp.vn24h.models.service.Shop;

public interface ShopDetailFrMvpView extends MvpView {
    void didGetListShop(List<Shop> listShop);
    void didGetShopDetail(ListProducts products);
    void gotBanners(List<String> bannerResponses);
}
