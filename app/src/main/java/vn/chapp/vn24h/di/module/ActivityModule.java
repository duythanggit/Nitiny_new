package vn.chapp.vn24h.di.module;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;
import vn.chapp.vn24h.di.ActivityContext;
import vn.chapp.vn24h.di.PerActivity;
import vn.chapp.vn24h.models.FragmentController;
import vn.chapp.vn24h.ui.accumulatepoint.PointHistoryAdapter;
import vn.chapp.vn24h.ui.addCode.AddCodeFragment;
import vn.chapp.vn24h.ui.addCode.ShopLinkedAdapter;
import vn.chapp.vn24h.ui.auth.AuthMvpPresenter;
import vn.chapp.vn24h.ui.auth.AuthMvpView;
import vn.chapp.vn24h.ui.auth.AuthPresenter;
import vn.chapp.vn24h.ui.auth.ChangePasswordFrMvpPresent;
import vn.chapp.vn24h.ui.auth.ChangePasswordFrMvpView;
import vn.chapp.vn24h.ui.auth.ChangePasswordFrPresenter;
import vn.chapp.vn24h.ui.auth.forgot.ForgotPasswordFrMvpPresent;
import vn.chapp.vn24h.ui.auth.forgot.ForgotPasswordFrMvpView;
import vn.chapp.vn24h.ui.auth.forgot.ForgotPasswordFrPresenter;
import vn.chapp.vn24h.ui.auth.login.LoginFrMvpPresent;
import vn.chapp.vn24h.ui.auth.login.LoginFrMvpView;
import vn.chapp.vn24h.ui.auth.login.LoginFrPresenter;
import vn.chapp.vn24h.ui.auth.login.LoginFragment;
import vn.chapp.vn24h.ui.auth.login.RefCodeDialogMvpPresenter;
import vn.chapp.vn24h.ui.auth.login.RefCodeDialogMvpView;
import vn.chapp.vn24h.ui.auth.login.RefCodeDialogPresenter;
import vn.chapp.vn24h.ui.auth.register.RegisterFrMvpPresent;
import vn.chapp.vn24h.ui.auth.register.RegisterFrMvpView;
import vn.chapp.vn24h.ui.auth.register.RegisterFrPresenter;
import vn.chapp.vn24h.ui.auth.register.RegisterFragment;
import vn.chapp.vn24h.ui.booking.BookingAdapter;
import vn.chapp.vn24h.ui.booking.BookingFragment;
import vn.chapp.vn24h.ui.cart.CartAdapter;
import vn.chapp.vn24h.ui.cart.CartFrMvpPresent;
import vn.chapp.vn24h.ui.cart.CartFrMvpView;
import vn.chapp.vn24h.ui.cart.CartFrPresenter;
import vn.chapp.vn24h.ui.cart.CartSectionAdapter;
import vn.chapp.vn24h.ui.chat.ChatAdapter;
import vn.chapp.vn24h.ui.chat.ChatBoxAdapter;
import vn.chapp.vn24h.ui.chat.ChatFragment;
import vn.chapp.vn24h.ui.chat.ChatMvpPresenter;
import vn.chapp.vn24h.ui.chat.ChatMvpView;
import vn.chapp.vn24h.ui.chat.ChatPresenter;
import vn.chapp.vn24h.ui.chat.ChatRoomFrMvpPresent;
import vn.chapp.vn24h.ui.chat.ChatRoomFrMvpView;
import vn.chapp.vn24h.ui.chat.ChatRoomFrPresenter;
import vn.chapp.vn24h.ui.chat.ChatRoomFragment;
import vn.chapp.vn24h.ui.chat.CustomerOnlineAdapter;
import vn.chapp.vn24h.ui.common.CommonPickCategoryAdapter;
import vn.chapp.vn24h.ui.customer.CustomerFrMvpPresent;
import vn.chapp.vn24h.ui.customer.CustomerFrMvpView;
import vn.chapp.vn24h.ui.customer.CustomerFrPresenter;
import vn.chapp.vn24h.ui.customer.CustomerFragment;
import vn.chapp.vn24h.ui.customer.CustomerLinkAdapter;
import vn.chapp.vn24h.ui.detail.DetailMvpPresenter;
import vn.chapp.vn24h.ui.detail.DetailMvpView;
import vn.chapp.vn24h.ui.detail.DetailNewsFrMvpPresent;
import vn.chapp.vn24h.ui.detail.DetailNewsFrMvpView;
import vn.chapp.vn24h.ui.detail.DetailNewsFrPresenter;
import vn.chapp.vn24h.ui.detail.DetailPresenter;
import vn.chapp.vn24h.ui.detail.DetailPromotionFrMvpPresent;
import vn.chapp.vn24h.ui.detail.DetailPromotionFrMvpView;
import vn.chapp.vn24h.ui.detail.DetailPromotionFrPresenter;
import vn.chapp.vn24h.ui.detailBooking.DetailBookingAdapter;
import vn.chapp.vn24h.ui.detailHistory.DetailHistoryFragment;
import vn.chapp.vn24h.ui.history.HistoryAdapter;
import vn.chapp.vn24h.ui.history.HistoryFrMvpPresent;
import vn.chapp.vn24h.ui.history.HistoryFrMvpView;
import vn.chapp.vn24h.ui.history.HistoryFrPresenter;
import vn.chapp.vn24h.ui.history.HistoryFragment;
import vn.chapp.vn24h.ui.location.ChooseLocationMvpPresent;
import vn.chapp.vn24h.ui.location.ChooseLocationMvpView;
import vn.chapp.vn24h.ui.location.ChooseLocationPresenter;
import vn.chapp.vn24h.ui.location.SearchPlaceAdapter;
import vn.chapp.vn24h.ui.location.locationsearch.LocationSearchMvpPresent;
import vn.chapp.vn24h.ui.location.locationsearch.LocationSearchMvpView;
import vn.chapp.vn24h.ui.location.locationsearch.LocationSearchPresenter;
import vn.chapp.vn24h.ui.main.BackableMvpPresenter;
import vn.chapp.vn24h.ui.main.BackableMvpView;
import vn.chapp.vn24h.ui.main.BackablePresenter;
import vn.chapp.vn24h.ui.main.MainMvpPresenter;
import vn.chapp.vn24h.ui.main.MainMvpView;
import vn.chapp.vn24h.ui.main.MainPresenter;
import vn.chapp.vn24h.ui.profile.ProfileDetailFrMvpPresent;
import vn.chapp.vn24h.ui.profile.ProfileDetailFrMvpView;
import vn.chapp.vn24h.ui.profile.ProfileDetailFrPresenter;
import vn.chapp.vn24h.ui.profile.ProfileFrMvpPresent;
import vn.chapp.vn24h.ui.profile.ProfileFrMvpView;
import vn.chapp.vn24h.ui.profile.ProfileFrPresenter;
import vn.chapp.vn24h.ui.profile.ProfileFragment;
import vn.chapp.vn24h.ui.rating.RatingFragment;
import vn.chapp.vn24h.ui.rating.ShopSlideFrMvpView;
import vn.chapp.vn24h.ui.rating.ShopSlideMvpPresent;
import vn.chapp.vn24h.ui.rating.ShopSlidePresenter;
import vn.chapp.vn24h.ui.sample.SampleFrMvpPresent;
import vn.chapp.vn24h.ui.sample.SampleFrMvpView;
import vn.chapp.vn24h.ui.sample.SampleFrPresenter;
import vn.chapp.vn24h.ui.sample.SampleMvpPresenter;
import vn.chapp.vn24h.ui.sample.SampleMvpView;
import vn.chapp.vn24h.ui.sample.SamplePresenter;
import vn.chapp.vn24h.ui.scheduled.ConfirmAdapter;
import vn.chapp.vn24h.ui.scheduled.ScheduledAdapter;
import vn.chapp.vn24h.ui.scheduledProduct.ConfirmProductAdapter;
import vn.chapp.vn24h.ui.scheduledProduct.ScheduleProductAdapter;
import vn.chapp.vn24h.ui.services.ConfirmLinkingDialogMvpPresenter;
import vn.chapp.vn24h.ui.services.ConfirmLinkingDialogMvpView;
import vn.chapp.vn24h.ui.services.ConfirmLinkingDialogPresenter;
import vn.chapp.vn24h.ui.services.LinkServiceFrMvpPresent;
import vn.chapp.vn24h.ui.services.LinkServiceFrMvpView;
import vn.chapp.vn24h.ui.services.LinkServiceFrPresenter;
import vn.chapp.vn24h.ui.services.LinkServiceFragment;
import vn.chapp.vn24h.ui.services.SearchShopAdapter;
import vn.chapp.vn24h.ui.services.SearchShopFrMvpPresent;
import vn.chapp.vn24h.ui.services.SearchShopFrMvpView;
import vn.chapp.vn24h.ui.services.SearchShopFrPresenter;
import vn.chapp.vn24h.ui.services.ServicesAdapter;
import vn.chapp.vn24h.ui.services.UpdateInfoServiceFrMvpPresent;
import vn.chapp.vn24h.ui.services.UpdateInfoServiceFrMvpView;
import vn.chapp.vn24h.ui.services.UpdateInfoServiceFrPresenter;
import vn.chapp.vn24h.ui.shop.ListNewsAdapter;
import vn.chapp.vn24h.ui.shop.ListProductAdapter;
import vn.chapp.vn24h.ui.shop.ListProductFrMvpPresent;
import vn.chapp.vn24h.ui.shop.ListProductFrMvpView;
import vn.chapp.vn24h.ui.shop.ListProductFrPresenter;
import vn.chapp.vn24h.ui.shop.MyServiceViewPagerAdapter;
import vn.chapp.vn24h.ui.shop.ShopDetailFrMvpPresent;
import vn.chapp.vn24h.ui.shop.ShopDetailFrMvpView;
import vn.chapp.vn24h.ui.shop.ShopDetailFrPresenter;
import vn.chapp.vn24h.ui.shop.ShopDetailFragment;
import vn.chapp.vn24h.ui.shop.ShopDetailServiceAdapter;
import vn.chapp.vn24h.ui.shopLinked.ListShopLinkedAdapter;
import vn.chapp.vn24h.ui.shopLinked.ShopLinkedFrMvpPresent;
import vn.chapp.vn24h.ui.shopLinked.ShopLinkedFrMvpView;
import vn.chapp.vn24h.ui.shopLinked.ShopLinkedFrPresenter;
import vn.chapp.vn24h.ui.splash.SplashMvpPresenter;
import vn.chapp.vn24h.ui.splash.SplashMvpView;
import vn.chapp.vn24h.ui.splash.SplashPresenter;
import vn.chapp.vn24h.ui.starter.StarterMvpPresenter;
import vn.chapp.vn24h.ui.starter.StarterMvpView;
import vn.chapp.vn24h.ui.starter.StarterPresenter;
import vn.chapp.vn24h.ui.tichDiem.TichDiemAdapter;
import vn.chapp.vn24h.ui.tichDiem.TichDiemFragment;
import vn.chapp.vn24h.ui.wallet.WalletAdapter;
import vn.chapp.vn24h.ui.wallet.WalletFragment;
import vn.chapp.vn24h.ui.web.WebLoaderFrMvpPresent;
import vn.chapp.vn24h.ui.web.WebLoaderFrMvpView;
import vn.chapp.vn24h.ui.web.WebLoaderFrPresenter;

