package com.troya.menuplanner.model.repositories;

import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.troya.menuplanner.model.InsertDummyData;
import com.troya.menuplanner.model.db.dao.CategoryDao;
import com.troya.menuplanner.model.db.dao.IngredientDao;
import com.troya.menuplanner.model.db.dao.IngredientInRecipeDao;
import com.troya.menuplanner.model.db.dao.RecipeAndCategoryDao;
import com.troya.menuplanner.model.db.dao.RecipeDao;
import com.troya.menuplanner.model.db.dao.UnitDao;
import com.troya.menuplanner.model.db.entity.CategoryEntity;
import com.troya.menuplanner.model.db.entity.RecipeEntity;
import com.troya.menuplanner.model.views.IngredientInRecipeInfo;
import com.troya.menuplanner.model.views.RecipeInfo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class Repository {

    private final RecipeDao mRecipeDao;
    private final CategoryDao mCategoryDao;
    private final RecipeAndCategoryDao mRecipeAndCategoryDao;
    private final IngredientDao mIngredientDao;
    private final UnitDao mUnitDao;
    private final IngredientInRecipeDao mIngredientInRecipeDao;

    @Inject
    public Repository(RecipeDao recipeDao, CategoryDao categoryDao,
                      RecipeAndCategoryDao recipeAndCategoryDao, IngredientDao ingredientDao,
                      UnitDao unitDao, IngredientInRecipeDao ingredientInRecipeDao) {
        mRecipeDao = recipeDao;
        mCategoryDao = categoryDao;
        mRecipeAndCategoryDao = recipeAndCategoryDao;
        mIngredientDao = ingredientDao;
        mUnitDao = unitDao;
        mIngredientInRecipeDao = ingredientInRecipeDao;
    }

    //  ------------------------- Categories -------------------------
    public LiveData<List<CategoryEntity>> getAllCategories() {
        return mCategoryDao.getAll();
    }

    //  ------------------------- Units -------------------------
    public String getUnitNameById(int unitId) {
        return mUnitDao.getNameById(unitId);
    }

    public int getUnitIdByName(String unitName) {
        try {
            return new AsyncTask<String, Void, Integer>() {
                @Override
                protected Integer doInBackground(String... strings) {
                    return mUnitDao.getIdByName(unitName);
                }
            }.execute().get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return  0;
        }
    }

    //  ------------------------- Recipes -------------------------
    public LiveData<List<RecipeInfo>> getAllRecipesExt() {
        return mRecipeDao.getAllExt();
    }

    public Integer addRecipe(RecipeEntity recipe) {
        return (int) mRecipeDao.addItems(recipe);
    }

    public List<Long> addRecipes(RecipeEntity... recipes) {
        return mRecipeDao.addItems(Arrays.asList(recipes));
    }

    public void deleteRecipe(RecipeEntity recipe) {
        mRecipeDao.deleteItem(recipe);
    }

    public LiveData<RecipeEntity> getRecipeById(int id) {
        return mRecipeDao.getOneById(id);
    }


    //  ------------------------- Ingredients -------------------------
    public LiveData<List<IngredientInRecipeInfo>> getIngredientsById(int recipeId) {
        return mIngredientInRecipeDao.getIngredientsByRecipeId(recipeId);
    }

/*
    class GetUnitIdTask extends AsyncTask<String, Void, Integer> {
        @Override
        protected Integer doInBackground(String... strings) {
            return null;
        }
    }*/


    public void insertDummyDataAsync() {
        new AddDummyDataTask().execute();
    }

    protected class AddDummyDataTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... item) {
            InsertDummyData data = new InsertDummyData();

            data.setCategoryIds((ArrayList<Long>) mCategoryDao.addItems(data.getCategories()));
            data.setRecipeIds((ArrayList<Long>) mRecipeDao.addItems(data.getRecipes()));
            mRecipeAndCategoryDao.addItems(data.getRecipeAndCategoryBindings());

            data.setUnitsIds((ArrayList<Long>) mUnitDao.addItems(data.getUnits()));
            data.setIngredientsIds((ArrayList<Long>) mIngredientDao.addItems(data.getIngredients()));
            mIngredientInRecipeDao.addItems(data.getRecipeAndIngredientBindings(1));

            return null;
        }
    }
}
