package vn.chapp.vn24h.ui.wallet;

import vn.chapp.vn24h.base.MvpPresenter;

public interface WalletFrMvpPresent <V extends WalletFrMvpView> extends MvpPresenter<V> {
    void getWallet();
}