@Module
public class ActivityModule {

    private AppCompatActivity activity;

    public ActivityModule(AppCompatActivity activity) {
        this.activity = activity;
    }

    @Provides
    @ActivityContext
    Context provideContext() {
        return activity;
    }

    @Provides
    AppCompatActivity provideActivity() {
        return activity;
    }

    @Provides
    CompositeDisposable provideCompositeDisposable() {
        return new CompositeDisposable();
    }

    @Provides
    Calendar provideCalendar() {
        return Calendar.getInstance();
    }

    @Provides
    @PerActivity
    SampleMvpPresenter<SampleMvpView> provideSampleMvpPresenter(SamplePresenter<SampleMvpView> presenter) {
        return presenter;
    }

    @Provides
    SampleFrMvpPresent<SampleFrMvpView> provideSampleFrMvpPresent(SampleFrPresenter<SampleFrMvpView> presenter) {
        return presenter;
    }

    @Provides
    SplashMvpPresenter<SplashMvpView> provideSplashActivityMvpPresent(SplashPresenter<SplashMvpView> presenter) {
        return presenter;
    }

    @Provides
    StarterMvpPresenter<StarterMvpView> provideStarterActivityMvpPresent(StarterPresenter<StarterMvpView> presenter) {
        return presenter;
    }

