package com.troya.menuplanner.model;

import com.troya.menuplanner.model.db.entity.CategoryEntity;
import com.troya.menuplanner.model.db.entity.RecipeAndCategoryEntity;
import com.troya.menuplanner.model.db.entity.RecipeEntity;

import java.util.ArrayList;

public class InsertDummyData {

    private ArrayList<CategoryEntity> mCategories;
    private ArrayList<RecipeEntity> mRecipes;
    private ArrayList<RecipeAndCategoryEntity> mBindings;
    private ArrayList<Long> mCategoryIds;
    private ArrayList<Long> mRecipeIds;

    public ArrayList<Long> getCategoryIds() {
        return mCategoryIds;
    }

    public void setCategoryIds(ArrayList<Long> categoryIds) {
        mCategoryIds = categoryIds;
    }

    public ArrayList<Long> getRecipeIds() {
        return mRecipeIds;
    }

    public void setRecipeIds(ArrayList<Long> recipeIds) {
        mRecipeIds = recipeIds;
    }

    public ArrayList<CategoryEntity> getCategories() {
        return mCategories;
    }

    public ArrayList<RecipeEntity> getRecipes() {
        return mRecipes;
    }

    public ArrayList<RecipeAndCategoryEntity> getBindings() {
        initRecipeCategoryBinding();
        return mBindings;
    }

    public InsertDummyData() {
        initCategories();
        initRecipes();
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
        mBindings = new ArrayList<>();
        mBindings.add(new RecipeAndCategoryEntity(mRecipeIds.get(0).intValue(), mCategoryIds.get(1).intValue()));
        mBindings.add(new RecipeAndCategoryEntity(mRecipeIds.get(1).intValue(), mCategoryIds.get(2).intValue()));
        mBindings.add(new RecipeAndCategoryEntity(mRecipeIds.get(2).intValue(), mCategoryIds.get(2).intValue()));
        mBindings.add(new RecipeAndCategoryEntity(mRecipeIds.get(2).intValue(), mCategoryIds.get(3).intValue()));
        mBindings.add(new RecipeAndCategoryEntity(mRecipeIds.get(3).intValue(), mCategoryIds.get(1).intValue()));
        mBindings.add(new RecipeAndCategoryEntity(mRecipeIds.get(3).intValue(), mCategoryIds.get(0).intValue()));
    }





}
