/**
 * 
 */
package com.lytz.finance.web.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lytz.finance.common.Pager;

/**
 * @author cloudlu
 *
 */
@Controller
@RequestMapping("/service")
public class ServiceController {
    
    @RequestMapping(value="/list",method = RequestMethod.GET)
    public String list() {
        return "service/home";
    }
    
    public String submit(){
        return "service/home";
    }
    
    public String delete(){
        return "service/home";
    }
    
    public String modify(Model model){
        model.addAttribute("owner", "");
        return "service/home";
    }
}
