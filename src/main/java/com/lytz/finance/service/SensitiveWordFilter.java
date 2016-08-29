/**
 * 
 */
package com.lytz.finance.service;

import java.util.Set;

import com.lytz.finance.common.MatchType;

public interface SensitiveWordFilter {

    public boolean containsSensitiveWord(String txt, MatchType type);

    public Set<String> getSensitiveWord(String txt, MatchType type);
    
    public String replaceSensitiveWord(String txt,MatchType matchType);
    
    public String replaceSensitiveWord(String txt,MatchType matchType,String replaceChar);
}