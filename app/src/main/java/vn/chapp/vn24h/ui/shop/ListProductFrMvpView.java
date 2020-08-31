package vn.chapp.vn24h.ui.shop;


import java.util.List;

import vn.chapp.vn24h.base.MvpView;
import vn.chapp.vn24h.models.service.CategoryAdd;
import vn.chapp.vn24h.models.service.ProductResponse;

public interface ListProductFrMvpView extends MvpView {
    void didGetCategory(List<CategoryAdd> categories);
    void gotProducts(List<ProductResponse> productResponseList);
    void didAddProductToCart();
}
