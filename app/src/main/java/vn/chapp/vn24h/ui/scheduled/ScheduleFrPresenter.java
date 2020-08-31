package vn.chapp.vn24h.ui.scheduled;

import android.text.TextUtils;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import vn.chapp.vn24h.base.BasePresenter;
import vn.chapp.vn24h.data.DataManager;
import vn.chapp.vn24h.models.sale.Scheduled;


public class ScheduleFrPresenter<V extends ScheduleFrMvpView> extends BasePresenter<V> implements ScheduleFrMvpPresent<V> {

    @Inject
    public ScheduleFrPresenter(DataManager dataManager, CompositeDisposable compositeDisposable) {
        super(dataManager,compositeDisposable);
    }

    @Override
    public void validateData(Scheduled scheduled) {
        getMvpView().isValidateData(!TextUtils.isEmpty(scheduled.getAddress()), scheduled);
    }
}
