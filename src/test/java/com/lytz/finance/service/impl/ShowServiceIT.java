/**
 * 
 */
package com.lytz.finance.service.impl;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.lytz.finance.dao.ShowDAO;
import com.lytz.finance.vo.Show;

/**
 * @author cloudlu
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration(
        locations = {"classpath:spring-*-dev.xml"})
@Transactional  
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true) 
public class ShowServiceIT {

    @Autowired
    private ShowDAO showDAO;
    
    @Test
    public void displayShow(){
        Show show = showDAO.findById(7);
        Document doc = Jsoup.parseBodyFragment(show.getContent());
        Elements elements = doc.getElementsByTag("img");
        for(Element e : elements){
            if(e.attr("src").contains("resources")){
                System.out.println(e.attr("src"));
            }
        }
    }
}
