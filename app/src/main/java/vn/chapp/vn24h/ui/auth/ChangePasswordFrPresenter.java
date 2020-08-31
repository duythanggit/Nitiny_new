package vn.chapp.vn24h.ui.auth;

import android.text.TextUtils;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import vn.chapp.vn24h.MainApp;
import vn.chapp.vn24h.R;
import vn.chapp.vn24h.base.BasePresenter;
import vn.chapp.vn24h.data.DataManager;
import vn.chapp.vn24h.data.network.models.Response;
import vn.chapp.vn24h.models.auth.UserDefault;


public class ChangePasswordFrPresenter<V extends ChangePasswordFrMvpView> extends BasePresenter<V> implements ChangePasswordFrMvpPresent<V> {

    @Inject
    public ChangePasswordFrPresenter(DataManager dataManager, CompositeDisposable compositeDisposable) {
        super(dataManager,compositeDisposable);
    }

    @Override
    public boolean isValidate(String oldPassword, String newPassword, String rePassword) {
        if (TextUtils.isEmpty(oldPassword)){
            getMvpView().showMessage("Vui lòng nhập mật khẩu cũ");
            return false;
        }
        if (TextUtils.isEmpty(newPassword)){
            getMvpView().showMessage("Vui lòng nhập mật khẩu mới");
            return false;
        }
        if (TextUtils.isEmpty(rePassword)){
            getMvpView().showMessage("Vui lòng nhập lại mật khẩu mới");
            return false;
        }
        if (!newPassword.equals(rePassword)) {
            getMvpView().showMessage("Mật khẩu nhập lại không khớp");
            return false;
        }
        return true;
    }

    @Override
    public void doChangePassword(String oldPassword, String newPassword, String rePassword) {
        getMvpView().hideKeyboard();
        if (isValidate(oldPassword, newPassword, rePassword)) {
            doNetworkRequest(MainApp.newInstance().getNetworking().changePassword(getDataManager().getUserDefault().getId(), oldPassword, newPassword),
                    () -> getMvpView().showLoading(),
                    () -> getMvpView().hideLoading(), new OnNetworkRequest<Response<Object>>() {
                        @Override
                        public void onNetworkRequestSuccess(Response<Object> response) {
                            if (response.isSuccess()) {
                                getMvpView().showMessage(R.string.msg_change_password_success);
                                UserDefault userDefault = getDataManager().getUserDefault();
                                userDefault.setPassword(newPassword);
                                getDataManager().setUserDefault(userDefault);
                                getMvpView().didChangePassword();
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
