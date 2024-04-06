package com.example.newsapp.di;

import android.app.Application;
import android.content.Context;

import com.example.newsapp.data.repository.NewsRepository;
import com.example.newsapp.data.repository.NewsRepositoryImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    @Provides
    @Singleton
    public Context provideContext(Application application) {
        return application.getApplicationContext();
    }

    @Provides
    @Singleton
    public NewsRepository provideNewsRepository(Application application) {
        return new NewsRepositoryImpl(application);
    }
}