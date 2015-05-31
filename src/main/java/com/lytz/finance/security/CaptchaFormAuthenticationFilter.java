/**
 * 
 */
package com.lytz.finance.security;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author cloudlu
 *
 */
public class CaptchaFormAuthenticationFilter extends FormAuthenticationFilter {

	private static final Logger LOG = LoggerFactory.getLogger(CaptchaFormAuthenticationFilter.class);  
    
    public CaptchaFormAuthenticationFilter() {  
    }  
    
    
    private DefaultWebSessionManager sessionManager;
    
    @Autowired
	public void setSessionManager(DefaultWebSessionManager sessionManager) {
		this.sessionManager = sessionManager;
	} 
    
    @Override  
    /** 
     * 登录验证 
     */  
    protected boolean executeLogin(ServletRequest request,  
            ServletResponse response) throws Exception {  
        CaptchaUsernamePasswordToken token = createToken(request, response);
        try {
            if(null == token){
                throw new AuthenticationException("invalid request");
            }
            doCaptchaValidate((HttpServletRequest) request, token);  
            Subject subject = getSubject(request, response);  
            subject.login(token);
            if(LOG.isDebugEnabled()){
                LOG.info(token.getUsername()+"login success");  
            }
            return onLoginSuccess(token, subject, request, response);  
        }catch (AuthenticationException e) {  
            if(LOG.isWarnEnabled()){
                LOG.warn(token.getUsername()+"login failure--"+e);  
            }
            return onLoginFailure(token, e, request, response);  
        }  
    }  
  

    protected void doCaptchaValidate(HttpServletRequest request,  
            CaptchaUsernamePasswordToken token) {  
        String captcha = (String) request.getSession().getAttribute(  
                com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);   
        if (captcha != null && !captcha.equalsIgnoreCase(token.getCaptcha())) {  
            throw new IncorrectCaptchaException("invalid captcha code！");  
        }  
    }  
  
    @Override  
    protected CaptchaUsernamePasswordToken createToken(ServletRequest request,  
            ServletResponse response) {  
        String username = getUsername(request);  
        String password = getPassword(request);  
        String captcha = getCaptcha(request);  
        boolean rememberMe = isRememberMe(request);  
        String host = getHost(request);  
  
        return new CaptchaUsernamePasswordToken(username,  
                password.toCharArray(), rememberMe, host, captcha);  
    }  
  
    public static final String DEFAULT_CAPTCHA_PARAM = "captcha";  
  
    private String captchaParam = DEFAULT_CAPTCHA_PARAM;  
  
    public String getCaptchaParam() {  
        return captchaParam;  
    }  
  
    public void setCaptchaParam(String captchaParam) {  
        this.captchaParam = captchaParam;  
    }  
  
    protected String getCaptcha(ServletRequest request) {  
        return WebUtils.getCleanParam(request, getCaptchaParam());  
    }  
      
//保存异常对象到request  
    @Override  
    protected void setFailureAttribute(ServletRequest request,  
            AuthenticationException ae) {  
        request.setAttribute(getFailureKeyAttribute(), ae);  
    } 
}  
