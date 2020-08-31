package vn.chapp.vn24h.ui.auth;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import vn.chapp.vn24h.base.BasePresenter;
import vn.chapp.vn24h.data.DataManager;

public class AuthPresenter<V extends AuthMvpView> extends BasePresenter<V> implements AuthMvpPresenter<V> {

    @Inject
    public AuthPresenter(DataManager dataManager, CompositeDisposable compositeDisposable) {
        super(dataManager,compositeDisposable);
    }
}
