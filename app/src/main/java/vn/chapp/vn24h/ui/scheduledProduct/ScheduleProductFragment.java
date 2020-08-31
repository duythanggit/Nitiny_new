package vn.chapp.vn24h.ui.scheduledProduct;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import vn.chapp.vn24h.MainApp;
import vn.chapp.vn24h.R;
import vn.chapp.vn24h.base.BaseFragment;
import vn.chapp.vn24h.di.component.ActivityComponent;
import vn.chapp.vn24h.models.auth.UserDefault;
import vn.chapp.vn24h.models.cart.Cart;
import vn.chapp.vn24h.models.cart.CartProduct;
import vn.chapp.vn24h.models.sale.ProductSchedule;
import vn.chapp.vn24h.models.sale.Scheduled;
import vn.chapp.vn24h.models.service.ListProducts;
import vn.chapp.vn24h.models.service.ProductResponse;
import vn.chapp.vn24h.ui.main.BackableActivity;
import vn.chapp.vn24h.utils.AppConstants;
import vn.chapp.vn24h.utils.CommonUtils;

public class ScheduleProductFragment extends BaseFragment implements ScheduleProductFrMvpView, ScheduleProductAdapter.OnClickItemScheduled,
        CompoundButton.OnCheckedChangeListener, DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    public static final String TAG = ScheduleProductFragment.class.getCanonicalName();

    public static final String ARG_CART = "ARG_CART";

    @Inject
    ScheduleProductFrPresenter<ScheduleProductFrMvpView> presenter;

    @Inject
    @Named("vertical")
    LinearLayoutManager linearLayoutManager;
    @Inject
    ScheduleProductAdapter scheduledAdapter;

    @BindView(R.id.rcListProduct)
    RecyclerView rcListProduct;
    @BindView(R.id.btnAddProduct)
    Button btnAddProduct;
    @BindView(R.id.edtNote)
    EditText edtNote;
    @BindView(R.id.cbAddress)
    CheckBox cbAddress;
    @BindView(R.id.edtAddress)
    EditText edtAddress;
    @BindView(R.id.txtShopName)
    TextView txtShopName;
    @BindView(R.id.txtTotalCount)
    TextView txtTotalCount;
    @BindView(R.id.txtTotalPrice)
    TextView txtTotalPrice;
    @BindView(R.id.btnScheduled)
    Button btnScheduled;
    @BindView(R.id.txtLabelService)
    TextView txtLabelService;
    @BindView(R.id.txtCurrentAddress)
    TextView txtCurrentAddress;
    @BindView(R.id.txtDateTime)
    TextView txtDateTime;
    @BindView(R.id.txtPickDateTime)
    TextView txtPickDateTime;
    @BindView(R.id.lnAddress)
    LinearLayout lnAddress;
    @BindView(R.id.rGTypePay)
    RadioGroup rGTypePay;
    @BindView(R.id.txtBalance)
    TextView txtBalance;
    @BindView(R.id.edtPhone)
    EditText edtPhone;


    private Cart cart;

    private boolean isReceiveCurrentAddess = false;
    private List<ProductSchedule> listScheduled;
    private ListProducts products;
    private List<ProductResponse> listProduct;
    private List<String> listProductSpinner = new ArrayList<>();
    private Calendar calendar;
    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;
    private int payment = 0;
    private UserDefault userDefault;

    private int indexRequestApi = 0;
    private Scheduled scheduled;

    public static ScheduleProductFragment newInstance(Cart cart) {
        ScheduleProductFragment fragment = new ScheduleProductFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_CART, cart);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int configView() {
        return R.layout.fragment_scheduled;
    }

    @Override
    protected void init(View v) {

        cart = getArguments().getParcelable(ARG_CART);

        userDefault = MainApp.newInstance().getPreference().getUserDefault();

        if (getActivity() != null && getActivity() instanceof BackableActivity) {
            ((BackableActivity)getActivity()).setToolbarState(true,getString(R.string.title_order));
            txtLabelService.setText(getString(R.string.title_order));
            btnScheduled.setText(getString(R.string.title_order));
        }

        //check box address
        cbAddress.setOnCheckedChangeListener(this);

        //init date -> current date
        calendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(
                getContext(),
                this,
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());
        timePickerDialog = new TimePickerDialog(
                getContext(),
                this,
                calendar.get(Calendar.HOUR), calendar.get(Calendar.MINUTE),
                true
        );
        txtDateTime.setText(CommonUtils.formatDateToDayddHHyyyyHHmm(calendar.getTime()));

        rGTypePay.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId==R.id.rbTypePayCash) {
                    payment = 0;
                } else {
                    payment = 1;
                }
            }
        });

        String balance = MainApp.newInstance().getBalance();
        if (!TextUtils.isEmpty(balance))
            txtBalance.setText(String.format("%s đ", CommonUtils.parseMoney(balance)));
        else txtBalance.setText("0 đ");

        UserDefault userDefault = MainApp.newInstance().getPreference().getUserDefault();
        if (userDefault != null) {
            if (!TextUtils.isEmpty(userDefault.getPhone())) edtPhone.setText(userDefault.getPhone());
        }

        presenter.doGetProduct();

    }

    private Runnable runnableDirect = new Runnable() {
        @Override
        public void run() {
            if (getBaseActivity().getSupportFragmentManager().getBackStackEntryCount() > 0) {
                getBaseActivity().getSupportFragmentManager().popBackStackImmediate();
            } else {
                getBaseActivity().finish();
            }
        }
    };

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
            ((BackableActivity)getActivity()).setToolbarState(true,getString(R.string.title_schedule));
        }
    }

