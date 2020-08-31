

package vn.chapp.vn24h.base;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import vn.chapp.vn24h.data.DataManager;
import vn.chapp.vn24h.data.network.models.Response;
import vn.chapp.vn24h.utils.rx.RxUtil;


public class BasePresenter<V extends MvpView> implements MvpPresenter<V> {

    private static final String TAG = BasePresenter.class.getSimpleName();
    private final DataManager mDataManager;
    private final CompositeDisposable mCompositeDisposable;

    private V mMvpView;

    @Inject
    public BasePresenter(DataManager dataManager, CompositeDisposable compositeDisposable) {
        this.mCompositeDisposable = compositeDisposable;
        this.mDataManager = dataManager;
    }

    @Override
    public void onAttach(V mvpView) {
        mMvpView = mvpView;
    }

    @Override
    public void onDetach() {
        mCompositeDisposable.dispose();
        mMvpView = null;
    }

    public boolean isViewAttached() {
        return mMvpView != null;
    }

    public V getMvpView() {
        return mMvpView;
    }

    public void checkViewAttached() {
        if (!isViewAttached()) throw new MvpViewNotAttachedException();
    }

    public DataManager getDataManager() {
        return mDataManager;
    }

    public CompositeDisposable getCompositeDisposable() {
        return mCompositeDisposable;
    }


    public static class MvpViewNotAttachedException extends RuntimeException {
        public MvpViewNotAttachedException() {
            super("Please call Presenter.onAttach(MvpView) before" +
                    " requesting data to the Presenter");
        }
    }

    public <T> void doNetworkRequest(Observable<Response<T>> src, final Runnable start, final Runnable finish, OnNetworkRequest<Response<T>> onNetworkRequest) {
        getCompositeDisposable().add(RxUtil.appleHandlerStartFinish(
                src,
                start,
                finish)
                .compose(RxUtil.applySchedulers())
                .subscribe(
                        tResponse -> {
                            if (tResponse.isSuccess()) {
                                if (onNetworkRequest != null)
                                    onNetworkRequest.onNetworkRequestSuccess(tResponse);
                            } else {
                                switch (tResponse.getError()) {
                                    default:
                                        if (onNetworkRequest != null)
                                            onNetworkRequest.onNetworkRequestSuccess(tResponse);
                                        break;

                                }
                            }

                        },
                        throwable -> {
                            if (onNetworkRequest != null)
                                onNetworkRequest.onNetworkRequestError(throwable);
                        }
                )
        );
    }

    public interface OnNetworkRequest<T> {
        void onNetworkRequestSuccess(T response);

        void onNetworkRequestError(Throwable throwable);
    }


}
