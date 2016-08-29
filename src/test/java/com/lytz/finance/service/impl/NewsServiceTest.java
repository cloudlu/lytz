package com.lytz.finance.service.impl;

import org.junit.Test;

import com.lytz.finance.service.NewsService;

public class NewsServiceTest {

    private NewsService newsService;
    
    
    @Test public void test(){
        newsService = new NewsServiceImpl();
        newsService.updateNews();
        System.out.println(newsService.getNews().size());
    }
}
