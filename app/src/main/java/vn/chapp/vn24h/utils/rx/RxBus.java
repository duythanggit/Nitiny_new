package vn.chapp.vn24h.utils.rx;

import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.PublishSubject;
import vn.chapp.vn24h.utils.AppLogger;

public class RxBus {

    private Disposable busDisposable;

    public RxBus() {
    }

    private PublishSubject<Object> bus = PublishSubject.create();

    public void send(Buser buser) {
        bus.onNext(buser);
    }

    public void registerBuser(OnMessageReceived onMessageReceived){
        busDisposable = bus.compose(RxUtil.applySchedulers()).subscribe(
                o -> {
                    if (o instanceof Buser) {
                        if (onMessageReceived != null) onMessageReceived.onMessageReceived((Buser) o);
                    }
                },throwable -> AppLogger.e(throwable.getMessage())
        );
    }

    public void unRegisterBuser(){
        if (busDisposable != null) busDisposable.dispose();
    }

    public interface OnMessageReceived {
        void onMessageReceived(Buser buser);
    }

}