package com.troya.menuplanner.dependencyinjection;

import android.app.Application;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.persistence.room.Room;

import com.troya.menuplanner.model.db.AppDatabase;
import com.troya.menuplanner.model.db.dao.CategoryDao;
import com.troya.menuplanner.model.db.dao.IngredientDao;
import com.troya.menuplanner.model.db.dao.IngredientInRecipeDao;
import com.troya.menuplanner.model.db.dao.RecipeAndCategoryDao;
import com.troya.menuplanner.model.db.dao.RecipeDao;
import com.troya.menuplanner.model.db.dao.UnitDao;
import com.troya.menuplanner.model.repositories.Repository;
import com.troya.menuplanner.viewmodel.ViewModelFactory;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class RoomModule {
    private static final String DB_NAME = "MenuPlanner.db";

    private final AppDatabase mDatabase;

    public RoomModule(Application application) {
        this.mDatabase = Room.databaseBuilder(
                application,
                AppDatabase.class,
                DB_NAME
        ).build();
    }

    @Provides
    @Singleton
    AppDatabase provideDatabase(Application application) {
        return mDatabase;
    }

    @Provides
    @Singleton
    UnitDao provideUnitDao(AppDatabase database) {
        return database.unitDao();
    }

    @Provides
    @Singleton
    RecipeDao provideRecipeDao(AppDatabase database) {
        return database.recipeDao();
    }

    @Provides
    @Singleton
    CategoryDao provideCategoryDao(AppDatabase database) {
        return database.categoryDao();
    }

    @Provides
    @Singleton
    RecipeAndCategoryDao provideRecipeAndCategoryDao(AppDatabase database) {
        return database.recipeAndCategoryDao();
    }

    @Provides
    @Singleton
    IngredientDao provideIngredientDao(AppDatabase database) {
        return database.ingredientDao();
    }

    @Provides
    @Singleton
    IngredientInRecipeDao provideIngredientInRecipeDao(AppDatabase database) {
        return database.ingredientInRecipeDao();
    }

    @Provides
    @Singleton
    Repository provideRepository(RecipeDao recipeDao, CategoryDao categoryDao,
                                 RecipeAndCategoryDao recipeAndCategoryDao, IngredientDao ingredientDao,
                                 UnitDao unitDao, IngredientInRecipeDao ingredientInRecipeDao) {
        return new Repository(recipeDao, categoryDao, recipeAndCategoryDao,
                ingredientDao, unitDao, ingredientInRecipeDao);
    }

    @Provides
    @Singleton
    ViewModelProvider.Factory provideViewModelFactory(Repository repository) {
        return new ViewModelFactory(repository);
    }
}
