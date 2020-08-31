package vn.chapp.vn24h.ui.location.locationsearch;

import android.annotation.SuppressLint;
import android.content.Context;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.model.LatLng;

import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import vn.chapp.vn24h.BuildConfig;
import vn.chapp.vn24h.MainApp;
import vn.chapp.vn24h.R;
import vn.chapp.vn24h.base.BasePresenter;
import vn.chapp.vn24h.data.DataManager;
import vn.chapp.vn24h.models.map.Address;
import vn.chapp.vn24h.utils.AppLogger;
import vn.chapp.vn24h.utils.CommonUtils;
import vn.chapp.vn24h.utils.rx.RxUtil;


public class LocationSearchPresenter<V extends LocationSearchMvpView> extends BasePresenter<V> implements LocationSearchMvpPresent<V> {

    private Timer timerGetAddr;
    private Disposable disposable;

    @Inject
    public LocationSearchPresenter(DataManager dataManager, CompositeDisposable compositeDisposable) {
        super(dataManager, compositeDisposable);
    }

    @SuppressLint("MissingPermission")
    @Override
    public void getCurrentLocation(FusedLocationProviderClient fusedLocationProviderClient) {
        if (fusedLocationProviderClient == null) return;
        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(location -> getMvpView().onGotCurrentLocation(location))
                .addOnFailureListener(e -> getMvpView().onFailGotAddr());
    }

    @Override
    public void startSearchPlace(String s) {
        getCompositeDisposable().add(RxUtil.appleHandlerStartFinish(MainApp.newInstance().getMapNetworking().autoCompletePlace(s, BuildConfig.PLACE_SEARCH_KEY),
                () -> { },
                () -> { })
                .compose(RxUtil.applySchedulers())
                .subscribe(
                        placeResponse -> {
                            if (placeResponse.getStatus().equals("OK")) {
                                getMvpView().displayPlaces(placeResponse.getPredictions());
                            } else {
                                AppLogger.e(placeResponse.getStatus());
                            }
                        },
                        throwable -> AppLogger.e(throwable.getLocalizedMessage())
                ));
    }

    @Override
    public void getDetailPlace(String placeId) {
        getCompositeDisposable().add(RxUtil.appleHandlerStartFinish(
                MainApp.newInstance().getMapNetworking().getDetailPlace(placeId, BuildConfig.PLACE_SEARCH_KEY),
                () -> {
                },
                () -> {
                })
                .compose(RxUtil.applySchedulers())
                .subscribe(
                        detailPlaceResponse -> {
                            if (detailPlaceResponse.getStatus().equals("OK")) {
                                Address address = new Address(
                                        detailPlaceResponse.getResult().getGeometry().getLocation().getLat(),
                                        detailPlaceResponse.getResult().getGeometry().getLocation().getLng(),
                                        detailPlaceResponse.getResult().getFormattedAddress(),
                                        detailPlaceResponse.getResult().getAddressComponents()
                                );
                                getMvpView().gotPlaceDetail(address);
                            } else {
                                getMvpView().showMessage(R.string.message_unknow_error);
                            }
                        },
                        throwable -> {
                            AppLogger.e(throwable.getLocalizedMessage());
                            getMvpView().showMessage(R.string.message_unknow_error);
                            throwable.printStackTrace();
                        }
                )
        );
    }

    @Override
    public void onStartMoveMapCamera() {
        if (timerGetAddr != null) timerGetAddr.cancel();
    }

    @Override
    public void onCameraMapIdle(Context context, LatLng latLng) {
        timerGetAddr = new Timer();
        timerGetAddr.schedule(new TimerTask() {
            @Override
            public void run() {
                String addr = CommonUtils.getAddrLatLng(context, latLng.latitude, latLng.longitude);
//                getMvpView().onGotAddr(new Address(latLng.latitude, latLng.longitude, addr));

            }
        }, 300);

    }

    public void removeDisposable() {
        if (disposable != null) disposable.dispose();
    }

    @Override
    public void getMarkAddress(String address) {
        getCompositeDisposable().add(RxUtil.appleHandlerStartFinish(MainApp.newInstance().getMapNetworking().autoCompletePlace(address, BuildConfig.PLACE_SEARCH_KEY),
                () -> {
                },
                () -> {
                })
                .compose(RxUtil.applySchedulers())
                .subscribe(
                        placeResponse -> {
                            if (placeResponse.getStatus().equals("OK")) {
                                if (placeResponse.getPredictions() != null && placeResponse.getPredictions().size() > 0) {
                                    getDetailMarkPlace(placeResponse.getPredictions().get(0).getPlaceId());
                                } else {
                                    getMvpView().moveToCurrentLocation();
                                }
                            } else {
                                getMvpView().moveToCurrentLocation();
                            }
                        },
                        throwable -> getMvpView().moveToCurrentLocation()
                ));
    }

    @Override
    public void getDetailMarkPlace(String placeId) {
        getCompositeDisposable().add(RxUtil.appleHandlerStartFinish(
                MainApp.newInstance().getMapNetworking().getDetailPlace(placeId, BuildConfig.PLACE_SEARCH_KEY),
                () -> {
                },
                () -> {
                })
                .compose(RxUtil.applySchedulers())
                .subscribe(
                        detailPlaceResponse -> {
                            if (detailPlaceResponse.getStatus().equals("OK")) {
                                Address address = new Address(
                                        detailPlaceResponse.getResult().getGeometry().getLocation().getLat(),
                                        detailPlaceResponse.getResult().getGeometry().getLocation().getLng(),
                                        detailPlaceResponse.getResult().getFormattedAddress(),
                                        detailPlaceResponse.getResult().getAddressComponents()
                                );
                                getMvpView().gotMarkAddress(address);
                            } else {
                                getMvpView().moveToCurrentLocation();
                            }
                        },
                        throwable -> getMvpView().moveToCurrentLocation()
                )
        );
    }

}
