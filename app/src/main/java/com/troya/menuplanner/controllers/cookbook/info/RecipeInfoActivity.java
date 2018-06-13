package com.troya.menuplanner.controllers.cookbook.info;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import com.troya.menuplanner.R;
import com.troya.menuplanner.controllers.BaseActivity;
import com.troya.menuplanner.helpers.RecipeInfoTabsCallback;
import com.troya.menuplanner.model.views.IngredientInRecipeInfo;

import java.util.List;

public class RecipeInfoActivity extends BaseActivity implements RecipeInfoTabsCallback {

    public static final int LAYOUT = R.layout.activity_recipe_info;

    public static final String KEY_RECIPE_ID = "recipe_id";
    public static final String KEY_RECIPE = "recipe";

    private String mRecipeInfoFragmentTag;
    private String mIngredientsFragmentTag;
    private String mDetailsFragmentTag;

    private FragmentManager mFragmentManager;

    public void setRecipeInfoFragmentTag(String tag) {
        mRecipeInfoFragmentTag = tag;
    }

    public void setIngredientsFragmentTag(String tag) {
        mIngredientsFragmentTag = tag;
    }

    public void setDetailsFragmentTag(String tag) {
        mDetailsFragmentTag = tag;
    }

    private RecipeInfoFragment getRecipeInfoFragment() {
        try {
            return (RecipeInfoFragment) mFragmentManager.findFragmentByTag(mRecipeInfoFragmentTag);
        } catch (Exception ex) {
            return null;
        }
    }

    private IngredientsFragment getIngredientsFragment() {
        try {
            return (IngredientsFragment) mFragmentManager.findFragmentByTag(mIngredientsFragmentTag);
        } catch (Exception ex) {
            return null;
        }
    }

    /*------------------------------- Lifecycle -------------------------------*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT);

        int recipeId = getIntent().getIntExtra(KEY_RECIPE_ID, 0);
        mFragmentManager = getSupportFragmentManager();
        RecipeInfoFragment recipeInfoFragment = RecipeInfoFragment.newInstance(recipeId);
        addFragmentToActivity(mFragmentManager, recipeInfoFragment, R.id.fragment, RecipeInfoFragment.TAG);
    }

    /*------------------------------- Callbacks -------------------------------*/

    @Override
    public void addIngredient(IngredientInRecipeInfo ingredient) {
        RecipeInfoFragment recipeInfoFragment = getRecipeInfoFragment();
        if (recipeInfoFragment != null) {
            recipeInfoFragment.addIngredient(ingredient);
        }
    }

    @Override
    public void setInstructions(String instructions) {
        RecipeInfoFragment recipeInfoFragment = getRecipeInfoFragment();
        if (recipeInfoFragment != null) {
            recipeInfoFragment.setInstructions(instructions);
        }
    }

    @Override
    public void setResultAmount(float resultAmount) {
        RecipeInfoFragment recipeInfoFragment = getRecipeInfoFragment();
        if (recipeInfoFragment != null) {
            recipeInfoFragment.setResultAmount(resultAmount);
        }
    }

    @Override
    public void setResultUnit(String unit) {
        IngredientsFragment ingredientsFragment = getIngredientsFragment();
        RecipeInfoFragment recipeInfoFragment = getRecipeInfoFragment();

        if (ingredientsFragment != null) {
            ingredientsFragment.setDefaultUnit(unit);
        }

        if (recipeInfoFragment != null) {
            recipeInfoFragment.setResultUnit(unit);
        }
    }

    @Override
    public void setIngredientsInfo(List<IngredientInRecipeInfo> ingredientsInfo) {
        RecipeInfoFragment recipeInfoFragment = getRecipeInfoFragment();
        if (recipeInfoFragment != null) {
            recipeInfoFragment.setIngredientsInfo(ingredientsInfo);
        }
    }
}
