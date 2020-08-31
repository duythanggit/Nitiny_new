package vn.chapp.vn24h.ui.starter;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import vn.chapp.vn24h.base.BasePresenter;
import vn.chapp.vn24h.data.DataManager;

public class StarterPresenter<V extends StarterMvpView> extends BasePresenter<V> implements StarterMvpPresenter<V> {

    @Inject
    public StarterPresenter(DataManager dataManager, CompositeDisposable compositeDisposable) {
        super(dataManager,compositeDisposable);
    }
}
