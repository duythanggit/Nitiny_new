

package vn.chapp.vn24h.ui.services;


import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import vn.chapp.vn24h.base.BasePresenter;
import vn.chapp.vn24h.data.DataManager;

public class ConfirmLinkingDialogPresenter<V extends ConfirmLinkingDialogMvpView> extends BasePresenter<V> implements ConfirmLinkingDialogMvpPresenter<V> {


    @Inject
    public ConfirmLinkingDialogPresenter(DataManager dataManager,
                                         CompositeDisposable compositeDisposable) {
        super(dataManager, compositeDisposable);
    }
}
