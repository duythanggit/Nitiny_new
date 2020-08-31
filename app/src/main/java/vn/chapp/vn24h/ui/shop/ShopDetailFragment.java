package vn.chapp.vn24h.ui.shop;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import vn.chapp.vn24h.R;
import vn.chapp.vn24h.base.BaseFragment;
import vn.chapp.vn24h.di.component.ActivityComponent;
import vn.chapp.vn24h.models.service.ListProducts;
import vn.chapp.vn24h.models.service.NewsResponse;
import vn.chapp.vn24h.models.service.ProductResponse;
import vn.chapp.vn24h.models.service.ServiceType;
import vn.chapp.vn24h.models.service.Shop;
import vn.chapp.vn24h.ui.chat.ChatActivity;
import vn.chapp.vn24h.ui.main.BackableActivity;
import vn.chapp.vn24h.ui.main.MainActivity;
import vn.chapp.vn24h.utils.AppConstants;
import vn.chapp.vn24h.utils.AppUtils;
import vn.chapp.vn24h.utils.CommonUtils;
import vn.chapp.vn24h.utils.NetworkUtils;

public class ShopDetailFragment extends BaseFragment implements ShopDetailFrMvpView, OnClickItemShopDetail, AdapterView.OnItemSelectedListener {

    public static final String TAG = ShopDetailFragment.class.getCanonicalName();

    public static final String ARG_ID_SERVICE = "ARG_ID_SERVICE";
    public static final String ARG_IS_ONLY_SHOP_LINKED_SERVICE = "ARG_IS_ONLY_SHOP_LINKED_SERVICE";
    public static final String ARG_SHOP_DETAIL_ID_SHOP = "ARG_SHOP_DETAIL_ID_SHOP";
    public static final String ARG_SHOP_INFOR = "ARG_SHOP_INFOR";

    @Inject
    ShopDetailFrPresenter<ShopDetailFrMvpView> presenter;

    @BindView(R.id.sliderServices)
    SliderLayout sliderServices;

    @BindView(R.id.lnProduct)
    LinearLayout lnProduct;
    @BindView(R.id.lnService)
    LinearLayout lnService;
    @BindView(R.id.lnSale)
    LinearLayout lnSale;
    @BindView(R.id.lnNews)
    LinearLayout lnNews;

    //Promotion
    @BindView(R.id.llPromotion)
    LinearLayout llPromotion;
    @BindView(R.id.imageView)
    ImageView imageViewPromotion;
    @BindView(R.id.tvTimePromotion)
    TextView tvTimePromotion;
    @BindView(R.id.tvTitlePromotion)
    TextView tvTitlePromotion;
    //Promotion_1
    @BindView(R.id.llPromotion_1)
    LinearLayout llPromotion_1;
    @BindView(R.id.imageView_1)
    ImageView imageViewPromotion_1;
    @BindView(R.id.tvTimePromotion_1)
    TextView tvTimePromotion_1;
    @BindView(R.id.tvTitlePromotion_1)
    TextView tvTitlePromotion_1;

    //News
    @BindView(R.id.ivNews)
    ImageView ivNews;
    @BindView(R.id.tvTimeNews)
    TextView tvTimeNews;
    @BindView(R.id.tvTitleNews)
    TextView tvTitleNews;
    @BindView(R.id.llNews)
    LinearLayout llNews;

    //News_1
    @BindView(R.id.ivNews_1)
    ImageView ivNews_1;
    @BindView(R.id.tvTimeNews_1)
    TextView tvTimeNews_1;
    @BindView(R.id.tvTitleNews_1)
    TextView tvTitleNews_1;
    @BindView(R.id.llNews_1)
    LinearLayout llNews_1;

    private int idService;
    private int isOnlyShopLinked;
    private int shopId;
    private Shop shop;
    private Shop shopOfSpinner;

    private List<Shop> listShop;
    private ListProducts listProduct;
    private List<ServiceType> serviceTypes = AppConstants.SERVICE_TYPES;
    private ArrayList<String> bannerResponses;
    private String shopNameSelected;
    private String shopAddressSelected;
    private String shopPhoneSelected;
    private OnGotProductListener onGotProductListener;
    private OnGotServiceListener onGotServiceListener;
    private OnGotNewsListener onGotNewsListener;
    private OnGotPromotionListener onGotPromotionListener;


    @Inject
    MyServiceViewPagerAdapter myServiceViewPagerAdapter;

