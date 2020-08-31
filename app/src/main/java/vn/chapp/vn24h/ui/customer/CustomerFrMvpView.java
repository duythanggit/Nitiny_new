package vn.chapp.vn24h.ui.customer;

import java.util.List;

import vn.chapp.vn24h.base.MvpView;
import vn.chapp.vn24h.models.service.Shop;

public interface CustomerFrMvpView extends MvpView {
    void didGetShopLinked(List<Shop> listShopLinked);
    void setRefresh(boolean refresh);
}
