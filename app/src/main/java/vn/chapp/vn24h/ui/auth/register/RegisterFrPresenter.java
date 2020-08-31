package vn.chapp.vn24h.ui.auth.register;

import android.text.TextUtils;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import vn.chapp.vn24h.MainApp;
import vn.chapp.vn24h.R;
import vn.chapp.vn24h.base.BasePresenter;
import vn.chapp.vn24h.data.DataManager;
import vn.chapp.vn24h.data.network.models.Response;
import vn.chapp.vn24h.models.auth.UserDefault;


public class RegisterFrPresenter<V extends RegisterFrMvpView> extends BasePresenter<V> implements RegisterFrMvpPresent<V> {

    @Inject
    public RegisterFrPresenter(DataManager dataManager, CompositeDisposable compositeDisposable) {
        super(dataManager,compositeDisposable);
    }
    @Override
    public boolean isVerifyOTP(int otp, String inputOTP) {
        if (TextUtils.isEmpty(inputOTP)) {
            getMvpView().showMessage(R.string.msg_error_empty_otp);
            return false;
        }
        if (!String.valueOf(otp).equals(inputOTP)) {
            getMvpView().showMessage(R.string.msg_error_invalid_otp);
            return false;
        }
        return true;
    }

    @Override
    public boolean isVerifyRegisterInfo(String name, String phone, String password, String rePassword) {
        if (TextUtils.isEmpty(name)) {
            getMvpView().showMessage(R.string.msg_error_empty_name);
            return false;
        }
        if (TextUtils.isEmpty(phone)) {
            getMvpView().showMessage(R.string.msg_error_empty_phone);
            return false;
        }
        if (TextUtils.isEmpty(password)) {
            getMvpView().showMessage(R.string.msg_error_empty_password);
            return false;
        }
        if (TextUtils.isEmpty(rePassword)) {
            getMvpView().showMessage(R.string.msg_error_empty_repassword);
            return false;
        }
        if (phone.length() != 10) {
            getMvpView().showMessage(R.string.msg_error_invalid_phone);
            return false;
        }
        if (!password.equals(rePassword)) {
            getMvpView().showMessage(R.string.msg_error_invalid_repassword);
            return false;
        }
        return true;
    }

    @Override
    public void doRegisterInfo(int otp, String inputOTP, String name, String phone, String password, String rePassword, String refCode) {
        getMvpView().hideKeyboard();
        if (isVerifyRegisterInfo(name, phone, password, rePassword) && isVerifyOTP(otp, inputOTP)) {
            doNetworkRequest(MainApp.newInstance().getNetworking().postRegister(phone, name, password, refCode),
                    () -> getMvpView().showLoading(),
                    () -> getMvpView().hideLoading(), new OnNetworkRequest<Response<UserDefault>>() {
                        @Override
                        public void onNetworkRequestSuccess(Response<UserDefault> response) {
                            if (response.isSuccessNonNull()) {
                                getDataManager().setUserDefault(response.getData());
                                getDataManager().setIsLogin(true);
                                getMvpView().didRegister(response.getData());
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
    public void doSendOTP(String name, String phone, String password, String rePassword) {
        getMvpView().hideKeyboard();
        if (isVerifyRegisterInfo(name, phone, password, rePassword)) {
            doNetworkRequest(MainApp.newInstance().getNetworking().getVerifyCode(phone),
                    () -> getMvpView().showLoading(),
                    () -> getMvpView().hideLoading(), new OnNetworkRequest<Response<Integer>>() {
                        @Override
                        public void onNetworkRequestSuccess(Response<Integer> response) {
                            if (response.isSuccess()) {
                                getMvpView().didVerifyOTP(response.getData());
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
}
