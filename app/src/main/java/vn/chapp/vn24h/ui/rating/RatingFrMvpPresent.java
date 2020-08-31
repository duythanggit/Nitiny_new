package vn.chapp.vn24h.ui.rating;

import vn.chapp.vn24h.base.MvpPresenter;


public interface RatingFrMvpPresent<V extends RatingFrMvpView> extends MvpPresenter<V> {
    void doGetShopLinked();
    void ratingService(String serviceId, Float ratingShop, Float ratingStaff);
}
