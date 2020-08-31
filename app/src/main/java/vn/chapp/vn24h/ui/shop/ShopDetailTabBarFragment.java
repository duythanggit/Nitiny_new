package vn.chapp.vn24h.ui.shop;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import vn.chapp.vn24h.R;
import vn.chapp.vn24h.base.BaseFragment;
import vn.chapp.vn24h.di.component.ActivityComponent;
import vn.chapp.vn24h.ui.main.MainActivity;

public class ShopDetailTabBarFragment extends BaseFragment {

    public static final String TAG = ShopDetailTabBarFragment.class.getCanonicalName();
    public static final String ARG_DEFAULT_SELECTION = "ARG_DEFAULT_SELECTION";

    TabAdapter adapter;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;

    private int position = 0;

    @Override
    protected int configView() {
        return R.layout.fragment_shop_detail_tabbar;
    }

    public static ShopDetailTabBarFragment newInstance(int position) {
        ShopDetailTabBarFragment fragment = new ShopDetailTabBarFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_DEFAULT_SELECTION, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void init(View v) {
        position = getArguments().getInt(ARG_DEFAULT_SELECTION, 0);
        adapter = new TabAdapter(getActivity().getSupportFragmentManager());
        adapter.addFragment(ListProductFragment.newInstance(0), getString(R.string.str_production));
        adapter.addFragment(ListProductFragment.newInstance(1), getString(R.string.str_service));
        adapter.addFragment(ListProductFragment.newInstance(2), getString(R.string.str_promotion));
        adapter.addFragment(ListProductFragment.newInstance(3), getString(R.string.str_news_paper));
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        ((MainActivity) getActivity()).setTitleToolBar(adapter.getPageTitle(position).toString());
        tabLayout.setScrollPosition(position, 0f, true);

        viewPager.setCurrentItem(position);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                ((MainActivity) getActivity()).setTitleToolBar(tab.getText().toString());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    protected void initCreatedView(View v) {
        ActivityComponent component = getActivityComponent();
        if (component != null) {
            setUnbinder(ButterKnife.bind(this, v));
        }
    }

    public class TabAdapter extends FragmentStatePagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        TabAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }
    }
}
