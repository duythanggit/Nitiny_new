package vn.chapp.vn24h.ui.location.locationsearch;

import android.content.Context;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.model.LatLng;
import vn.chapp.vn24h.base.MvpPresenter;


public interface LocationSearchMvpPresent<V extends LocationSearchMvpView> extends MvpPresenter<V> {
    void startSearchPlace(String s);
    void getCurrentLocation(FusedLocationProviderClient fusedLocationProviderClient);
    void onStartMoveMapCamera();
    void onCameraMapIdle(Context context, LatLng latLng);
    void getDetailPlace(String placeId);
    void removeDisposable();
    void getMarkAddress(String address);
    void getDetailMarkPlace(String placeId);
}
