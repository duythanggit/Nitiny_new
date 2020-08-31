package vn.chapp.vn24h.ui.location.locationsearch;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import vn.chapp.vn24h.R;
import vn.chapp.vn24h.base.BaseActivity;
import vn.chapp.vn24h.base.OnItemClickListener;
import vn.chapp.vn24h.models.map.Address;
import vn.chapp.vn24h.models.map.PredictionsItem;
import vn.chapp.vn24h.ui.location.ChooseLocationActivity;
import vn.chapp.vn24h.ui.location.SearchPlaceAdapter;
import vn.chapp.vn24h.ui.widget.UiToolbar;
import vn.chapp.vn24h.utils.AppConstants;
import vn.chapp.vn24h.utils.AppUtils;

public class LocationSearchActivity extends BaseActivity implements LocationSearchMvpView, OnMapReadyCallback, GoogleMap.OnCameraIdleListener, GoogleMap.OnCameraMoveStartedListener, AppUtils.PermissionCallBack, OnItemClickListener, UiToolbar.OnToolbarWithBackClickListener, TextWatcher {

    public static final String ARG_LOCATION = "ARG_LOCATION";
    public static final String ARG_ADDRESS = "ARG_ADDRESS";
    public static final int REQUEST_CODE_LOCATION_SEND_SEARCH = 200;
    public static final int RESULT_CODE_LOCATION = 10;


    @Inject
    LocationSearchMvpPresent<LocationSearchMvpView> presenter;

    @Inject
    SearchPlaceAdapter searchPlaceAdapter;
    @Inject
    @Named("vertical")
    LinearLayoutManager linearLayoutManager;
    @Inject
    Timer timerDelaySearch;

    @BindView(R.id.rcPlace)
    RecyclerView rcPlace;
    @BindView(R.id.txtAddr)
    EditText txtAddr;
    @BindView(R.id.toolbar)
    UiToolbar toolbar;
    @BindView(R.id.tvSearch)
    TextView tvSearch;

    private GoogleMap ggMap;
    private FusedLocationProviderClient fusedLocationProviderClient;

    private Address currentAddress;
    private boolean isMoved = true;
    private String markAddress;
    private Address mLocationManager;
    private String lat;
    private String lng;
    private final int delayTime = 300;

    public static Intent newInstance(Context context, String markAddress, Address address) {
        Intent intent = new Intent(context, LocationSearchActivity.class);
        intent.putExtra(ARG_LOCATION, markAddress);
        intent.putExtra(ARG_ADDRESS, address);
        return intent;
    }


    @Override
    protected void onBeforeConfigView() {

    }

    @Override
    protected int configView() {
        return R.layout.activity_location_search;
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
        txtAddr.addTextChangedListener(this);
    }

   /* @OnEditorAction(R.id.txtAddr)
    public boolean onActionSearchClick(TextView v, int actionId, KeyEvent event) {
        hideKeyboard();
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            searchPlaceAdapter.clearPredictionsItems();
            presenter.startSearchPlace(txtAddr.getText().toString());
            hideKeyboard();
            txtAddr.clearFocus();
            return true;
        }
        return false;
    }*/

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

    // TODO: 8/30/2019 search
    /*@OnClick(R.id.imgSearch)
    public void onSearchClick(View v) {
        hideLoading();
        presenter.startSearchPlace(txtAddr.getText().toString());
    }*/

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
        });
    }

    @Override
    public void onFailGotAddr() {
    }

    @Override
    public void displayPlaces(List<PredictionsItem> places) {

        rcPlace.setVisibility(View.VISIBLE);
        searchPlaceAdapter.replacePredictionsItems(places);
    }

    @Override
    public void gotPlaceDetail(Address address) {
        if (ggMap!=null){
            ggMap.setOnCameraIdleListener(null);
            ggMap.setOnCameraMoveStartedListener(null);
            ggMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(address.getLat(), address.getLng()), 16f));
            ggMap.setOnCameraIdleListener(this);
            ggMap.setOnCameraMoveStartedListener(this);
        }
        this.currentAddress = address;
        isMoved = false;
        Intent resultIntent = new Intent();
        if(currentAddress != null) {
            resultIntent.putExtra(ARG_ADDRESS, currentAddress);
            setResult(RESULT_OK, resultIntent);
            finish();
        }
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

    @SuppressLint("MissingPermission")
    @Override
    public void onPermissionsChecked(MultiplePermissionsReport report) {
        if (report.areAllPermissionsGranted()) {
            ggMap.setMyLocationEnabled(true);
            if (currentAddress != null) {
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
        txtAddr.clearFocus();
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
        presenter.removeDisposable();
        clearActivity(this,R.id.ctlLocation);
        super.onDestroy();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK && data!=null){
            switch (requestCode) {
                case REQUEST_CODE_LOCATION_SEND_SEARCH:
                        mLocationManager = (Address) data.getSerializableExtra(ChooseLocationActivity.ARG_ADDRESS);
                        Intent resultIntent = new Intent();
                        resultIntent.putExtra(ARG_ADDRESS, mLocationManager);
                        setResult(RESULT_OK, resultIntent);
                        finish();
                       // setResult(CreateJobFragment.RESULT_CODE_LOCATION, resultIntent);
                    break;
            }
        }
    }

    @OnClick(R.id.tvSearch)
    public void onClickMap(){
        startActivityForResult(ChooseLocationActivity.newInstance(this,"", null),REQUEST_CODE_LOCATION_SEND_SEARCH);
       }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (timerDelaySearch != null){
            timerDelaySearch.cancel();
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
        timerDelaySearch=new Timer();
        timerDelaySearch.schedule(new TimerTask() {
            @Override
            public void run() {
                if (s.length() > 1) {
                    presenter.startSearchPlace(txtAddr.getText().toString());//Tim kiem dia diem neu ki tu nhap vao lon hon 1
                } else if (s.length() <= 0) {
                    runOnUiThread(() -> searchPlaceAdapter.clearPlace());//<0 xoa dia diem
                }
            }
        }, delayTime);
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
