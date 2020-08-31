package vn.chapp.vn24h.ui.detailBooking;

import vn.chapp.vn24h.base.MvpPresenter;

public interface DetailBookingFrMvpPresent<V extends DetailBookingFrMvpView> extends MvpPresenter<V> {
    void doGetDetailBooking(int idBooking);
}
