package vn.chapp.vn24h.ui.detailHistory;

import vn.chapp.vn24h.base.MvpPresenter;

public interface DetailHistoryFrMvpPresent<V extends DetailHistoryFrMvpView> extends MvpPresenter<V> {
    void doGetDetailBooking(int idBooking);
}