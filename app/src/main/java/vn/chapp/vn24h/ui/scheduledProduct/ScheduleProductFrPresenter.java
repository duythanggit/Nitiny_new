package vn.chapp.vn24h.ui.scheduledProduct;

import android.text.TextUtils;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import vn.chapp.vn24h.MainApp;
import vn.chapp.vn24h.base.BasePresenter;
import vn.chapp.vn24h.data.DataManager;
import vn.chapp.vn24h.data.network.models.Response;
import vn.chapp.vn24h.models.sale.Scheduled;
import vn.chapp.vn24h.models.service.ListProducts;


public class ScheduleProductFrPresenter<V extends ScheduleProductFrMvpView> extends BasePresenter<V> implements ScheduleProductFrMvpPresent<V> {

    @Inject
    public ScheduleProductFrPresenter(DataManager dataManager, CompositeDisposable compositeDisposable) {
        super(dataManager,compositeDisposable);
    }

    @Override
    public void validateData(Scheduled scheduled) {
        getMvpView().isValidateData(!TextUtils.isEmpty(scheduled.getAddress()), scheduled);
    }

    @Override
    public void doGetProduct() {
        doNetworkRequest(MainApp.newInstance().getNetworking().getProducts(getDataManager().getUserDefault().getId()),
                () -> getMvpView().showLoading(),
                () -> getMvpView().hideLoading(), new OnNetworkRequest<Response<ListProducts>>() {
                    @Override
                    public void onNetworkRequestSuccess(Response<ListProducts> response) {
                        if (response.isSuccessNonNull()) {
                            getMvpView().didGetProduct(response.getData());
                        }
                    }

                    @Override
                    public void onNetworkRequestError(Throwable throwable) {
                    }
                });
    }

    @Override
    public void doEditCart(String productId, String number) {
        doNetworkRequest(MainApp.newInstance().getNetworking().editProtoCart(getDataManager().getUserDefault().getId(), productId, number),
                () -> getMvpView().showLoading(),
                () -> getMvpView().hideLoading(), new OnNetworkRequest<Response<Object>>() {
                    @Override
                    public void onNetworkRequestSuccess(Response<Object> response) {
                        if (response.isSuccess()) {
                            getMvpView().didEditCart();
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
    public void doDeleteCart(String productId) {
        doNetworkRequest(MainApp.newInstance().getNetworking().deleteProtoCart(getDataManager().getUserDefault().getId(), productId),
                () -> getMvpView().showLoading(),
                () -> getMvpView().hideLoading(), new OnNetworkRequest<Response<Object>>() {
                    @Override
                    public void onNetworkRequestSuccess(Response<Object> response) {
                        if (response.isSuccess()) {
                            getMvpView().didDeleteCart();
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
    public void doAddProductToCart(String productId) {
        doNetworkRequest(MainApp.newInstance().getNetworking().addProtoCart(getDataManager().getUserDefault().getId(), productId),
                () -> getMvpView().showLoading(),
                () -> getMvpView().hideLoading(), new OnNetworkRequest<Response<Object>>() {
                    @Override
                    public void onNetworkRequestSuccess(Response<Object> response) {
                        if (response.isSuccess()) {
                            getMvpView().didAddProductToCart();
                        }
                    }

                    @Override
                    public void onNetworkRequestError(Throwable throwable) {
                    }
                });
    }

}
