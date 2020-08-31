package vn.chapp.vn24h.ui.main;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import vn.chapp.vn24h.R;
import vn.chapp.vn24h.base.AsyncTaskExecutor;
import vn.chapp.vn24h.base.BaseActivity;
import vn.chapp.vn24h.models.FragmentController;
import vn.chapp.vn24h.models.point.PointResponse;
import vn.chapp.vn24h.models.sale.Scheduled;
import vn.chapp.vn24h.models.service.HistoryResponse;
import vn.chapp.vn24h.models.service.ListProducts;
import vn.chapp.vn24h.models.service.NewsResponse;
import vn.chapp.vn24h.models.service.ProductResponse;
import vn.chapp.vn24h.ui.accumulatepoint.AccumulatePointFragment;
import vn.chapp.vn24h.ui.addCode.AddCodeFragment;
import vn.chapp.vn24h.ui.auth.ChangePasswordFragment;
import vn.chapp.vn24h.ui.auth.forgot.ForgotPasswordFragment;
import vn.chapp.vn24h.ui.cart.CartFragment;
import vn.chapp.vn24h.ui.detail.DetailNewsFragment;
import vn.chapp.vn24h.ui.detail.DetailPromotionFragment;
import vn.chapp.vn24h.ui.detailBooking.DetailBookingFragment;
import vn.chapp.vn24h.ui.detailHistory.DetailHistoryFragment;
import vn.chapp.vn24h.ui.detailProduct.DetailProductFragment;
import vn.chapp.vn24h.ui.history.HistoryFragment;
import vn.chapp.vn24h.ui.scheduledProduct.ConfirmProductFragment;
import vn.chapp.vn24h.ui.services.SearchShopFragment;
import vn.chapp.vn24h.ui.shop.ShopDetailFragment;
import vn.chapp.vn24h.ui.profile.ProfileDetailFragment;
import vn.chapp.vn24h.ui.rating.RatingFragment;
import vn.chapp.vn24h.ui.scheduled.ConfirmFragment;
import vn.chapp.vn24h.ui.scheduled.ScheduleFragment;
import vn.chapp.vn24h.ui.services.LinkServiceFragment;
import vn.chapp.vn24h.ui.shopLinked.ShopLinkedFragment;
import vn.chapp.vn24h.ui.wallet.WalletFragment;
import vn.chapp.vn24h.ui.web.WebLoaderFragment;
import vn.chapp.vn24h.ui.widget.UiToolbar;
import vn.chapp.vn24h.utils.AppUtils;
import vn.chapp.vn24h.utils.CommonUtils;

public class BackableActivity extends BaseActivity implements BackableMvpView, UiToolbar.OnToolbarWithBackClickListener {

    @Inject
    BackableMvpPresenter<BackableMvpView> presenter;

    public static final String ARG_NAVIGATOR = "ARG_NAVIGATOR";


    public static final String NAVIGATOR_FGP = "NAVIGATOR_FGP";
    public static final String NAVIGATOR_FLS = "NAVIGATOR_FLS";
    public static final String NAVIGATOR_FPD = "NAVIGATOR_FPD";
    public static final String NAVIGATOR_FSCHEDULED = "NAVIGATOR_FSCHEDULED";
    public static final String NAVIGATOR_FCF = "NAVIGATOR_FCF";
    public static final String NAVIGATOR_FRT = "NAVIGATOR_FRT";
    public static final String NAVIGATOR_FADD = "NAVIGATOR_FADD";
    public static final String NAVIGATOR_FDT = "NAVIGATOR_FDT";
    public static final String NAVIGATOR_CPW = "NAVIGATOR_CPW";
    public static final String NAVIGATOR_FHS = "NAVIGATOR_FHS";
    public static final String NAVIGATOR_FBD = "NAVIGATOR_FBD";
    public static final String NAVIGATOR_FPDTL = "NAVIGATOR_FPDTL";
    public static final String NAVIGATOR_HSDT = "NAVIGATOR_HSDT";
    public static final String NAVIGATOR_WEBLOADER = "NAVIGATOR_WEBLOADER";
    public static final String NAVIGATOR_WEBCONTACT = "NAVIGATOR_WEBCONTACT";
    public static final String NAVIGATOR_WALLET = "NAVIGATOR_WALLET";
    public static final String NAVIGATOR_SHOP_NEAR_BY = "NAVIGATOR_SHOP_NEAR_BY";
    public static final String NAVIGATOR_SHOP_LINKED = "NAVIGATOR_SHOP_LINKED";

