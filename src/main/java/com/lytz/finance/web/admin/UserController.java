/**
 * 
 */
package com.lytz.finance.web.admin;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.lytz.finance.common.Constants;
import com.lytz.finance.common.Pager;
import com.lytz.finance.common.UserQuery;
import com.lytz.finance.service.UserService;
import com.lytz.finance.vo.User;

/**
 * @author cloudlu
 *
 */
@Controller
@RequestMapping("/admin/user")
@SessionAttributes({"userQuery","userPager"})
public class UserController {
    
    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

    @Value("${pager.size}")
    private int pageSize;
    
    private UserService userManager = null;

    @Autowired
    public void setUserService(UserService userManager) {
        this.userManager = userManager;
    }
    
    @ModelAttribute(value="userSearchQuery")
    public UserQuery createQuery(){
        UserQuery query = new UserQuery();
        query.setQuerySize(pageSize);
        return query;
    }
    
    @RequestMapping(method = RequestMethod.GET)
    public String search(@ModelAttribute(value="userSearchQuery") UserQuery query, Model model) throws Exception {
        if(LOG.isTraceEnabled()){
            LOG.trace("entering 'search' method...with query: " + query);
        }
        Pager pager = new Pager(userManager.getTotalCount(query), query.getQuerySize());
        query.setStartRow(pager.getStartRow());
        model.addAttribute(Constants.USER_LIST, userManager.findByQuery(query));
        model.addAttribute("userPager", pager);
        UserQuery searchQuery = new UserQuery(query);
        model.addAttribute("userQuery", searchQuery);
        if(LOG.isTraceEnabled()){
            LOG.trace("finish 'search' method...with query: " + query + " pager: " + pager);
        }
        return "admin/user/adminUserList";
    }

    @RequestMapping(value="list", method = RequestMethod.GET)
    public String list(@ModelAttribute(value="userQuery") UserQuery query, @ModelAttribute(value="userPager") Pager pager, @RequestParam(value="pageNum", required=false) int pageNum, Model model) throws Exception {
        if(LOG.isTraceEnabled()){
            LOG.trace("entering 'list' method...with query: " + query + " pager: " + pager);
        }
        pager.setCurrentPage(pageNum);
        query.setStartRow(pager.getStartRow());
        model.addAttribute(Constants.USER_LIST, userManager.findByQuery(query));
        model.addAttribute("userPager", pager);
        if(LOG.isTraceEnabled()){
            LOG.trace("finish 'list' method...with query: " + query + " pager: " + pager);
        }
        return "admin/user/adminUserList";
    }
    
    @RequestMapping(value = "update/{id}", method = RequestMethod.GET)
    public String updateForm(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("user", userManager.findById(id));
        return "admin/user/adminUserForm";
    }
      
    @RequestMapping(value = "update", method = RequestMethod.POST)
    public String update(@Valid @ModelAttribute("user") User user, RedirectAttributes redirectAttributes) {
        userManager.save(user);
        redirectAttributes.addFlashAttribute("message", "更新用户" + user.getUsername() + "成功");
        return "redirect:/admin/user";
    }

    @RequestMapping(value = "delete/{id}")
    public String delete(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        User user = userManager.findById(id);
        userManager.remove(id);
        redirectAttributes.addFlashAttribute("message", "删除用户" + user.getUsername() + "成功");
        return "redirect:/admin/user";
    }

}
