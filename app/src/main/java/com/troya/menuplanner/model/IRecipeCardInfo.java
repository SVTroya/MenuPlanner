package com.troya.menuplanner.model;

import java.util.List;

public interface IRecipeCardInfo {
    int getId();
    String getName();
    byte[] getImage();
    String getSource();
    int getRating();
    List<Integer> getCategoriesId();
}
