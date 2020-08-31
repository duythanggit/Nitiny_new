package vn.chapp.vn24h.ui.scheduled;

import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import vn.chapp.vn24h.R;
import vn.chapp.vn24h.base.BaseAdapter;
import vn.chapp.vn24h.base.BaseViewHolder;
import vn.chapp.vn24h.models.sale.ProductSchedule;
import vn.chapp.vn24h.models.service.ProductResponse;
import vn.chapp.vn24h.utils.CommonUtils;
import vn.chapp.vn24h.utils.NetworkUtils;
    //Thangggggggggggggggg
public class ScheduledAdapter extends BaseAdapter<ProductSchedule> {

    public ScheduledAdapter(List<ProductSchedule> collection) {
        super(collection);
    }

    private OnClickItemScheduled onClickItemScheduled;
    private ArrayAdapter<String> pickServiceAdapter;

    private List<ProductResponse> listProduct;

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ScheduledViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_scheduled, viewGroup, false), new MyCustomEditTextListener());

    }

    class ScheduledViewHolder extends BaseViewHolder {

        @BindView(R.id.spnPickService)
        Spinner spnPickService;
        @BindView(R.id.edtCount)
        EditText edtCount;
        @BindView(R.id.tvItemScheduleName)
        TextView tvItemScheduleName;
        @BindView(R.id.tvMinus)
        TextView tvMinus;
        @BindView(R.id.tvPlus)
        TextView tvPlus;
        @BindView(R.id.txtPrice)
        TextView txtPrice;
        @BindView(R.id.imgClose)
        ImageView imgClose;
        @BindView(R.id.ivPhoto)
        ImageView ivPhoto;
        @BindView(R.id.tvOldPrice)
        TextView tvOldPrice;
        @BindView(R.id.layoutItem)
        RelativeLayout layoutItem;

        public MyCustomEditTextListener myCustomEditTextListener;

        public ScheduledViewHolder(View itemView, MyCustomEditTextListener myCustomEditTextListener) {
            super(itemView);
            this.myCustomEditTextListener = myCustomEditTextListener;
        }

        @Override
        public void onBind(final int position) {
            super.onBind(position);

            spnPickService.setAdapter(pickServiceAdapter);
            spnPickService.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int index, long id) {
                    onClickItemScheduled.onChooseService(position, index);

                    // sản phẩm mới filter theo giá đại lý
                    if (listProduct.get(index).getType().equals("2")) {
                        txtPrice.setText(String.format("%sđ", CommonUtils.parseMoney(listProduct.get(index).getPriceDiscount())));
//                        tvOldPrice.setText(String.format("%sđ", CommonUtils.parseMoney(listProduct.get(index).getPrice())));
                    } else {
                        /* String price = getPrice(getCollection().get(position));
                        String realPrice = getRealPrice(getCollection().get(position));

                        if (!TextUtils.isEmpty(price))
                            txtPrice.setText(String.format("%s đ", CommonUtils.parseMoney(price)));
                        else txtPrice.setText("Chưa rõ");

                        if (!TextUtils.isEmpty(realPrice))
                            tvOldPrice.setText(String.format("%s đ", CommonUtils.parseMoney(realPrice)));
                        else tvOldPrice.setText("Chưa rõ"); */

                        if (listProduct.get(index).getTypeUser() == null) {
                            tvOldPrice.setVisibility(View.VISIBLE);
                        } else if (listProduct.get(index).getTypeUser().equals("3")) {
                            tvOldPrice.setVisibility(View.VISIBLE);
                        } else {
                            tvOldPrice.setVisibility(View.GONE);
                        }
                        String price = getPrice(listProduct.get(index));
                        String realPrice = getRealPrice(listProduct.get(index));

                        if (!TextUtils.isEmpty(price))
                            txtPrice.setText(String.format("%s đ", CommonUtils.parseMoney(price)));
                        else txtPrice.setText("Chưa rõ");

                        if (!TextUtils.isEmpty(realPrice))
                            tvOldPrice.setText(String.format("%s đ", CommonUtils.parseMoney(realPrice)));
                        else tvOldPrice.setText("Chưa rõ");
                    }

                    if (!TextUtils.isEmpty(listProduct.get(index).getImg()))
                        NetworkUtils.loadImage(itemView.getContext(), listProduct.get(index).getImg(), ivPhoto);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            try {
                if (getCollection().get(position).getIndexSpinner() != 0)
                    spnPickService.setSelection(getCollection().get(position).getIndexSpinner());
            } catch (ArrayIndexOutOfBoundsException e) {

            }

            myCustomEditTextListener.updatePosition(position);
            edtCount.addTextChangedListener(myCustomEditTextListener);

            imgClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickItemScheduled.onDeleteItem(position);
                }
            });

            tvPlus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickItemScheduled.onPlusItem(position);
                    getCollection().get(position).setNumber(getCollection().get(position).getNumber() + 1);
                }
            });
            tvMinus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickItemScheduled.onMinusItem(position);
