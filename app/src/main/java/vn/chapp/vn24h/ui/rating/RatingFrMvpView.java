package vn.chapp.vn24h.ui.rating;


import java.util.List;

import vn.chapp.vn24h.base.MvpView;
import vn.chapp.vn24h.models.service.Shop;

public interface RatingFrMvpView extends MvpView {
    void parseShopLinked(List<Shop> shops);
}
