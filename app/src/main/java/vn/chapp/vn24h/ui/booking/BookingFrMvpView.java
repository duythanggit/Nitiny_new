package vn.chapp.vn24h.ui.booking;

import java.util.List;

import vn.chapp.vn24h.base.MvpView;
import vn.chapp.vn24h.models.booking.Booking;

public interface BookingFrMvpView extends MvpView {
    void didGetListBooking(List<Booking> bookingList);
    void didUpdatedBooking(int position, int active);
}
