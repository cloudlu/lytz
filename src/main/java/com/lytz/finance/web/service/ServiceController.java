/**
 * 
 */
package com.lytz.finance.web.service;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author cloudlu
 *
 */
@Controller
public class ServiceController {
    
    @RequestMapping(value="service/list",method = RequestMethod.GET)
    public String list() {
        return "service/home";
    }
}