//                    getCollection().get(position).setNumber(getCollection().get(position).getNumber() + 1);
//                    edtCount.setText(getCollection().get(position).getNumber());
                }
            });


            spnPickService.setSelection(getCollection().get(position).getIndexSpinner());
            edtCount.setText(getCollection().get(position).getNumber() + "");
            tvItemScheduleName.setText(getCollection().get(position).getProductName());

            txtPrice.setText(String.format("%sđ", CommonUtils.parseMoney(getCollection().get(position).getPriceDiscount())));
//            tvOldPrice.setText(String.format("%sđ", CommonUtils.parseMoney(getCollection().get(position).getPrice())));


            if (!TextUtils.isEmpty(getCollection().get(position).getImg()))
                NetworkUtils.loadImage(itemView.getContext(), getCollection().get(position).getImg(), ivPhoto);
        }

        private String getPrice(ProductResponse productResponse) {
            if (productResponse.getTypeUser() == null) return productResponse.getPriceDiscount();

            String price = "0";
            switch (productResponse.getTypeUser()) {
                case "1":
                    price = productResponse.getPrice1();
                    break;
                case "2":
                    price = productResponse.getPrice2();
                    break;
                case "3":
                    price = productResponse.getPriceDiscount();
                    break;
                case "4":
                    price = productResponse.getPrice3();
                    break;
                default:
                    price = productResponse.getPriceDiscount();
                    break;

            }
            return price;
        }

        private String getRealPrice(ProductResponse productResponse) {
            if (productResponse.getTypeUser() == null) return productResponse.getPriceDiscount();

            String price = "0";
            switch (productResponse.getTypeUser()) {
                case "1":
                    price = productResponse.getPrice1();
                    break;
                case "2":
                    price = productResponse.getPrice2();
                    break;
                case "3":
                    price = String.valueOf(productResponse.getPrice());
                    break;
                case "4":
                    price = productResponse.getPrice3();
                    break;
                default:
                    price = String.valueOf(productResponse.getPrice());
                    break;
            }
            return price;
        }
    }

    public interface OnClickItemScheduled {
        void onChooseService(int position, int indexSpinner);

        void onChangeCount(int position, int count);

        void onDeleteItem(int position);

        void onPlusItem(int position);

        void onMinusItem(int position);
    }

    public void setOnClickItemScheduled(OnClickItemScheduled onClickItemScheduled) {
        this.onClickItemScheduled = onClickItemScheduled;
    }

    public void setPickServiceAdapter(ArrayAdapter<String> pickServiceAdapter) {
        this.pickServiceAdapter = pickServiceAdapter;
    }

    public void setListProduct(List<ProductResponse> listProduct) {
        this.listProduct = listProduct;
    }

    private class MyCustomEditTextListener implements TextWatcher {
        private int position;

        public void updatePosition(int position) {
            this.position = position;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            // no op
        }

        @Override
        public void onTextChanged(CharSequence s, int i, int i2, int i3) {
            Log.d("DATA_PRODUCT", "on change: " + position + " count: " + s);
            int countt = 0;
            try {
                countt = Integer.parseInt(s.toString());
            } catch (NumberFormatException e) {

            } catch (NullPointerException ex) {

            }
            onClickItemScheduled.onChangeCount(position, countt);
//            onClickItemScheduled.onPlusItem(position);
//            onClickItemScheduled.onMinusItem(position);


        }

        @Override
        public void afterTextChanged(Editable editable) {
            // no op
        }
    }

}
