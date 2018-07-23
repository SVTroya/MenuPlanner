package com.troya.menuplanner.dependencyinjection;

import android.app.Application;

import com.troya.menuplanner.controllers.cookbook.RecipesListFragment;
import com.troya.menuplanner.controllers.cookbook.info.DetailsFragment;
import com.troya.menuplanner.controllers.cookbook.info.IngredientsFragment;
import com.troya.menuplanner.controllers.cookbook.info.RecipeInfoActivity;
import com.troya.menuplanner.controllers.cookbook.info.RecipeInfoFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, RoomModule.class})
public interface AppComponent {

    void inject(RecipesListFragment fragment);
    void inject(RecipeInfoFragment fragment);
    void inject(IngredientsFragment fragment);
    void inject(DetailsFragment fragment);
    void inject(RecipeInfoActivity activity);

    Application application();
}
