package vn.chapp.vn24h.ui.addCode;

import vn.chapp.vn24h.base.MvpPresenter;


public interface AddCodeFrMvpPresent<V extends AddCodeFrMvpView> extends MvpPresenter<V> {
    void doAddService(String code);
    void doGetShopLinked();
    boolean isValidateAddService(String code);
}
