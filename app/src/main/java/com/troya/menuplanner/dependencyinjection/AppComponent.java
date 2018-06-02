package com.troya.menuplanner.dependencyinjection;

import android.app.Application;

import com.troya.menuplanner.controllers.cookbook.RecipesListFragment;
import com.troya.menuplanner.controllers.cookbook.info.RecipeInfoFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, RoomModule.class})
public interface AppComponent {

    void inject(RecipesListFragment fragment);
    void inject(RecipeInfoFragment fragment);

    Application application();
}