    public static final String NAVIGATOR_DETAIL_PROMOTION = "NAVIGATOR_DETAIL_PROMOTION";
    public static final String NAVIGATOR_DETAIL_NEWS_PAPER = "NAVIGATOR_DETAIL_NEWS_PAPER";
    public static final String NAVIGATOR_POINT_DETAIL = "NAVIGATOR_POINT_DETAIL";
    public static final String NAVIGATOR_CART = "NAVIGATOR_CART";
    public static final String NAVIGATOR_FCF_PRO = "NAVIGATOR_FCF_PRO";


    @BindView(R.id.toolbarBackable)
    UiToolbar toolbar;

    @BindView(R.id.frBackable)
    FrameLayout frBackable;

    private String navigator;
    private AsyncTask asyncTaskExecutor;
    private String prevTitle;
    private boolean prevBackEnable;
    private int idService;
    private int isOnLyShopLinked;
    private int shoppDetail_shopId;
    private int shopId;
    private int productId;
    private int type;
    private int bookingId;
    private String shopName;
    private String shopAddress;
    private String shopPhone;
    private String shopPhoneCF;
    private String shopAddressCF;
    private ListProducts products;
    private Scheduled scheduled;
    private String phone;
    private int otp;

    //for detail product
    private NewsResponse newsResponse;
    private ProductResponse productResponse;
    private ListProducts listProduct;
    private String shopNameSelected;
    private String shopAddressSelected;
    private String shopPhoneSelected;
    private HistoryResponse historyResponseDetail;

    //for shop linked
    private int idServiceShopLinked;
    private int isOnLyShopLinkedForShopLinked;

    //for point
    private PointResponse pointResponse;

    //confirm product schedule
    private String shopNamePro;
    private String shopPhoneCFPro;
    private Scheduled scheduledPro;
    private String shopAddressPro;

    public static Intent newInstanceForgotPassword(Context context, String phone, int otp) {
        Intent intent = new Intent(context, BackableActivity.class);
        intent.putExtra(ForgotPasswordFragment.ARG_PHONE, phone);
        intent.putExtra(ForgotPasswordFragment.ARG_OTP, otp);
        intent.putExtra(ARG_NAVIGATOR, NAVIGATOR_FGP);
        return intent;
    }

    public static Intent newInstance(Context context, String navigator) {
        Intent intent = new Intent(context, BackableActivity.class);
        intent.putExtra(ARG_NAVIGATOR, navigator);
        return intent;
    }

    public static Intent newInstanceLinkServices(Context context) {
        Intent intent = new Intent(context, BackableActivity.class);
        intent.putExtra(ARG_NAVIGATOR, NAVIGATOR_FLS);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        return intent;
    }

    public static Intent newInstanceShopDetail(Context context, String navigator, int idService, int shopDetail_shopId) {
        Intent intent = new Intent(context, BackableActivity.class);
        intent.putExtra(ARG_NAVIGATOR, navigator);
        intent.putExtra(ShopDetailFragment.ARG_ID_SERVICE, idService);
        intent.putExtra(ShopDetailFragment.ARG_SHOP_DETAIL_ID_SHOP, shopDetail_shopId);
        return intent;
    }

    public static Intent newInstanceShopNearBy(Context context) {
        Intent intent = new Intent(context, BackableActivity.class);
        intent.putExtra(ARG_NAVIGATOR, NAVIGATOR_SHOP_NEAR_BY);
        return intent;
    }

    public static Intent newInstanceSchedule(
            Context context, ProductResponse productResponse, String navigator, int shopId, int productId, int type, ListProducts products,
            String shopName, String shopAddress, String shopPhone, String image
    ) {
        Intent intent = new Intent(context, BackableActivity.class);
        intent.putExtra(ARG_NAVIGATOR, navigator);
        intent.putExtra(DetailProductFragment.ARG_PRODUCT,productResponse);
        intent.putExtra(ScheduleFragment.ARG_SHOP_ID, shopId);
        intent.putExtra(ScheduleFragment.ARG_PRODUCT_ID, productId);
        intent.putExtra(ScheduleFragment.ARG_TYPE, type);
        intent.putExtra(ScheduleFragment.ARG_LIST_PRODUCTS, products);
        intent.putExtra(ScheduleFragment.ARG_SHOP_NAME, shopName);
        intent.putExtra(ScheduleFragment.ARG_SHOP_ADDRESS, shopAddress);
        intent.putExtra(ScheduleFragment.ARG_SHOP_PHONE_SCH, shopPhone);
        return intent;
    }

