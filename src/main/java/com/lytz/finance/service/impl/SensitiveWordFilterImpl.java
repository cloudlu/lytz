/**
 * 
 */
package com.lytz.finance.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.shiro.codec.Base64;
import org.springframework.stereotype.Service;

import com.lytz.finance.common.MatchType;
import com.lytz.finance.service.SensitiveWordFilter;

/**
 * @author cloudlu
 *
 */
@SuppressWarnings({"unchecked", "rawtypes"})
@Service("sensitiveWordFilter")
public class SensitiveWordFilterImpl implements SensitiveWordFilter {

    private static final String IS_END = "isEnd";

    // 字符编码
    private static final String ENCODING = "UTF-8";

    private static final String DEFAULT_REPLACED_CHAR = "*";

    private Map<String, String> sensitiveWordMap = new HashMap<String, String>();

    /**
     * 初始化敏感字库
     * 
     * @return
     * @throws IOException 
     */
    public SensitiveWordFilterImpl(File sensitiveWordFile) throws IOException {
        // 读取敏感词库
        Set<String> wordSet = readSensitiveWordFile(sensitiveWordFile);
        // 将敏感词库加入到HashMap中
        addSensitiveWordToHashMap(wordSet);
    }

    private Set<String> readSensitiveWordFile(File sensitiveWordFile) throws IOException {
       /* try(Scanner read = new Scanner(sensitiveWordFile, ENCODING)){
            read.
            Set<String> wordSet = new HashSet<String>();
            // 读取文件，将文件内容放入到set中
            while (read.hasNextLine()) {
                wordSet.add(read.nextLine());
            }
            return wordSet;
        }*/
       String decodedSensitiveWords = Base64.decodeToString(FileUtils.readFileToString(sensitiveWordFile));
       Set<String> wordSet = new HashSet<String>();
       wordSet.addAll(Arrays.asList(decodedSensitiveWords.split("\\r?\\n")));
       return wordSet;
    }

    
    private void addSensitiveWordToHashMap(Set<String> wordSet) {
        for (String word : wordSet) {
            Map nowMap = sensitiveWordMap;
            for (int i = 0; i < word.length(); i++) {
                char keyChar = word.charAt(i);
                Object tempMap = nowMap.get(keyChar);
                if (tempMap != null) {
                    nowMap = (Map) tempMap;
                } else {
                    Map<String, String> newMap = new HashMap<String, String>();
                    newMap.put(IS_END, "0");
                    nowMap.put(keyChar, newMap);
                    nowMap = newMap;
                }
                // 最后一个
                if (i == word.length() - 1) {
                    nowMap.put(IS_END, "1");
                }
            }
        }
    }
    
    public boolean containsSensitiveWord(String txt, MatchType type){
        boolean flag = false;
        for(int i = 0 ; i < txt.length() ; i++){
            int matchFlag = this.checkSensitiveWord(txt, i, type);
            if(matchFlag > 0){
                flag = true;
            }
        }
        return flag;
    }

    public Set<String> getSensitiveWord(String txt, MatchType type){
        Set<String> sensitiveWordList = new HashSet<String>();
        for(int i = 0 ; i < txt.length() ; i++){
            int length = checkSensitiveWord(txt, i, type);
            if(length > 0){
                sensitiveWordList.add(txt.substring(i, i+length));
                i = i + length - 1;
            }
        }
        
        return sensitiveWordList;
    }
    
    public String replaceSensitiveWord(String txt,MatchType matchType){
        return replaceSensitiveWord(txt, matchType, DEFAULT_REPLACED_CHAR);
    }
    
    public String replaceSensitiveWord(String txt,MatchType matchType,String replaceChar){
        String resultTxt = txt;
        Set<String> set = getSensitiveWord(txt, matchType);
        Iterator<String> iterator = set.iterator();
        String word = null;
        String replaceString = null;
        while (iterator.hasNext()) {
            word = iterator.next();
            replaceString = getReplaceChars(replaceChar, word.length());
            resultTxt = resultTxt.replaceAll(word, replaceString);
        }
        
        return resultTxt;
    }

    private String getReplaceChars(String replaceChar,int length){
        String resultReplace = replaceChar;
        for(int i = 1 ; i < length ; i++){
            resultReplace += replaceChar;
        }
        
        return resultReplace;
    }
    
    public int checkSensitiveWord(String txt,int beginIndex,MatchType matchType){
        boolean  flag = false;
        int matchFlag = 0;
        char word = 0;
        Map nowMap = sensitiveWordMap;
        for(int i = beginIndex; i < txt.length() ; i++){
            word = txt.charAt(i);
            nowMap = (Map) nowMap.get(word);
            if(nowMap != null){
                matchFlag++;
                if("1".equals(nowMap.get(IS_END))){
                    flag = true;
                    if(MatchType.MIN.equals(matchType)){
                        break;
                    }
                }
            } else {
                break;
            }
        }
        if(matchFlag < 2 || !flag){
            matchFlag = 0;
        }
        return matchFlag;
    }

}
