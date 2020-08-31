package vn.chapp.vn24h.ui.detail;


import vn.chapp.vn24h.base.MvpView;
import vn.chapp.vn24h.models.service.NewsResponse;

public interface DetailPromotionFrMvpView extends MvpView {
    void parseNewsResponse(NewsResponse news);
}
