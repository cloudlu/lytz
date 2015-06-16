package com.lytz.finance.web.account;

import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.lytz.finance.service.NewsService;

@Controller
@RequestMapping(value = "/login")
public class LoginController {
    
    private static final Logger LOG = LoggerFactory.getLogger(LoginController.class);
    
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