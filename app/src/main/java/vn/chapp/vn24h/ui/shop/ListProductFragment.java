package vn.chapp.vn24h.ui.shop;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Spinner;
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
import vn.chapp.vn24h.base.NothingSelectedSpinnerAdapter;
import vn.chapp.vn24h.base.OnItemClickListener;
import vn.chapp.vn24h.di.component.ActivityComponent;
import vn.chapp.vn24h.models.service.CategoryAdd;
import vn.chapp.vn24h.models.service.CategoryFilter;
import vn.chapp.vn24h.models.service.NewsResponse;
import vn.chapp.vn24h.models.service.ProductResponse;
import vn.chapp.vn24h.ui.common.CommonPickCategoryAdapter;
import vn.chapp.vn24h.ui.main.BackableActivity;

public class ListProductFragment extends BaseFragment implements ListProductFrMvpView,
        ShopDetailFragment.OnGotProductListener, ShopDetailFragment.OnGotServiceListener,
        OnItemClickListener, ListProductAdapter.OnOrderClickListener, ShopDetailFragment.OnGotPromotionListener,
        ShopDetailFragment.OnGotNewsListener, AdapterView.OnItemSelectedListener,
        CommonPickCategoryAdapter.OnPickCategoryListener{

    public static final String TAG_PRODUCT = "ListProductFragment";
    public static final String TAG_SERVICE = "ListServiceFragment";
    public static final String TAG_PROMOTION = "ListPromotionFragment";
    public static final String TAG_NEW_PAPER = "ListNewPaperFragment";
    public static final String ARG_POSITON = "ARG_POSITON";

    @Inject
    ListProductFrPresenter<ListProductFrMvpView> presenter;
    @Inject
    ListProductAdapter listProductAdapter;
    @Inject
    @Named("2Coloumn")
    GridLayoutManager gridLayoutManager;

    @Inject
    @Named("vertical")
    LinearLayoutManager linearLayoutManager;
    @Inject
    ListNewsAdapter listNewsAdapter;

    @Inject
    @Named("horizontal")
    LinearLayoutManager linearLayoutHorizontalManager;
    @Inject
    CommonPickCategoryAdapter commonPickCategoryAdapter;

    @BindView(R.id.rcProduct)
    RecyclerView rcProduct;
    @BindView(R.id.llSearchProduct)
    LinearLayout llSearchProduct;
    @BindView(R.id.fabCart)
    FloatingActionButton fabCart;
    @BindView(R.id.spnPickService)
    Spinner spinnerCategory;
    @BindView(R.id.rcCategory)
    RecyclerView rcCategory;

    private int position;
    private CategorySpinnerAdapter categorySpinnerAdapter;
    private CategoryAdd categoryAdd;

    private List<CategoryFilter> listCategory;
    private int currentPosPickCategory = 0;
    private String serviceId;
    private String shopId;


    public static ListProductFragment newInstance(int position) {
        ListProductFragment fragment = new ListProductFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_POSITON, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int configView() {
        return R.layout.fragment_list_product;
    }

    @SuppressLint("RestrictedApi")
    @Override
    protected void init(View v) {

        position = getArguments().getInt(ARG_POSITON, -1);
        listProductAdapter.setTypePosition(position);
        rcProduct.setHasFixedSize(true);
        rcProduct.setAdapter(listProductAdapter);
        rcProduct.addItemDecoration(new ListProductItemDecoration());
        listProductAdapter.setOnItemClickListener(this);
        listProductAdapter.setOnOrderClickListener(this);
        switch (position) {
            case 0:
                rcCategory.setVisibility(View.VISIBLE);
                rcCategory.setHasFixedSize(true);
                commonPickCategoryAdapter.setOnPickCategoryListener(this);
                rcCategory.setLayoutManager(linearLayoutHorizontalManager);
                rcCategory.setAdapter(commonPickCategoryAdapter);
                listCategory = new ArrayList<>();
                listCategory.add(new CategoryFilter(null, "Tất cả", true));
                commonPickCategoryAdapter.replaceData(listCategory);

                fabCart.setVisibility(View.VISIBLE);
                List<ProductResponse> productResponseList;
                if (getActivity().getSupportFragmentManager().findFragmentByTag(ShopDetailFragment.TAG) != null ) {
                    rcProduct.setLayoutManager(gridLayoutManager);
                    productResponseList = ((ShopDetailFragment)getActivity().getSupportFragmentManager().findFragmentByTag(ShopDetailFragment.TAG)).getListProduct().getProduct();
                    listProductAdapter.replaceData(productResponseList);
                    if (productResponseList!=null) {
                        if (productResponseList.size() > 0) {
                            serviceId = productResponseList.get(0).getServiceId();
                            shopId = productResponseList.get(0).getShopId();
                            presenter.doGetCategory(serviceId, String.valueOf(this.position+1));
                        }
                    }
                }
                break;
            case 1:
                rcCategory.setVisibility(View.VISIBLE);
                rcCategory.setHasFixedSize(true);
                commonPickCategoryAdapter.setOnPickCategoryListener(this);
                rcCategory.setLayoutManager(linearLayoutHorizontalManager);
                rcCategory.setAdapter(commonPickCategoryAdapter);
                listCategory = new ArrayList<>();
                listCategory.add(new CategoryFilter(null, "Tất cả", true));
                commonPickCategoryAdapter.replaceData(listCategory);

                fabCart.setVisibility(View.GONE);
                List<ProductResponse> productResponseListForService;
                if (getActivity().getSupportFragmentManager().findFragmentByTag(ShopDetailFragment.TAG) != null ) {
                    rcProduct.setLayoutManager(linearLayoutManager);
                    productResponseListForService = ((ShopDetailFragment)getActivity().getSupportFragmentManager().findFragmentByTag(ShopDetailFragment.TAG)).getListProduct().getService();
                    listProductAdapter.replaceData(productResponseListForService);
                    if (productResponseListForService!=null) {
                        if (productResponseListForService.size() > 0) {
                            serviceId = productResponseListForService.get(0).getServiceId();
                            shopId = productResponseListForService.get(0).getShopId();
                            presenter.doGetCategory(serviceId, String.valueOf(this.position+1));
                        }
                    }
                }
                break;
            case 2:
                fabCart.setVisibility(View.GONE);
                rcProduct.setLayoutManager(linearLayoutManager);
                rcProduct.setAdapter(listNewsAdapter);
                if (getActivity().getSupportFragmentManager().findFragmentByTag(ShopDetailFragment.TAG) != null) {
                    listNewsAdapter.replaceData(((ShopDetailFragment) getActivity().getSupportFragmentManager().findFragmentByTag(ShopDetailFragment.TAG)).getListProduct().getPromotion());
                }
                listNewsAdapter.setOnItemClickListener(this);
                break;
            case 3:
                fabCart.setVisibility(View.GONE);
                rcProduct.setLayoutManager(linearLayoutManager);
                rcProduct.setAdapter(listNewsAdapter);
                if (getActivity().getSupportFragmentManager().findFragmentByTag(ShopDetailFragment.TAG) != null) {
                    listNewsAdapter.replaceData(((ShopDetailFragment) getActivity().getSupportFragmentManager().findFragmentByTag(ShopDetailFragment.TAG)).getListProduct().getNews());
                }
                listNewsAdapter.setOnItemClickListener(this);
                break;
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
    public void onGotProduct(List<ProductResponse> product) {
        listProductAdapter.replaceData(product);
    }

    @Override
    public void onGotService(List<ProductResponse> service) {
        listProductAdapter.replaceData(service);
    }

    @OnClick({R.id.fabCart})
    public void onClickCart(View v) {
        startActivity(BackableActivity.newInstance(getContext(), BackableActivity.NAVIGATOR_CART));
    }

    @Override
    public void onClick(int position) {
        //((ShopDetailFragment)getActivity().getSupportFragmentManager().findFragmentByTag(ShopDetailFragment.TAG)).onClickViewDetail(position,listProductAdapter.getCollection().get(position));
        switch (this.position) {
            case 0:
            case 1:
                ((ShopDetailFragment) getActivity().getSupportFragmentManager().findFragmentByTag(ShopDetailFragment.TAG)).onClickViewDetail(position, listProductAdapter.getCollection().get(position));
                break;
            case 2:
                ((ShopDetailFragment) getActivity().getSupportFragmentManager().findFragmentByTag(ShopDetailFragment.TAG)).onClickViewDetailNewPaper(position, listNewsAdapter.getCollection().get(position), BackableActivity.NAVIGATOR_DETAIL_PROMOTION);
                break;
            case 3:
                ((ShopDetailFragment) getActivity().getSupportFragmentManager().findFragmentByTag(ShopDetailFragment.TAG)).onClickViewDetailNewPaper(position, listNewsAdapter.getCollection().get(position), BackableActivity.NAVIGATOR_DETAIL_NEWS_PAPER);
                break;
        }
    }

    @OnClick(R.id.btnSearch)
    public void onSearchFilterClick(View v) {
        switch (this.position) {
            case 0:
                if (categoryAdd == null) {

                } else {
                    if (!TextUtils.isEmpty(shopId)) {
                        presenter.getSearchProduct(categoryAdd, shopId, this.position+1);
                    }
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onOrderClick(int position) {
        ((ShopDetailFragment) getActivity().getSupportFragmentManager().findFragmentByTag(ShopDetailFragment.TAG)).handleClick(listProductAdapter.getCollection().get(position));
    }

    @Override
    public void onAddProductToCart(ProductResponse productResponse) {
        presenter.doAddProductToCart(productResponse.getId());
    }

    @Override
    public void onGotPromotion(List<NewsResponse> promotions) {
        listNewsAdapter.replaceData(promotions);
    }

    @Override
    public void onGotNews(List<NewsResponse> news) {
        listNewsAdapter.replaceData(news);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        position = getArguments().getInt(ARG_POSITON, -1);
        String strTitle = "";
        switch (position) {
            case 0:
                strTitle = "Sản phẩm";
                break;
            case 1:
                strTitle = "Dịch vụ";
                break;
            case 2:
                strTitle = "Khuyến mãi";
                break;
            case 3:
                strTitle = "Tin tức";
                break;
            case 4:
                strTitle = "Tư vấn";
                break;
            case 5:
                strTitle = "Tuyển dụng";
                break;
        }
        if (getActivity() != null && getActivity() instanceof BackableActivity) {
            ((BackableActivity) getActivity()).setToolbarState(true, strTitle);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (getActivity() != null && getActivity() instanceof BackableActivity) {
            ((BackableActivity) getActivity()).restoreToolbar();
        }
    }

    @Override
    public void didGetCategory(List<CategoryAdd> categories) {
        categorySpinnerAdapter = new CategorySpinnerAdapter(getContext(), categories);
        NothingSelectedSpinnerAdapter nothingSelectedSpinnerAdapter = new NothingSelectedSpinnerAdapter(categorySpinnerAdapter, R.layout.item_nothing_product_category, R.layout.item_nothing_product_category, getContext());
        spinnerCategory.setAdapter(nothingSelectedSpinnerAdapter);
        spinnerCategory.setOnItemSelectedListener(this);

        listCategory.removeAll(listCategory);
        listCategory.add(new CategoryFilter(null, "Tất cả", true));
        for (CategoryAdd categoryAdd: categories) {
            listCategory.add(new CategoryFilter(categoryAdd.getId(), categoryAdd.getName(), false));
        }
        commonPickCategoryAdapter.replaceData(listCategory);
    }

    @Override
    public void gotProducts(List<ProductResponse> listProducts) {
        listProductAdapter.replaceData(listProducts);
    }

    @Override
    public void didAddProductToCart() {
        Toast.makeText(getContext(), "Thêm sản phẩm vào giỏ hàng thành công", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        categoryAdd = position == 0 ? null : categorySpinnerAdapter.getCustomerTypes().get(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onPickCategory(int position, CategoryFilter categoryFilter) {
        listCategory.get(currentPosPickCategory).setCheck(false);
        listCategory.get(position).setCheck(true);
        currentPosPickCategory = position;
        commonPickCategoryAdapter.replaceData(listCategory);
        if (!TextUtils.isEmpty(shopId)) {
            presenter.getSearchProduct(new CategoryAdd(categoryFilter.getId(), serviceId, categoryFilter.getName(), null, null), shopId, this.position+1);
        }
    }
}
