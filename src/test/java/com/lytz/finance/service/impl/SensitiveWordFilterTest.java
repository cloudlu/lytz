/**
 * 
 */
package com.lytz.finance.service.impl;

import java.io.File;
import java.io.IOException;

import org.junit.Test;
import org.springframework.core.io.ClassPathResource;

import com.lytz.finance.service.NewsService;
import com.lytz.finance.service.SensitiveWordFilter;

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
