package vn.chapp.vn24h.ui.auth.forgot;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import vn.chapp.vn24h.MainApp;
import vn.chapp.vn24h.R;
import vn.chapp.vn24h.base.BasePresenter;
import vn.chapp.vn24h.data.DataManager;
import vn.chapp.vn24h.data.network.models.Response;
import vn.chapp.vn24h.models.auth.UserDefault;
import vn.chapp.vn24h.utils.AppConstants;


public class ForgotPasswordFrPresenter<V extends ForgotPasswordFrMvpView> extends BasePresenter<V> implements ForgotPasswordFrMvpPresent<V> {

    @Inject
    public ForgotPasswordFrPresenter(DataManager dataManager, CompositeDisposable compositeDisposable) {
        super(dataManager,compositeDisposable);
    }

    @Override
    public void doGetOTP(String phone) {
        getMvpView().hideKeyboard();
        doNetworkRequest(MainApp.newInstance().getNetworking().forgetPassword(phone, AppConstants.APP_TYPE),
                () -> getMvpView().showLoading(),
                () -> getMvpView().hideLoading(), new OnNetworkRequest<Response<Integer>>() {
                    @Override
                    public void onNetworkRequestSuccess(Response<Integer> response) {
                        if (response.isSuccessNonNull()) {
                            getMvpView().didGetOTP(response.getData());
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

    @Override
    public void doChangePassword(String phone, String password) {
        getMvpView().hideKeyboard();
        doNetworkRequest(MainApp.newInstance().getNetworking().updatePassword(phone, password),
                () -> getMvpView().showLoading(),
                () -> getMvpView().hideLoading(), new OnNetworkRequest<Response<Object>>() {
                    @Override
                    public void onNetworkRequestSuccess(Response<Object> response) {
                        if (response.isSuccess()) {
                            getMvpView().showMessage(R.string.msg_change_password_success);
                            doLogin(phone, password);
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

    public void doLogin(String phone, String password) {
        doNetworkRequest(MainApp.newInstance().getNetworking().getLogin(phone, password),
                () -> getMvpView().showLoading(),
                () -> getMvpView().hideLoading(), new OnNetworkRequest<Response<UserDefault>>() {
                    @Override
                    public void onNetworkRequestSuccess(Response<UserDefault> response) {
                        if (response.isSuccessNonNull()) {
                            getDataManager().setUserDefault(response.getData());
                            getDataManager().setIsLogin(true);
                            getMvpView().didLogin(response.getData());
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
