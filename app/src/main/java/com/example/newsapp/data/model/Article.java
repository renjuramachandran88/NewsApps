package com.example.newsapp.data.model;

import java.util.Objects;

public class Article {
    private String publishedAt;
    private String title;
    private String url;
    private String urlToImage;

    public Article(String publishedAt, String title, String url, String urlToImage) {
        this.publishedAt = publishedAt;
        this.title = title;
        this.urlToImage = urlToImage;
        this.url = url;
    }

    public Article() {

    }


    // Getters and setters
    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }

    // Override toString(), hashCode(), and equals() methods
    @Override
    public String toString() {
        return "Article{" +
                "publishedAt='" + publishedAt + '\'' +
                ", title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", urlToImage='" + urlToImage + '\'' +
                '}';
    }

    @Override
    public int hashCode() {
        int result = publishedAt != null ? publishedAt.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (url != null ? url.hashCode() : 0);
        result = 31 * result + (urlToImage != null ? urlToImage.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Article article = (Article) o;

        if (!Objects.equals(publishedAt, article.publishedAt))
            return false;
        if (!Objects.equals(title, article.title)) return false;
        if (!Objects.equals(url, article.url)) return false;
        return Objects.equals(urlToImage, article.urlToImage);
    }
}
