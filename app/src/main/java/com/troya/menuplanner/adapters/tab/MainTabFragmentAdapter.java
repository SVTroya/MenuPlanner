package com.troya.menuplanner.adapters.tab;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.SparseArray;

import com.troya.menuplanner.Temp.Main.Fragments.BaseTabFragment;
import com.troya.menuplanner.Temp.Main.Fragments.FoodSuppliesFragment;
import com.troya.menuplanner.Temp.Main.Fragments.PlanMenuFragment;
import com.troya.menuplanner.Temp.Main.Fragments.ShoppingListFragment;

public class MainTabFragmentAdapter extends FragmentPagerAdapter {

    private SparseArray<BaseTabFragment> mTabs;
    private Context mContext;

    public MainTabFragmentAdapter(Context context, FragmentManager fragmentManager) {
        super(fragmentManager);
        this.mContext = context;
        initTabsMap();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTabs.get(position).getTitle();
    }

    @Override
    public Fragment getItem(int position) {

        return mTabs.get(position);
    }

    @Override
    public int getCount() {
        return mTabs.size();
    }

    private void initTabsMap() {
        mTabs = new SparseArray<>();
        mTabs.put(0, PlanMenuFragment.newInstance(mContext));
        mTabs.put(2, ShoppingListFragment.newInstance(mContext));
        mTabs.put(3, FoodSuppliesFragment.newInstance(mContext));
    }
}
