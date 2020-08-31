package vn.chapp.vn24h.ui.cart;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import vn.chapp.vn24h.R;
import vn.chapp.vn24h.base.BaseFragment;
import vn.chapp.vn24h.di.component.ActivityComponent;
import vn.chapp.vn24h.models.cart.Cart;
import vn.chapp.vn24h.models.cart.CartProduct;
import vn.chapp.vn24h.ui.scheduledProduct.ScheduleProductFragment;
import vn.chapp.vn24h.utils.AppUtils;

public class CartFragment extends BaseFragment implements CartFrMvpView, CartSectionAdapter.OnItemCartClickListener {

    public static final String TAG = CartFragment.class.getCanonicalName();

    @Inject
    CartFrPresenter<CartFrMvpView> presenter;
    @Inject
    CartAdapter cartAdapter;

    @Inject
    CartSectionAdapter cartSectionAdapter;

    @Inject
    @Named("vertical")
    LinearLayoutManager linearLayoutManager;

    @BindView(R.id.rcCart)
    RecyclerView rcCart;
    @BindView(R.id.tvQuantity)
    TextView tvQuantity;
    @BindView(R.id.tvCost)
    TextView tvCost;

    private int indexCartChoose = -1;
    private List<Cart> carts = new ArrayList<>();

    public static CartFragment newInstance() {
        CartFragment fragment = new CartFragment();
        return fragment;
    }

    @Override
    protected int configView() {
        return R.layout.fragment_cart;
    }

    @Override
    protected void init(View v) {
        rcCart.setHasFixedSize(true);
        rcCart.setLayoutManager(linearLayoutManager);
        cartSectionAdapter.setOnItemCartClickListener(this);
        rcCart.setAdapter(cartSectionAdapter);
    }

    @Override
    protected void initCreatedView(View v) {
        ActivityComponent component = getActivityComponent();
        if (component != null) {
            component.inject(this);
            setUnbinder(ButterKnife.bind(this, v));
            presenter.onAttach(this);

            presenter.doGetCart();
        }

    }

    @OnClick({R.id.btnPayment})
    public void onClickPayment(View v) {
        if (carts.size()<=0) {
            Toast.makeText(getContext(), "Bạn chưa có sản phẩm nào trong giỏ hàng", Toast.LENGTH_SHORT).show();
            return;
        }

        AppUtils.replaceFragmentToActivity(getBaseActivity().getSupportFragmentManager(),
                ScheduleProductFragment.newInstance(carts.get(indexCartChoose)),
                R.id.frBackable, false, ScheduleProductFragment.TAG);
    }

    @Override
    public void didGetCart(List<Cart> carts) {
        this.carts.removeAll(this.carts);
        this.carts.addAll(carts);

        if (indexCartChoose < 0) {
            if (this.carts.size() > 0) {
                this.carts.get(0).setChoose(true);
                indexCartChoose = 0;
            }
        } else {
            if (this.carts.size() > indexCartChoose-1) {
                this.carts.get(indexCartChoose).setChoose(true);
            }
        }

        cartSectionAdapter.replaceData(this.carts);

        this.calculatorTotal();
    }

    @Override
    public void didDeleteCart() {
        presenter.doGetCart();
    }

    @Override
    public void didEditCart() {
        presenter.doGetCart();
    }

    @Override
    public void onChangeCount(int section, int relativePosition, int count, String productId) {
        presenter.doEditCart(productId, String.valueOf(count));
    }

    @Override
    public void onDeleteItem(int section, int relativePosition, String productId) {
        presenter.doDeleteCart(productId);
    }

    @Override
    public void onChangeShop(int section) {
        List<Cart> list = new ArrayList<>();
        for (int i = 0; i < carts.size(); i++) {
            if (i == section) {
                Cart cart = carts.get(i);
                cart.setChoose(true);
                list.add(cart);
            } else {
                Cart cart = carts.get(i);
                cart.setChoose(false);
                list.add(cart);
            }
        }
        cartSectionAdapter.replaceData(list);
        indexCartChoose = section;

        this.calculatorTotal();
    }

    private void calculatorTotal() {
        try {
            if (indexCartChoose > -1) {
                int quantity = 0;
                int totalPrice = 0;
                for (CartProduct cartProduct : carts.get(indexCartChoose).getProduct()) {
                    quantity += Integer.parseInt(cartProduct.getNumber());
                    totalPrice += Integer.parseInt(cartProduct.getNumber()) * Float.parseFloat(this.getPrice(cartProduct));
                }
                tvQuantity.setText(String.valueOf(quantity));
                tvCost.setText(String.valueOf(totalPrice));
            }
        } catch (Exception ex) {

        }
    }

    private String getPrice(CartProduct cartProduct) {
        if (cartProduct.getTypeUser() == null) return cartProduct.getPriceDiscount();

        String price = "0";
        switch (cartProduct.getTypeUser()) {
            case "1":
                price = cartProduct.getPrice1();
                break;
            case "2":
                price = cartProduct.getPrice2();
                break;
            case "3":
                price = cartProduct.getPriceDiscount();
                break;
            case "4":
                price = cartProduct.getPrice3();
                break;
            default:
                price = cartProduct.getPriceDiscount();
                break;

        }
        return price;
    }

    private String getRealPrice(CartProduct cartProduct) {
        if (cartProduct.getTypeUser() == null) return cartProduct.getPriceDiscount();

        String price = "0";
        switch (cartProduct.getTypeUser()) {
            case "1":
                price = cartProduct.getPrice1();
                break;
            case "2":
                price = cartProduct.getPrice2();
                break;
            case "3":
                price = cartProduct.getPrice();
                break;
            case "4":
                price = cartProduct.getPrice3();
                break;
        }
        return price;
    }
}
