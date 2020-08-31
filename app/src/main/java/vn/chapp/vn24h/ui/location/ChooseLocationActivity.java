package vn.chapp.vn24h.ui.location;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnEditorAction;
import vn.chapp.vn24h.R;
import vn.chapp.vn24h.base.BaseActivity;
import vn.chapp.vn24h.base.OnItemClickListener;
import vn.chapp.vn24h.models.map.Address;
import vn.chapp.vn24h.models.map.PredictionsItem;
import vn.chapp.vn24h.ui.widget.UiToolbar;
import vn.chapp.vn24h.utils.AppConstants;
import vn.chapp.vn24h.utils.AppUtils;

public class ChooseLocationActivity extends BaseActivity implements ChooseLocationMvpView, OnMapReadyCallback, GoogleMap.OnCameraIdleListener, GoogleMap.OnCameraMoveStartedListener, AppUtils.PermissionCallBack, OnItemClickListener, UiToolbar.OnToolbarWithBackClickListener {

    public static final String ARG_LOCATION = "ARG_LOCATION";
    public static final String ARG_ADDRESS = "ARG_ADDRESS";


    @Inject
    ChooseLocationMvpPresent<ChooseLocationMvpView> presenter;

    @Inject
    SearchPlaceAdapter searchPlaceAdapter;
    @Inject
    @Named("vertical")
    LinearLayoutManager linearLayoutManager;
    @BindView(R.id.rcPlace)
    RecyclerView rcPlace;
    @BindView(R.id.txtAddr)
    EditText txtAddr;
    @BindView(R.id.toolbar)
    UiToolbar toolbar;

    private GoogleMap ggMap;
    private SupportMapFragment ggMapFr;
    private FusedLocationProviderClient fusedLocationProviderClient;

    private Address currentAddress;
    private boolean isMoved = true;
    private String markAddress;

    public static Intent newInstance(Context context, String markAddress, Address address) {
        Intent intent = new Intent(context, ChooseLocationActivity.class);
        intent.putExtra(ARG_LOCATION, markAddress);
        intent.putExtra(ARG_ADDRESS, address);
        return intent;
    }


    @Override
    protected void onBeforeConfigView() {

    }

    @Override
    protected int configView() {
        return R.layout.activity_location_picker;
    }