//    @Override
//    public void onDetach() {
//        super.onDetach();
//        if(getActivity() != null && getActivity() instanceof BackableActivity) {
//            ((BackableActivity)getActivity()).restoreToolbar();
//        }
//    }

    @OnClick(R.id.btnScheduled)
    public void onClickScheduled(View v){
        if (cart.getShopId() == null) {
            Toast.makeText(getContext(), "Có lỗi xảy ra. Vui lòng thử lại sau", Toast.LENGTH_SHORT).show();
            return;
        }

        Scheduled scheduled = new Scheduled();
        int totalNumber = 0;
        float totalMoney = 0;
        String address = "";

        //tính tổng tiền
        for(ProductSchedule pro : listScheduled) {
            totalNumber += pro.getNumber();
            totalMoney = totalMoney + (pro.getNumber()*Float.parseFloat(pro.getPriceDiscount()));

            //test
            Log.d("DATA_PRODUCT", pro.toString());
        }

        //check tiền
        int balance = 0;
        try {
            balance = Integer.parseInt(MainApp.newInstance().getBalance());
        } catch (Exception ex) {

        }
        if(payment==1) {
            if(balance<totalMoney) {
                Toast.makeText(getContext(), "Số xu trong ví của bạn không đủ để thanh toán. Vui lòng chọn phương thức thanh toán khác", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        if(cbAddress.isChecked()) {
            address = txtCurrentAddress.getText().toString();
        } else {
            address = edtAddress.getText().toString();
        }

        scheduled.setListProduct(listScheduled);
        scheduled.setDateBooking(CommonUtils.formatDateToStringforScheduled(calendar.getTime()));
        scheduled.setTimeBooking(CommonUtils.formatDateToStringforScheduled(calendar.getTime()));
        scheduled.setNumber(totalNumber);
        scheduled.setTotalMoney(totalMoney);
        scheduled.setNote(edtNote.getText().toString());
        scheduled.setAddress(address);
        scheduled.setShopId(Integer.parseInt(cart.getShopId()));
        scheduled.setIsReceive(isReceiveCurrentAddess ? AppConstants.IS_TRUE : AppConstants.IS_FALSE);
        scheduled.setType(AppConstants.TYPE_BUY_BOOKING);
        scheduled.setPayment(payment+1);
        scheduled.setPhone(edtPhone.getText().toString());

        presenter.validateData(scheduled);
    }

    @OnClick(R.id.btnAddProduct)
    public void onClickAddProduct(View v) {
        ProductResponse temp = listProduct.get(0);
        float tempPrice = 0;
        int tempProductId = 0;
        float tempPriceReal = 0;
        try {

//            tempPrice = Float.parseFloat(temp.getPriceDiscount());
            tempPrice = Float.parseFloat(this.getPrice(temp));
            tempProductId = Integer.parseInt(temp.getId());
            tempPriceReal = Float.parseFloat(this.getRealPrice(temp));
        } catch (NumberFormatException e) {

        } catch (NullPointerException ex) {

        }
        listScheduled.add(new ProductSchedule(tempProductId, 1, tempPrice, 0, temp.getName(), tempPriceReal, temp.getImg()));
        scheduledAdapter.replaceData(listScheduled);

        if(!btnScheduled.isEnabled()) {
            btnScheduled.setEnabled(true);
            btnScheduled.setBackground(getResources().getDrawable(R.drawable.bg_button_primary));
        }

        presenter.doAddProductToCart(temp.getId());
    }

    @OnClick(R.id.rlPickDateTime)
    public void onPickDateTime(View v) {
        datePickerDialog.show();
    }

    @Override
    public void onChooseService(int position, int indexSpinner) {
        ProductSchedule productSchedule = listScheduled.get(position);
        int productId = 0;
        float price = 0;
        String productName = "";
        float realPrice = 0;
        try {
            productId = Integer.parseInt(listProduct.get(indexSpinner).getId());
//            price = Float.parseFloat(listProduct.get(indexSpinner).getPriceDiscount());
            price = Float.parseFloat(this.getPrice(listProduct.get(indexSpinner)));
            productName = listProduct.get(indexSpinner).getName();
            realPrice = Float.parseFloat(this.getRealPrice(listProduct.get(indexSpinner)));
        } catch (NumberFormatException e) {

        } catch (NullPointerException ex) {

        }

        ProductSchedule pro = new ProductSchedule(
                productId,
                productSchedule.getNumber(),
                price,
//                productSchedule.getIndexSpinner(),
                indexSpinner,
                productName,
                realPrice,
                productSchedule.getImg()
        );
        listScheduled.set(position, pro);

        int totalCount = 0;
        float totalPrice = 0;
        for(ProductSchedule product : listScheduled) {
            totalCount += product.getNumber();
            totalPrice = totalPrice + (product.getNumber()*Float.parseFloat(product.getPriceDiscount()));
        }
        txtTotalCount.setText(totalCount+"");
        txtTotalPrice.setText(String.format("%sđ", CommonUtils.parseMoney(totalPrice)));
    }

    @Override
    public void onChangeCount(int position, int count) {
//        Log.d("DATA_PRODUCT", "on change: "+position+" count: "+count);
        ProductSchedule productSchedule = listScheduled.get(position);
        ProductSchedule pro = new ProductSchedule(
                productSchedule.getProductId(),
                count,
                Float.parseFloat(productSchedule.getPriceDiscount()),
                productSchedule.getIndexSpinner(),
                productSchedule.getProductName(),
                productSchedule.getPrice(),
                productSchedule.getImg()
        );
        listScheduled.set(position, pro);

        int totalCount = 0;
        float totalPrice = 0;
        for(ProductSchedule product : listScheduled) {
            totalCount += product.getNumber();
            totalPrice = totalPrice + (product.getNumber()*Float.parseFloat(product.getPriceDiscount()));
        }
        txtTotalCount.setText(totalCount+"");
//        txtTotalPrice.setText(totalPrice+"");
        txtTotalPrice.setText(String.format("%sđ", CommonUtils.parseMoney(totalPrice)));
    }

    @Override
    public void onDeleteItem(int position) {
        /**
        presenter.doDeleteCart(String.valueOf(listScheduled.get(position).getProductId()));
        listScheduled.remove(position);
        scheduledAdapter.replaceData(listScheduled);
        int totalCount = 0;
        float totalPrice = 0;
        for(ProductSchedule product : listScheduled) {
            totalCount += product.getNumber();
            totalPrice = totalPrice + (product.getNumber()*Float.parseFloat(product.getPriceDiscount()));
        }
        txtTotalCount.setText(totalCount+"");
//        txtTotalPrice.setText(totalPrice+"");
        txtTotalPrice.setText(String.format("%sđ", CommonUtils.parseMoney(totalPrice)));

        if(listScheduled.size()<1) {
            btnScheduled.setEnabled(false);
            btnScheduled.setBackground(getResources().getDrawable(R.drawable.bg_button_grey));
        }
        */

        AlertDialog alertDialog = new AlertDialog.Builder(getContext())
                .setTitle(R.string.ask_del_these_items)
                .setPositiveButton(R.string.dialog_OK, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        presenter.doDeleteCart(String.valueOf(listScheduled.get(position).getProductId()));
                        listScheduled.remove(position);
                        scheduledAdapter.replaceData(listScheduled);
                        int totalCount = 0;
                        float totalPrice = 0;
                        for(ProductSchedule product : listScheduled) {
                            totalCount += product.getNumber();
                            totalPrice = totalPrice + (product.getNumber()*Float.parseFloat(product.getPriceDiscount()));
                        }
                        txtTotalCount.setText(totalCount+"");
                        //txtTotalPrice.setText(totalPrice+"");
                        txtTotalPrice.setText(String.format("%sđ", CommonUtils.parseMoney(totalPrice)));

                        if(listScheduled.size()<1) {
                            btnScheduled.setEnabled(false);
                            btnScheduled.setBackground(getResources().getDrawable(R.drawable.bg_button_grey));
                        }
                    }
                })
                .setNegativeButton(R.string.dialog_CANCEL, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //empty
                    }
                })
                .create();

        alertDialog.show();
    }

    @Override
    public void onPlusItem(int position) {
        ProductSchedule productSchedule = listScheduled.get(position);
        ProductSchedule pro = new ProductSchedule(
                productSchedule.getProductId(),
                productSchedule.getNumber() + 1,
                Float.parseFloat(productSchedule.getPriceDiscount()),
                productSchedule.getIndexSpinner(),
                productSchedule.getProductName(),
                productSchedule.getPrice(),
                productSchedule.getImg()
        );
        listScheduled.set(position, pro);

        int totalCount = 0;
        float totalPrice = 0;
        for(ProductSchedule product : listScheduled) {
            totalCount += product.getNumber();
            totalPrice = totalPrice + (product.getNumber()*Float.parseFloat(product.getPriceDiscount()));
        }
        txtTotalCount.setText(totalCount+"");
//        txtTotalPrice.setText(totalPrice+"");
        txtTotalPrice.setText(String.format("%sđ", CommonUtils.parseMoney(totalPrice)));
    }

    @Override
    public void onMinusItem(int position) {
        ProductSchedule productSchedule = listScheduled.get(position);
        if (productSchedule.getNumber() > 1) {
            ProductSchedule pro = new ProductSchedule(
                    productSchedule.getProductId(),
                    productSchedule.getNumber() - 1,
                    Float.parseFloat(productSchedule.getPriceDiscount()),
                    productSchedule.getIndexSpinner(),
                    productSchedule.getProductName(),
                    productSchedule.getPrice(),
                    productSchedule.getImg()
            );
            listScheduled.set(position, pro);

            int totalCount = 0;
            float totalPrice = 0;
            for(ProductSchedule product : listScheduled) {
                totalCount += product.getNumber();
                totalPrice = totalPrice + (product.getNumber()*Float.parseFloat(product.getPriceDiscount()));
            }
            txtTotalCount.setText(totalCount+"");
//        txtTotalPrice.setText(totalPrice+"");
            txtTotalPrice.setText(String.format("%sđ", CommonUtils.parseMoney(totalPrice)));
        } else {
            AlertDialog alertDialog = new AlertDialog.Builder(getContext())
                    .setTitle(R.string.ask_del_these_items)
                    .setPositiveButton(R.string.dialog_OK, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            presenter.doDeleteCart(String.valueOf(listScheduled.get(position).getProductId()));
                            listScheduled.remove(position);
                            scheduledAdapter.replaceData(listScheduled);
                            int totalCount = 0;
                            float totalPrice = 0;
                            for(ProductSchedule product : listScheduled) {
                                totalCount += product.getNumber();
                                totalPrice = totalPrice + (product.getNumber()*Float.parseFloat(product.getPriceDiscount()));
                            }
                            txtTotalCount.setText(totalCount+"");
                            //txtTotalPrice.setText(totalPrice+"");
                            txtTotalPrice.setText(String.format("%sđ", CommonUtils.parseMoney(totalPrice)));

                            if(listScheduled.size()<1) {
                                btnScheduled.setEnabled(false);
                                btnScheduled.setBackground(getResources().getDrawable(R.drawable.bg_button_grey));
                            }
                        }
                    })
                    .setNegativeButton(R.string.dialog_CANCEL, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //empty
                        }
                    })
                    .create();

            alertDialog.show();
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(isChecked) {
            edtAddress.setEnabled(false);
            isReceiveCurrentAddess = true;
            lnAddress.setVisibility(View.GONE);
        } else {
            edtAddress.setEnabled(true);
            isReceiveCurrentAddess = false;
            lnAddress.setVisibility(View.VISIBLE);
        }


    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        datePickerDialog.updateDate(year, month, dayOfMonth);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.YEAR, year);
        timePickerDialog = new TimePickerDialog(
                getContext(),
                this,
                calendar.get(Calendar.HOUR), calendar.get(Calendar.MINUTE),
                true
        );
        timePickerDialog.show();
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        timePickerDialog.updateTime(hourOfDay, minute);
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);
        txtDateTime.setText(CommonUtils.formatDateToDayddHHyyyyHHmm(calendar.getTime()));
    }

    @Override
    public void isValidateData(boolean isValidate, Scheduled scheduled) {
        if(isValidate) {
            this.scheduled = scheduled;

            if (listScheduled!=null) {
                if (listScheduled.size() > indexRequestApi) {
                    presenter.doEditCart(
                            String.valueOf(listScheduled.get(indexRequestApi).getProductId()),
                            String.valueOf(listScheduled.get(indexRequestApi).getNumber()));
                    indexRequestApi++;
                } else {
                    startActivity(BackableActivity.newInstanceConfirmSaleProduct(getContext(), BackableActivity.NAVIGATOR_FCF_PRO, scheduled, listProduct.get(0).getShopName(), listProduct.get(0).getShopPhone(), listProduct.get(0).getShopAddress()));
                }
            }
        }
        else showMessage(R.string.msg_error_validate_address);
    }

    @Override
    public void didGetProduct(ListProducts products) {
        //spinner
        // qqqqqqqqqqqqqqqqqqqqqqqweqwewqewq
        listProduct = new ArrayList<ProductResponse>();
        listProductSpinner = new ArrayList<String>();

        for(ProductResponse pro : products.getProduct()) {
            listProduct.add(pro);
            listProductSpinner.add(pro.getName());
        }

        ArrayAdapter<String> pickServiceAdapter = new ArrayAdapter(getContext(), R.layout.custom_spinner_item, listProductSpinner);
        pickServiceAdapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        scheduledAdapter.setPickServiceAdapter(pickServiceAdapter);

        //recycler view booking
        rcListProduct.setHasFixedSize(true);
        rcListProduct.setLayoutManager(linearLayoutManager);
        scheduledAdapter.setOnClickItemScheduled(this);
        scheduledAdapter.setListProduct(listProduct);
        rcListProduct.setAdapter(scheduledAdapter);
        listScheduled = new ArrayList<ProductSchedule>();

        int temp = 0;
        int indexSpinner = 0;
        for(ProductResponse pro : products.getProduct()) {
            for (CartProduct cartProduct : cart.getProduct()) {
                if (pro.getId().equals(cartProduct.getProductId())) {
                    indexSpinner = temp;

                    float tempPrice = 0;
                    int tempProductId = 0;
                    float tempPriceReal = 0;
                    try {
//                        tempPrice = Float.parseFloat(cartProduct.getPriceDiscount());
                        tempPrice = Float.parseFloat(this.getPrice(cartProduct));
                        tempProductId = Integer.parseInt(cartProduct.getProductId());
//                        tempPriceReal = Float.parseFloat(cartProduct.getPrice());
                        tempPriceReal = Float.parseFloat(this.getRealPrice(cartProduct));
                    } catch (NumberFormatException e) {

                    } catch (NullPointerException ex) {

                    }
                    listScheduled.add(new ProductSchedule(tempProductId, Integer.parseInt(cartProduct.getNumber()), tempPrice, indexSpinner, cartProduct.getProductName(), tempPriceReal, cartProduct.getImg()));
                    scheduledAdapter.replaceData(listScheduled);
                }
            }
            temp++;
        }

        String shopAddress = "";
        String shopName = "";
        String shopPhone = "";
        if(products.getProduct()!=null && products.getProduct().size()>0) {
            if(!TextUtils.isEmpty(products.getProduct().get(0).getShopAddress())) {
                if (TextUtils.isEmpty(shopAddress)) {
                    shopAddress = products.getProduct().get(0).getShopAddress();
                }
            }
            if(!TextUtils.isEmpty(products.getProduct().get(0).getShopName())) {
                if (TextUtils.isEmpty(shopName)) {
                    shopName = products.getProduct().get(0).getShopName();
                }
            }
            if(!TextUtils.isEmpty(products.getProduct().get(0).getShopPhone())) {
                if (TextUtils.isEmpty(shopPhone)) {
                    shopPhone = products.getProduct().get(0).getShopPhone();
                }
            }
        }

        txtShopName.setText(shopName);
        txtCurrentAddress.setText(shopAddress);
    }

    @Override
    public void didDeleteCart() {
        
    }

    @Override
    public void didEditCart() {
        if (listScheduled!=null) {
            if (listScheduled.size() > indexRequestApi) {
                presenter.doEditCart(
                        String.valueOf(listScheduled.get(indexRequestApi).getProductId()),
                        String.valueOf(listScheduled.get(indexRequestApi).getNumber()));
                indexRequestApi++;
            } else {
                startActivity(BackableActivity.newInstanceConfirmSaleProduct(getContext(), BackableActivity.NAVIGATOR_FCF_PRO, scheduled, listProduct.get(0).getShopName(), listProduct.get(0).getShopPhone(), listProduct.get(0).getShopAddress()));
            }
        }
    }
    // tha
    @Override
    public void didAddProductToCart() {

    }

    private String getPrice(ProductResponse productResponse) {
        if (productResponse.getTypeUser()==null) return productResponse.getPriceDiscount();

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
        if (productResponse.getTypeUser()==null) return productResponse.getPrice();

        String price = "0";
        switch (productResponse.getTypeUser()) {
            case "1":
                price = productResponse.getPrice1();
                break;
            case "2":
                price = productResponse.getPrice2();
                break;
            case "3":
                price = productResponse.getPrice();
                break;
            case "4":
                price = productResponse.getPrice3();
                break;
            default:
                price = productResponse.getPrice();
                break;
        }
        return price;
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

