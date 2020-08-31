package vn.chapp.vn24h.ui.services;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import vn.chapp.vn24h.base.BasePresenter;
import vn.chapp.vn24h.data.DataManager;


public class UpdateInfoServiceFrPresenter<V extends UpdateInfoServiceFrMvpView> extends BasePresenter<V> implements UpdateInfoServiceFrMvpPresent<V> {

    @Inject
    public UpdateInfoServiceFrPresenter(DataManager dataManager, CompositeDisposable compositeDisposable) {
        super(dataManager,compositeDisposable);
    }
}
