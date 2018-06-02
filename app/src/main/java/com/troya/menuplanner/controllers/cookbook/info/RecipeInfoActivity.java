package com.troya.menuplanner.controllers.cookbook.info;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import com.troya.menuplanner.R;
import com.troya.menuplanner.controllers.BaseActivity;

public class RecipeInfoActivity extends BaseActivity {

    public static final int LAYOUT = R.layout.activity_recipe_info;

    public static final String KEY_RECIPE_ID = "recipe_id";


    /*------------------------------- Lifecycle -------------------------------*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT);

        int recipeId = getIntent().getIntExtra(KEY_RECIPE_ID, 0);
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.fragment, RecipeInfoFragment.newInstance(recipeId)).commit();
        //manager.beginTransaction().replace(R.id.fragment, PartThreeActivity.createInstance()).commit();
    }
}
