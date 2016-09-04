/**
 * 
 */
package com.lytz.finance.service.impl;

import java.io.IOException;

import org.junit.Test;
import org.springframework.core.io.ClassPathResource;

import com.lytz.finance.utils.wordFilter.SensitiveWordFilter;
import com.lytz.finance.utils.wordFilter.SensitiveWordFilterImpl;

/**
 * @author cloudlu
 *
 */
public class SensitiveWordFilterTest {

    private SensitiveWordFilter filter;
    
    
    @Test public void test() throws IOException{
        filter = new SensitiveWordFilterImpl(new ClassPathResource("sensitivewords_encoded.txt").getFile());
        
    }
    
}
