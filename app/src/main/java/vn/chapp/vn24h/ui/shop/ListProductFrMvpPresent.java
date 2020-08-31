package vn.chapp.vn24h.ui.shop;

import vn.chapp.vn24h.base.MvpPresenter;
import vn.chapp.vn24h.models.service.CategoryAdd;


public interface ListProductFrMvpPresent<V extends ListProductFrMvpView> extends MvpPresenter<V> {
    void doGetCategory(String serviceId, String type);
    void getSearchProduct(CategoryAdd categoryAdd, String shopId, int type);
    void doAddProductToCart(String productId);
}
