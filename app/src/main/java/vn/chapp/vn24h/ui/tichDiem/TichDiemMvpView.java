package vn.chapp.vn24h.ui.tichDiem;

import java.util.List;

import vn.chapp.vn24h.base.MvpView;
import vn.chapp.vn24h.models.point.PointResponse;

public interface TichDiemMvpView extends MvpView {
    void parsePointHistory(List<PointResponse> pointResponses);
}
