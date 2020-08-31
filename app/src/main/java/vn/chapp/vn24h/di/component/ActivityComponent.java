package vn.chapp.vn24h.di.component;

import dagger.Component;
import vn.chapp.vn24h.di.PerActivity;
import vn.chapp.vn24h.di.module.ActivityModule;
import vn.chapp.vn24h.ui.accumulatepoint.AccumulatePointFragment;
import vn.chapp.vn24h.ui.addCode.AddCodeFragment;
import vn.chapp.vn24h.ui.auth.ChangePasswordFragment;
import vn.chapp.vn24h.ui.auth.login.RefCodeDialog;
import vn.chapp.vn24h.ui.booking.BookingFragment;
import vn.chapp.vn24h.ui.cart.CartFragment;
import vn.chapp.vn24h.ui.chat.ChatActivity;
import vn.chapp.vn24h.ui.chat.ChatFragment;
import vn.chapp.vn24h.ui.chat.ChatRoomFragment;
import vn.chapp.vn24h.ui.customer.CustomerFragment;
import vn.chapp.vn24h.ui.detail.DetailActivity;
import vn.chapp.vn24h.ui.detail.DetailNewsFragment;
import vn.chapp.vn24h.ui.detail.DetailPromotionFragment;
import vn.chapp.vn24h.ui.detailBooking.DetailBookingFragment;
import vn.chapp.vn24h.ui.detailHistory.DetailHistoryFragment;
import vn.chapp.vn24h.ui.detailProduct.DetailProductFragment;
import vn.chapp.vn24h.ui.history.HistoryFragment;
import vn.chapp.vn24h.ui.location.ChooseLocationActivity;
import vn.chapp.vn24h.ui.location.locationsearch.LocationSearchActivity;
import vn.chapp.vn24h.ui.rating.ShopSlideFragment;
import vn.chapp.vn24h.ui.scheduledProduct.ConfirmProductFragment;
import vn.chapp.vn24h.ui.scheduledProduct.ScheduleProductFragment;
import vn.chapp.vn24h.ui.services.SearchShopFragment;
import vn.chapp.vn24h.ui.shop.ListProductFragment;
import vn.chapp.vn24h.ui.shop.ShopDetailFragment;
import vn.chapp.vn24h.ui.main.MainActivity;
import vn.chapp.vn24h.ui.auth.AuthActivity;
import vn.chapp.vn24h.ui.auth.forgot.ForgotPasswordFragment;
import vn.chapp.vn24h.ui.auth.login.LoginFragment;
import vn.chapp.vn24h.ui.auth.register.RegisterFragment;
import vn.chapp.vn24h.ui.main.BackableActivity;
import vn.chapp.vn24h.ui.profile.ProfileDetailFragment;
import vn.chapp.vn24h.ui.profile.ProfileFragment;
import vn.chapp.vn24h.ui.rating.RatingFragment;
import vn.chapp.vn24h.ui.sample.SampleActivity;
import vn.chapp.vn24h.ui.sample.SampleFragment;
import vn.chapp.vn24h.ui.scheduled.ConfirmFragment;
import vn.chapp.vn24h.ui.scheduled.ScheduleFragment;
import vn.chapp.vn24h.ui.services.ConfirmLinkingDialog;
import vn.chapp.vn24h.ui.services.LinkServiceFragment;
import vn.chapp.vn24h.ui.services.UpdateInfoServiceFragment;
import vn.chapp.vn24h.ui.shopLinked.ShopLinkedFragment;
import vn.chapp.vn24h.ui.splash.SplashActivity;
import vn.chapp.vn24h.ui.starter.StarterActivity;
import vn.chapp.vn24h.ui.tichDiem.TichDiemFragment;
import vn.chapp.vn24h.ui.wallet.WalletFragment;
import vn.chapp.vn24h.ui.web.WebLoaderFragment;


@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(SampleActivity sampleActivity);

    void inject(SampleFragment sampleFragment);

    void inject(SplashActivity splashActivity);

    void inject(StarterActivity starterActivity);

    void inject(MainActivity mainActivity);

    void inject(ShopDetailFragment homeFragment);

    void inject(AuthActivity authActivity);

    void inject(RegisterFragment registerFragment);

    void inject(LoginFragment loginFragment);

    void inject(BackableActivity backableActivity);

    void inject(ForgotPasswordFragment forgotPasswordFragment);

    void inject(ChatRoomFragment chatRoomFragment);

    void inject(HistoryFragment historyFragment);

    void inject(CustomerFragment customerFragment);

    void inject(ProfileFragment profileFragment);

    void inject(LinkServiceFragment linkServiceFragment);

    void inject(UpdateInfoServiceFragment updateInfoServiceFragment);

    void inject(ConfirmLinkingDialog confirmLinkingDialog);

    void inject(ProfileDetailFragment profileDetailFragment);

    void inject(ChatActivity chatActivity);

    void inject(AddCodeFragment addCodeFragment);

    void inject(DetailActivity detailActivity);

    void inject(ScheduleFragment scheduleFragment);

    void inject(ConfirmFragment confirmFragment);

    void inject(RatingFragment ratingFragment);

    void inject(ShopSlideFragment shopSlideFragment);

    void inject(ChangePasswordFragment changePasswordFragment);

    void inject(BookingFragment bookingFragment);

    void inject(DetailBookingFragment detailBookingFragment);

    void inject(DetailProductFragment detailProductFragment);

    void inject(ListProductFragment listProductFragment);

    void inject(DetailHistoryFragment detailHistoryFragment);

    void inject(DetailNewsFragment detailNewsFragment);

    void inject(DetailPromotionFragment detailPromotionFragment);

    void inject(WebLoaderFragment webLoaderFragment);

    void inject(WalletFragment walletFragment);

    void inject(LocationSearchActivity locationSearchActivity);

    void inject(ChooseLocationActivity chooseLocationActivity);

    void inject(SearchShopFragment searchShopFragment);

    void inject(ShopLinkedFragment shopLinkedFragment);

    void inject(ChatFragment chatFragment);

    void inject(AccumulatePointFragment accumulatePointFragment);

    void inject(CartFragment cartFragment);

    void inject(TichDiemFragment tichDiemFragment);

    void inject(ScheduleProductFragment scheduleProductFragment);

    void inject(ConfirmProductFragment confirmProductFragment);

    void inject(RefCodeDialog refCodeDialog);

}
