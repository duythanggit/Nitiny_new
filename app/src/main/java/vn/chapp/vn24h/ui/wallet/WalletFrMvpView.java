package vn.chapp.vn24h.ui.wallet;

import vn.chapp.vn24h.base.MvpView;
import vn.chapp.vn24h.models.wallet.WalletHistory;

public interface WalletFrMvpView extends MvpView {
    void gotWallet(WalletHistory walletHistory);
    void setRefresh(boolean refreshed);
}
