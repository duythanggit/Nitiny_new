package vn.chapp.vn24h.ui.shop;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import vn.chapp.vn24h.MainApp;
import vn.chapp.vn24h.base.BasePresenter;
import vn.chapp.vn24h.data.DataManager;
import vn.chapp.vn24h.data.network.models.Response;
import vn.chapp.vn24h.models.service.CategoryAdd;
import vn.chapp.vn24h.models.service.ProductResponse;


public class ListProductFrPresenter<V extends ListProductFrMvpView> extends BasePresenter<V> implements ListProductFrMvpPresent<V> {

    @Inject
    public ListProductFrPresenter(DataManager dataManager, CompositeDisposable compositeDisposable) {
        super(dataManager,compositeDisposable);
    }

    @Override
    public void doGetCategory(String serviceId, String type) {
        doNetworkRequest(MainApp.newInstance().getNetworking().getCategory(type, 1),
                () -> getMvpView().showLoading(),
                () -> getMvpView().hideLoading(), new OnNetworkRequest<Response<List<CategoryAdd>>>() {
                    @Override
                    public void onNetworkRequestSuccess(Response<List<CategoryAdd>> response) {
                        if (response.isSuccessNonNull()) {
                            getMvpView().didGetCategory(response.getData());
                        }
                    }

                    @Override
                    public void onNetworkRequestError(Throwable throwable) {
                        //getMvpView().showMessage(R.string.message_unknow_error);
                    }
                });
    }

    @Override
    public void getSearchProduct(CategoryAdd categoryAdd, String shopId, int type) {
//        doNetworkRequest(MainApp.newInstance().getNetworking().getSearchProduct(
//                    shopId,categoryAdd.getId(), type,
//                    getDataManager().getUserDefault().getId()
//                ),
//                () -> getMvpView().showLoading(),
//                () -> getMvpView().hideLoading(), new OnNetworkRequest<Response<List<ProductResponse>>>() {
//                    @Override
//                    public void onNetworkRequestSuccess(Response<List<ProductResponse>> response) {
//                        if (response.isSuccessNonNull()) {
//                            getMvpView().gotProducts(response.getData());
//                        } else if (response.isSuccess()) {
//                            getMvpView().gotProducts(new ArrayList<ProductResponse>());
//                        }
//                    }
//
//                    @Override
//                    public void onNetworkRequestError(Throwable throwable) {
//                    }
//                });

        // sửa ko truyền shop id
        doNetworkRequest(MainApp.newInstance().getNetworking().getSearchProduct(
                categoryAdd.getId(), type,
                getDataManager().getUserDefault().getId()
                ),
                () -> getMvpView().showLoading(),
                () -> getMvpView().hideLoading(), new OnNetworkRequest<Response<List<ProductResponse>>>() {
                    @Override
                    public void onNetworkRequestSuccess(Response<List<ProductResponse>> response) {
                        if (response.isSuccessNonNull()) {
                            getMvpView().gotProducts(response.getData());
                        } else if (response.isSuccess()) {
                            getMvpView().gotProducts(new ArrayList<ProductResponse>());
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