    public static Intent newInstanceConfirmSale(Context context, String navigator, Scheduled scheduled, String shopName, String shopPhoneCF, String shopAddressCF) {
        Intent intent = new Intent(context, BackableActivity.class);
        intent.putExtra(ARG_NAVIGATOR, navigator);
        intent.putExtra(ConfirmFragment.ARG_SCHEDULED, scheduled);
        intent.putExtra(ConfirmFragment.ARG_SHOP_NAME, shopName);
        intent.putExtra(ConfirmFragment.ARG_SHOP_PHONE_CF, shopPhoneCF);
        intent.putExtra(ConfirmFragment.ARG_SHOP_ADDRESS_CF, shopAddressCF);
        return intent;
    }

    public static Intent newInstanceBookingDetail(Context context, String navigator, int idBooking) {
        Intent intent = new Intent(context, BackableActivity.class);
        intent.putExtra(ARG_NAVIGATOR, navigator);
        intent.putExtra(DetailBookingFragment.ARG_ID_BOOKING, idBooking);
        return intent;
    }

    public static Intent newInstanceBase(Context context, String navigator, NewsResponse newsResponse, int position) {
        Intent intent = new Intent(context, BackableActivity.class);
        intent.putExtra(ARG_NAVIGATOR, navigator);
        intent.putExtra(DetailPromotionFragment.ARG_NEWS, newsResponse);
        intent.putExtra(DetailPromotionFragment.ARG_POS, position);
        return intent;
    }

    public static Intent newInstanceProductDetail(
            Context context, String navigator, ProductResponse productResponse, ListProducts listProduct,
            String shopNameSelected, String shopAddressSelected, String shopPhoneSelected
    ) {
        Intent intent = new Intent(context, BackableActivity.class);
        intent.putExtra(ARG_NAVIGATOR, navigator);
        intent.putExtra(DetailProductFragment.ARG_PRODUCT, productResponse);
        intent.putExtra(DetailProductFragment.ARG_LIST_PRODUCT, listProduct);
        intent.putExtra(DetailProductFragment.ARG_SHOP_NAME, shopNameSelected);
        intent.putExtra(DetailProductFragment.ARG_SHOP_ADDRESS, shopAddressSelected);
        intent.putExtra(DetailProductFragment.ARG_SHOP_PHONE, shopPhoneSelected);
        return intent;
    }

    public static Intent newInstanceHistoryDetail(Context context, String navigator, HistoryResponse historyResponse) {
        Intent intent = new Intent(context, BackableActivity.class);
        intent.putExtra(ARG_NAVIGATOR, navigator);
        intent.putExtra(DetailHistoryFragment.ARG_HISTORY_RESPONSE, historyResponse);
        return intent;
    }

    public static Intent newInstanceWebLoader(Context context, String url) {
        Intent intent = new Intent(context, BackableActivity.class);
        intent.putExtra(ARG_NAVIGATOR, NAVIGATOR_WEBLOADER);
        intent.putExtra(WebLoaderFragment.ARG_URL, url);
        return intent;
    }

    public static Intent newInstanceWebContact(Context context, String url) {
        Intent intent = new Intent(context, BackableActivity.class);
        intent.putExtra(ARG_NAVIGATOR, NAVIGATOR_WEBCONTACT);
        intent.putExtra(WebLoaderFragment.ARG_URL, url);
        return intent;
    }

    public static Intent newInstanceWallet(Context context) {
        Intent intent = new Intent(context, BackableActivity.class);
        intent.putExtra(ARG_NAVIGATOR, NAVIGATOR_WALLET);
        return intent;
    }

    public static Intent newInstanceShopLinked(Context context, String navigator, int idService, int isOnLyShopLinked) {
        Intent intent = new Intent(context, BackableActivity.class);
        intent.putExtra(ARG_NAVIGATOR, navigator);
        intent.putExtra(ShopLinkedFragment.ARG_ID_SERVICE_SHOP_LINKED, idService);
        intent.putExtra(ShopLinkedFragment.ARG_IS_ONLY_SHOPLINKED_SERVICE_SHOP_LINKED, isOnLyShopLinked);
        return intent;
    }

