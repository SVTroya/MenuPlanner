package com.troya.menuplanner.model.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.troya.menuplanner.model.db.dao.CategoryDao;
import com.troya.menuplanner.model.db.dao.IngredientDao;
import com.troya.menuplanner.model.db.dao.IngredientGroupDao;
import com.troya.menuplanner.model.db.dao.IngredientInRecipeDao;
import com.troya.menuplanner.model.db.dao.RecipeAndCategoryDao;
import com.troya.menuplanner.model.db.dao.RecipeDao;
import com.troya.menuplanner.model.db.dao.UnitDao;
import com.troya.menuplanner.model.db.entity.CategoryEntity;
import com.troya.menuplanner.model.db.entity.IngredientEntity;
import com.troya.menuplanner.model.db.entity.IngredientGroupEntity;
import com.troya.menuplanner.model.db.entity.IngredientInRecipeEntity;
import com.troya.menuplanner.model.db.entity.RecipeAndCategoryEntity;
import com.troya.menuplanner.model.db.entity.RecipeEntity;
import com.troya.menuplanner.model.db.entity.UnitEntity;

@Database(entities = {
        CategoryEntity.class,
        RecipeEntity.class,
        IngredientGroupEntity.class,
        IngredientEntity.class,
        UnitEntity.class,
        IngredientInRecipeEntity.class,
        RecipeAndCategoryEntity.class},
        version = 1,
        exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract CategoryDao categoryDao();

    public abstract IngredientGroupDao ingredientGroupDao();

    public abstract IngredientDao ingredientDao();

    public abstract UnitDao unitDao();

    public abstract RecipeDao recipeDao();

    public abstract IngredientInRecipeDao ingredientInRecipeDao();

    public abstract RecipeAndCategoryDao recipeAndCategoryDao();
}
