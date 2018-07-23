package com.troya.menuplanner.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.troya.menuplanner.model.repositories.Repository;

import javax.inject.Inject;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private Repository mRepository;

    @Inject
    public ViewModelFactory(Repository repository) {
        mRepository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {

        ViewModel viewModel;
        if (modelClass.isAssignableFrom(RecipeListViewModel.class)) {
            viewModel = new RecipeListViewModel(mRepository);
        } else if (modelClass.isAssignableFrom(RecipeInfoViewModel.class)) {
            viewModel = new RecipeInfoViewModel(mRepository);
        } else if (modelClass.isAssignableFrom(IngredientsViewModel.class)) {
            viewModel = new IngredientsViewModel(mRepository);
        } else if (modelClass.isAssignableFrom(DetailsViewModel.class)) {
            viewModel = new DetailsViewModel(mRepository);
        } else {
            throw new IllegalArgumentException("ViewModel Not Found");
        }

        return (T) viewModel;
    }
}
