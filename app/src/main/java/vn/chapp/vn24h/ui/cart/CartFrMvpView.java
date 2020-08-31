package vn.chapp.vn24h.ui.cart;


import java.util.List;

import vn.chapp.vn24h.base.MvpView;
import vn.chapp.vn24h.models.cart.Cart;

public interface CartFrMvpView extends MvpView {
    void didGetCart(List<Cart> carts);
    void didDeleteCart();
    void didEditCart();
}
