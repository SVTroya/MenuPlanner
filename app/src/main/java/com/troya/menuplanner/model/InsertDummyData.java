package com.troya.menuplanner.model;

import com.troya.menuplanner.model.db.entity.CategoryEntity;
import com.troya.menuplanner.model.db.entity.IngredientEntity;
import com.troya.menuplanner.model.db.entity.IngredientInRecipeEntity;
import com.troya.menuplanner.model.db.entity.RecipeAndCategoryEntity;
import com.troya.menuplanner.model.db.entity.RecipeEntity;
import com.troya.menuplanner.model.db.entity.UnitEntity;

import java.util.ArrayList;

public class InsertDummyData {

    private ArrayList<CategoryEntity> mCategories;
    private ArrayList<RecipeEntity> mRecipes;
    private ArrayList<IngredientEntity> mIngredients;
    private ArrayList<UnitEntity> mUnits;
    private ArrayList<RecipeAndCategoryEntity> mRecipeAndCategoryBindings;
    private ArrayList<IngredientInRecipeEntity> mRecipeAndIngredientBindings;
    private ArrayList<Long> mCategoryIds;
    private ArrayList<Long> mRecipeIds;
    private ArrayList<Long> mUnitsIds;
    private ArrayList<Long> mIngredientsIds;


    public void setCategoryIds(ArrayList<Long> categoryIds) {
        mCategoryIds = categoryIds;
    }

    public void setRecipeIds(ArrayList<Long> recipeIds) {
        mRecipeIds = recipeIds;
    }

    public void setUnitsIds(ArrayList<Long> unitsIds) {
        mUnitsIds = unitsIds;
    }

    public void setIngredientsIds(ArrayList<Long> ingredientsIds) {
        mIngredientsIds = ingredientsIds;
    }

    public ArrayList<CategoryEntity> getCategories() {
        return mCategories;
    }

    public ArrayList<RecipeEntity> getRecipes() {
        return mRecipes;
    }

    public ArrayList<RecipeAndCategoryEntity> getRecipeAndCategoryBindings() {
        initRecipeCategoryBinding();
        return mRecipeAndCategoryBindings;
    }

    public ArrayList<IngredientEntity> getIngredients() {
        return mIngredients;
    }

    public ArrayList<UnitEntity> getUnits() {
        return mUnits;
    }

    public ArrayList<IngredientInRecipeEntity> getRecipeAndIngredientBindings(int recipeId) {
        initRecipeAndIngredientBinding(recipeId);
        return mRecipeAndIngredientBindings;
    }

    public InsertDummyData() {
        initCategories();
        initRecipes();
        initUnits();
        initIngredients();
    }

    private void initCategories() {
        mCategories = new ArrayList<>();
        mCategories.add(new CategoryEntity("Vegetarian", 5));
        mCategories.add(new CategoryEntity("Pasta", 200));
        mCategories.add(new CategoryEntity("Dessert", 800));
        mCategories.add(new CategoryEntity("Diet", 150));
    }

    private void initRecipes() {
        mRecipes = new ArrayList<>();
        mRecipes.add(new RecipeEntity("Stir-Fried Pork Noodles", null, "Gordon Ramsay", 4.5f));
        mRecipes.add(new RecipeEntity("Lemon Tart", null, "Gordon Ramsay", 5f));
        mRecipes.add(new RecipeEntity("Frozen Chocolate-Coconut Milk with Strawberries", null, null, 2.5f));
        mRecipes.add(new RecipeEntity("Pasta with Tomatoes, Anchovy and Chillies", null, "Gordon Ramsay", 3f));
    }

    private void initRecipeCategoryBinding() {
        mRecipeAndCategoryBindings = new ArrayList<>();
        mRecipeAndCategoryBindings.add(new RecipeAndCategoryEntity(mRecipeIds.get(0).intValue(), mCategoryIds.get(1).intValue()));
        mRecipeAndCategoryBindings.add(new RecipeAndCategoryEntity(mRecipeIds.get(1).intValue(), mCategoryIds.get(2).intValue()));
        mRecipeAndCategoryBindings.add(new RecipeAndCategoryEntity(mRecipeIds.get(2).intValue(), mCategoryIds.get(2).intValue()));
        mRecipeAndCategoryBindings.add(new RecipeAndCategoryEntity(mRecipeIds.get(2).intValue(), mCategoryIds.get(3).intValue()));
        mRecipeAndCategoryBindings.add(new RecipeAndCategoryEntity(mRecipeIds.get(3).intValue(), mCategoryIds.get(1).intValue()));
        mRecipeAndCategoryBindings.add(new RecipeAndCategoryEntity(mRecipeIds.get(3).intValue(), mCategoryIds.get(0).intValue()));
    }

    private void initIngredients() {
        mIngredients = new ArrayList<>();
        mIngredients.add(new IngredientEntity("Carrot", "carrot"));
        mIngredients.add(new IngredientEntity("Apple", "apple"));
        mIngredients.add(new IngredientEntity("Cheese", "cheese"));
        mIngredients.add(new IngredientEntity("Potato", null));
    }

    private void initUnits() {
        mUnits = new ArrayList<>();
        mUnits.add(new UnitEntity("g"));
        mUnits.add(new UnitEntity("kg"));
        mUnits.add(new UnitEntity("pack"));
        mUnits.add(new UnitEntity("can"));
    }

    private void initRecipeAndIngredientBinding(int recipeId) {
        mRecipeAndIngredientBindings = new ArrayList<>();
        mRecipeAndIngredientBindings.add(new IngredientInRecipeEntity(
                recipeId,
                mIngredientsIds.get(0).intValue(),
                3,
                null,
                "Comment for 1-St row"));
        mRecipeAndIngredientBindings.add(new IngredientInRecipeEntity(
                recipeId,
                mIngredientsIds.get(1).intValue(),
                250,
                mUnitsIds.get(0).intValue(),
                "Looooov oooooo oong Comment for 2-St row"));
        mRecipeAndIngredientBindings.add(new IngredientInRecipeEntity(
                recipeId,
                mIngredientsIds.get(2).intValue(),
                5.5f,
                mUnitsIds.get(1).intValue(),
                null));
        mRecipeAndIngredientBindings.add(new IngredientInRecipeEntity(
                recipeId,
                mIngredientsIds.get(3).intValue(),
                3,
                mUnitsIds.get(3).intValue(),
                null));
    }

}
