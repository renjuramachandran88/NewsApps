package com.example.newsapp.di;


import com.example.newsapp.ui.MainActivity;
import com.example.newsapp.ui.NewsViewModel;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, ApplicationModule.class})
public interface ApplicationComponent {
    void inject(MainActivity mainActivity);

    void inject(NewsViewModel viewModel);
}
