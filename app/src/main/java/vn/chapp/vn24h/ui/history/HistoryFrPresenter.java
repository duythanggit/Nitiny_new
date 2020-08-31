package vn.chapp.vn24h.ui.history;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import vn.chapp.vn24h.MainApp;
import vn.chapp.vn24h.base.BasePresenter;
import vn.chapp.vn24h.data.DataManager;
import vn.chapp.vn24h.data.network.models.Response;
import vn.chapp.vn24h.models.service.HistoryResponse;
import vn.chapp.vn24h.models.service.ServiceResponse;
import vn.chapp.vn24h.models.service.Shop;

public class HistoryFrPresenter<V extends HistoryFrMvpView> extends BasePresenter<V> implements HistoryFrMvpPresent<V> {

    @Inject
    public HistoryFrPresenter(DataManager dataManager, CompositeDisposable compositeDisposable) {
        super(dataManager, compositeDisposable);
    }

    @Override
    public void doGetShopLinked() {
        doNetworkRequest(MainApp.newInstance().getNetworking().getListShopLinked(getDataManager().getUserDefault().getId(), null, "1"),
                () -> {},
                () -> {}, new OnNetworkRequest<Response<List<Shop>>>() {
                    @Override
                    public void onNetworkRequestSuccess(Response<List<Shop>> response) {
                        if (response.isSuccessNonNull()) {
                            getMvpView().didGetShopLinked(response.getData());
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
    public void getServices() {
        doNetworkRequest(MainApp.newInstance().getNetworking().getServices(),
                () -> {},
                () -> {}, new OnNetworkRequest<Response<List<ServiceResponse>>>() {
                    @Override
                    public void onNetworkRequestSuccess(Response<List<ServiceResponse>> response) {
                        if (response.isSuccessNonNull()) {
                            getMvpView().gotServices(response.getData());
                        }
                    }

                    @Override
                    public void onNetworkRequestError(Throwable throwable) {

                    }
                });
    }

    @Override
    public void getUserHistory(String shopId) {
        if(shopId!=null) {
            doNetworkRequest(MainApp.newInstance().getNetworking().getUserHistory(getDataManager().getUserDefault().getId(), shopId),
                    () -> getMvpView().showLoading(),
                    () -> getMvpView().hideLoading(), new OnNetworkRequest<Response<List<HistoryResponse>>>() {
                        @Override
                        public void onNetworkRequestSuccess(Response<List<HistoryResponse>> response) {
                            if (response.isSuccessNonNull()) {
                                getMvpView().gotHistory(response.getData());
                            } else {
                                getMvpView().showMessage(response.getMessage());
                            }
                        }

                        @Override
                        public void onNetworkRequestError(Throwable throwable) {

                        }
                    });
        } else {
            doNetworkRequest(MainApp.newInstance().getNetworking().getUserHistory(getDataManager().getUserDefault().getId()),
                    () -> getMvpView().showLoading(),
                    () -> getMvpView().hideLoading(), new OnNetworkRequest<Response<List<HistoryResponse>>>() {
                        @Override
                        public void onNetworkRequestSuccess(Response<List<HistoryResponse>> response) {
                            if (response.isSuccessNonNull()) {
                                getMvpView().gotHistory(response.getData());
                            } else {
                                getMvpView().showMessage(response.getMessage());
                            }
                        }

                        @Override
                        public void onNetworkRequestError(Throwable throwable) {

                        }
                    });
        }
    }

}