    @Provides
    MainMvpPresenter<MainMvpView> provideMainMvpPresenter(MainPresenter<MainMvpView> presenter) {
        return presenter;
    }

    @Provides
    ShopDetailFrMvpPresent<ShopDetailFrMvpView> provideHomeFrMvpPresent(ShopDetailFrPresenter<ShopDetailFrMvpView> presenter) {
        return presenter;
    }


    @Provides
    @PerActivity
    AuthMvpPresenter<AuthMvpView> provideAuthActivityMvpPresent(AuthPresenter<AuthMvpView> presenter) {
        return presenter;
    }

    @Provides
    RegisterFrMvpPresent<RegisterFrMvpView> provideRegisterFrMvpPresent(RegisterFrPresenter<RegisterFrMvpView> presenter) {
        return presenter;
    }

    @Provides
    LoginFrMvpPresent<LoginFrMvpView> provideLoginFrMvpPresent(LoginFrPresenter<LoginFrMvpView> presenter) {
        return presenter;
    }

    @Provides
    RegisterFragment provideRegisterFragment() {
        return RegisterFragment.newInstance();
    }

    @Provides
    LoginFragment provideLoginFragment() {
        return LoginFragment.newInstance();
    }

    @Provides
    ShopDetailFragment provideShopDetailFragment() {
        return ShopDetailFragment.newInstance();
    }

