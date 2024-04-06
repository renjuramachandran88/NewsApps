package com.example.newsapp.data.repository;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;

import android.app.Application;
import android.content.res.AssetManager;

import com.example.newsapp.data.model.Article;
import com.example.newsapp.data.model.News;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;

import io.reactivex.observers.TestObserver;

@RunWith(MockitoJUnitRunner.class)
public class NewsRepositoryImplTest {

    @Mock
    Application application;

    @Mock
    AssetManager assetManager;

    @Mock
    InputStream inputStream;

    @InjectMocks
    NewsRepositoryImpl newsRepository;

    @Before
    public void setUp() throws IOException {
        when(application.getAssets()).thenReturn(assetManager);
        when(assetManager.open(anyString())).thenReturn(inputStream);
        when(inputStream.available()).thenReturn(1); // Adjust this size as needed
        when(inputStream.read(any(byte[].class))).thenReturn(0); // Mock reading from input stream

    }

    @Test
    public void testGetNews_Success() {
        // Create a mock JSON string and set up expectations for its parsing
        String mockJsonString = "{\"articles\":[{\"publishedAt\":\"2024-01-10T22:41:25Z\",\"title\":\"Test Article\",\"url\":\"http://example.com\",\"urlToImage\":\"http://example.com/image.jpg\"}]}";
        List<Article> expectedArticles = Collections.singletonList(new Article("2024-01-10T22:41:25Z", "Test Article", "http://example.com", "http://example.com/image.jpg"));

        try {
            when(inputStream.read(any(byte[].class))).thenReturn(mockJsonString.getBytes().length);

            TestObserver<News> testObserver = newsRepository.getNews().test();
            testObserver.awaitTerminalEvent();
            testObserver.assertNoErrors();
            testObserver.assertValueCount(1);

            News news = testObserver.values().get(0);
            assertEquals(expectedArticles, news.getArticles());
        } catch (IOException e) {
            fail("IOException should not be thrown during testing");
        }
    }

    @Test
    public void testGetNews_Error() {
        // Simulate IOException during loading JSON from asset
        try {
            when(assetManager.open(anyString())).thenThrow(new IOException("Mocked IOException"));
        } catch (IOException e) {
            fail("IOException should not be thrown during testing");
        }

        // Test getNews method
        TestObserver<News> testObserver = newsRepository.getNews().test();
        testObserver.awaitTerminalEvent();
        testObserver.assertError(IOException.class);
    }
}
