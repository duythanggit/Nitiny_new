package vn.chapp.vn24h.ui.customer;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;
import butterknife.ButterKnife;
import vn.chapp.vn24h.R;
import vn.chapp.vn24h.base.BaseFragment;
import vn.chapp.vn24h.base.BaseRecyclerView;
import vn.chapp.vn24h.di.component.ActivityComponent;
import vn.chapp.vn24h.models.service.Shop;

//public class CustomerFragment extends BaseFragment
//        implements CustomerFrMvpView, OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener,
//        PermissionInterface {
public class CustomerFragment extends BaseFragment implements CustomerFrMvpView, SwipeRefreshLayout.OnRefreshListener, CustomerLinkAdapter.OnCustomerItemClickListener {

    public static final String TAG = CustomerFragment.class.getCanonicalName();

    @Inject
    CustomerFrPresenter<CustomerFrMvpView> presenter;

    @Inject
    @Named("vertical")
    LinearLayoutManager linearLayoutManager;
    @Inject
    CustomerLinkAdapter customerLinkAdapter;

    @BindView(R.id.txtLabel)
    TextView txtLabel;
    @BindView(R.id.rcCustomerLink)
    BaseRecyclerView rcCustomerLink;

    private List<Shop> listShopLinked;


//
//    private GoogleMap mMap;
//
//    // The entry point to the Fused Location Provider.
//    private FusedLocationProviderClient mFusedLocationProviderClient;
//
//    // A default location (FLC Twintower, Cau Giay, HN) and default zoom to use when location permission is
//    // not granted.
//    private final LatLng mDefaultLocation = new LatLng(21.0345255, 105.7950261);
//    private static final int DEFAULT_ZOOM = 15;
//    private boolean mLocationPermissionGranted;
//
//    // The geographical location where the device is currently located. That is, the last-known
//    // location retrieved by the Fused Location Provider.
//    private Location mLastKnownLocation;

    public static CustomerFragment newInstance() {
        CustomerFragment fragment = new CustomerFragment();
        return fragment;
    }

    @Override
    protected int configView() {
        return R.layout.fragment_customer_link_new;
    }

