package com.troya.menuplanner.Temp.Recipes.info;

import android.os.Bundle;

import com.troya.menuplanner.model.db.entity.RecipeEntity;
import com.troya.menuplanner.R;
import com.troya.menuplanner.controllers.BaseActivity;

public class RecipeInfoActivity extends BaseActivity {

    public static final int LAYOUT = R.layout.fragment_recipe_info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppDefault);
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT);

        /*Repository recipe;
        long recipeId = 0;
        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            recipeId = bundle.getLong(RecipeRepo.KEY_SELECTED_RECIPE_ID);
        }

        recipe = new RecipeRepo(this).getOneById(recipeId);

        if (recipe == null)
        {
            recipe = new Repository(0, "", false);
        }





        mFavoriteView.setChecked(recipe.isFavorite());
        mFavoriteView.setOnClickListener(new FavoriteCbClickListener(this, recipeId));*/

        //this.findViewById(R.id.imgBtnDelete).setOnClickListener(new DeleteRecipeOnClickListener(this, recipeId));

        //initTab(recipe);
    }



    private void initTab(RecipeEntity recipe) {


    }


}
