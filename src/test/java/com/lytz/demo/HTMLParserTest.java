/**
 * 
 */
package com.lytz.demo;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


/**
 * @author cloudlu
 *
 */
public class HTMLParserTest {

    /**
     * @param args
     * @throws IOException 
     */
    public static void main(String[] args) throws IOException {
        Document doc = Jsoup.connect("http://kuaixun.eastmoney.com/").get();
        Element data = doc.getElementById("livenews-list");
        System.out.println(data);
        Elements newElement = data.getElementsByClass("media-comment");
        for(Element e : newElement){
       // System.out.println(e);
        }
    }

}
