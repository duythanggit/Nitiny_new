package vn.chapp.vn24h.ui.scheduled;


import vn.chapp.vn24h.base.MvpView;
import vn.chapp.vn24h.models.sale.Scheduled;

public interface ScheduleFrMvpView extends MvpView {
    void isValidateData(boolean isValidate, Scheduled scheduled);
}
