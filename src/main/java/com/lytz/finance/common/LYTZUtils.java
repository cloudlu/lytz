/**
 * 
 */
package com.lytz.finance.common;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import lombok.extern.log4j.Log4j2;

import org.apache.commons.codec.binary.Base64;
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
@Log4j2
public class LYTZUtils {

    private static ThreadLocal<SimpleDateFormat> dateFormat = null;
    
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
    
    public static final Map<String, String> STATUS_MAP = new LinkedHashMap<String,String>();
    public static final Map<String, String> TOPIC_STATUS_MAP = new LinkedHashMap<String,String>();
    //public static Map<String, String> messageStatusMap = new LinkedHashMap<String,String>();
    /**
     * called in spring
     */
    public static void init() {
        dateFormat = new ThreadLocal<SimpleDateFormat>(){
            @Override protected SimpleDateFormat initialValue(){
                return new SimpleDateFormat("yyyy-MM-SS hh:mm:ss.SSS");
            }
        };
        STATUS_MAP.put("","全部");
        STATUS_MAP.put(Status.DRAFT.toString(),"草稿");
        STATUS_MAP.put(Status.COMPLETED.toString(),"已发布");
        
        TOPIC_STATUS_MAP.put("","全部");
        TOPIC_STATUS_MAP.put(TopicStatus.DRAFT.toString(),"草稿");
        TOPIC_STATUS_MAP.put(TopicStatus.SUBMITTED.toString(),"已发布");
        TOPIC_STATUS_MAP.put(TopicStatus.PROCESSING.toString(),"正在处理");
        TOPIC_STATUS_MAP.put(TopicStatus.ACCPECT.toString(),"已接受");
        TOPIC_STATUS_MAP.put(TopicStatus.DENY.toString(),"已拒绝");
    }
    
    public static String encodeBase64 (String original) {
        byte[] bytesEncoded = Base64.encodeBase64(original .getBytes());
        String encoded = new String(bytesEncoded);
        if(LOG.isDebugEnabled()){
            LOG.debug("encoded value is " + encoded);
        }
        return encoded;
    }
    
    public static String decodeBase64 (String encoded) {
        byte[] valueDecoded = Base64.decodeBase64(encoded .getBytes());
        String decoded = new String(valueDecoded);
        if(LOG.isDebugEnabled()){
            LOG.debug("decoded value is " + decoded);
        }
        return decoded;
    }
}
