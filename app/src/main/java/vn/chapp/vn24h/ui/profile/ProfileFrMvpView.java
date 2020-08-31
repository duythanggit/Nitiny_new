package vn.chapp.vn24h.ui.profile;

import vn.chapp.vn24h.base.MvpView;
import vn.chapp.vn24h.models.auth.UserDefault;

public interface ProfileFrMvpView extends MvpView {
    void parseUserDefault(UserDefault userDefault);
}
