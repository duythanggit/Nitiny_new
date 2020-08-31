package vn.chapp.vn24h.ui.rating;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.mikhaellopez.circularimageview.CircularImageView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import vn.chapp.vn24h.R;
import vn.chapp.vn24h.base.BaseFragment;
import vn.chapp.vn24h.di.component.ActivityComponent;
import vn.chapp.vn24h.models.service.Shop;
import vn.chapp.vn24h.utils.NetworkUtils;

public class ShopSlideFragment extends BaseFragment implements ShopSlideFrMvpView {

    public static final String TAG = ShopSlideFragment.class.getCanonicalName();
    public static final String ARG_SHOP = "ARG_SHOP";

    @BindView(R.id.imgAvatarHost)
    CircularImageView imgAvatarHost;
    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.tvAddress)
    TextView tvAddress;
    @BindView(R.id.tvPhone)
    TextView tvPhone;

    private Shop shop;

    @Inject
    ShopSlidePresenter<ShopSlideFrMvpView> presenter;

    public static ShopSlideFragment newInstance(Shop shop) {
        ShopSlideFragment fragment = new ShopSlideFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_SHOP, shop);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int configView() {
        return R.layout.fragment_shop_slide;
    }

    @Override
    protected void init(View v) {

        shop = getArguments().getParcelable(ARG_SHOP);

        if (shop != null) {
            if (!TextUtils.isEmpty(shop.getName()))
                tvName.setText(shop.getName());
            if (!TextUtils.isEmpty(shop.getAddress()))
                tvAddress.setText(shop.getAddress());
            if (!TextUtils.isEmpty(shop.getAvatar()))
                NetworkUtils.loadImage(getContext(),shop.getAvatar(),imgAvatarHost);
        }

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
}
