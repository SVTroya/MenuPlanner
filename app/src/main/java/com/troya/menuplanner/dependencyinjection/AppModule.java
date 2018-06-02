package com.troya.menuplanner.dependencyinjection;

import android.app.Application;

import com.troya.menuplanner.App;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {
    private final App mApplication;

    public AppModule(App application) {
        mApplication = application;
    }

    @Singleton
    @Provides
    Application provideApplication() {
        return mApplication;
    }
}
