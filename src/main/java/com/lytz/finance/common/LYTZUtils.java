/**
 * 
 */
package com.lytz.finance.common;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * @author cloudlu
 *
 */
public class LYTZUtils {
    
    private static ThreadLocal<SimpleDateFormat> dateFormat = new ThreadLocal<SimpleDateFormat>(){
        @Override protected SimpleDateFormat initialValue(){
            return new SimpleDateFormat("yyyy-MM-SS hh:mm:ss.SSS");
        }
    };
    
    public static SimpleDateFormat getSafeDateFormat(){
        return dateFormat.get();
    }
    
    public static List<String> getFilePathFromContent(String content){
        Document doc = Jsoup.parseBodyFragment(content);
        Elements elements = doc.getElementsByTag("img");
        List<String> list = new ArrayList<String>(elements.size());
        for(Element e : elements){
            list.add(e.attr("src"));
        }
        return list;
    }
    

}
