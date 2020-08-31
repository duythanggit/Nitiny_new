package vn.chapp.vn24h.ui.booking;

import vn.chapp.vn24h.base.MvpPresenter;
import vn.chapp.vn24h.models.booking.Booking;

public interface BookingFrMvpPresent<V extends BookingFrMvpView> extends MvpPresenter<V> {
    void getListBooking(String active);
    void updateStatusBooking(int position, Booking booking, int active);
}
