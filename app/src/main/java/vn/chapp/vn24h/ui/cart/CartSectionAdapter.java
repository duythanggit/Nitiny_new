package vn.chapp.vn24h.ui.cart;

import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import vn.chapp.vn24h.R;
import vn.chapp.vn24h.base.adapter.SectionedRecyclerViewAdapter;
import vn.chapp.vn24h.base.adapter.SectionedViewHolder;
import vn.chapp.vn24h.models.cart.Cart;
import vn.chapp.vn24h.models.cart.CartProduct;
import vn.chapp.vn24h.models.service.ProductResponse;
import vn.chapp.vn24h.utils.CommonUtils;
import vn.chapp.vn24h.utils.NetworkUtils;

public class CartSectionAdapter extends SectionedRecyclerViewAdapter<SectionedViewHolder> {

    private List<Cart> carts;
    private OnItemCartClickListener onItemCartClickListener;
    private ArrayAdapter<String> pickServiceAdapter;
    private List<ProductResponse> listProduct;

    public CartSectionAdapter(List<Cart> carts) {
        if (carts != null) {
            this.carts = carts;
        } else {
            this.carts = new ArrayList<>();
        }
    }

    public void replaceData(List<Cart> carts) {
        this.carts.clear();
        this.carts.addAll(carts);
        notifyDataSetChanged();
    }


    @Override
    public int getSectionCount() {
        return this.carts.size();
    }

    @Override
    public int getItemCount(int section) {
        return this.carts.get(section).getProduct().size();
    }

    @Override
    public void onBindHeaderViewHolder(SectionedViewHolder holder, int section, boolean expanded) {
        ((HeaderCartViewHolder) holder).onBind(section);
    }

    @Override
    public void onBindFooterViewHolder(SectionedViewHolder holder, int section) {

    }

    @Override
    public void onBindViewHolder(SectionedViewHolder holder, int section, int relativePosition, int absolutePosition) {
        ((CartViewHolder) holder).onBind(section, relativePosition);
    }

    @NonNull
    @Override
    public SectionedViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        switch (i) {
            case VIEW_TYPE_HEADER:
                return new HeaderCartViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_cart_section_header, viewGroup, false));
            case VIEW_TYPE_ITEM:
                return new CartViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_cart_section_item, viewGroup, false), new MyCustomEditTextListener());
            default:
                return null;
        }
    }

    class HeaderCartViewHolder extends SectionedViewHolder {

        @BindView(R.id.rbCart)
        RadioButton rbCart;

        public HeaderCartViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        public void onBind(int section) {
            if (!TextUtils.isEmpty(carts.get(section).getShopName())) rbCart.setText(carts.get(section).getShopName());
            else rbCart.setText("Chưa rõ");
            rbCart.setChecked(carts.get(section).isChoose());

            rbCart.setOnClickListener(v -> {
                if (onItemCartClickListener!=null) onItemCartClickListener.onChangeShop(section);
            });
        }
    }

    class CartViewHolder extends SectionedViewHolder {

        @BindView(R.id.edtCount)
        EditText edtCount;
        @BindView(R.id.tvPrice)
        TextView tvPrice;
        @BindView(R.id.ivDelete)
        ImageView ivDelete;
        @BindView(R.id.ivPhoto)
        ImageView ivPhoto;
        @BindView(R.id.tvPriceDiscount)
        TextView tvPriceDiscount;
        @BindView(R.id.tvProductName)
        TextView tvProductName;

        public MyCustomEditTextListener myCustomEditTextListener;

        public CartViewHolder(View itemView, MyCustomEditTextListener myCustomEditTextListener) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            this.myCustomEditTextListener = myCustomEditTextListener;
        }

        public void onBind(int section, int relativePosition) {

            CartProduct cartProduct = carts.get(section).getProduct().get(relativePosition);

            if (!TextUtils.isEmpty(cartProduct.getImg()))
                NetworkUtils.loadImage(itemView.getContext(), cartProduct.getImg(),ivPhoto);

            if (!TextUtils.isEmpty(cartProduct.getNumber()))
                edtCount.setText(cartProduct.getNumber());
            else edtCount.setText("0");

            if (!TextUtils.isEmpty(cartProduct.getProductName()))
                tvProductName.setText(cartProduct.getProductName());
            else tvProductName.setText("Chưa rõ");

//            if (!TextUtils.isEmpty(cartProduct.getPriceDiscount()))
//                tvPriceDiscount.setText(String.format("%sđ", CommonUtils.parseMoney(cartProduct.getPriceDiscount())));
//            else tvPriceDiscount.setText("0đ");
//
//            if (!TextUtils.isEmpty(cartProduct.getPriceDiscount()))
//                tvPrice.setText(String.format("%sđ", CommonUtils.parseMoney(cartProduct.getPrice())));
//            else tvPrice.setText("0đ");

            if (cartProduct.getTypeUser()==null) {
                tvPrice.setVisibility(View.VISIBLE);
            } else if (cartProduct.getTypeUser().equals("3")) {
                tvPrice.setVisibility(View.VISIBLE);
            } else {
                tvPrice.setVisibility(View.GONE);
            }
            String price = getPrice(cartProduct);
            String realPrice = getRealPrice(cartProduct);

            if (!TextUtils.isEmpty(price))
                tvPriceDiscount.setText(String.format("%s đ", CommonUtils.parseMoney(price)));
            else tvPriceDiscount.setText("Chưa rõ");

            if (!TextUtils.isEmpty(realPrice))
                tvPrice.setText(String.format("%s đ", CommonUtils.parseMoney(realPrice)));
            else tvPrice.setText("Chưa rõ");

            ivDelete.setOnClickListener(v -> {
                if (onItemCartClickListener!=null) onItemCartClickListener.onDeleteItem(section, relativePosition, cartProduct.getProductId());
            });

            myCustomEditTextListener.updatePosition(section, relativePosition, cartProduct.getProductId());
            edtCount.setOnEditorActionListener(myCustomEditTextListener);
        }
    }

    public interface OnItemCartClickListener{
        void onChangeCount(int section, int relativePosition, int count, String productId);
        void onDeleteItem(int section, int relativePosition, String productId);
        void onChangeShop(int section);
    }

    public void setOnItemCartClickListener(OnItemCartClickListener onItemCartClickListener) {
        this.onItemCartClickListener = onItemCartClickListener;
    }

    private class MyCustomEditTextListener implements TextView.OnEditorActionListener {
        private int position;
        private int section;
        private String productId;

        public void updatePosition(int position, int section, String productId) {
            this.position = position;
            this.section = section;
            this.productId = productId;
        }

        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                // the user is done typing.
                InputMethodManager imm = (InputMethodManager)v.getContext().getSystemService(v.getContext().INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

                int count = 0;
                try {
                    count = Integer.parseInt(v.getText().toString());
                } catch (NumberFormatException e) {

                } catch (NullPointerException ex) {

                }
                onItemCartClickListener.onChangeCount(section, position, count, productId);

                return true; // consume.
            }
            return false; // pass on to other listeners.
        }
    }

    private String getPrice(CartProduct cartProduct) {
        if (cartProduct.getTypeUser()==null) return cartProduct.getPriceDiscount();

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
        if (cartProduct.getTypeUser()==null) return cartProduct.getPrice();

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
            default:
                price = cartProduct.getPrice();
                break;
        }
        return price;
    }

}