    @Override
    protected void init() {
        getActivityComponent().inject(this);
        setUnbinder(ButterKnife.bind(this));
        presenter.onAttach(this);

        toolbar.setOnToolbarWithCloseClick(this);

        if (getIntent().getExtras() != null) {
            markAddress = getIntent().getStringExtra(ARG_LOCATION);
            currentAddress = (Address) getIntent().getSerializableExtra(ARG_ADDRESS);
        }

        rcPlace.setHasFixedSize(true);
        rcPlace.setLayoutManager(linearLayoutManager);
        rcPlace.setAdapter(searchPlaceAdapter);
        searchPlaceAdapter.setOnItemClickListener(this);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        ggMapFr = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        View myLocationButton = ggMapFr.getView().findViewById(0x2);
        if (myLocationButton != null && myLocationButton.getLayoutParams() instanceof RelativeLayout.LayoutParams) {
            // location button is inside of RelativeLayout
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) myLocationButton.getLayoutParams();
            // Align it to - parent BOTTOM|LEFT
            params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, 0);
            params.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
            // Update margins, set to 10dp
            final int margin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50,
                    getResources().getDisplayMetrics());
            final int marginBottom = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100,
                    getResources().getDisplayMetrics());
            params.setMargins(20, 20, margin, marginBottom);

            myLocationButton.setLayoutParams(params);
        }


        ggMapFr.getMapAsync(this);

    }

    @OnClick(R.id.btnAcceptLocation)
    public void onBtnAcceptLocationClick(View v) {
        if (currentAddress != null) {
            Intent resultIntent = new Intent();
            resultIntent.putExtra(ARG_ADDRESS, currentAddress);
            setResult(RESULT_OK, resultIntent);
            finish();
            /*if (currentAddress.getAddressComponents() == null) {
                presenter.getDetailBestPlace(currentAddress.getLat()+","+currentAddress.getLng());
            } else {
                updateCurrentAddr(currentAddress);
            }*/
        }
    }

    @OnEditorAction(R.id.txtAddr)
    public boolean onActionSearchClick(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            searchPlaceAdapter.clearPredictionsItems();
            presenter.startSearchPlace(txtAddr.getText().toString());
            hideKeyboard();
            txtAddr.clearFocus();
            return true;
        }
        return false;
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.ggMap = googleMap;

        ggMap.setOnCameraIdleListener(this);
        ggMap.setOnCameraMoveStartedListener(this);

        ggMap.getUiSettings().setMyLocationButtonEnabled(true);
        ggMap.getUiSettings().setMapToolbarEnabled(false);
        if (AppUtils.isPermisstionLocationGrant(this)) {
            ggMap.setMyLocationEnabled(true);
            if (currentAddress != null) {
                gotMarkAddress(currentAddress);
            } else {
                String checkingMark = markAddress.replace(",", "").trim();
                if (TextUtils.isEmpty(checkingMark)) {
                    presenter.getCurrentLocation(fusedLocationProviderClient);
                } else {
                    presenter.getMarkAddress(markAddress);
                }
            }
        } else {
            AppUtils.requestPermission(this, AppConstants.LOCATION_PERMISSION, this);
        }
    }

    @Override
    public void onCameraIdle() {
        if (isMoved) {
            presenter.onCameraMapIdle(this, ggMap.getCameraPosition().target);
        }
        isMoved = true;
    }

    @Override
    public void onCameraMoveStarted(int i) {
        txtAddr.setText("");
        presenter.onStartMoveMapCamera();
    }

    @Override
    public void onGotCurrentLocation(Location location) {
        if (location != null) {
            presenter.onCameraMapIdle(this, new LatLng(location.getLatitude(), location.getLongitude()));
            ggMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 16f));
        }
    }

    @Override
    public void onGotAddr(Address addr) {
        runOnUiThread(() -> {
            this.currentAddress = addr;
            if (txtAddr != null) {
                txtAddr.setText(addr.getAddr());
            }
        });
    }

    @Override
    public void onFailGotAddr() {
    }

    @Override
    public void displayPlaces(List<PredictionsItem> places) {
        rcPlace.setVisibility(View.VISIBLE);
        searchPlaceAdapter.replacePredictionsItems(places);
        presenter.getDetailPlace(searchPlaceAdapter.getPredictionsItems().get(1).getPlaceId());
    }

    @Override
    public void gotPlaceDetail(Address address) {
        ggMap.setOnCameraIdleListener(null);
        ggMap.setOnCameraMoveStartedListener(null);
        this.currentAddress = address;
        isMoved = false;
        ggMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(address.getLat(), address.getLng()), 16f));
        ggMap.setOnCameraIdleListener(this);
        ggMap.setOnCameraMoveStartedListener(this);
    }

    @Override
    public void clearSearch() {
        runOnUiThread(() -> searchPlaceAdapter.clearPredictionsItems());
    }

    @Override
    public void gotMarkAddress(Address address) {
        ggMap.setOnCameraIdleListener(null);
        ggMap.setOnCameraMoveStartedListener(null);
        this.currentAddress = address;
        isMoved = false;
        txtAddr.setText(currentAddress.getAddr());
        ggMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(address.getLat(), address.getLng()), 16f));
        ggMap.setOnCameraIdleListener(this);
        ggMap.setOnCameraMoveStartedListener(this);
    }

    @Override
    public void moveToCurrentLocation() {

        presenter.getCurrentLocation(fusedLocationProviderClient);
    }

    @Override
    public void updateCurrentAddr(Address address) {
        this.currentAddress = address;
        Intent resultIntent = new Intent();
        if(currentAddress != null) {
            resultIntent.putExtra(ARG_ADDRESS, currentAddress);
            setResult(RESULT_OK, resultIntent);
            finish();
        }
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onPermissionsChecked(MultiplePermissionsReport report) {
        if (report.areAllPermissionsGranted()) {
            ggMap.setMyLocationEnabled(true);
            if (currentAddress != null && !TextUtils.isEmpty(currentAddress.getAddr())) {
                gotMarkAddress(currentAddress);
            } else {
                String checkingMark = markAddress.replace(",", "").trim();
                if (TextUtils.isEmpty(checkingMark)) {
                    presenter.getCurrentLocation(fusedLocationProviderClient);
                } else {
                    ggMap.setOnCameraIdleListener(null);
                    ggMap.setOnCameraMoveStartedListener(null);
                }
            }

        }
    }

    @Override
    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
        token.continuePermissionRequest();
    }

    @Override
    public void onClick(int position) {
        hideKeyboard();
        presenter.removeDisposable();
        if (!TextUtils.isEmpty(searchPlaceAdapter.getPredictionsItems().get(position).getDescription())) {
            txtAddr.setText(searchPlaceAdapter.getPredictionsItems().get(position).getDescription());
            txtAddr.setSelection(txtAddr.getText().toString().length());
        }
        presenter.getDetailPlace(searchPlaceAdapter.getPredictionsItems().get(position).getPlaceId());
        rcPlace.setVisibility(View.GONE);
        searchPlaceAdapter.clearPredictionsItems();
        this.currentAddress = null;
    }

    @Override
    protected void onDestroy() {
        if (fusedLocationProviderClient != null) fusedLocationProviderClient = null;
        if (ggMap != null) ggMap = null;
        if (ggMapFr != null) ggMapFr = null;
        presenter.removeDisposable();
        clearActivity(this,R.id.ctlLocation);
        super.onDestroy();
    }

    @Override
    public void onToolbarBackClick() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0 ) {
            getSupportFragmentManager().popBackStackImmediate();
        } else {
            finish();
        }
    }

    @Override
    public void onToolbarActionRightClick() {

    }
}
