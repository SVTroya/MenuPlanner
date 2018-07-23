package com.troya.menuplanner.helpers;

import com.troya.menuplanner.model.views.IngredientInRecipeInfo;

public interface RecipeInfoTabsCallback {

    void addIngredientInfo(IngredientInRecipeInfo ingredient);

    void setInstructions(String instructions);

    void setResultAmount(float resultAmount);

    void setResultUnit(String unitName);

    void onAddIngredientClick(IngredientInRecipeInfo ingredient);

    void onIngredientDelete(int id);
}
