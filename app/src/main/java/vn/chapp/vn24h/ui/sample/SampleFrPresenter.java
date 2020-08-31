package vn.chapp.vn24h.ui.sample;

import vn.chapp.vn24h.base.BasePresenter;
import vn.chapp.vn24h.data.DataManager;
import javax.inject.Inject;
import io.reactivex.disposables.CompositeDisposable;


public class SampleFrPresenter<V extends SampleFrMvpView> extends BasePresenter<V> implements SampleFrMvpPresent<V> {

    @Inject
    public SampleFrPresenter(DataManager dataManager, CompositeDisposable compositeDisposable) {
        super(dataManager,compositeDisposable);
    }
}
