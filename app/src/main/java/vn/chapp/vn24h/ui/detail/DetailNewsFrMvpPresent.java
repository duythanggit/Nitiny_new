package vn.chapp.vn24h.ui.detail;

import vn.chapp.vn24h.base.MvpPresenter;


public interface DetailNewsFrMvpPresent<V extends DetailNewsFrMvpView> extends MvpPresenter<V> {

    void getNewsDetail(String id);

}
