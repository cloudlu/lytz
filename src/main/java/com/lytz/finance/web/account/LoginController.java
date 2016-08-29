package com.lytz.finance.web.account;

import lombok.extern.log4j.Log4j2;

import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.lytz.finance.service.NewsService;

@Log4j2
@Controller
@RequestMapping(value = "/login")
public class LoginController {
    
    @Autowired
    @Qualifier("newsService")
    private NewsService newsService;

	@RequestMapping(method = RequestMethod.GET)
	public String login(Model model) {
	    if(LOG.isDebugEnabled()){
	        LOG.debug("try to add news to request, its size: " + newsService.getNews().size());
	    }
		return "login";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String fail(@RequestParam(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM) String userName, Model model) {
	    model.addAttribute(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM, userName);
		return "login";
	}

}