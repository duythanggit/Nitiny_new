package vn.chapp.vn24h.ui.services;


import java.util.List;

import vn.chapp.vn24h.base.MvpView;
import vn.chapp.vn24h.models.banner.BannerResponse;
import vn.chapp.vn24h.models.service.Service;

public interface LinkServiceFrMvpView extends MvpView {
    void didGetListService(List<Service> listService);
    void gotBanners(List<BannerResponse> bannerResponses);
    void didGetListServiceLinked(List<Service> listService);
}
