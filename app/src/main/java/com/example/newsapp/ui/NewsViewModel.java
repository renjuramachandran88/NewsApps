package com.example.newsapp.ui;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.newsapp.data.model.Article;
import com.example.newsapp.data.repository.NewsRepository;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class NewsViewModel extends ViewModel {
    @Inject
    NewsRepository newsRepository;

    private final MutableLiveData<List<Article>> newsLiveData = new MutableLiveData<>();
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Inject
    public NewsViewModel() {
    }

    public LiveData<List<Article>> getNews() {
        return newsLiveData;
    }


    public void fetchNews() {
        Disposable disposable = newsRepository.getNews()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(news -> {
                    List<Article> newsList = new ArrayList<>(news.getArticles());
                    newsLiveData.setValue(newsList);
                }, throwable -> Log.e("NewsViewModel", "Error fetching news: " + throwable.getMessage()));

        compositeDisposable.add(disposable);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
    }
}