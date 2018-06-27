package com.troya.menuplanner.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.util.SparseArray;
import android.util.SparseIntArray;

import com.troya.menuplanner.model.db.entity.IngredientEntity;
import com.troya.menuplanner.model.db.entity.IngredientInRecipeEntity;
import com.troya.menuplanner.model.db.entity.RecipeEntity;
import com.troya.menuplanner.model.db.entity.UnitEntity;
import com.troya.menuplanner.model.repositories.Repository;

import java.util.List;

public class RecipeInfoViewModel extends ViewModel {
    private Repository mRepository;

    public RecipeInfoViewModel(Repository repository) {
        mRepository = repository;
    }

    public LiveData<RecipeEntity> getRecipeById(int recipeId) {
        return mRepository.getRecipeById(recipeId);
    }

    public void saveRecipeChanges(RecipeEntity recipe, List<Integer> deletedIngredientsIds,
                                  List<IngredientInRecipeEntity> ingredients,
                                  SparseArray<String> newIngredientsData, SparseArray<String> newUnitsData) {
        if (recipe != null) {
            mRepository.addRecipe(recipe);
        }

        if (deletedIngredientsIds != null) {
            mRepository.deleteIngredientsFromRecipe(deletedIngredientsIds);
        }

        if (ingredients != null && ingredients.size() > 0) {
            mRepository.addIngredientsToRecipe(ingredients, newIngredientsData, newUnitsData);
        }
    }

    public SparseIntArray getOrCreateIngredientIds(SparseArray<String> ingredientNames) {
        return mRepository.getOrCreateIngredientIds(ingredientNames);
    }

    public SparseIntArray getOrCreateUnitIds(SparseArray<String> unitNames) {
        return mRepository.getOrCreateUnitIds(unitNames);
    }

    public int addIngredient(IngredientEntity ingredient) {
        return mRepository.addIngredient(ingredient);
    }

    public int addUnit(UnitEntity unit) {
        return mRepository.addUnit(unit);
    }
}
