package vn.chapp.vn24h.ui.detailHistory;

import vn.chapp.vn24h.base.MvpView;
import vn.chapp.vn24h.models.booking.DetailBooking;

public interface DetailHistoryFrMvpView extends MvpView {
    void didGetDetailBooking(DetailBooking detailBooking);
}