/**
 * 
 */
package com.lytz.finance.web.common;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.web.servlet.LocaleResolver;

/**
 * @author cloudlu
 *
 */
public class BaseController {

    private LocaleResolver localeResolver;
    
    /**
     * @param localResolver the localResolver to set
     */
    @Autowired
    @Qualifier(value="localeResolver")
    public void setLocaleResolver(LocaleResolver localeResolver) {
        this.localeResolver = localeResolver;
    }
    
    private MessageSource messageSource;
    
    /**
     * @param messageSource the messageSource to set
     */
    @Autowired
    @Qualifier(value="messageSource")
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }
    
    public String getText(String key, HttpServletRequest request, String... messages){
        Locale locale = localeResolver.resolveLocale(request);
        return messageSource.getMessage(key, messages, locale);
    }
}
