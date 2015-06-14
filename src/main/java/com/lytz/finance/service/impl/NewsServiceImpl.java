/**
 * 
 */
package com.lytz.finance.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
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

    private static final int NEWS_FETCH_TIMEOUT = 10000;

    private static final int DEFAULT_SIZE = 50;

    private static final Logger LOG = LoggerFactory.getLogger(NewsServiceImpl.class);
    
    private List<News> newsList = new ArrayList<News>(DEFAULT_SIZE);
    
    public void updateNews(){
        Document doc;
        try {
            doc = Jsoup.connect("http://kuaixun.eastmoney.com/").timeout(NEWS_FETCH_TIMEOUT).get();
            Element data = doc.getElementById("livenews-list");
            if(LOG.isDebugEnabled()){
                LOG.debug(data.html());
            }
            List<News> updatedList = new ArrayList<News>(DEFAULT_SIZE);
            Elements newElement = data.getElementsByClass("media-title-box");
            if(LOG.isDebugEnabled()){
                LOG.debug("fetch data size: " + newElement.size());
            }
            for(Element e : newElement){
               Elements contents = e.getElementsByClass("media-title");
               if(contents.isEmpty()){
                   continue;
               }
               Element content = contents.get(0);
               if(LOG.isDebugEnabled()){
                   LOG.debug(content.html());
               }
               News message = new News();
               if(content.hasAttr("href")){
                   message.setLink(content.attr("href"));
                  
               }
               message.setTitle(content.text());
               updatedList.add(message);
            }
            newElement = data.getElementsByClass("time");
            int i = 0;
            for(Element e : newElement){
                updatedList.get(i).setTime(e.text());
                i++;
            }
            if(LOG.isDebugEnabled()){
                for(News message : updatedList){
                    LOG.debug(message.getTime() + "------" + message.getLink() + "----" + message.getTitle());
                }
            }
            synchronized(newsList){
                newsList.clear();
                newsList.addAll(updatedList);
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        
    }
    
    public List<News> getNews(){
        List<News> returnedList = null;
        synchronized(newsList){
            returnedList = new ArrayList<News>(newsList.size());
            returnedList.addAll(newsList);
        }
        return returnedList;
    }
    
    
}
