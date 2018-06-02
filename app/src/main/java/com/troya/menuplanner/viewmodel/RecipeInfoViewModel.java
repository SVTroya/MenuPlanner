package com.troya.menuplanner.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.troya.menuplanner.model.db.entity.RecipeEntity;
import com.troya.menuplanner.model.repositories.Repository;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RecipeInfoViewModel extends ViewModel {
    private Repository mRepository;
    private ExecutorService mExecutorService;

    public RecipeInfoViewModel(Repository repository) {
        mRepository = repository;
        mExecutorService = Executors.newSingleThreadExecutor();
    }

    public LiveData<RecipeEntity> getRecipeById(int recipeId) {
        return mRepository.getRecipeById(recipeId);
    }

    public void saveRecipeChanges(RecipeEntity recipe) {
        mExecutorService.execute(() -> mRepository.addRecipe(recipe));
    }
}
