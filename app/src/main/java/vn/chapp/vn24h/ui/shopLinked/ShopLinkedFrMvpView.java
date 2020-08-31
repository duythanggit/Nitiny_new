package vn.chapp.vn24h.ui.shopLinked;


import java.util.List;

import vn.chapp.vn24h.base.MvpView;
import vn.chapp.vn24h.models.service.Shop;

public interface ShopLinkedFrMvpView extends MvpView {
    void didGetShopLinked(List<Shop> listShopLinked);
    void setRefresh(boolean refresh);
    void didUnLink();
}
