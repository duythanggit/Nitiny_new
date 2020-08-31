package vn.chapp.vn24h.ui.profile;


import java.util.List;

import vn.chapp.vn24h.base.MvpView;
import vn.chapp.vn24h.models.auth.UserDefault;
import vn.chapp.vn24h.models.map.Address;
import vn.chapp.vn24h.models.map.PredictionsItem;

public interface ProfileDetailFrMvpView extends MvpView {
    void parseUserDetail(UserDefault userDefault);
    void didUploadAvatar(String url);
    void didUpdateProfile(String address, String email, String name, String phone);
    void gotPlaces(List<PredictionsItem> predictionsItems, List<String> placeAddress);
    void gotPlaceDetail(Address address);
}
