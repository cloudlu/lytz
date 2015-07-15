/**
 * 
 */
package com.lytz.finance.common;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.lytz.finance.vo.Status;
import com.lytz.finance.vo.TopicStatus;

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
    
    private static Map<String, String> statusMap = new LinkedHashMap<String,String>();
    private static Map<String, String> topicStatusMap = new LinkedHashMap<String,String>();
    //private static Map<String, String> messageStatusMap = new LinkedHashMap<String,String>();
    
    static {
        statusMap.put("","全部");
        statusMap.put(Status.DRAFT.name(),"草稿");
        statusMap.put(Status.COMPLETED.name(),"已发布");
        
        topicStatusMap.put("","全部");
        topicStatusMap.put(TopicStatus.DRAFT.name(),"草稿");
        topicStatusMap.put(TopicStatus.SUBMITTED.name(),"已发布");
        topicStatusMap.put(TopicStatus.PROCESSING.name(),"正在处理");
        topicStatusMap.put(TopicStatus.ACCPECT.name(),"已接受");
        topicStatusMap.put(TopicStatus.DENY.name(),"已拒绝");
    }
    
    public static Map<String, String> getStatusMap(){
        return statusMap;
    }
    
    public static Map<String, String> getTopicStatusMap(){
        return topicStatusMap;
    }
}
