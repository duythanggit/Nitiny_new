package vn.chapp.vn24h.ui.wallet;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import vn.chapp.vn24h.R;
import vn.chapp.vn24h.base.BaseAdapter;
import vn.chapp.vn24h.base.BaseViewHolder;
import vn.chapp.vn24h.models.wallet.Wallet;
import vn.chapp.vn24h.utils.CommonUtils;

public class WalletAdapter extends BaseAdapter<Wallet> {

    public WalletAdapter(List<Wallet> collection) {
        super(collection);
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new WalletHistoryViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_wallet_history, viewGroup, false));
    }

    class WalletHistoryViewHolder extends BaseViewHolder {

        @BindView(R.id.txtName)
        TextView txtName;
        @BindView(R.id.txtDateTime)
        TextView txtDateTime;
        @BindView(R.id.txtMoney)
        TextView txtMoney;
        @BindView(R.id.txtStatus)
        TextView txtStatus;

        public WalletHistoryViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onBind(int position) {
            super.onBind(position);

            if(getCollection().size()>position) {
                Wallet wallet = getCollection().get(position);

                if (!TextUtils.isEmpty(wallet.getContent()))
                    txtName.setText(wallet.getContent());
                else txtName.setText("");

                if (!TextUtils.isEmpty(wallet.getDate()))
                    txtDateTime.setText(CommonUtils.formatDateServerToViewWallet(wallet.getDate()));
                else txtDateTime.setText("");

                if (!TextUtils.isEmpty(wallet.getValue()))
                    txtMoney.setText(String.format("%sđ", CommonUtils.parseMoney(wallet.getValue())));
                else txtMoney.setText("");

                if (wallet.getValue().contains("-"))
                    txtMoney.setTextColor(itemView.getResources().getColor(R.color.color242937));
                else txtMoney.setTextColor(itemView.getResources().getColor(R.color.colorFF8800));

    //            if (!TextUtils.isEmpty(wallet.getDate()))
    //                txtStatus.setText(wallet.getDate());
    //            else txtStatus.setText("");
            }

        }
    }
}
