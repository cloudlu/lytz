/**
 * 
 */
package com.lytz.finance.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.lytz.finance.service.NewsService;
import com.lytz.finance.vo.News;

/**
 * @author cloudlu
 *
 */
@Service("newsService")
public class NewsServiceImpl implements NewsService {

    private static final Logger LOG = LoggerFactory.getLogger(NewsServiceImpl.class);
    
    private List<News> newsList;
    
    //TODO use spring scheduled task to get the first page of news
    // as div string 
    public void updateNews(){
        Document doc;
        try {
            doc = Jsoup.connect("http://kuaixun.eastmoney.com/").get();
            Element data = doc.getElementById("livenews-list");
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }
        
    }
    
    public List<News> getNews(){
        List<News> returnedList = new ArrayList<News>();
        synchronized(newsList){
            Collections.copy(returnedList, newsList);
        }
        return returnedList;
    }
    
    
}
