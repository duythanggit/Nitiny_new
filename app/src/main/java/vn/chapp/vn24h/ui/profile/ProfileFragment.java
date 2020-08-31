package vn.chapp.vn24h.ui.profile;

import android.support.v4.app.ShareCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.mikhaellopez.circularimageview.CircularImageView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import vn.chapp.vn24h.R;
import vn.chapp.vn24h.base.BaseFragment;
import vn.chapp.vn24h.di.component.ActivityComponent;
import vn.chapp.vn24h.models.auth.UserDefault;
import vn.chapp.vn24h.ui.main.BackableActivity;
import vn.chapp.vn24h.utils.AppUtils;
import vn.chapp.vn24h.utils.NetworkUtils;

public class ProfileFragment extends BaseFragment implements ProfileFrMvpView {

    public static final String TAG = ProfileFragment.class.getCanonicalName();

    @Inject
    ProfileFrPresenter<ProfileFrMvpView> presenter;

    @BindView(R.id.civAvatar)
    CircularImageView civAvatar;
    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.tvUserCode)
    TextView tvUserCode;
    @BindView(R.id.tvUserLink)
    TextView tvUserLink;

    public static ProfileFragment newInstance() {
        ProfileFragment fragment = new ProfileFragment();
        return fragment;
    }

    @Override
    protected int configView() {
        return R.layout.fragment_profile;
    }

    @Override
    protected void init(View v) {
        presenter.getUserDefault();
    }

    @Override
    protected void initCreatedView(View v) {
        ActivityComponent component = getActivityComponent();
        if (component != null) {
            component.inject(this);
            setUnbinder(ButterKnife.bind(this, v));
            presenter.onAttach(this);
        }

    }

    @OnClick(R.id.layoutWallet)
    public void onClickWallet(View v) {
        startActivity(BackableActivity.newInstanceWallet(getContext()));
    }

    @OnClick(R.id.cardInfoHeader)
    public void onCardInfoHeaderClick(View v){
        startActivity(BackableActivity.newInstance(getContext(),BackableActivity.NAVIGATOR_FPD));
    }

    @OnClick(R.id.layoutFeedBack)
    public void ontxtFeedBackClick(View v){
        startActivity(BackableActivity.newInstance(getContext(),BackableActivity.NAVIGATOR_FRT));
    }

    @OnClick(R.id.layoutRatingApp)
    public void onRatingAppClick(View v) {
        AppUtils.openPlayStoreForApp(getContext());
    }


    @OnClick(R.id.layoutChangePassword)
    public void onChangePassword(View v) {
        startActivity(BackableActivity.newInstance(getContext(), BackableActivity.NAVIGATOR_CPW));
}
    @OnClick(R.id.layoutHistory)
    public void onClickHistory(View v) {
        startActivity(BackableActivity.newInstance(getContext(),BackableActivity.NAVIGATOR_FHS));
    }

    @OnClick(R.id.layoutGuide)
    public void onGuideClick(View v) {
        startActivity(BackableActivity.newInstanceWebLoader(getContext(),""));
    }

    @OnClick(R.id.lnContact)
    public void onContactClick(View v) {
        startActivity(BackableActivity.newInstanceWebContact(getContext(),"https://chapp.com.vn/lien-he"));
    }

    @OnClick(R.id.lnShare)
    public void onClickShare(View v) {
        try {
            ShareCompat.IntentBuilder.from(getBaseActivity())
                    .setType("text/plain")
                    .setChooserTitle("Chia sẻ ứng dụng cho")
                    .setText("https://play.google.com/store/apps/details?id=" + getContext().getPackageName())
                    .startChooser();
        } catch (Exception ex) {

        }

    }

    @Override
    public void parseUserDefault(UserDefault userDefault) {
        if (!TextUtils.isEmpty(userDefault.getAvatar()))
            NetworkUtils.loadImage(getContext(), userDefault.getAvatar(),civAvatar);
        if (!TextUtils.isEmpty(userDefault.getName()))
            tvName.setText(userDefault.getName());
        if (!TextUtils.isEmpty(userDefault.getCode()))
            tvUserCode.setText(userDefault.getCode());
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.getUserDefault();
    }
}