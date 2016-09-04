/**
 * 
 */
package com.lytz.demo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import com.lytz.finance.vo.News;


/**
 * @author cloudlu
 *
 */
public class ITHTMLParser {

    /**
     * @param args
     * @throws IOException 
     */
    @Test public void readData() throws IOException {
        Document doc = Jsoup.connect("http://kuaixun.eastmoney.com/").get();
        Element data = doc.getElementById("livenews-list");
        System.out.println(data);
        List<News> list = new ArrayList<News>();
        Elements newElement = data.getElementsByClass("media-title-box");
        System.out.println(newElement.size());
        for(Element e : newElement){
           Elements contents = e.getElementsByClass("media-title");
           System.out.println(contents.size());
           if(contents.isEmpty()){
               continue;
           }
           Element content = contents.get(0);
           System.out.println(content);
           News message = new News();
           if(content.hasAttr("href")){
               message.setLink(content.attr("href"));
              
           }
           message.setTitle(content.text());
           list.add(message);
        }
        newElement = data.getElementsByClass("time");
        System.out.println(newElement.size());
        int i = 0;
        for(Element e : newElement){
            list.get(i).setTime(e.text());
            i++;
        }
        for(News message : list){
            System.out.println(message.getTime() + "------" + message.getLink() + "----" + message.getTitle());
        }
    }

}
