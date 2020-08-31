package vn.chapp.vn24h.ui.scheduledProduct;

import vn.chapp.vn24h.base.MvpPresenter;
import vn.chapp.vn24h.models.sale.Scheduled;

public interface ConfirmProductFrMvpPresent<V extends ConfirmProductFrMvpView> extends MvpPresenter<V> {
    void doBooking(Scheduled scheduled);
}
