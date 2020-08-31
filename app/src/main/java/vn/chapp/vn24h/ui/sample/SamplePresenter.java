package vn.chapp.vn24h.ui.sample;

import vn.chapp.vn24h.base.BasePresenter;
import vn.chapp.vn24h.data.DataManager;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class SamplePresenter<V extends SampleMvpView> extends BasePresenter<V> implements SampleMvpPresenter<V> {

    @Inject
    public SamplePresenter(DataManager dataManager, CompositeDisposable compositeDisposable) {
        super(dataManager,compositeDisposable);
    }
}