    @Provides
    ChatFragment provideChatFragment() {
        return ChatFragment.newInstance();
    }

    @Provides
    List<FragmentController> provideListFragmentControlers() {
        return new ArrayList<>();
    }

    @Provides
    List<TextView> provideTextViewTitleAuth() {
        return new ArrayList<>();
    }

    @Provides
    @PerActivity
    BackableMvpPresenter<BackableMvpView> provideBackableActivityMvpPresent(BackablePresenter<BackableMvpView> presenter) {
        return presenter;
    }

    @Provides
    ForgotPasswordFrMvpPresent<ForgotPasswordFrMvpView> provideForgotPasswordFrMvpPresent(ForgotPasswordFrPresenter<ForgotPasswordFrMvpView> presenter) {
        return presenter;
    }

    @Provides
    ChatRoomFrMvpPresent<ChatRoomFrMvpView> provideChatRoomFrMvpPresent(ChatRoomFrPresenter<ChatRoomFrMvpView> presenter) {
        return presenter;
    }

    @Provides
    HistoryFrMvpPresent<HistoryFrMvpView> provideHistoryFrMvpPresent(HistoryFrPresenter<HistoryFrMvpView> presenter) {
        return presenter;
    }

    @Provides
    CustomerFrMvpPresent<CustomerFrMvpView> provideCustomerFrMvpPresent(CustomerFrPresenter<CustomerFrMvpView> presenter) {
        return presenter;
    }

    @Provides
    ProfileFrMvpPresent<ProfileFrMvpView> provideProfileFrMvpPresent(ProfileFrPresenter<ProfileFrMvpView> presenter) {
        return presenter;
    }

    @Provides
    ChatRoomFragment provideChatRoomFragment() {
        return ChatRoomFragment.newInstance();
    }

    @Provides
    HistoryFragment provideHistoryFragment() {
        return HistoryFragment.newInstance();
    }

    @Provides
    CustomerFragment provideCustomerFragment() {
        return CustomerFragment.newInstance();
    }

    @Provides
    ProfileFragment provideProfileFragment() {
        return ProfileFragment.newInstance();
    }

    @Provides
    LinkServiceFrMvpPresent<LinkServiceFrMvpView> provideLinkServicesFrMvpPresent(LinkServiceFrPresenter<LinkServiceFrMvpView> presenter) {
        return presenter;
    }

    @Provides
    @Named("2Coloumn")
    GridLayoutManager provideGridTwoLayoutManager() {
        return new GridLayoutManager(activity, 2);
    }

    @Provides
    @Named("3Coloumn")
    GridLayoutManager provideGridLayoutManager() {
        return new GridLayoutManager(activity, 3);
    }

    @Provides
    @Named("4Coloumn")
    GridLayoutManager provideFourGridLayoutManager() {
        return new GridLayoutManager(activity, 4);
    }

    @Provides
    ServicesAdapter provideServiceAdapter() {
        return new ServicesAdapter(null);
    }

