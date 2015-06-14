/**
 * 
 */
package com.lytz.finance.service;

import java.util.List;

import com.lytz.finance.vo.News;

/**
 * @author cloudlu
 *
 */
public interface NewsService {

    List<News> getNews(); 
    
    void updateNews();
}
