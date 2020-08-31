package vn.chapp.vn24h.ui.history;

import java.util.List;

import vn.chapp.vn24h.base.MvpView;
import vn.chapp.vn24h.models.service.HistoryResponse;
import vn.chapp.vn24h.models.service.ServiceResponse;
import vn.chapp.vn24h.models.service.Shop;

public interface HistoryFrMvpView extends MvpView {
    void didGetShopLinked(List<Shop> shops);
    void gotServices(List<ServiceResponse> serviceResponses);
    void gotHistory(List<HistoryResponse> historyResponses);
}
