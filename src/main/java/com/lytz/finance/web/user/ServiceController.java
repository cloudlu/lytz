/**
 * 
 */
package com.lytz.finance.web.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author cloudlu
 *
 */
@Controller
public class ServiceController {
    
    private boolean hasNext;

    private boolean hasPrevious;
    
    private int currentPage;

    private long totalPage;
    
    private int pageSize;
    
    @RequestMapping(value="service/list",method = RequestMethod.GET)
    public String list() {
        return "service/home";
    }
    
    public String submit(){
        return "service/home";
    }
    
    public String delete(){
        return "service/home";
    }
    
    public String modify(){
        return "service/home";
    }
}
