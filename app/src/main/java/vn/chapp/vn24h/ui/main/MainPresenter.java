package vn.chapp.vn24h.ui.main;

import android.util.Log;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import vn.chapp.vn24h.base.BasePresenter;
import vn.chapp.vn24h.data.DataManager;
import vn.chapp.vn24h.models.auth.UserDefault;

public class MainPresenter<V extends MainMvpView> extends BasePresenter<V> implements MainMvpPresenter<V> {

    @Inject
    public MainPresenter(DataManager dataManager, CompositeDisposable compositeDisposable) {
        super(dataManager, compositeDisposable);
    }

    @Override
    public void doLogout() {
        getDataManager().logout();
    }

    @Override
    public UserDefault getUserDefault() {
        Log.d("TOKEN_FIREBASE", getDataManager().getTokenFirebase());
        return getDataManager().getUserDefault();
    }

}
