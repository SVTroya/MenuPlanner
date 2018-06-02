package com.troya.menuplanner.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.os.AsyncTask;

import com.troya.menuplanner.model.RecipeCardInfo;
import com.troya.menuplanner.model.db.entity.CategoryEntity;
import com.troya.menuplanner.model.db.entity.RecipeEntity;
import com.troya.menuplanner.model.repositories.Repository;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Inject;

public class RecipeListViewModel extends ViewModel {
    private Repository mRepository;
    private ExecutorService mExecutorService;

    @Inject
    public RecipeListViewModel(Repository repository) {
        this.mRepository = repository;
        this.mExecutorService = Executors.newSingleThreadExecutor();
    }

    public LiveData<List<RecipeCardInfo>> getAllRecipes() {
        return mRepository.getAllRecipesExt();
    }

    public LiveData<List<CategoryEntity>> getAllCategories() {
        return mRepository.getAllCategories();
    }

    public Integer addNewRecipe(RecipeEntity recipe){
        //mExecutorService.execute(() -> mRepository.addRecipes(recipe));
        try {
            return (new AddItemTask()).execute(recipe).get().get(0).intValue();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return 0;
    }

    public void deleteRecipeById(int id) {
        mExecutorService.execute(() -> mRepository.deleteRecipe(new RecipeEntity(id)));
    }

    protected class AddItemTask extends AsyncTask<RecipeEntity, Void, List<Long>>{

        @Override
        protected List<Long> doInBackground(RecipeEntity... recipeEntities) {
            return mRepository.addRecipes(recipeEntities);
        }

    }



    public void insertDummyData() {
        mRepository.insertDummyDataAsync();
    }
}
