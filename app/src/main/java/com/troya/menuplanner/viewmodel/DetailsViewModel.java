package com.troya.menuplanner.viewmodel;

import android.arch.lifecycle.ViewModel;

import com.troya.menuplanner.model.repositories.Repository;

public class DetailsViewModel extends ViewModel {
    private Repository mRepository;

    public DetailsViewModel(Repository repository) {
        mRepository = repository;
    }

    public String getUnitNameById(int unitId) {
        return mRepository.getUnitNameById(unitId);
    }
}
