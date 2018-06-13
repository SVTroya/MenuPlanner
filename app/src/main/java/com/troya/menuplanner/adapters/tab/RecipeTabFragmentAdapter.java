package com.troya.menuplanner.adapters.tab;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.SparseArray;

import com.troya.menuplanner.Temp.Main.Fragments.BaseTabFragment;
import com.troya.menuplanner.controllers.cookbook.info.DetailsFragment;
import com.troya.menuplanner.controllers.cookbook.info.IngredientsFragment;
import com.troya.menuplanner.model.db.entity.RecipeEntity;

public class RecipeTabFragmentAdapter extends FragmentPagerAdapter {

    private SparseArray<BaseTabFragment> tabs;
    private Context context;

    public RecipeTabFragmentAdapter(Context context, FragmentManager fragmentManager, RecipeEntity recipe) {
        super(fragmentManager);
        this.context = context;
        initTabsMap(recipe);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabs.get(position).getTitle();
    }

    @Override
    public Fragment getItem(int position) {

        return tabs.get(position);
    }

    @Override
    public int getCount() {
        return tabs.size();
    }

    private void initTabsMap(RecipeEntity recipe) {
        tabs = new SparseArray<>();
        tabs.put(0, IngredientsFragment.newInstance(context, recipe));
        tabs.put(1, DetailsFragment.newInstance(context, recipe));
    }
}
