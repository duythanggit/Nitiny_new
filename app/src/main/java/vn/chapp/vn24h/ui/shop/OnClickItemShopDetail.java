package vn.chapp.vn24h.ui.shop;

import vn.chapp.vn24h.models.service.NewsResponse;
import vn.chapp.vn24h.models.service.ProductResponse;

public interface OnClickItemShopDetail {
    void onClickOrder(int position);

    void onClickScheduled(int position);

    void onClickViewDetail(int position, ProductResponse productResponse);

    void onClickViewDetailNewPaper(int position, NewsResponse newsResponse, String agr);
}
