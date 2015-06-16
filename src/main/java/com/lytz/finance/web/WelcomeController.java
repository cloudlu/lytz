/**
 * 
 */
package com.lytz.finance.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lytz.finance.service.NewsService;
import com.lytz.finance.web.account.LoginController;

/**
 * @author cloudlu
 *
 */
@Controller
@RequestMapping(value = "/")
public class WelcomeController {

private static final Logger LOG = LoggerFactory.getLogger(LoginController.class);
    
    @Autowired
    @Qualifier("newsService")
    private NewsService newsService;

    @RequestMapping(method = RequestMethod.GET)
    public String welcome(Model model) {
        if(LOG.isDebugEnabled()){
            LOG.debug("try to add news to request, its size: " + newsService.getNews().size());
        }
        model.addAttribute("news",newsService.getNews());
        return "index";
    }
}
