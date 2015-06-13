package com.lytz.finance.web.account;

import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.lytz.finance.service.NewsService;

@Controller
@RequestMapping(value = "/login")
public class LoginController {
    
    @Autowired
    private NewsService newsService;

	@RequestMapping(method = RequestMethod.GET)
	public String login(Model model) {
	    model.addAttribute("news",newsService.getNews());
		return "index";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String fail(@RequestParam(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM) String userName, Model model) {
		model.addAttribute(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM, userName);
		model.addAttribute("news",newsService.getNews());
		return "index";
	}

}