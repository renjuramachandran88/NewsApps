package com.example.newsapp;

import android.app.Application;

import com.example.newsapp.di.AppModule;
import com.example.newsapp.di.ApplicationComponent;
import com.example.newsapp.di.ApplicationModule;
import com.example.newsapp.di.DaggerApplicationComponent;


public class NewsApp extends Application {

    private ApplicationComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        // Initialize the AppComponent using a builder pattern
        appComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    // Getter method to provide AppComponent to other parts of the application
    public ApplicationComponent getAppComponent() {
        return appComponent;
    }
}