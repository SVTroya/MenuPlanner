package com.troya.menuplanner.model.repositories;

import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.util.SparseArray;
import android.util.SparseIntArray;

import com.troya.menuplanner.model.InsertDummyData;
import com.troya.menuplanner.model.db.dao.CategoryDao;
import com.troya.menuplanner.model.db.dao.IngredientDao;
import com.troya.menuplanner.model.db.dao.IngredientInRecipeDao;
import com.troya.menuplanner.model.db.dao.RecipeAndCategoryDao;
import com.troya.menuplanner.model.db.dao.RecipeDao;
import com.troya.menuplanner.model.db.dao.UnitDao;
import com.troya.menuplanner.model.db.entity.CategoryEntity;
import com.troya.menuplanner.model.db.entity.IngredientEntity;
import com.troya.menuplanner.model.db.entity.IngredientInRecipeEntity;
import com.troya.menuplanner.model.db.entity.RecipeEntity;
import com.troya.menuplanner.model.db.entity.UnitEntity;
import com.troya.menuplanner.model.views.IngredientInRecipeInfo;
import com.troya.menuplanner.model.views.RecipeInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class Repository {

    private static RecipeDao mRecipeDao;
    private static CategoryDao mCategoryDao;
    private static RecipeAndCategoryDao mRecipeAndCategoryDao;
    private static IngredientDao mIngredientDao;
    private static UnitDao mUnitDao;
    private static IngredientInRecipeDao mIngredientInRecipeDao;

    private ExecutorService mExecutorService;

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

        mExecutorService = Executors.newSingleThreadExecutor();
    }


    //  ------------------------- Categories -------------------------
    public LiveData<List<CategoryEntity>> getAllCategories() {
        return mCategoryDao.getAll();
    }


    //  ------------------------- Units -------------------------
    public String getUnitNameById(int unitId) {
        try {
            return mExecutorService.submit(new GetUnitNameCallable(unitId)).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return null;
        }
    }

    public int getUnitIdByName(String unitName) {
        try {
            return mExecutorService.submit(new GetUnitIdCallable(unitName)).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public SparseIntArray getOrCreateUnitIds (SparseArray<String> unitNames) {
        ExecutorService taskExecutor = Executors.newFixedThreadPool(1);
        Future<SparseIntArray> result = null;
        result = taskExecutor.submit(new GetOrCreateUnitIdsCallable(unitNames));
        taskExecutor.shutdown();
        try {
            taskExecutor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
            return result.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return null;
        }

       /*


        try {
            Future<SparseIntArray> future = mExecutorService.submit(new GetOrCreateUnitIdsCallable(unitNames));
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return null;
        }*/
    }

    public List<String> getAllUnitNames() {
        try {
            return mExecutorService.submit(new UnitNamesCallable()).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return null;
        }
    }

    public int addUnit(UnitEntity unit) {
        try {
            return mExecutorService.submit(new AddUnitCallable(unit)).get().intValue();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return 0;
        }
    }

    protected static class UnitNamesCallable implements Callable<List<String>> {
        @Override
        public List<String> call() throws Exception {
            return mUnitDao.getAllNames();
        }
    }

    protected static class GetUnitIdCallable implements Callable<Integer> {

        private String mName;

        GetUnitIdCallable(String name) {
            this.mName = name;
        }

        @Override
        public Integer call() {
            return mUnitDao.getIdByName(mName);
        }

    }

    protected static class GetOrCreateUnitIdsCallable implements Callable<SparseIntArray> {

        private SparseArray<String> mNames;

        GetOrCreateUnitIdsCallable(SparseArray<String> names) {
            this.mNames = names;
        }

        @Override
        public SparseIntArray call() {
            SparseIntArray ids = new SparseIntArray();
            for (int i = 0; i < mNames.size(); i++) {
                int id = mUnitDao.getIdByName(mNames.valueAt(i));
                if (id == 0) {
                    mUnitDao.addItem(new UnitEntity(mNames.valueAt(i)));
                }
                ids.put(mNames.keyAt(i), id);
            }

            return ids;
        }

    }

    protected static class GetUnitNameCallable implements Callable<String> {

        private int mId;

        GetUnitNameCallable(int id) {
            this.mId = id;
        }

        @Override
        public String call() {
            return mUnitDao.getNameById(mId);
        }

    }

    protected static class AddUnitCallable implements Callable<Long> {

        private UnitEntity mUnit;

        AddUnitCallable(UnitEntity unit) {
            this.mUnit = unit;
        }

        @Override
        public Long call() {
            return mUnitDao.addItem(mUnit);
        }
    }


    //  ------------------------- Recipes -------------------------
    public LiveData<List<RecipeInfo>> getAllRecipesExt() {
        return mRecipeDao.getAllExt();
    }

    public Integer addRecipe(RecipeEntity recipe) {
        try {
            return mExecutorService.submit(new AddRecipeCallable(recipe)).get().intValue();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void deleteRecipe(RecipeEntity recipe) {
        mExecutorService.execute(() -> mRecipeDao.deleteItem(recipe));
    }

    public LiveData<RecipeEntity> getRecipeById(int id) {
        return mRecipeDao.getOneById(id);
    }

    protected static class AddRecipeCallable implements Callable<Long> {

        private RecipeEntity mRecipe;

        AddRecipeCallable(RecipeEntity recipe) {
            this.mRecipe = recipe;
        }

        @Override
        public Long call() {
            return mRecipeDao.addItem(mRecipe);
        }
    }


    //  ------------------------- Ingredients -------------------------
    public LiveData<List<IngredientInRecipeInfo>> getIngredientsById(int recipeId) {
        return mIngredientInRecipeDao.getIngredientsByRecipeId(recipeId);
    }

    public List<String> getAllIngredientNames() {
        try {
            return mExecutorService.submit(new IngredientNamesCallable()).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return null;
        }
    }

    public int addIngredient(IngredientEntity ingredient) {
        try {
            return mExecutorService.submit(new AddIngredientCallable(ingredient)).get().intValue();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int getIngredientIdByName(String name) {
        try {
            return mExecutorService.submit(new GetIngredientIdCallable(name)).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public SparseIntArray getOrCreateIngredientIds(SparseArray<String> ingredientNames) {

        ExecutorService taskExecutor = Executors.newFixedThreadPool(1);
        Future<SparseIntArray> result = null;
        result = taskExecutor.submit(new GetOrCreateIngredientIdsCallable(ingredientNames));
        taskExecutor.shutdown();
        try {
            taskExecutor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
            return result.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return null;
        }


       /* try {
            return mExecutorService.submit(new GetOrCreateIngredientIdsCallable(ingredientNames)).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return null;
        }*/
    }

    protected static class IngredientNamesCallable implements Callable<List<String>> {

        @Override
        public List<String> call() {
            return mIngredientDao.getAllNames();
        }

    }

    protected static class GetIngredientIdCallable implements Callable<Integer> {

        private String mName;

        GetIngredientIdCallable(String name) {
            this.mName = name;
        }

        @Override
        public Integer call() {
            return mIngredientDao.getIdByName(mName);
        }

    }

    protected static class GetOrCreateIngredientIdsCallable implements Callable<SparseIntArray> {

        private SparseArray<String> mNames;

        GetOrCreateIngredientIdsCallable(SparseArray<String> names) {
            this.mNames = names;
        }

        @Override
        public SparseIntArray call() {
            SparseIntArray ids = new SparseIntArray();
            for (int i = 0; i < mNames.size(); i++) {
                int id = mUnitDao.getIdByName(mNames.valueAt(i));
                if (id == 0) {
                    mIngredientDao.addItem(new IngredientEntity(mNames.valueAt(i)));
                }
                ids.put(mNames.keyAt(i), id);
            }

            return ids;
        }
    }

    protected static class AddIngredientCallable implements Callable<Long> {

        private IngredientEntity mIngredient;

        AddIngredientCallable(IngredientEntity ingredient) {
            this.mIngredient = ingredient;
        }

        @Override
        public Long call() {
            return mIngredientDao.addItem(mIngredient);
        }
    }


    //  ------------------------- Ingredients In Recipe -------------------------
    public void addIngredientsToRecipe(List<IngredientInRecipeEntity> ingredients,
                                       SparseArray<String> newIngredientsInfo, SparseArray<String> newUnitsInfo) {
        if (newIngredientsInfo != null) {
            SparseIntArray ingredientsIds = getOrCreateIngredientIds(newIngredientsInfo);
            for (int i = 0; i < ingredientsIds.size(); i++) {
                ingredients.get(ingredientsIds.keyAt(i)).setIngredientId(ingredientsIds.valueAt(i));
            }
        }

        if (newUnitsInfo != null) {
            SparseIntArray unitsIds = getOrCreateUnitIds(newUnitsInfo);
            for (int i = 0; i < unitsIds.size(); i++) {
                ingredients.get(unitsIds.keyAt(i)).setUnitId(unitsIds.valueAt(i));
            }
        }

        mExecutorService.execute(() -> mIngredientInRecipeDao.addItems(ingredients));
    }

    public void deleteIngredientsFromRecipe(List<Integer> ingredientsIds) {
        mExecutorService.execute(() -> mIngredientInRecipeDao.deleteItems(ingredientsIds));
    }


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
