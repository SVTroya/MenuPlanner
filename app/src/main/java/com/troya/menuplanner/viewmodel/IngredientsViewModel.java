package com.troya.menuplanner.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.troya.menuplanner.model.repositories.Repository;
import com.troya.menuplanner.model.views.IngredientInRecipeInfo;

import java.util.List;

public class IngredientsViewModel extends ViewModel {
    private Repository mRepository;

    public IngredientsViewModel(Repository repository) {
        mRepository = repository;
    }

    public LiveData<List<IngredientInRecipeInfo>> getIngredientsById(int recipeId) {
        return mRepository.getIngredientsById(recipeId);
    }

    public String getUnitNameById(int unitId) {
        return mRepository.getUnitNameById(unitId);
    }

    public List<String> getAllIngredientNames() {
        return mRepository.getAllIngredientNames();
    }

    public List<String> getAllUnitNames() {
        return mRepository.getAllUnitNames();
    }
}
