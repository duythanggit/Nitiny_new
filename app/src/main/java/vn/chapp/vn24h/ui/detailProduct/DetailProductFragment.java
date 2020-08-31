package vn.chapp.vn24h.ui.detailProduct;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import vn.chapp.vn24h.R;
import vn.chapp.vn24h.base.BaseFragment;
import vn.chapp.vn24h.di.component.ActivityComponent;
import vn.chapp.vn24h.models.service.ListProducts;
import vn.chapp.vn24h.models.service.ProductResponse;
import vn.chapp.vn24h.ui.chat.ChatActivity;
import vn.chapp.vn24h.ui.chat.ChatFragment;
import vn.chapp.vn24h.ui.chat.ChatRoomFragment;
import vn.chapp.vn24h.ui.main.BackableActivity;
import vn.chapp.vn24h.ui.scheduledProduct.ScheduleProductFragment;
import vn.chapp.vn24h.utils.AppConstants;
import vn.chapp.vn24h.utils.AppUtils;
import vn.chapp.vn24h.utils.CommonUtils;

public class DetailProductFragment extends BaseFragment implements DetailProductFrMvpView {

    public static final String TAG = DetailProductFragment.class.getCanonicalName();

    public static final String ARG_PRODUCT = "ARG_PRODUCT";
    public static final String ARG_LIST_PRODUCT = "ARG_LIST_PRODUCT";
    public static final String ARG_SHOP_NAME = "ARG_SHOP_NAME";
    public static final String ARG_SHOP_ADDRESS = "ARG_SHOP_ADDRESS";
    public static final String ARG_SHOP_PHONE = "ARG_SHOP_PHONE";

    private ProductResponse productResponse;
    private ListProducts listProduct;
    private String shopNameSelected;
    private String shopAddressSelected;
    private String shopPhoneSelected;

    @Inject
    DetailProductFrPresenter<DetailProductFrMvpView> presenter;

    @BindView(R.id.sliderProduct)
    SliderLayout sliderProduct;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.tvOtherProduct)
    TextView tvOtherProduct;
    @BindView(R.id.tvType)
    TextView tvType;
    @BindView(R.id.tvPriceReal)
    TextView tvPriceReal;
    @BindView(R.id.tvPriceSale)
    TextView tvPriceSale;
    @BindView(R.id.txtOrder)
    TextView txtOrder;
    @BindView(R.id.tvNote)
    TextView tvNote;
    @BindView(R.id.ivAddProduct)
    ImageView ivAddProduct;
    @BindView(R.id.imgChat)
    ImageView imgChat;
    @BindView(R.id.btnAddProduct)
    Button btnAddProduct;
    @BindView(R.id.tvLableType)
    TextView tvLableType;
    @BindView(R.id.llMadeFrom)
    LinearLayout llMadeFrom;
