package vn.chapp.vn24h.ui.shop;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import vn.chapp.vn24h.utils.AppConstants;

public class MyServiceViewPagerAdapter extends FragmentStatePagerAdapter {

    public MyServiceViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        return ListProductFragment.newInstance(i);
    }

    @Override
    public int getCount() {
        return AppConstants.categoryService.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return AppConstants.categoryService.get(position).getTitle();
    }
}
