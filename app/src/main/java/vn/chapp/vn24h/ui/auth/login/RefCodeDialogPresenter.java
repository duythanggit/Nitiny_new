

package vn.chapp.vn24h.ui.auth.login;


import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import vn.chapp.vn24h.base.BasePresenter;
import vn.chapp.vn24h.data.DataManager;

public class RefCodeDialogPresenter<V extends RefCodeDialogMvpView> extends BasePresenter<V> implements RefCodeDialogMvpPresenter<V> {


    @Inject
    public RefCodeDialogPresenter(DataManager dataManager,
                                  CompositeDisposable compositeDisposable) {
        super(dataManager, compositeDisposable);
    }
}
