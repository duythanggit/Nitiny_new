package vn.chapp.vn24h.ui.detail;

import vn.chapp.vn24h.base.MvpPresenter;


public interface DetailPromotionFrMvpPresent<V extends DetailPromotionFrMvpView> extends MvpPresenter<V> {

    void getNewsDetail(String id);

}