    public static Intent newInstancePointDetail(
            Context context, String navigator, PointResponse pointResponse
    ) {
        Intent intent = new Intent(context, BackableActivity.class);
        intent.putExtra(ARG_NAVIGATOR, navigator);
        intent.putExtra(AccumulatePointFragment.ARG_POINT_RESPONSE, pointResponse);
        return intent;
    }

    public static Intent newInstanceConfirmSaleProduct(Context context, String navigator, Scheduled scheduled, String shopName, String shopPhoneCF, String shopAddressPro) {
        Intent intent = new Intent(context, BackableActivity.class);
        intent.putExtra(ARG_NAVIGATOR, navigator);
        intent.putExtra(ConfirmProductFragment.ARG_SCHEDULED, scheduled);
        intent.putExtra(ConfirmProductFragment.ARG_SHOP_NAME, shopName);
        intent.putExtra(ConfirmProductFragment.ARG_SHOP_PHONE_CF, shopPhoneCF);
        intent.putExtra(ConfirmProductFragment.ARG_SHOP_ADDRESS_PRO, shopAddressPro);
        return intent;
    }

    @Override
    protected void onBeforeConfigView() {

    }

    @Override
    protected int configView() {
        return R.layout.activity_backable;
    }

    @Override
    protected void init() {
        getActivityComponent().inject(this);
        setUnbinder(ButterKnife.bind(this));
        presenter.onAttach(this);
        toolbar.setOnToolbarWithCloseClick(this);
        navigator = getIntent().getExtras().getString(ARG_NAVIGATOR);
        idService = getIntent().getExtras().getInt(ShopDetailFragment.ARG_ID_SERVICE);

        // type: 1: đã liên kết, 2: chưa liên kết
        isOnLyShopLinked = getIntent().getExtras().getInt(ShopDetailFragment.ARG_IS_ONLY_SHOP_LINKED_SERVICE);

        shoppDetail_shopId = getIntent().getExtras().getInt(ShopDetailFragment.ARG_SHOP_DETAIL_ID_SHOP);
        shopId = getIntent().getExtras().getInt(ScheduleFragment.ARG_SHOP_ID);
        productId = getIntent().getExtras().getInt(ScheduleFragment.ARG_PRODUCT_ID);
        type = getIntent().getExtras().getInt(ScheduleFragment.ARG_TYPE);
        products = getIntent().getExtras().getParcelable(ScheduleFragment.ARG_LIST_PRODUCTS);
        shopName = getIntent().getExtras().getString(ScheduleFragment.ARG_SHOP_NAME);
        shopAddress = getIntent().getExtras().getString(ScheduleFragment.ARG_SHOP_ADDRESS);
        shopPhone = getIntent().getExtras().getString(ScheduleFragment.ARG_SHOP_PHONE_SCH);
        scheduled = getIntent().getExtras().getParcelable(ConfirmFragment.ARG_SCHEDULED);
        shopPhoneCF = getIntent().getExtras().getString(ConfirmFragment.ARG_SHOP_PHONE_CF);
        shopAddressCF = getIntent().getExtras().getString(ConfirmFragment.ARG_SHOP_ADDRESS_CF);
        bookingId = getIntent().getExtras().getInt(DetailBookingFragment.ARG_ID_BOOKING);
        phone = getIntent().getExtras().getString(ForgotPasswordFragment.ARG_PHONE);
        otp = getIntent().getExtras().getInt(ForgotPasswordFragment.ARG_OTP);

        newsResponse = getIntent().getExtras().getParcelable(DetailPromotionFragment.ARG_NEWS);
        //for detail product
        productResponse = getIntent().getExtras().getParcelable(DetailProductFragment.ARG_PRODUCT);
        listProduct = getIntent().getExtras().getParcelable(DetailProductFragment.ARG_LIST_PRODUCT);
        shopNameSelected = getIntent().getExtras().getString(DetailProductFragment.ARG_SHOP_NAME, "");
        shopAddressSelected = getIntent().getExtras().getString(DetailProductFragment.ARG_SHOP_ADDRESS, "");
        shopPhoneSelected = getIntent().getExtras().getString(DetailProductFragment.ARG_SHOP_PHONE, "");

        historyResponseDetail = getIntent().getExtras().getParcelable(DetailHistoryFragment.ARG_HISTORY_RESPONSE);

        idServiceShopLinked = getIntent().getExtras().getInt(ShopLinkedFragment.ARG_ID_SERVICE_SHOP_LINKED);
        isOnLyShopLinkedForShopLinked = getIntent().getExtras().getInt(ShopLinkedFragment.ARG_IS_ONLY_SHOPLINKED_SERVICE_SHOP_LINKED);

        // for point
        pointResponse = getIntent().getExtras().getParcelable(AccumulatePointFragment.ARG_POINT_RESPONSE);

        //for confirm product schedule
        scheduledPro = getIntent().getExtras().getParcelable(ConfirmProductFragment.ARG_SCHEDULED);
        shopPhoneCFPro = getIntent().getExtras().getString(ConfirmProductFragment.ARG_SHOP_PHONE_CF);
        shopNamePro =  getIntent().getExtras().getString(ConfirmProductFragment.ARG_SHOP_NAME);
        shopAddressPro = getIntent().getExtras().getString(ConfirmProductFragment.ARG_SHOP_ADDRESS_PRO);

        navigatorFragment();
    }

