package vn.chapp.vn24h.ui.tichDiem;

import vn.chapp.vn24h.base.MvpPresenter;
import vn.chapp.vn24h.models.auth.UserDefault;

public interface TichDiemMvpPresent<V extends TichDiemMvpView> extends MvpPresenter<V> {
    void getPointHistory();
    UserDefault getUserDefault();
}
