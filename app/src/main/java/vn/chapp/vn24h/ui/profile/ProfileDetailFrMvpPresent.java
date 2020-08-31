package vn.chapp.vn24h.ui.profile;
import vn.chapp.vn24h.base.MvpPresenter;
import vn.chapp.vn24h.models.map.Address;


public interface ProfileDetailFrMvpPresent<V extends ProfileDetailFrMvpView> extends MvpPresenter<V> {
    void getUserDefault();
    void doUploadAvatar(String path);
    void getUserDetail();
    void doUpdateProfile(Address address, String email, String name, String phone);
    void startSearchPlace(String s);
    void getDetailPlace(String placeId);
}