    public static ShopDetailFragment newInstance() {
        ShopDetailFragment fragment = new ShopDetailFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_ID_SERVICE, 10);
        args.putInt(ARG_IS_ONLY_SHOP_LINKED_SERVICE, 0);
        args.putInt(ARG_SHOP_DETAIL_ID_SHOP, 152);
        args.putParcelable(ARG_SHOP_INFOR, null);
        fragment.setArguments(args);
        return fragment;
    }

    public static ShopDetailFragment newInstance(int idService, int shopId, Shop shop, int isOnlyShopLinked) {
        ShopDetailFragment fragment = new ShopDetailFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_ID_SERVICE, idService);
        args.putInt(ARG_IS_ONLY_SHOP_LINKED_SERVICE, isOnlyShopLinked);
        args.putInt(ARG_SHOP_DETAIL_ID_SHOP, shopId);
        args.putParcelable(ARG_SHOP_INFOR, shop);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int configView() {
        return R.layout.fragment_shop_detail;
    }

    @Override
    protected void init(View v) {

        idService = getArguments().getInt(ARG_ID_SERVICE, 0);

        // type: 1: đã liên kết, 2: chưa liên kết
        isOnlyShopLinked = getArguments().getInt(ARG_IS_ONLY_SHOP_LINKED_SERVICE, 0);

        shopId = getArguments().getInt(ARG_SHOP_DETAIL_ID_SHOP, 0);
        shop = getArguments().getParcelable(ARG_SHOP_INFOR);

        if (getActivity() != null && getActivity() instanceof BackableActivity) {
            String title = getString(R.string.title_home);
            title = title + " " + CommonUtils.getServiceName(idService);
            ((BackableActivity) getActivity()).setToolbarState(true, title);
        }

        bannerResponses = new ArrayList<>();

        if (shop != null) {
            presenter.doGetShopDetail(shopId + "");

//            bannerResponses.add(new BannerResponse("", shop.getAvatar(), shop.getCompanyName(), "", ""));
//            bannerResponses.add(shop.getAvatar());
//            if (!TextUtils.isEmpty(shop.getAvatar())) {
//                String html = "";
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                    html = Html.fromHtml(shop.getCompanyName(), Html.FROM_HTML_MODE_COMPACT).toString();
//                } else {
//                    html = Html.fromHtml(shop.getCompanyName()).toString();
//                }
//
//                TextSliderView textSliderView = new TextSliderView(getContext());
//                textSliderView
//                        .description(html)
//                        .image(shop.getAvatar())
//                        .setScaleType(BaseSliderView.ScaleType.Fit);
//                sliderServices.addSlider(textSliderView);
//            }

        } else {
            presenter.doGetListShop(idService + "", String.valueOf(isOnlyShopLinked));
            presenter.doGetShopDetail(String.valueOf(shopId));
        }

        TextSliderView textSliderView = new TextSliderView(getContext());
        TextSliderView textSliderView1 = new TextSliderView(getContext());
        TextSliderView textSliderView2 = new TextSliderView(getContext());

        textSliderView
                .image(R.drawable.bg_banner)
                .setScaleType(BaseSliderView.ScaleType.Fit);

        textSliderView1
                .image(R.drawable.bg_banner1)
                .setScaleType(BaseSliderView.ScaleType.Fit);

        textSliderView2
                .image(R.drawable.bg_banner2)
                .setScaleType(BaseSliderView.ScaleType.Fit);

        sliderServices.addSlider(textSliderView);
        sliderServices.addSlider(textSliderView1);
        sliderServices.addSlider(textSliderView2);

//        presenter.getBanners();
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

    @OnClick(R.id.imgCall)
    public void onClickCall(View v) {
        if (shop != null) {
            if (shop.getPhone() == null || TextUtils.isEmpty(shop.getPhone())) {
                showMessage("Số điện thoại không hợp lệ");
                return;
            }
            Intent callIntent = new Intent(Intent.ACTION_DIAL);
            callIntent.setData(Uri.parse("tel:" + shop.getPhone()));
            startActivity(callIntent);
        } else {
            if (shopOfSpinner.getPhone() == null || TextUtils.isEmpty(shopOfSpinner.getPhone())) {
                showMessage("Số điện thoại không hợp lệ");
                return;
            }
            Intent callIntent = new Intent(Intent.ACTION_DIAL);
            callIntent.setData(Uri.parse("tel:" + shopOfSpinner.getPhone()));
            startActivity(callIntent);
        }
    }

    @OnClick(R.id.imgChat)
    public void onClickChat(View v) {
        if (shop != null) {
            startActivity(ChatActivity.newInstance(getContext(), shopId + "", shop.getName(), shop.getAvatar(), shop.getPhone()));
        } else {
            startActivity(ChatActivity.newInstance(getContext(), shopId + "", shopOfSpinner.getName(), shopOfSpinner.getAvatar(), shopOfSpinner.getPhone()));
        }
    }

    @OnClick(R.id.imgLocation)
    public void onClickLocation(View v) {
        if (shop != null) {
            CommonUtils.openGoogleMap(getContext(), shop.getLat(), shop.getLng());
        }
    }

    @OnClick({R.id.lnProduct, R.id.lnService, R.id.lnSale, R.id.lnNews, R.id.tvPromotion, R.id.tvNewsPaper})
    public void onClickSercices(View v) {
        this.handleClickNavigateService(v.getId());
    }


    @OnClick(R.id.llPromotionTop)
    public void onClickDetailPromotionTop() {
        startActivity(BackableActivity.newInstanceBase(getActivity(), BackableActivity.NAVIGATOR_DETAIL_PROMOTION, listProduct.getPromotion().get(0), 0));
    }

    @OnClick(R.id.llPromotionTop_1)
    public void onClickDetailPromotionTop_1() {
        startActivity(BackableActivity.newInstanceBase(getActivity(), BackableActivity.NAVIGATOR_DETAIL_PROMOTION, listProduct.getPromotion().get(1), 1));
    }


    @OnClick(R.id.llNewsTop)
    public void onClickDetailNewsTop() {
        startActivity(BackableActivity.newInstanceBase(getActivity(), BackableActivity.NAVIGATOR_DETAIL_NEWS_PAPER, listProduct.getNews().get(0), 0));
    }

    @OnClick(R.id.llNewsTop_1)
    public void onClickDetailNewsTop_1() {
        startActivity(BackableActivity.newInstanceBase(getActivity(), BackableActivity.NAVIGATOR_DETAIL_NEWS_PAPER, listProduct.getNews().get(1), 1));
    }


    @Override
    public void onClickOrder(int position) {
        this.handleClick(listProduct.getProduct().get(position));
    }

    @Override
    public void onClickScheduled(int position) {
        this.handleClick(listProduct.getService().get(position));
    }

    @Override
    public void onClickViewDetail(int position, ProductResponse productResponse) {
        startActivity(BackableActivity.newInstanceProductDetail(
                getContext(), BackableActivity.NAVIGATOR_FPDTL, productResponse, listProduct,
                shopNameSelected, shopAddressSelected, shopPhoneSelected
        ));
    }

    @Override
    public void onClickViewDetailNewPaper(int position, NewsResponse newsResponse, String agr) {
        startActivity(BackableActivity.newInstanceBase(getActivity(), agr, newsResponse, position));
    }

    private void handleClickNavigateService(int id) {
        if (this.listProduct == null) {
            showMessage(("Lỗi mạng. Xin thử lại"));
            return;
        }
        int position = 0;
        switch (id) {
            case R.id.lnProduct:
                position = 0;
                break;
            case R.id.lnService:
                position = 1;
                break;
            case R.id.lnSale:
            case R.id.tvPromotion:
                position = 2;
                break;
            case R.id.lnNews:
            case R.id.tvNewsPaper:
                position = 3;
                break;
        }

        ((MainActivity) getActivity()).enableNavigationBottom(true);
        AppUtils.addFragmentToActivity(
                getActivity().getSupportFragmentManager(),
                ShopDetailTabBarFragment.newInstance(position),
                R.id.frMain,
                true,
                ShopDetailTabBarFragment.TAG);
    }

    public void handleClick(ProductResponse productResponse) {
        int shopId = 0;
        int productId = 0;
        int type = 0;
        try {
            shopId = Integer.parseInt(productResponse.getShopId());
            productId = Integer.parseInt(productResponse.getId());
            type = Integer.parseInt(productResponse.getType());
        } catch (NumberFormatException n) {

        } catch (NullPointerException e) {

        }
        startActivity(BackableActivity.newInstanceSchedule(
                getContext(), productResponse, BackableActivity.NAVIGATOR_FSCHEDULED, shopId, productId, type,
                listProduct, shopNameSelected, shopAddressSelected, shopPhoneSelected, productResponse.getImg()
        ));
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (getActivity() != null && getActivity() instanceof BackableActivity) {
            ((BackableActivity) getActivity()).setToolbarState(true, getString(R.string.title_home));
        }
    }

    @Override
    public void didGetListShop(List<Shop> listShop) {
        this.listShop = listShop;
        if (listShop.size() < 1) {
            showMessage(R.string.msg_no_shop);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (getActivity() != null && getActivity() instanceof BackableActivity) {
                        ((BackableActivity) getActivity()).onToolbarBackClick();
                    }
                }
            }, 500);
        } else {
            /* int indexShopNavigate = 0;
            int indexFor = -1;
            List<String> listShopSpinner = new ArrayList<String>();
            for (Shop shop : listShop) {
                indexFor++;
                listShopSpinner.add(shop.getCompanyName());
                if(shop.getShopId()==shopId) indexShopNavigate=indexFor;
            }
            ArrayAdapter<String> shopAdapter = new ArrayAdapter(
                    getContext(),
                    android.R.layout.simple_spinner_item,
                    listShopSpinner
            );
            shopAdapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
            spnShop.setAdapter(shopAdapter);

            if(indexShopNavigate!=0) {
                spnShop.setSelection(indexShopNavigate);
            }

            shop = listShop.get(0);
            shopId = shop.getShopId(); */

            for (Shop shop : listShop) {
                if (shop.getShopId() == shopId) {
                    this.shop = shop;
//                    txtCompanyName.setText(shop.getCompanyName());
                }
            }
        }
    }

    @Override
    public void didGetShopDetail(ListProducts products) {
        this.listProduct = products;
        //llPromotion
        llPromotion.setVisibility(products.getPromotion() != null && !products.getPromotion().isEmpty() ? View.VISIBLE : View.GONE);
        if (products.getPromotion() != null && !products.getPromotion().isEmpty()) {
            if (!TextUtils.isEmpty(products.getPromotion().get(0).getImg()))
                NetworkUtils.loadImage(getContext(), products.getPromotion().get(0).getImg(), imageViewPromotion);
            if (!TextUtils.isEmpty(products.getPromotion().get(0).getTitle()))
                tvTitlePromotion.setText(products.getPromotion().get(0).getTitle());
                tvTitlePromotion.setTypeface(tvTitlePromotion.getTypeface(), Typeface.BOLD);
            String time = "Áp dụng từ ";
            if (!TextUtils.isEmpty(products.getPromotion().get(0).getDateStart()))
                time += products.getPromotion().get(0).getDateStart();
            time += " đến ";
            if (!TextUtils.isEmpty(products.getPromotion().get(0).getDateEnd()))
                time += products.getPromotion().get(0).getDateEnd();
            tvTimePromotion.setText(time);
        }
        //them
        //llPromotion_1
        llPromotion_1.setVisibility(products.getPromotion() != null && products.getPromotion().size() > 1 ? View.VISIBLE : View.GONE);
        if (products.getPromotion() != null && products.getPromotion().size() > 1) {
            if (!TextUtils.isEmpty(products.getPromotion().get(1).getImg()))
                NetworkUtils.loadImage(getContext(), products.getPromotion().get(1).getImg(), imageViewPromotion_1);
            if (!TextUtils.isEmpty(products.getPromotion().get(1).getTitle()))
                tvTitlePromotion_1.setText(products.getPromotion().get(1).getTitle());
                tvTitlePromotion_1.setTypeface(tvTitlePromotion_1.getTypeface(), Typeface.BOLD);
            String time = "Áp dụng từ ";
            if (!TextUtils.isEmpty(products.getPromotion().get(1).getDateStart()))
                time += products.getPromotion().get(1).getDateStart();
            time += " đến ";
            if (!TextUtils.isEmpty(products.getPromotion().get(1).getDateEnd()))
                time += products.getPromotion().get(1).getDateEnd();
            tvTimePromotion_1.setText(time);
        }
        //llNews
        llNews.setVisibility(products.getNews() != null && !products.getNews().isEmpty() ? View.VISIBLE : View.GONE);
        if (products.getNews() != null && !products.getNews().isEmpty()) {
            if (!TextUtils.isEmpty(products.getNews().get(0).getImg()))
                NetworkUtils.loadImage(getContext(), products.getNews().get(0).getImg(), ivNews);
            if (!TextUtils.isEmpty(products.getNews().get(0).getTitle()))
                tvTitleNews.setText(products.getNews().get(0).getTitle());
                tvTitleNews.setTypeface(tvTitleNews.getTypeface(), Typeface.BOLD);
            String time = "Áp dụng từ ";
            if (!TextUtils.isEmpty(products.getNews().get(0).getDateStart()))
                time += products.getNews().get(0).getDateStart();
            time += " đến ";
            if (!TextUtils.isEmpty(products.getNews().get(0).getDateEnd()))
                time += products.getNews().get(0).getDateEnd();
            tvTimeNews.setText(time);
        }
        //them
        //llNews_1
        llNews_1.setVisibility(products.getNews() != null && products.getNews().size() > 1 ? View.VISIBLE : View.GONE);
        if (products.getNews() != null && products.getNews().size() > 1) {
            if (!TextUtils.isEmpty(products.getNews().get(1).getImg()))
                NetworkUtils.loadImage(getContext(), products.getNews().get(1).getImg(), ivNews_1);
            if (!TextUtils.isEmpty(products.getNews().get(1).getTitle()))
                tvTitleNews_1.setText(products.getNews().get(1).getTitle());
                tvTitleNews_1.setTypeface(tvTitleNews_1.getTypeface(), Typeface.BOLD);
            String time = "Áp dụng từ ";
            if (!TextUtils.isEmpty(products.getNews().get(1).getDateStart()))
                time += products.getNews().get(1).getDateStart();
            time += " đến ";
            if (!TextUtils.isEmpty(products.getNews().get(1).getDateEnd()))
                time += products.getNews().get(1).getDateEnd();
            tvTimeNews_1.setText(time);
        }


        /*this.listProduct = products;
        if (products.getProduct() != null) {
            serviceTypes.get(0).setProducts(products.getProduct());
        }
        if (products.getService() != null) {
            serviceTypes.get(1).setProducts(products.getService());
        }*/
        if (onGotProductListener != null) onGotProductListener.onGotProduct(products.getProduct());
        if (onGotServiceListener != null) onGotServiceListener.onGotService(products.getService());
        if (onGotNewsListener != null) onGotNewsListener.onGotNews(products.getNews());
        if (onGotPromotionListener != null)
            onGotPromotionListener.onGotPromotion(products.getPromotion());
    }

    @Override
    public void gotBanners(List<String> bannerResponses) {
        this.bannerResponses.clear();
        this.bannerResponses.addAll(bannerResponses);
        /** for (BannerResponse banner : bannerResponses) {
         String html = "";
         if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
         html = Html.fromHtml(banner.getName(), Html.FROM_HTML_MODE_COMPACT).toString();
         } else {
         html = Html.fromHtml(banner.getName()).toString();
         }

         TextSliderView textSliderView = new TextSliderView(getContext());
         textSliderView
         .description(html)
         .image(banner.getImg())
         .setScaleType(BaseSliderView.ScaleType.Fit);
         sliderServices.addSlider(textSliderView);
         } */
        for (String banner : bannerResponses) {
            String html = "";

            TextSliderView textSliderView = new TextSliderView(getContext());
            textSliderView
                    .description(html)
                    .image(banner)
                    .setScaleType(BaseSliderView.ScaleType.Fit);
            sliderServices.addSlider(textSliderView);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public interface OnGotProductListener {
        void onGotProduct(List<ProductResponse> product);
    }

    public interface OnGotServiceListener {
        void onGotService(List<ProductResponse> service);
    }

    public interface OnGotNewsListener {
        void onGotNews(List<NewsResponse> news);
    }

    public interface OnGotPromotionListener {
        void onGotPromotion(List<NewsResponse> promotions);
    }

    public void setOnGotProductListener(OnGotProductListener onGotProductListener) {
        this.onGotProductListener = onGotProductListener;
    }

    public void setOnGotServiceListener(OnGotServiceListener onGotServiceListener) {
        this.onGotServiceListener = onGotServiceListener;
    }

    public void setOnGotNewsListener(OnGotNewsListener onGotNewsListener) {
        this.onGotNewsListener = onGotNewsListener;
    }

    public void setOnGotPromotionListener(OnGotPromotionListener onGotPromotionListener) {
        this.onGotPromotionListener = onGotPromotionListener;
    }

    public ListProducts getListProduct() {
        return listProduct;
    }

    //    @Override
//        super.onDetach();
//        if (getActivity() != null && getActivity() instanceof BackableActivity) {
//            ((BackableActivity)getActivity()).restoreToolbar();
//        }
//    }
}