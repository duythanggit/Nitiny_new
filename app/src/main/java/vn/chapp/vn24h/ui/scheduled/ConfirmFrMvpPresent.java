package vn.chapp.vn24h.ui.scheduled;

import vn.chapp.vn24h.base.MvpPresenter;
import vn.chapp.vn24h.models.sale.Scheduled;

public interface ConfirmFrMvpPresent<V extends ConfirmFrMvpView> extends MvpPresenter<V> {
    void doBooking(Scheduled scheduled);
}
