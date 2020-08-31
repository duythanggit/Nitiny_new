package vn.chapp.vn24h.ui.scheduled;

import vn.chapp.vn24h.base.MvpPresenter;
import vn.chapp.vn24h.models.sale.Scheduled;


public interface ScheduleFrMvpPresent<V extends ScheduleFrMvpView> extends MvpPresenter<V> {
    void validateData(Scheduled scheduled);
}