    @SuppressLint("StaticFieldLeak")
    void navigatorFragment() {
        asyncTaskExecutor = AsyncTaskExecutor.executeConcurrently(new AsyncTask<Void, Void, FragmentController>() {
            @Override
            protected FragmentController doInBackground(Void... voids) {
                String title = "";
                Fragment fragment = null;
                String TAG = "";
                switch (navigator) {
                    case NAVIGATOR_FGP:
                        toolbar.setVisibility(View.GONE);
//                        title = getString(R.string.toolbar_forgot_password);
                        fragment = ForgotPasswordFragment.newInstance(phone,otp);
                        TAG = ForgotPasswordFragment.TAG;
                        break;
                    case NAVIGATOR_FLS:
                        title = getString(R.string.title_link_services);
                        fragment = LinkServiceFragment.newInstance();
                        TAG = LinkServiceFragment.TAG;
                        toolbar.setEnableBack(false);
                        break;
                    case NAVIGATOR_FPD:
                        title = getString(R.string.title_wellcome);
                        fragment = ProfileDetailFragment.newInstance();
                        TAG = ProfileDetailFragment.TAG;
                        break;
                    case NAVIGATOR_FSCHEDULED:
                        title = getString(R.string.title_schedule);
                        fragment = ScheduleFragment.newInstance(shopId, productId, type, products, shopName, shopAddress, shopPhone);
                        TAG = ScheduleFragment.TAG;
                        break;
                    case NAVIGATOR_FCF:
                        title = getString(R.string.title_confirm);
                        fragment = ConfirmFragment.newInstance(scheduled, shopName, shopPhoneCF, shopAddressCF);
                        TAG = ConfirmFragment.TAG;
                        break;
                    case NAVIGATOR_FRT:
                        title = getString(R.string.title_feedback);
                        fragment = RatingFragment.newInstance();
                        TAG = ConfirmFragment.TAG;
                        break;
                    case NAVIGATOR_FADD:
                        title = getString(R.string.title_add_code);
                        fragment = AddCodeFragment.newInstance();
                        TAG = AddCodeFragment.TAG;
                        break;
                    case NAVIGATOR_FDT:
                        title = getString(R.string.title_home);
                        title = title + " " + CommonUtils.getServiceName(idService);
                        fragment = ShopDetailFragment.newInstance(idService, shoppDetail_shopId, null, isOnLyShopLinked);
                        TAG = ShopDetailFragment.TAG;
                        break;
                    case NAVIGATOR_CPW:
                        title = "Đổi mật khẩu";
                        fragment = ChangePasswordFragment.newInstance();
                        TAG = ChangePasswordFragment.TAG;
                        break;
                    case NAVIGATOR_FHS:
                        title = getString(R.string.title_history);
                        fragment = HistoryFragment.newInstance();
                        TAG = HistoryFragment.TAG;
                        break;
                    case NAVIGATOR_FBD:
                        title = getString(R.string.title_detail_booking);
                        fragment = DetailBookingFragment.newInstance(bookingId);
                        TAG = DetailBookingFragment.TAG;
                        break;
                    case NAVIGATOR_FPDTL:
                        title = getString(R.string.title_detail_product);
                        fragment = DetailProductFragment.newInstance(productResponse, listProduct, shopNameSelected, shopAddressSelected, shopPhoneSelected);
                        TAG = DetailBookingFragment.TAG;
                        break;
                    case NAVIGATOR_HSDT:
                        title = getString(R.string.title_detail_history);
                        fragment = DetailHistoryFragment.newInstance(historyResponseDetail);
                        TAG = DetailHistoryFragment.TAG;
                        break;
                    case NAVIGATOR_WEBLOADER:
                        title = getString(R.string.title_guide);
                        fragment = WebLoaderFragment.newInstance(getIntent().getExtras().getString(WebLoaderFragment.ARG_URL, ""));
                        TAG = WebLoaderFragment.TAG;
                        break;
                    case NAVIGATOR_WEBCONTACT:
                        title = getString(R.string.title_contact);
                        fragment = WebLoaderFragment.newInstance(getIntent().getExtras().getString(WebLoaderFragment.ARG_URL, ""));
                        TAG = WebLoaderFragment.TAG;
                        break;
                    case NAVIGATOR_WALLET:
                        title = getString(R.string.title_wallet);
                        fragment = WalletFragment.newInstance();
                        TAG = WalletFragment.TAG;
//                        toolbar.setActionRight(true, R.drawable.ic_credit_card);
                        toolbar.setStyleTitle(Gravity.START);
                        break;
                    case NAVIGATOR_SHOP_NEAR_BY:
                        title = "Danh sách chủ shop";
                        fragment = SearchShopFragment.newInstance();
                        TAG = SearchShopFragment.TAG;
                        break;
                    case NAVIGATOR_SHOP_LINKED:
                        title = getString(R.string.title_shop_linked);
                        fragment = ShopLinkedFragment.newInstance(idServiceShopLinked, isOnLyShopLinkedForShopLinked);
                        TAG = ShopLinkedFragment.TAG;
                        break;

                    case NAVIGATOR_DETAIL_PROMOTION:
                        title = getString(R.string.title_detail_promotion);
                        fragment = DetailPromotionFragment.newInstance(getIntent().getExtras().getInt(DetailPromotionFragment.ARG_POS), newsResponse);
                        break;
                    case NAVIGATOR_DETAIL_NEWS_PAPER:
                        fragment = DetailNewsFragment.newInstance(getIntent().getExtras().getInt(DetailPromotionFragment.ARG_POS), newsResponse);
                        title = getString(R.string.title_detail_news_paper);
                        break;
                    case NAVIGATOR_POINT_DETAIL:
                        title = getString(R.string.title_accumulate_points);
                        fragment = AccumulatePointFragment.newInstance(pointResponse);
                        TAG = AccumulatePointFragment.TAG;
                        break;
                    case NAVIGATOR_CART:
                        title = "Giỏ hàng";
                        fragment = CartFragment.newInstance();
                        TAG = CartFragment.TAG;
                        break;
                    case NAVIGATOR_FCF_PRO:
                        title = getString(R.string.title_confirm);
                        fragment = ConfirmProductFragment.newInstance(scheduledPro, shopNamePro, shopPhoneCFPro, shopAddressPro);
                        TAG = ConfirmProductFragment.TAG;
                        break;
                }
                if (fragment != null) {
                    return new FragmentController(title, fragment, TAG);
                }
                return null;
            }

            @Override
            protected void onPostExecute(FragmentController fragmentController) {
                super.onPostExecute(fragmentController);
                if (fragmentController != null) {
                    toolbar.setTitle(fragmentController.getTitle());
                    AppUtils.replaceFragmentToActivity(getSupportFragmentManager(), fragmentController.getFragment(), R.id.frBackable, false, fragmentController.getTag());
                }
            }
        }, null);

    }

    @Override
    protected void onDestroy() {
        if (asyncTaskExecutor != null) asyncTaskExecutor.cancel(true);
        clearActivity(this, R.id.mainBackable);
        super.onDestroy();
    }

    public void setToolbarState(boolean enableBack, String title) {
        try {
            prevTitle = toolbar.getTitle();
            prevBackEnable = toolbar.getEnableBack();
            toolbar.setTitle(!TextUtils.isEmpty(title) ? title : "");
            toolbar.setEnableBack(enableBack);
        } catch (Exception ex) {

        }
    }

    public void restoreToolbar() {
        setToolbarState(prevBackEnable, prevTitle);
    }

    @Override
    public void onToolbarBackClick() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStackImmediate();
        } else {
            finish();
        }
    }

    @Override
    public void onToolbarActionRightClick() {

    }
}
