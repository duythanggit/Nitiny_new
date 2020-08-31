package vn.chapp.vn24h.ui.services;

import android.annotation.SuppressLint;
import android.location.Location;
import android.os.AsyncTask;

import com.google.android.gms.location.FusedLocationProviderClient;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import vn.chapp.vn24h.MainApp;
import vn.chapp.vn24h.R;
import vn.chapp.vn24h.base.AsyncTaskExecutor;
import vn.chapp.vn24h.base.BasePresenter;
import vn.chapp.vn24h.data.DataManager;
import vn.chapp.vn24h.data.network.NetworkParams;
import vn.chapp.vn24h.data.network.models.Response;
import vn.chapp.vn24h.models.service.Shop;


public class SearchShopFrPresenter<V extends SearchShopFrMvpView> extends BasePresenter<V> implements SearchShopFrMvpPresent<V> {

    @Inject
    FusedLocationProviderClient fusedLocationProviderClient;
    private Location locationSaved;
    private AsyncTask asyncTaskDistance;

    @Inject
    public SearchShopFrPresenter(DataManager dataManager, CompositeDisposable compositeDisposable) {
        super(dataManager,compositeDisposable);
    }

    @SuppressLint("MissingPermission")
    @Override
    public void getCurrentLocation(String serviceId) {
        if (fusedLocationProviderClient == null) return;
        fusedLocationProviderClient.getLastLocation()
                .addOnSuccessListener(location -> locationSaved = location)
                .addOnCompleteListener(locationTask -> searchShopNearBy(serviceId));
    }

    @Override
    public void searchShopNearBy(String serviceId) {
        doNetworkRequest(MainApp.newInstance().getNetworking().getShopNearby(NetworkParams.paramsShopNearBy(locationSaved, serviceId)),
                () -> getMvpView().showLoading(),
                () -> {
                    getMvpView().hideLoading();
                    getMvpView().didRefresh();
                }, new OnNetworkRequest<Response<List<Shop>>>() {
                    @Override
                    public void onNetworkRequestSuccess(Response<List<Shop>> response) {
                        if (response.isSuccess()) {
                            if (locationSaved != null) {
                                calDistance(response.getData());
                            } else {
                                getMvpView().didSearchShop(response.getData() != null ? response.getData() : new ArrayList<>());
                            }

                        } else {
                            getMvpView().showMessage(response.getMessage());
                        }
                    }

                    @Override
                    public void onNetworkRequestError(Throwable throwable) {

                    }
                });
    }

    @Override
    public void cancelAsyncDistance() {
        if (asyncTaskDistance != null)
            asyncTaskDistance.cancel(true);
    }

    @SuppressLint("StaticFieldLeak")
    public void calDistance(List<Shop> shops) {
        asyncTaskDistance = AsyncTaskExecutor.executeConcurrently(new AsyncTask<List<Shop>, Void, List<Shop>>() {
            @SafeVarargs
            @Override
            protected final List<Shop> doInBackground(List<Shop>... lists) {
                List<Shop> shops = lists[0];
                for (Shop shop : shops) {
                    Location locationShop = new Location(shop.getAddress());
                    locationShop.setLatitude(shop.getLat());
                    locationShop.setLongitude(shop.getLng());
                    shop.setDistance(locationSaved.distanceTo(locationShop));
                }
                return shops;
            }

            @Override
            protected void onPostExecute(List<Shop> shops) {
                super.onPostExecute(shops);
                getMvpView().didSearchShop(shops);
            }
        },shops);
    }

    @Override
    public void doAddService(String code, int idService, int shopId) {
        getMvpView().hideKeyboard();
        doNetworkRequest(MainApp.newInstance().getNetworking().getAddShop(getDataManager().getUserDefault().getId(), code),
                () -> getMvpView().showLoading(),
                () -> getMvpView().hideLoading(),
                new OnNetworkRequest<Response<Integer>>() {
                    @Override
                    public void onNetworkRequestSuccess(Response<Integer> response) {
                        if (response.isSuccessNonNull()) {
                            getMvpView().didAddShop(idService, shopId);
                        } else {
                            getMvpView().showMessage(response.getMessage());
                        }
                    }

                    @Override
                    public void onNetworkRequestError(Throwable throwable) {
                        getMvpView().showMessage(R.string.message_unknow_error);
                    }
                });
    }
}
