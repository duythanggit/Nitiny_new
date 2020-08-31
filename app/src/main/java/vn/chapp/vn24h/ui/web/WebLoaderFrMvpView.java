package vn.chapp.vn24h.ui.web;


import vn.chapp.vn24h.base.MvpView;
import vn.chapp.vn24h.models.guide.GuideResponse;

public interface WebLoaderFrMvpView extends MvpView {
    void parseContent(GuideResponse content);
}
