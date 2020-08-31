package vn.chapp.vn24h.ui.customer;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import vn.chapp.vn24h.MainApp;
import vn.chapp.vn24h.base.BasePresenter;
import vn.chapp.vn24h.data.DataManager;
import vn.chapp.vn24h.data.network.models.Response;
import vn.chapp.vn24h.models.service.Shop;

public class CustomerFrPresenter<V extends CustomerFrMvpView> extends BasePresenter<V> implements CustomerFrMvpPresent<V> {

    @Inject
    public CustomerFrPresenter(DataManager dataManager, CompositeDisposable compositeDisposable) {
        super(dataManager, compositeDisposable);
    }

    @Override
    public void doGetShopLinked() {
        doNetworkRequest(MainApp.newInstance().getNetworking().getListShopLinked(getDataManager().getUserDefault().getId(), null, "1"),
                () -> getMvpView().showLoading(),
                () -> {
                    getMvpView().setRefresh(false);
                    getMvpView().hideLoading();
                }, new OnNetworkRequest<Response<List<Shop>>>() {
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

}
