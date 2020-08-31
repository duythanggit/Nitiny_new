package vn.chapp.vn24h.models.wallet;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WalletHistory {

    @SerializedName("account")
    @Expose
    private String account;
    @SerializedName("wallet")
    @Expose
    private List<Wallet> wallet;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public List<Wallet> getWallet() {
        return wallet;
    }

    public void setWallet(List<Wallet> wallet) {
        this.wallet = wallet;
    }
}
