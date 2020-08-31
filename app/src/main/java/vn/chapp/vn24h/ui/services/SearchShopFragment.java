package vn.chapp.vn24h.ui.services;

import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.srodrigo.androidhintspinner.HintSpinner;
import vn.chapp.vn24h.MainApp;
import vn.chapp.vn24h.R;
import vn.chapp.vn24h.base.BaseActivity;
import vn.chapp.vn24h.base.BaseFragment;
import vn.chapp.vn24h.base.BaseRecyclerView;
import vn.chapp.vn24h.di.component.ActivityComponent;
import vn.chapp.vn24h.models.service.Service;
import vn.chapp.vn24h.models.service.Shop;
import vn.chapp.vn24h.ui.main.BackableActivity;
import vn.chapp.vn24h.ui.shop.ShopDetailFragment;
import vn.chapp.vn24h.utils.AppConstants;
import vn.chapp.vn24h.utils.AppUtils;

import static android.content.Context.LOCATION_SERVICE;

public class SearchShopFragment extends BaseFragment implements SearchShopFrMvpView, AppUtils.PermissionCallBack, AdapterView.OnItemSelectedListener, SearchShopAdapter.OnClickSearchShop, BaseActivity.OnDialogMessageClick, SwipeRefreshLayout.OnRefreshListener {

    public static final String TAG = SearchShopFragment.class.getCanonicalName();

    @Inject
    SearchShopFrPresenter<SearchShopFrMvpView> presenter;
    @Inject
    SearchShopAdapter searchShopAdapter;
    @Inject
    @Named("vertical")
    LinearLayoutManager linearLayoutManager;
    @BindView(R.id.rcShop)
    BaseRecyclerView rcShop;
    @BindView(R.id.spinnerService)
    Spinner spinnerService;
    @BindView(R.id.tvCloseSearch)
    TextView tvCloseSearch;
    @BindView(R.id.tvSearch)
    TextView tvSearch;
    private HintSpinner<Service> serviceHintSpinner;
    private ServiceSpinnerAdapter serviceSpinnerAdapter;
    private List<Service> servicesWrapper;
    private Service selectedService;
    private LocationManager locationManager;


    public static SearchShopFragment newInstance() {
        SearchShopFragment fragment = new SearchShopFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int configView() {
        return R.layout.fragment_search_shop;
    }

    @Override
    protected void init(View v) {
        servicesWrapper = new ArrayList<>();
        if (MainApp.newInstance().getListService() != null)
            servicesWrapper.addAll(MainApp.newInstance().getListService());
        servicesWrapper.add(0,new Service(-1));
        serviceSpinnerAdapter = new ServiceSpinnerAdapter(getContext(),servicesWrapper);
        spinnerService.setAdapter(serviceSpinnerAdapter);
        spinnerService.setOnItemSelectedListener(this);
        rcShop.setHasFixedSize(true);
        rcShop.setLayoutManager(linearLayoutManager);
        searchShopAdapter.setOnClickSearchShop(this);
        rcShop.setAdapter(searchShopAdapter);
        rcShop.setOnRefreshListener(this);
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
    public void onPermissionsChecked(MultiplePermissionsReport report) {
        if (report.areAllPermissionsGranted()) {
            presenter.getCurrentLocation(selectedService == null ? "" : String.valueOf(selectedService.getId()));
        }
    }

    @Override
    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
        token.continuePermissionRequest();
    }

    @Override
    public void didRefresh() {
        getActivity().runOnUiThread(() -> rcShop.setRefresh(false));
    }

    @Override
    public void didSearchShop(List<Shop> shops) {
        searchShopAdapter.replaceData(shops);
        if(selectedService!=null) {
            tvSearch.setVisibility(View.GONE);
            tvCloseSearch.setVisibility(View.VISIBLE);
        } else {
            tvSearch.setVisibility(View.VISIBLE);
            tvCloseSearch.setVisibility(View.GONE);
        }
    }

    @Override
    public void didAddShop(int idService, int shopId) {
        AppUtils.addFragmentToActivity(getActivity().getSupportFragmentManager(), ShopDetailFragment.newInstance(idService, shopId, null, 2),R.id.frBackable,true, ShopDetailFragment.TAG);
    }

    @Override
    public void onResume() {
        super.onResume();
        locationManager = (LocationManager) getContext().getSystemService(LOCATION_SERVICE);
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
        if (AppUtils.isPermisstionLocationGrant(getContext())) {
            presenter.getCurrentLocation(selectedService == null ? "" : String.valueOf(selectedService.getId()));
        } else {
            AppUtils.requestPermission(getActivity(), AppConstants.LOCATION_PERMISSION,this);
        }} else {
//            showDialogMessage(0,getString(R.string.app_name),"Vui lòng bật GPS để tiếp tục sử dụng chức năng này!","OK","Từ chối",false,this);
            presenter.getCurrentLocation(selectedService == null ? "" : String.valueOf(selectedService.getId()));
        }
    }

    @Override
    public void onDestroy() {
        presenter.cancelAsyncDistance();
        super.onDestroy();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        selectedService = position != 0 ? servicesWrapper.get(position) : null;
        presenter.searchShopNearBy(selectedService == null ? "" : String.valueOf(selectedService.getId()));
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @OnClick(R.id.tvSearch)
    public void onSearchClick() {
        presenter.searchShopNearBy(selectedService == null ? "" : String.valueOf(selectedService.getId()));
    }

    @OnClick(R.id.tvCloseSearch)
    public void onClearSearch(View v) {
        selectedService = null;
        spinnerService.setSelection(0);
        onSearchClick();
    }

    @Override
    public void onClickView(int serviceId, int shopId, Shop shop) {
        AppUtils.addFragmentToActivity(getActivity().getSupportFragmentManager(), ShopDetailFragment.newInstance(serviceId, shopId, shop, 2),R.id.frBackable,true, ShopDetailFragment.TAG);
    }

    @Override
    public void onClickAddShop(String code, int serviceId, int shopId) {
        presenter.doAddService(code, serviceId, shopId);
    }

    @Override
    public void onPositiveDialogMessageClick(DialogInterface dialogInterface, int type) {
        if (type == 0) {
            Intent callGPSSettingIntent = new Intent(
                    android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(callGPSSettingIntent);
        }
    }

    @Override
    public void onNegativeDialogMessageClick(DialogInterface dialogInterface, int type) {
        if (type == 0) {
            ((BackableActivity)getActivity()).onToolbarBackClick();
        }
    }

    @Override
    public void onRefresh() {
        presenter.getCurrentLocation(selectedService == null ? "" : String.valueOf(selectedService.getId()));
    }
}
