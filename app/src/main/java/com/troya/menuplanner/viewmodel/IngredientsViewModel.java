package com.troya.menuplanner.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.troya.menuplanner.model.repositories.Repository;
import com.troya.menuplanner.model.views.IngredientInRecipeInfo;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class IngredientsViewModel extends ViewModel {
    private Repository mRepository;
    private ExecutorService mExecutorService;

    public IngredientsViewModel(Repository repository) {
        mRepository = repository;
        mExecutorService = Executors.newSingleThreadExecutor();
    }

    public LiveData<List<IngredientInRecipeInfo>> getIngredientsById(int recipeId) {
        return mRepository.getIngredientsById(recipeId);
    }

    public String getUnitNameById(int unitId) {
        return mRepository.getUnitNameById(unitId);
    }
}
