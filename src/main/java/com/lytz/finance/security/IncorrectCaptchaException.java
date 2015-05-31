/**
 * 
 */
package com.lytz.finance.security;

import org.apache.shiro.authc.AuthenticationException;

/**
 * @author cloudlu
 *
 */
public class IncorrectCaptchaException extends AuthenticationException {

    /**
     * 
     */
    private static final long serialVersionUID = -9140509025113457559L;

    public IncorrectCaptchaException(){
        super();
    }
    
    public IncorrectCaptchaException(String message, Throwable cause){
        super(message, cause);
    }
    
    public IncorrectCaptchaException(String message){
        super(message);
    }
    
    public IncorrectCaptchaException(Throwable cause){
        super(cause);
    }
}
