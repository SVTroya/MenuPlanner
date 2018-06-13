package com.troya.menuplanner.helpers;

import com.troya.menuplanner.model.views.IngredientInRecipeInfo;

import java.util.List;

public interface RecipeInfoTabsCallback {

    void addIngredient(IngredientInRecipeInfo ingredient);

    void setInstructions(String instructions);

    void setResultAmount(float resultAmount);

    void setResultUnit(String unit);

    void setIngredientsInfo(List<IngredientInRecipeInfo> ingredientsInfo);
}
