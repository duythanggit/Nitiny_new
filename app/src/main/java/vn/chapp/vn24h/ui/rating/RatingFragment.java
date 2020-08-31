package vn.chapp.vn24h.ui.rating;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Spinner;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import vn.chapp.vn24h.R;
import vn.chapp.vn24h.base.BaseFragment;
import vn.chapp.vn24h.di.component.ActivityComponent;
import vn.chapp.vn24h.models.service.Shop;
import vn.chapp.vn24h.ui.main.BackableActivity;

public class RatingFragment extends BaseFragment implements RatingFrMvpView, ViewPager.OnPageChangeListener {

    public static final String TAG = RatingFragment.class.getCanonicalName();

    @Inject
    RatingFrPresenter<RatingFrMvpView> presenter;

    @BindView(R.id.spnService)
    Spinner spnService;
    @BindView(R.id.vpSalon)
    ViewPager vpSalon;
    @BindView(R.id.imgPre)
    ImageView imgPre;
    @BindView(R.id.imgNext)
    ImageView imgNext;
    @BindView(R.id.rBSalon)
    RatingBar rBSalon;
    @BindView(R.id.rBStaff)
    RatingBar rBStaff;

    private ShopSlidePagerAdapter pagerAdapter;


    public static RatingFragment newInstance() {
        RatingFragment fragment = new RatingFragment();
        return fragment;
    }

    @Override
    protected int configView() {
        return R.layout.fragment_rating;
    }

    @Override
    protected void init(View v) {
        vpSalon.addOnPageChangeListener(this);
        presenter.doGetShopLinked();
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

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {
        if (i == 0) {
            imgPre.setVisibility(View.GONE);
            imgNext.setVisibility(View.VISIBLE);
        } else if (i == pagerAdapter.getCount() - 1) {
            imgPre.setVisibility(View.VISIBLE);
            imgNext.setVisibility(View.GONE);
        } else {
            imgPre.setVisibility(View.VISIBLE);
            imgNext.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    @OnClick(R.id.btnSendRating)
    public void onSendRating(View v) {
        if (pagerAdapter != null && pagerAdapter.getShops() != null) {
            Shop shop = pagerAdapter.getShops().get(vpSalon.getCurrentItem());
            presenter.ratingService(String.valueOf(shop.getShopId()),rBSalon.getRating(),rBStaff.getRating());
        }
    }

    @Override
    public void parseShopLinked(List<Shop> shops) {
        pagerAdapter = new ShopSlidePagerAdapter(getActivity().getSupportFragmentManager(), shops);
        vpSalon.setAdapter(pagerAdapter);
    }

     private class ShopSlidePagerAdapter extends FragmentStatePagerAdapter {
        private List<Shop> shops;

        public ShopSlidePagerAdapter(FragmentManager fm, List<Shop> shops) {
            super(fm);
            this.shops = shops;
        }

        @Override
        public Fragment getItem(int position) {
            return ShopSlideFragment.newInstance(shops.get(position));
        }

        @Override
        public int getCount() {
            return shops.size();
        }

        public List<Shop> getShops() {
            return shops;
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (getActivity() != null && getActivity() instanceof BackableActivity) {
            ((BackableActivity) getActivity()).setToolbarState(true, getString(R.string.title_feedback));
        }
    }

    @OnClick(R.id.imgPre)
    public void onClickPrePage() {
        vpSalon.setCurrentItem(vpSalon.getCurrentItem() - 1);
    }

    @OnClick(R.id.imgNext)
    public void onClickNextPage() {
        vpSalon.setCurrentItem(vpSalon.getCurrentItem() + 1);
    }
}
