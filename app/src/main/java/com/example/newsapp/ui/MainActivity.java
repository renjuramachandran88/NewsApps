package com.example.newsapp.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.newsapp.NewsApp;
import com.example.newsapp.R;
import com.example.newsapp.di.ApplicationComponent;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ApplicationComponent applicationComponent;
    @Inject
    NewsViewModel newsViewModel;
    private NewsAdapter newsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((NewsApp) getApplication()).getAppComponent().inject(this);

        recyclerView = findViewById(R.id.recyclerView);
        SearchView searchView = findViewById(R.id.search_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        newsAdapter = new NewsAdapter(this);
        recyclerView.setAdapter(newsAdapter);
        NewsApp application = (NewsApp) getApplication();
        applicationComponent = application.getAppComponent();
        newsViewModel = new NewsViewModel();
        applicationComponent.inject(newsViewModel);
        newsViewModel.fetchNews();
        newsViewModel.getNews().observe(this, newsList -> {
            newsAdapter.setNewsList(newsList);
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Filter the news list based on the query and update the adapter
                newsAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Filter the news list based on the changing query and update the adapter
                newsAdapter.getFilter().filter(newText);
                return false;
            }
        });
    }
}