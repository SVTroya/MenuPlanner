package com.troya.menuplanner.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.troya.menuplanner.model.db.entity.CategoryEntity;
import com.troya.menuplanner.model.db.entity.RecipeEntity;
import com.troya.menuplanner.model.repositories.Repository;
import com.troya.menuplanner.model.views.RecipeInfo;

import java.util.List;

import javax.inject.Inject;

public class RecipeListViewModel extends ViewModel {
    private Repository mRepository;

    @Inject
    public RecipeListViewModel(Repository repository) {
        this.mRepository = repository;
    }

    public LiveData<List<RecipeInfo>> getAllRecipes() {
        return mRepository.getAllRecipesExt();
    }

    public LiveData<List<CategoryEntity>> getAllCategories() {
        return mRepository.getAllCategories();
    }

    public Integer addNewRecipe(RecipeEntity recipe){
        return mRepository.addRecipe(recipe);
    }

    public void deleteRecipeById(int id) {
        mRepository.deleteRecipe(new RecipeEntity(id));
    }
}