//    @BindView(R.id.tvTotalNumber)
//    TextView tvTotalNumber;
    @BindView(R.id.tvMadeFrom)
    TextView tvMadeFrom;


    @BindView(R.id.lnPriceSale)
    LinearLayout lnPriceSale;

    public static DetailProductFragment newInstance(ProductResponse productResponse, ListProducts listProduct,
                                                    String shopNameSelected, String shopAddressSelected, String shopPhoneSelected) {
        DetailProductFragment fragment = new DetailProductFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PRODUCT, productResponse);
        args.putParcelable(ARG_LIST_PRODUCT, listProduct);
        args.putString(ARG_SHOP_NAME, shopNameSelected);
        args.putString(ARG_SHOP_ADDRESS, shopAddressSelected);
        args.putString(ARG_SHOP_PHONE, shopPhoneSelected);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int configView() {
        return R.layout.fragment_product_detail;
    }

    @Override
    protected void init(View v) {
        productResponse = getArguments().getParcelable(ARG_PRODUCT);
        listProduct = getArguments().getParcelable(ARG_LIST_PRODUCT);
        shopNameSelected = getArguments().getString(ARG_SHOP_NAME, "");
        shopAddressSelected = getArguments().getString(ARG_SHOP_ADDRESS, "");
        shopPhoneSelected = getArguments().getString(ARG_SHOP_PHONE, "");

        if (productResponse != null && listProduct != null) {
            if (productResponse.getImgs() != null && productResponse.getImgs().size() > 0) {
                for (String image : productResponse.getImgs()) {
                    TextSliderView textSliderView = new TextSliderView(getContext());
                    textSliderView
                            .image(image)
                            .setScaleType(BaseSliderView.ScaleType.CenterCrop);
                    sliderProduct.addSlider(textSliderView);
                }
            } else if (!TextUtils.isEmpty(productResponse.getImg())) {
                TextSliderView textSliderView = new TextSliderView(getContext());
                textSliderView
                        .image(productResponse.getImg())
                        .setScaleType(BaseSliderView.ScaleType.CenterCrop);
                sliderProduct.addSlider(textSliderView);
            }
            if (!TextUtils.isEmpty(productResponse.getName()))
                tvTitle.setText(productResponse.getName());
            Typeface boldTypeface = Typeface.defaultFromStyle(Typeface.BOLD);
            tvTitle.setTypeface(boldTypeface);
            tvOtherProduct.setTypeface(boldTypeface);

            if (!TextUtils.isEmpty(productResponse.getCategoryName()))
                tvType.setText(productResponse.getCategoryName());

//            if (!TextUtils.isEmpty(productResponse.getNumber()))
//                tvTotalNumber.setText(productResponse.getNumber());
//            else tvTitle.setText("Chưa rõ");

            //getMadeFrom thì chưa có dữ liệu từ API
            if (!TextUtils.isEmpty(productResponse.getMadeFrom())) //chờ API mới
                tvMadeFrom.setText(productResponse.getMadeFrom());
            else tvMadeFrom.setText("Chưa rõ");



            // sản phẩm mới filter theo giá đại lý
            if (productResponse.getType().equals("2")) {
                if (!TextUtils.isEmpty(productResponse.getPriceDiscount()))
                    tvPriceSale.setText(String.format("%sđ", CommonUtils.parseMoney(productResponse.getPriceDiscount())));

                if (!TextUtils.isEmpty(productResponse.getPrice()))
                    tvPriceReal.setText(String.format("%sđ", CommonUtils.parseMoney(productResponse.getPrice())));
            } else {
                if (productResponse.getTypeUser() == null) {
                    if (!TextUtils.isEmpty(productResponse.getPriceDiscount()))
                        tvPriceSale.setText(String.format("%sđ", CommonUtils.parseMoney(productResponse.getPriceDiscount())));
                    if (!TextUtils.isEmpty(productResponse.getPrice())) {
                        tvPriceReal.setText(String.format("%sđ", CommonUtils.parseMoney(productResponse.getPrice())));
                    }
                } else if (productResponse.getTypeUser().equals("3")) {
                    tvPriceReal.setVisibility(View.VISIBLE);
                    if (!TextUtils.isEmpty(productResponse.getPriceDiscount()))
                        tvPriceSale.setText(String.format("%sđ", CommonUtils.parseMoney(productResponse.getPriceDiscount())));
                    if (!TextUtils.isEmpty(productResponse.getPrice())) {
                        tvPriceReal.setText(String.format("%sđ", CommonUtils.parseMoney(productResponse.getPrice())));
                    }
                } else {
                    lnPriceSale.setVisibility(View.GONE);
                    switch (productResponse.getTypeUser()) {
                        case "1":
                            if (!TextUtils.isEmpty(productResponse.getPrice1())) {
                                tvPriceReal.setText(String.format("%sđ", CommonUtils.parseMoney(productResponse.getPrice1())));
                            }
                            break;
                        case "2":
                            if (!TextUtils.isEmpty(productResponse.getPrice2())) {
                                tvPriceReal.setText(String.format("%sđ", CommonUtils.parseMoney(productResponse.getPrice2())));
                            }
                            break;
                        case "4":
                            if (!TextUtils.isEmpty(productResponse.getPrice3())) {
                                tvPriceReal.setText(String.format("%sđ", CommonUtils.parseMoney(productResponse.getPrice3())));
                            }
                            break;
                    }
                }
            }

            String desc = "";
            if (productResponse.getType().equals(AppConstants.TYPE_PRODUCT + "")) {
                llMadeFrom.setVisibility(View.VISIBLE);
                ivAddProduct.setVisibility(View.GONE);
//                btnAddProduct.setVisibility(View.GONE); //them
                txtOrder.setText(getString(R.string.title_order));
                tvLableType.setText(getString(R.string.str_category));
                desc = getString(R.string.str_descrip);
            } else {
                txtOrder.setText(getString(R.string.title_schedule));
                ivAddProduct.setVisibility(View.GONE);
//                btnAddProduct.setVisibility(View.GONE); //them
                btnAddProduct.setText("Đặt quà");
                tvLableType.setText(getString(R.string.str_type));
                llMadeFrom.setVisibility(View.GONE);
                desc = getString(R.string.str_note);
            }

            if (!TextUtils.isEmpty(productResponse.getNote()))
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    tvNote.setText(desc + " " + Html.fromHtml(productResponse.getNote(), Html.FROM_HTML_MODE_COMPACT));
                } else {
                    tvNote.setText(desc + " " + Html.fromHtml(productResponse.getNote()));
                }
            else tvNote.setText("Chưa có thông tin.");
        }
    }

    @Override
    protected void initCreatedView(View v) {
        ActivityComponent component = getActivityComponent();
        if (component != null) {
            component.inject(this);
            setUnbinder(ButterKnife.bind(this, v));
            presenter.onAttach(this);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (getActivity() != null && getActivity() instanceof BackableActivity) {
            ((BackableActivity) getActivity()).setToolbarState(true, getString(R.string.title_detail_product));
        }
    }

    @OnClick({R.id.txtOrder})
    public void onClickBooking(View v) {
        int shopId = 0;
        int productId = 0;
        int type = 0;
        try {
            shopId = Integer.parseInt(productResponse.getShopId());
//            productId = Integer.parseInt(productResponse.getServiceId());
            productId = Integer.parseInt(productResponse.getId());
            type = Integer.parseInt(productResponse.getType());
        } catch (NumberFormatException n) {

        } catch (NullPointerException e) {

        }
        startActivity(BackableActivity.newInstanceSchedule(
                getContext(), productResponse, BackableActivity.NAVIGATOR_FSCHEDULED, shopId, productId, type, listProduct, shopNameSelected, shopAddressSelected, shopPhoneSelected, productResponse.getImg()
        ));
    }

    @OnClick({R.id.ivAddProduct})
    public void onAddProduct(View v) {
        presenter.doAddProductToCart(productResponse.getId());
    }

    //imgChat
    @OnClick({R.id.imgChat})
    public void onGoToChat(View v) {
        AppUtils.replaceFragmentToActivity(getBaseActivity().getSupportFragmentManager(),
                ChatRoomFragment.newInstance(),
                R.id.frBackable, false, ChatRoomFragment.TAG);
        if (getActivity() != null && getActivity() instanceof BackableActivity) {
            ((BackableActivity) getActivity()).setToolbarState(true, getString(R.string.title_favourite));
        }
    }


    //them
    //add trực tiếp vào giở hàng, chưa có chọn số lượng...
//    @OnClick({R.id.btnAddProduct})
//    public void onAddProductToCart(View v) {
//        presenter.doAddProductToCart(productResponse.getId());
//    }

    @OnClick({R.id.btnAddProduct})
    public void doShowBottomDialog(View v) {
//        Toast.makeText(getContext(), "Clicked show bottom sheet dialog", Toast.LENGTH_SHORT).show();
        btnAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext(), R.style.BottomSheetDialogTheme);
                View bottomSheetView = LayoutInflater.from(getBaseActivity().getApplicationContext())
                        .inflate(R.layout.layout_bottom_sheet, (LinearLayout)v.findViewById(R.id.bottomSheetContainer));

                bottomSheetView.findViewById(R.id.tvMinus).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getContext(), "Clicked Minus", Toast.LENGTH_SHORT).show();
                    }
                });
                bottomSheetView.findViewById(R.id.tvPlus).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getContext(), "Clicked Plus", Toast.LENGTH_SHORT).show();
                    }
                });

                bottomSheetView.findViewById(R.id.btnAddProductToCart).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getContext(), "Clicked bottom sheet Dialog", Toast.LENGTH_SHORT).show();
                        bottomSheetDialog.dismiss();
                    }
                });
                bottomSheetDialog.setContentView(bottomSheetView);
                bottomSheetDialog.show();
            }
        });
    }





    @Override
    public void didAddProductToCart() {
        Toast.makeText(getContext(), "Thêm sản phẩm vào giỏ hàng thành công", Toast.LENGTH_SHORT).show();
    }

//    @Override
//    public void doShowBottomDialog(String productId) {
//        Toast.makeText(getContext(), "Clicked toooooo show bottom sheet dialog", Toast.LENGTH_SHORT).show();
//    }

//    @Override
//    public void onDetach() {
//        super.onDetach();
//        if(getActivity() != null && getActivity() instanceof BackableActivity) {
//            ((BackableActivity)getActivity()).restoreToolbar();
//        }
//    }
}
