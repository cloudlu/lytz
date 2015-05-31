/**
 * 
 */
package com.lytz.finance.security;

import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * @author cloudlu
 *
 */
public class CaptchaUsernamePasswordToken extends UsernamePasswordToken {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7943299550756757756L;
	
	private String captcha;  
	  
    public CaptchaUsernamePasswordToken(String username, char[] password,  
            boolean rememberMe, String host, String captcha) {  
        super(username, password, rememberMe, host);  
        this.captcha = captcha;  
    }  
  
    public String getCaptcha() {  
        return captcha;  
    }  
  
    public void setCaptcha(String captcha) {  
        this.captcha = captcha;  
    }  
}
