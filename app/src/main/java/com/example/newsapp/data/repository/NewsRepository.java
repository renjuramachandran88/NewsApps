package com.example.newsapp.data.repository;

import com.example.newsapp.data.model.News;

import io.reactivex.Single;


public interface NewsRepository {

    public Single<News> getNews();
}
