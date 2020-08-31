package vn.chapp.vn24h.ui.addCode;


import java.util.List;

import vn.chapp.vn24h.base.MvpView;
import vn.chapp.vn24h.models.service.Shop;

public interface AddCodeFrMvpView extends MvpView {
    void didAddShop(Integer idService);
    void didGetShopLinked(List<Shop> listShopLinked);
    void setRefresh(boolean refresh);
}
