package com.troya.menuplanner.model.repositories;

import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.troya.menuplanner.model.InsertDummyData;
import com.troya.menuplanner.model.RecipeCardInfo;
import com.troya.menuplanner.model.db.dao.CategoryDao;
import com.troya.menuplanner.model.db.dao.RecipeAndCategoryDao;
import com.troya.menuplanner.model.db.dao.RecipeDao;
import com.troya.menuplanner.model.db.entity.CategoryEntity;
import com.troya.menuplanner.model.db.entity.RecipeEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class Repository {

    private final RecipeDao mRecipeDao;
    private final CategoryDao mCategoryDao;
    private final RecipeAndCategoryDao mRecipeAndCategoryDao;

    @Inject
    public Repository(RecipeDao recipeDao, CategoryDao categoryDao,
                      RecipeAndCategoryDao recipeAndCategoryDao) {
        mRecipeDao = recipeDao;
        mCategoryDao = categoryDao;
        mRecipeAndCategoryDao = recipeAndCategoryDao;
    }

    public LiveData<List<RecipeCardInfo>> getAllRecipesExt() {
        return mRecipeDao.getAllExt();
    }

    public LiveData<List<CategoryEntity>> getAllCategories() {
        return mCategoryDao.getAll();
    }

    public Integer addRecipe(RecipeEntity recipe) {
        return (int) mRecipeDao.addItem(recipe);
    }

    public List<Long> addRecipes(RecipeEntity... recipes) {
        return mRecipeDao.addItem(Arrays.asList(recipes));
    }

    public void deleteRecipe(RecipeEntity recipe) {
        mRecipeDao.deleteItem(recipe);
    }

    public LiveData<RecipeEntity> getRecipeById(int id) {
        return mRecipeDao.getOneById(id);
    }




    public void insertDummyDataAsync() {
        new AddDummyDataTask().execute();
    }

    protected class AddDummyDataTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... item) {
            InsertDummyData data = new InsertDummyData();

            data.setCategoryIds((ArrayList<Long>) mCategoryDao.addItem(data.getCategories()));
            data.setRecipeIds((ArrayList<Long>) mRecipeDao.addItem(data.getRecipes()));
            mRecipeAndCategoryDao.addItem(data.getBindings());
            return null;
        }

    }
}
