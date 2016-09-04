/**
 * 
 */
package com.lytz.finance.utils.wordFilter;

import java.util.Set;

public interface SensitiveWordFilter {

    public boolean containsSensitiveWord(String txt);

    /**
     * get sensitive words from txt
     * @param txt
     * @param type
     * @return
     */
    public Set<String> getSensitiveWord(String txt, MatchType type);
    
    /**
     * replace with default char
     * @param txt
     * @param matchType
     * @return
     */
    public String replaceSensitiveWord(String txt,MatchType matchType);
    
    /**
     * replace with input char
     * @param txt
     * @param matchType
     * @param replaceChar
     * @return
     */
    public String replaceSensitiveWord(String txt,MatchType matchType,String replaceChar);
}