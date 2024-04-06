package com.example.newsapp.data.model;

import java.util.List;
import java.util.Objects;

public class News {
    private List<Article> articles;

    public News() {}
    public News(List<Article> articles){
        this.articles = articles;
    }


    // Getter and setter
    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

    // Override toString(), hashCode(), and equals() methods
    @Override
    public String toString() {
        return "ArticleList{" +
                "articles=" + articles +
                '}';
    }

    @Override
    public int hashCode() {
        return articles != null ? articles.hashCode() : 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        News that = (News) o;

        return Objects.equals(articles, that.articles);
    }
}
