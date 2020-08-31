package vn.chapp.vn24h.ui.rating;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import vn.chapp.vn24h.base.BasePresenter;
import vn.chapp.vn24h.data.DataManager;


public class ShopSlidePresenter<V extends ShopSlideFrMvpView> extends BasePresenter<V> implements ShopSlideMvpPresent<V> {

    @Inject
    public ShopSlidePresenter(DataManager dataManager, CompositeDisposable compositeDisposable) {
        super(dataManager,compositeDisposable);
    }
}