    @Override
    protected void init(View v) {


//        // Build the map.
//        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager()
//                .findFragmentById(R.id.map);
//        mapFragment.getMapAsync(this);
//
//        // Construct a FusedLocationProviderClient.
//        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getContext());
//
////        RefCodeDialog dialog = RefCodeDialog.newInstance(true);
////        dialog.show(getActivity().getSupportFragmentManager(), RefCodeDialog.TAG);
//
//        if(getActivity()!=null && getActivity() instanceof MainActivity) {
//            ((MainActivity) getActivity()).setOnPermissionListener(this);
//        }

        rcCustomerLink.setLayoutManager(linearLayoutManager);
        rcCustomerLink.setAdapter(customerLinkAdapter);
        rcCustomerLink.setOnRefreshListener(this);
        presenter.doGetShopLinked();
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

//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//        mMap = googleMap;
//        mMap.getUiSettings().setMyLocationButtonEnabled(true);
//
//        //set windown for marker
//        this.setPopupWindown();
//
//    }

//    private void getDeviceLocation() {
//        /*
//         * Get the best and most recent location of the device, which may be null in rare
//         * cases when a location is not available.
//         */
//        try {
//            if (mLocationPermissionGranted) {
//                Task<Location> locationResult = mFusedLocationProviderClient.getLastLocation();
//                locationResult.addOnCompleteListener(getActivity(), new OnCompleteListener<Location>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Location> task) {
//                        if (task.isSuccessful() && task.getResult()!=null) {
//                            // Set the map's camera position to the current location of the device.
//                            mLastKnownLocation = task.getResult();
//                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
//                                    new LatLng(mLastKnownLocation.getLatitude(),
//                                            mLastKnownLocation.getLongitude()), DEFAULT_ZOOM));
//                            if(MainApp.newInstance().getCurrentPlaceInfor()==null) {
//                                getAddress(new LatLng(mLastKnownLocation.getLatitude(),
//                                        mLastKnownLocation.getLongitude()));
//                            }
//                        } else {
//                            Log.d(TAG, "Current location is null. Using defaults.");
//                            Log.e(TAG, "Exception: %s", task.getException());
//                            mMap.moveCamera(CameraUpdateFactory
//                                    .newLatLngZoom(mDefaultLocation, DEFAULT_ZOOM));
//                            mMap.getUiSettings().setMyLocationButtonEnabled(false);
//                        }
//                    }
//                });
//            }
//        } catch (SecurityException e)  {
//            Log.e("Exception: %s", e.getMessage());
//        }
//    }
//
//    void setPopupWindown() {
//        // Use a custom info window adapter to handle multiple lines of text in the
//        // info window contents.
//        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
//
//            @Override
//            // Return null here, so that getInfoContents() is called next.
//            public View getInfoWindow(Marker arg0) {
//                return null;
//            }
//
//            @Override
//            public View getInfoContents(Marker marker) {
//                // Inflate the layouts for the info window, title and snippet.
////                View infoWindow = getLayoutInflater().inflate(R.layout.custom_info_map_contents,
////                        (FrameLayout) getActivity().findViewById(R.id.map), false);
//                View infoWindow = LayoutInflater.from(getContext()).inflate(R.layout.custom_info_map_contents, null);
//
//                TextView title = ((TextView) infoWindow.findViewById(R.id.title));
//                title.setText(marker.getTitle());
//
//                TextView snippet = ((TextView) infoWindow.findViewById(R.id.snippet));
//                snippet.setText(marker.getSnippet());
//
//                return infoWindow;
//            }
//        });
//
//        mMap.setOnInfoWindowClickListener(this);
//    }

    @Override
    public void didGetShopLinked(List<Shop> listShopLinked) {
        this.listShopLinked = listShopLinked;
        customerLinkAdapter.replaceData(listShopLinked);
        txtLabel.setText("Danh mục điểm "+listShopLinked.size()+" shop đã liên kết");
//        for(Shop shop : listShopLinked) {
//            mMap.addMarker(new MarkerOptions()
//                    .title(shop.getCompanyName())
//                    .position(new LatLng(shop.getLat(), shop.getLng()))
//                    .snippet(shop.getAddress()));
//        }
    }

    @Override
    public void setRefresh(boolean refresh) {
        getActivity().runOnUiThread(() -> rcCustomerLink.setRefresh(refresh));
    }

    @Override
    public void onRefresh() {
        presenter.doGetShopLinked();
    }

    @Override
    public void onItemClick(int position) {

    }

//    @Override
//    public void onInfoWindowClick(Marker marker) {
//        LatLng latLon = marker.getPosition();
//
//        for(Shop shop : listShopLinked){
//            if (latLon.equals(new LatLng(shop.getLat(), shop.getLng()))){
//                startActivity(BackableActivity.newInstanceShopDetail(getContext(), BackableActivity.NAVIGATOR_FDT, shop.getServiceId(), shop.getShopId()));
//            }
//        }
//    }

    /** private void getAddress(LatLng latLng) {
        Geocoder geocoder;
        List<Address> addresses = null;
        geocoder = new Geocoder(getContext(), Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(addresses!=null && addresses.size() > 0) {
            Log.d(TAG, " " + addresses.get(0).getMaxAddressLineIndex());

            String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            String city = addresses.get(0).getLocality();
            String state = addresses.get(0).getAdminArea();
            String country = addresses.get(0).getCountryName();
            String postalCode = addresses.get(0).getPostalCode();
            String knownName = addresses.get(0).getFeatureName(); // Only if available else return NULL
            String street = addresses.get(0).getThoroughfare();
            String district = addresses.get(0).getSubAdminArea();

            MainApp.newInstance().setCurrentPlaceInfor(new CurrentPlaceInfor(
                    address,
                    latLng
            ));
        }
    }

    @Override
    public void onLocationPermission(boolean isGranted) {
        Log.d("TEST_123", "is Granted: "+isGranted);
        if(isGranted) {
//            updateLocationUI();
            mLocationPermissionGranted = true;
            this.getDeviceLocation();
            presenter.doGetShopLinked();
        } else {
            mLocationPermissionGranted = false;
        }
    } */
}