package vn.chapp.vn24h.ui.auth.login;

import android.text.TextUtils;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import vn.chapp.vn24h.MainApp;
import vn.chapp.vn24h.R;
import vn.chapp.vn24h.base.BasePresenter;
import vn.chapp.vn24h.data.DataManager;
import vn.chapp.vn24h.data.network.models.Response;
import vn.chapp.vn24h.models.auth.UserDefault;
import vn.chapp.vn24h.utils.AppConstants;
import vn.chapp.vn24h.utils.AppLogger;


public class LoginFrPresenter<V extends LoginFrMvpView> extends BasePresenter<V> implements LoginFrMvpPresent<V> {

    @Inject
    public LoginFrPresenter(DataManager dataManager, CompositeDisposable compositeDisposable) {
        super(dataManager,compositeDisposable);
    }

    @Override
    public boolean isValidateLogin(String phone, String password) {
        if (TextUtils.isEmpty(phone)) {
            getMvpView().showMessage(R.string.msg_error_empty_phone);
            return false;
        }
        if (TextUtils.isEmpty(password)) {
            getMvpView().showMessage(R.string.msg_error_empty_password);
            return false;
        }
        if (phone.length() != 10) {
            getMvpView().showMessage(R.string.msg_error_invalid_phone);
            return false;
        }
        return true;
    }

    @Override
    public void doLogin(String phone, String password) {
        getMvpView().hideKeyboard();
        if (isValidateLogin(phone, password)) {
            doNetworkRequest(MainApp.newInstance().getNetworking().getLogin(phone, password),
                    () -> getMvpView().showLoading(),
                    () -> getMvpView().hideLoading(), new OnNetworkRequest<Response<UserDefault>>() {
                        @Override
                        public void onNetworkRequestSuccess(Response<UserDefault> response) {
                            if (response.isSuccessNonNull()) {
                                getDataManager().setUserDefault(response.getData());
                                getDataManager().setIsLogin(true);
                                getMvpView().didLogin(response.getData());

                                //update token
                                updateTokenFCM(response.getData().getId());
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

    @Override
    public boolean isValidateLoginGoogle(String email, String ggid, String name) {
        if (TextUtils.isEmpty(email)) {
            getMvpView().showMessage(R.string.msg_error_login_google_loss_params);
            return false;
        }
        if (TextUtils.isEmpty(ggid)) {
            getMvpView().showMessage(R.string.msg_error_login_google_loss_params);
            return false;
        }
        if (TextUtils.isEmpty(name)) {
            getMvpView().showMessage(R.string.msg_error_login_google_loss_params);
            return false;
        }
        return true;
    }

    @Override
    public void doLoginGoogle(String email, String ggid, String name, String refCode) {
        getMvpView().hideKeyboard();
        if (isValidateLoginGoogle(email, ggid, name)) {
            doNetworkRequest(MainApp.newInstance().getNetworking().getLoginGoogle(email, ggid, name, refCode),
                    () -> getMvpView().showLoading(),
                    () -> getMvpView().hideLoading(), new OnNetworkRequest<Response<UserDefault>>() {
                        @Override
                        public void onNetworkRequestSuccess(Response<UserDefault> response) {
                            if (response.isSuccessNonNull()) {
                                getDataManager().setUserDefault(response.getData());
                                getDataManager().setIsLogin(true);
                                getMvpView().didLogin(response.getData());

                                //update token
                                updateTokenFCM(response.getData().getId());
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

    @Override
    public void doForgetPassword(String phone) {
        getMvpView().hideKeyboard();
        doNetworkRequest(MainApp.newInstance().getNetworking().forgetPassword(phone, AppConstants.APP_TYPE),
                () -> getMvpView().showLoading(),
                () -> getMvpView().hideLoading(), new OnNetworkRequest<Response<Integer>>() {
                    @Override
                    public void onNetworkRequestSuccess(Response<Integer> response) {
                        if (response.isSuccessNonNull()) {
                            getMvpView().didForgetPassword(phone, response.getData());
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

    private void updateTokenFCM(String id) {
        doNetworkRequest(MainApp.newInstance().getNetworking().updateTokenFCM(id, AppConstants.APP_TYPE, getDataManager().getTokenFirebase()),
                () -> {},
                () -> {}, new OnNetworkRequest<Response<Integer>>() {
                    @Override
                    public void onNetworkRequestSuccess(Response<Integer> response) {
                        if (response.isSuccess()) {
                            AppLogger.d("FCM", "update token fcm success");
                        } else {
                            AppLogger.d("FCM", "update token fcm fail");
                        }
                    }

                    @Override
                    public void onNetworkRequestError(Throwable throwable) {
                        AppLogger.d("FCM", "update token fcm fail");
                    }
                });
    }
}
