package com.example.newsapp.data.repository;

import android.app.Application;
import android.content.Context;
import android.content.res.AssetManager;

import com.example.newsapp.data.model.Article;
import com.example.newsapp.data.model.News;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;


public class NewsRepositoryImpl implements NewsRepository {
    private final Application application;

    @Inject
    public NewsRepositoryImpl(Application application) {
        this.application = application;
    }

    @Override
    public Single<News> getNews() {
        return Single.create(emitter -> {
            try {
                String jsonString = loadJSONFromAsset("news.json");
                List<Article> articles = parseJson(jsonString);
                News news = new News();
                news.setArticles(articles);
                emitter.onSuccess(news);
            } catch (IOException e) {
                emitter.onError(e);
            }
        });

    }

     String loadJSONFromAsset(String fileName) throws IOException {
        AssetManager assetManager = application.getAssets();
        InputStream inputStream = assetManager.open(fileName);
        int size = inputStream.available();
        byte[] buffer = new byte[size];
        inputStream.read(buffer);
        inputStream.close();
        return new String(buffer, StandardCharsets.UTF_8);
    }

    private List<Article> parseJson(String jsonString) throws JSONException {
        JSONObject jsonObject = new JSONObject(jsonString);
        JSONArray articlesJsonArray = jsonObject.getJSONArray("articles");
        List<Article> articles = new ArrayList<>();
        for (int i = 0; i < articlesJsonArray.length(); i++) {
            JSONObject articleJson = articlesJsonArray.getJSONObject(i);
            String imageUrl = articleJson.has("urlToImage") ? articleJson.getString("urlToImage") : "";

            Article article = new Article(
                    articleJson.getString("publishedAt"),
                    articleJson.getString("title"),
                    articleJson.getString("url"),
                    imageUrl
            );
            articles.add(article);
        }
        return articles;
    }


}
