package com.troya.menuplanner.adapters.tab;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.SparseArray;

import com.troya.menuplanner.Temp.Main.Fragments.BaseTabFragment;

public class ItemsManagerTabFragmentAdapter extends FragmentPagerAdapter {

    public interface OnPageCallback {
        void onClick();
    }

    private SparseArray<BaseTabFragment> mTabs;
    private Context mContext;
    private OnPageCallback mOnPageCallback;

    public ItemsManagerTabFragmentAdapter(Context context, FragmentManager fragmentManager, OnPageCallback onPageCallback) {
        super(fragmentManager);
        this.mContext = context;
        this.mOnPageCallback = onPageCallback;
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
        mTabs = new SparseArray<>();/*
        mTabs.put(0, IngredientsManagerFragment.newInstance(mContext, mOnPageCallback));
        mTabs.put(1, ShopsManagerFragment.newInstance(mContext));
        mTabs.put(2, StoragesManagerFragment.newInstance(mContext));*/
    }
}
