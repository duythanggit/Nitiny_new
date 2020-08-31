package vn.chapp.vn24h.ui.accumulatepoint;

import vn.chapp.vn24h.base.MvpPresenter;
import vn.chapp.vn24h.models.auth.UserDefault;

public interface AccumulatePointMvpPresent<V extends AccumulatePointMvpView> extends MvpPresenter<V> {
    void getPointHistory(String shopId, String month, String year);
    UserDefault getUserDefault();
}
