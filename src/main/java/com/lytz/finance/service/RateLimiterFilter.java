/**
 * 
 */
package com.lytz.finance.service;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import com.google.common.util.concurrent.RateLimiter;

/**
 * @author cloudlu
 *
 */
public class RateLimiterFilter implements Filter {

    private static Logger logger = LoggerFactory.getLogger(RateLimiterFilter.class);

    @Value("${ratelimit.maxRate}")
    private double maxRate = 100;

    private RateLimiter limiter = null;

    
    public void init(FilterConfig config) throws ServletException {
        limiter = RateLimiter.create(maxRate);
    }

    public void destroy() {
    }

    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        if(limiter.tryAcquire()) {
             if(logger.isTraceEnabled()){
                 logger.trace("get access");
             }
             chain.doFilter(request, response);
        } else {
             if(logger.isWarnEnabled()){
                 //TODO: add metrics count/timer
                 logger.warn("system limitation reached!");
             }
             req.getRequestDispatcher("/WEB-INF/jsp/error/429.jsp").forward(req,res);
        }
    }
}
