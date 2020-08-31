package vn.chapp.vn24h.ui.detailBooking;

import vn.chapp.vn24h.base.MvpView;
import vn.chapp.vn24h.models.booking.DetailBooking;

public interface DetailBookingFrMvpView extends MvpView {
    void didGetDetailBooking(DetailBooking detailBooking);
}
