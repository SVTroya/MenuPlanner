package com.troya.menuplanner.model.repositories;

import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
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

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class Repository {

    private static final int CODE_INGREDIENTS_FINISH = 10;
    private static final int CODE_UNITS_FINISH = 30;

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

    public SparseIntArray getOrCreateUnitIds(SparseArray<String> unitNames) {
        SparseIntArray ids = new SparseIntArray();
        for (int i = 0; i < unitNames.size(); i++) {
            int id = mUnitDao.getIdByName(unitNames.valueAt(i));
            if (id == 0) {
                id = (int) mUnitDao.addItem(new UnitEntity(unitNames.valueAt(i)));
            }
            ids.put(unitNames.keyAt(i), id);
        }

        return ids;
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
        SparseIntArray ids = new SparseIntArray();
        for (int i = 0; i < ingredientNames.size(); i++) {
            int id = mIngredientDao.getIdByName(ingredientNames.valueAt(i));
            if (id == 0) {
                id = (int) mIngredientDao.addItem(new IngredientEntity(ingredientNames.valueAt(i)));
            }
            ids.put(ingredientNames.keyAt(i), id);
        }

        return ids;
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

        final boolean[] unitsFinished = {true};
        final boolean[] ingredientsFinished = {true};

        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case CODE_UNITS_FINISH:
                        SparseIntArray unitsIds = (SparseIntArray) msg.obj;
                        for (int i = 0; i < unitsIds.size(); i++) {
                            ingredients.get(unitsIds.keyAt(i)).setUnitId(unitsIds.valueAt(i));
                        }
                        unitsFinished[0] = true;
                        break;
                    case CODE_INGREDIENTS_FINISH:
                        SparseIntArray ingredientsIds = (SparseIntArray) msg.obj;
                        for (int i = 0; i < ingredientsIds.size(); i++) {
                            ingredients.get(ingredientsIds.keyAt(i)).setIngredientId(ingredientsIds.valueAt(i));
                        }
                        ingredientsFinished[0] = true;
                        break;
                }

                if (unitsFinished[0] && ingredientsFinished[0]) {
                    mExecutorService.execute(() -> mIngredientInRecipeDao.addItems(ingredients));
                }
            }
        };

        if (newIngredientsInfo == null && newUnitsInfo == null) {
            mExecutorService.execute(() -> mIngredientInRecipeDao.addItems(ingredients));
        } else {
            if (newIngredientsInfo != null) {
                ingredientsFinished[0] = false;
                Thread ingredientsThread = new Thread(() -> {
                    SparseIntArray ingredientsIds = getOrCreateIngredientIds(newIngredientsInfo);
                    Message message = handler.obtainMessage(CODE_INGREDIENTS_FINISH, ingredientsIds);
                    handler.sendMessage(message);
                });
                ingredientsThread.setName("IngredientsInfoThread");
                ingredientsThread.start();
            }

            if (newUnitsInfo != null) {
                unitsFinished[0] = false;
                Thread unitsThread = new Thread(() -> {
                    SparseIntArray unitsIds = getOrCreateUnitIds(newUnitsInfo);
                    Message message = handler.obtainMessage(CODE_UNITS_FINISH, unitsIds);
                    handler.sendMessage(message);
                });
                unitsThread.setName("UnitsInfoThread");
                unitsThread.start();
            }
        }
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
