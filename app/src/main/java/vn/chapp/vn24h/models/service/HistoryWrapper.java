package vn.chapp.vn24h.models.service;

import java.util.ArrayList;
import java.util.List;

public class HistoryWrapper {
    private List<Shop> shops;
    private List<HistoryResponse> historyResponses;
    private int type;//1 - linked shop, 2 - history

    public HistoryWrapper(int type) {
        this.type = type;
        shops = new ArrayList<>();
        historyResponses = new ArrayList<>();
    }

    public List<Shop> getShops() {
        return shops;
    }

    public void setShops(List<Shop> shops) {
        this.shops = shops;
    }

    public List<HistoryResponse> getHistoryResponses() {
        return historyResponses;
    }

    public void setHistoryResponses(List<HistoryResponse> historyResponses) {
        this.historyResponses = historyResponses;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
