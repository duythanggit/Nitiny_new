package vn.chapp.vn24h.ui.main;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import vn.chapp.vn24h.base.BasePresenter;
import vn.chapp.vn24h.data.DataManager;

public class BackablePresenter<V extends BackableMvpView> extends BasePresenter<V> implements BackableMvpPresenter<V> {

    @Inject
    public BackablePresenter(DataManager dataManager, CompositeDisposable compositeDisposable) {
        super(dataManager,compositeDisposable);
    }
}