    @Provides
    UpdateInfoServiceFrMvpPresent<UpdateInfoServiceFrMvpView> provideUpdateInfoServiceFrMvpPresent(UpdateInfoServiceFrPresenter<UpdateInfoServiceFrMvpView> presenter) {
        return presenter;
    }

    @Provides
    ConfirmLinkingDialogMvpPresenter<ConfirmLinkingDialogMvpView> provideConfirmLinkingDialogMvpPresent(ConfirmLinkingDialogPresenter<ConfirmLinkingDialogMvpView> presenter) {
        return presenter;
    }

    @Provides
    @Named("vertical")
    LinearLayoutManager provideLinearLayoutManager() {
        return new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false);
    }

    @Provides
    HistoryAdapter provideHistoryAdapter() {
        return new HistoryAdapter(null);
    }

    @Provides
    ProfileDetailFrMvpPresent<ProfileDetailFrMvpView> provideProfileDetailFrMvpViewProfileDetailFrMvpPresent(ProfileDetailFrPresenter<ProfileDetailFrMvpView> presenter) {
        return presenter;
    }

    @Provides
    @Named("horizontal")
    LinearLayoutManager linearLayoutManagerHorizontal() {
        return new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false);
    }

    @Provides
    @Named("vertReverse")
    LinearLayoutManager linearLayoutManagerVertReverse() {
        return new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, true);
    }

    @Provides
    CustomerOnlineAdapter provideCustomerOnlineAdapter() {
        return new CustomerOnlineAdapter(null);
    }

    @Provides
    ChatBoxAdapter provideChatBoxAdapter() {
        return new ChatBoxAdapter(null);
    }

    @Provides
    @PerActivity
    ChatMvpPresenter<ChatMvpView> provideChatMvpPresent(ChatPresenter<ChatMvpView> presenter) {
        return presenter;
    }

    @Provides
    ChatAdapter provideChatAdapter() {
        return new ChatAdapter(null);
    }

    @Provides
    AddCodeFragment addCodeFragment() {
        return AddCodeFragment.newInstance();
    }

    @Provides
    ShopLinkedAdapter shopLinkedAdapter() {
        return new ShopLinkedAdapter(null);
    }

    @Provides
    @PerActivity
    DetailMvpPresenter<DetailMvpView> provideDetailMvpPresent(DetailPresenter<DetailMvpView> presenter) {
        return presenter;
    }

    @Provides
    ShopDetailServiceAdapter provideMyServiceAdapter() {
        return new ShopDetailServiceAdapter(null);
    }

    @Provides
    RatingFragment ratingFragment() {
        return RatingFragment.newInstance();
    }

    @Provides
    LinkServiceFragment linkServiceFragment() {
        return LinkServiceFragment.newInstance();
    }

    @Provides
    ScheduledAdapter scheduledAdapter() {
        return new ScheduledAdapter(null);
    }

    @Provides
    ConfirmAdapter confirmAdapter() {
        return new ConfirmAdapter(null);
    }

    @Provides
    ShopSlideMvpPresent<ShopSlideFrMvpView> provideShopSlideFrPresent(ShopSlidePresenter<ShopSlideFrMvpView> presenter) {
        return presenter;
    }

    @Provides
    ChangePasswordFrMvpPresent<ChangePasswordFrMvpView> provideChangePasswordPresent(ChangePasswordFrPresenter<ChangePasswordFrMvpView> presenter) {
        return presenter;
    }

    @Provides
    BookingFragment bookingFragment() {
        return BookingFragment.newInstance();
    }

    @Provides
    BookingAdapter bookingAdapter() {
        return new BookingAdapter(null);
    }

    @Provides
    DetailBookingAdapter detailBookingAdapter() {
        return new DetailBookingAdapter(null);
    }

    @Provides
    ListProductFrMvpPresent<ListProductFrMvpView> provideListProductFrMvpPresent(ListProductFrPresenter<ListProductFrMvpView> presenter) {
        return presenter;
    }

    @Provides
    MyServiceViewPagerAdapter provideMyServiceViewPagerAdapter() {
        return new MyServiceViewPagerAdapter(activity.getSupportFragmentManager());
    }

    @Provides
    ListProductAdapter provideListProductAdapter() {
        return new ListProductAdapter(null);
    }

    @Provides
    ListNewsAdapter provideListNewsAdapter() {
        return new ListNewsAdapter(null);
    }

    @Provides
    DetailHistoryFragment detailHistoryFragment() {
        return DetailHistoryFragment.newInstance(null);
    }

    @Provides
    DetailNewsFrMvpPresent<DetailNewsFrMvpView> provideDetailNewsFrMvpPresent(DetailNewsFrPresenter<DetailNewsFrMvpView> presenter) {
        return presenter;
    }

    @Provides
    DetailPromotionFrMvpPresent<DetailPromotionFrMvpView> provideDetailPromotionMvpPresent(DetailPromotionFrPresenter<DetailPromotionFrMvpView> presenter) {
        return presenter;
    }

    @Provides
    WebLoaderFrMvpPresent<WebLoaderFrMvpView> provideWebLoaderFrMvpPresent(WebLoaderFrPresenter<WebLoaderFrMvpView> presenter) {
        return presenter;
    }

    @Provides
    WalletFragment walletFragment() {
        return WalletFragment.newInstance();
    }

    @Provides
    WalletAdapter walletAdapter() {
        return new WalletAdapter(null);
    }

    @Provides
    @PerActivity
    ChooseLocationMvpPresent<ChooseLocationMvpView> provideChooseLocationMvpPresent(ChooseLocationPresenter<ChooseLocationMvpView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    LocationSearchMvpPresent<LocationSearchMvpView> provideLocationSearchMvpPresent(LocationSearchPresenter<LocationSearchMvpView> presenter) {
        return presenter;
    }

    @Provides
    SearchPlaceAdapter provideSearchPlaceAdapter() {
        return new SearchPlaceAdapter(null);
    }

    @Provides
    Timer provideTimer() {
        return new Timer();
    }

    @Provides
    SearchShopFrMvpPresent<SearchShopFrMvpView> provideSearchShopFrMvpPresent(SearchShopFrPresenter<SearchShopFrMvpView> presenter) {
        return presenter;
    }

    @Provides
    SearchShopAdapter provideSearchShopAdapter() {
        return new SearchShopAdapter(null);
    }

    @Provides
    FusedLocationProviderClient provideFusedLocationProviderClient() {
        return LocationServices.getFusedLocationProviderClient(activity);
    }

    @Provides
    ListShopLinkedAdapter listShopLinkedAdapter() {
        return new ListShopLinkedAdapter(null);
    }

    @Provides
    ShopLinkedFrMvpPresent<ShopLinkedFrMvpView> shopLinkedFrMvpPresent(ShopLinkedFrPresenter<ShopLinkedFrMvpView> presenter) {
        return presenter;
    }

    @Provides
    CustomerLinkAdapter customerLinkAdapter() {
        return new CustomerLinkAdapter(null);
    }

    @Provides
    CartFrMvpPresent<CartFrMvpView> provideCartFrMvpViewCartFrMvpPresent(CartFrPresenter<CartFrMvpView> presenter) {
        return presenter;
    }

    @Provides
    CartAdapter provideCartAdapter() {
        return new CartAdapter(null);
    }

    @Provides
    PointHistoryAdapter providePointHistoryAdapter() {
        return new PointHistoryAdapter(null);
    }

//    @Provides
//    AccumulatePointMvpPresent<AccumulatePointMvpView> provideAccumulatePointMvpPresent(AccumulatePointPresenter<AccumulatePointMvpView> presenter) {
//        return presenter;
//    }

    @Provides
    TichDiemFragment tichDiemFragment() {
        return TichDiemFragment.newInstance();
    }

    @Provides
    TichDiemAdapter tichDiemAdapter() {
        return new TichDiemAdapter(null);
    }

    @Provides
    CartSectionAdapter cartSectionAdapter() {
        return new CartSectionAdapter(null);
    }

    @Provides
    ScheduleProductAdapter scheduleProductAdapter() {
        return new ScheduleProductAdapter(null);
    }

    @Provides
    ConfirmProductAdapter confirmProductAdapter() {
        return new ConfirmProductAdapter(null);
    }

    @Provides
    CommonPickCategoryAdapter commonPickCategoryAdapter(){
        return new CommonPickCategoryAdapter(null);
    }

    @Provides
    RefCodeDialogMvpPresenter<RefCodeDialogMvpView> provideRefCodeDialogPresenter(RefCodeDialogPresenter<RefCodeDialogMvpView> presenter) {
        return presenter;
    }
}
