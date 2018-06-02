package com.troya.menuplanner.controllers.cookbook;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import com.troya.menuplanner.R;

public class RecipesListActivity extends FragmentActivity {

    private static final int LAYOUT = R.layout.activity_recipe_list;

    /*------------------------------- Lifecycle -------------------------------*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT);

        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.fragment, RecipesListFragment.newInstance()).commit();
    }

    /*------------------------------- Callbacks -------------------------------*/


}
