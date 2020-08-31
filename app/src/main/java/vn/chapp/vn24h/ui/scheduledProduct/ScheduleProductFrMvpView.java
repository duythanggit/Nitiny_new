package vn.chapp.vn24h.ui.scheduledProduct;


import vn.chapp.vn24h.base.MvpView;
import vn.chapp.vn24h.models.sale.Scheduled;
import vn.chapp.vn24h.models.service.ListProducts;

public interface ScheduleProductFrMvpView extends MvpView {
    void isValidateData(boolean isValidate, Scheduled scheduled);
    void didGetProduct(ListProducts products);
    void didDeleteCart();
    void didEditCart();
    void didAddProductToCart();
}
