package vn.chapp.vn24h.ui.scheduledProduct;

import vn.chapp.vn24h.base.MvpPresenter;
import vn.chapp.vn24h.models.sale.Scheduled;


public interface ScheduleProductFrMvpPresent<V extends ScheduleProductFrMvpView> extends MvpPresenter<V> {
    void validateData(Scheduled scheduled);
    void doGetProduct();
    void doDeleteCart(String productId);
    void doEditCart(String productId, String number);
    void doAddProductToCart(String productId);
}
