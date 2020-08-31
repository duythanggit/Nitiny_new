package vn.chapp.vn24h.ui.profile;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import vn.chapp.vn24h.base.BasePresenter;
import vn.chapp.vn24h.data.DataManager;

public class ProfileFrPresenter<V extends ProfileFrMvpView> extends BasePresenter<V> implements ProfileFrMvpPresent<V> {

    @Inject
    public ProfileFrPresenter(DataManager dataManager, CompositeDisposable compositeDisposable) {
        super(dataManager, compositeDisposable);
    }

    @Override
    public void getUserDefault() {
        getMvpView().parseUserDefault(getDataManager().getUserDefault());
    }

}
