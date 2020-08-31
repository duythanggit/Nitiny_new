package vn.chapp.vn24h.ui.accumulatepoint;

import java.util.List;

import vn.chapp.vn24h.base.MvpView;
import vn.chapp.vn24h.models.point.PointDetailResponse;

public interface AccumulatePointMvpView extends MvpView {
    void parsePointHistory(List<PointDetailResponse> pointResponses);
}
