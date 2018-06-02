package com.troya.menuplanner;

import android.app.Application;

import com.troya.menuplanner.dependencyinjection.AppComponent;
import com.troya.menuplanner.dependencyinjection.AppModule;
import com.troya.menuplanner.dependencyinjection.DaggerAppComponent;
import com.troya.menuplanner.dependencyinjection.RoomModule;


public class App extends Application{

    private AppComponent mAppComponent;


    @Override
    public void onCreate() {
        super.onCreate();

        mAppComponent = DaggerAppComponent
                .builder()
                .appModule(new AppModule(this))
                .roomModule(new RoomModule(this))
                .build();
    }

    public AppComponent getAppComponent() {
        return mAppComponent;
    }
}
