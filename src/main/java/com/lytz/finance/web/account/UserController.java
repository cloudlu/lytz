/**
 * 
 */
package com.lytz.finance.web.account;

import javax.validation.Valid;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
@SessionAttributes({"userQuery","userPager"})
public class UserController {
    
    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);
    private static final Logger AUDIT = LoggerFactory.getLogger("AUDITLOG");

    @Value("${pager.size}")
    private int pageSize;
    
    private UserService userManager = null;

    @Autowired
    public void setUserService(UserService userManager) {
        this.userManager = userManager;
    }
    
    private User getCurrentUser(){
        Subject currentUser = SecurityUtils.getSubject();
        return userManager.getUserByName((String) currentUser.getPrincipal());
    }
    
    @RequestMapping(value = "/user/view", method = RequestMethod.GET)
    public String updateForm(Model model) {
        model.addAttribute("user", getCurrentUser());
        return "service/user/userForm";
    }
      
    @RequestMapping(value = "/user/update", method = RequestMethod.POST)
    public String update(@Valid @ModelAttribute("user") User user, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        User currentUser = getCurrentUser();
        if (bindingResult.hasErrors()) {
            model.addAttribute("message", "用户信息不正确");
            return "service/user/userForm";
        }
        if(user.getId() != currentUser.getId()){
            redirectAttributes.addFlashAttribute("message", "非法用户，只能更新自己的信息");
            return "redirect:/";
        }
        
        currentUser.setConfirmPassword(user.getConfirmPassword());
        currentUser.setEmail(user.getEmail());
        currentUser.setPassword(user.getPassword());
        currentUser.setPasswordHint(user.getPasswordHint());
        currentUser.setPhoneNumber(user.getPhoneNumber());
        currentUser.setRealname(user.getRealname());
        currentUser.setVersion(user.getVersion());
        userManager.save(currentUser);
        redirectAttributes.addFlashAttribute("message", "更新用户" + user.getUsername() + "成功");
        return "redirect:/";
    }
    
    @ModelAttribute(value="userSearchQuery")
    public UserQuery createQuery(){
        UserQuery query = new UserQuery();
        query.setQuerySize(pageSize);
        return query;
    }
    
    @RequestMapping(value="/admin/user", method = RequestMethod.GET)
    public String search(@ModelAttribute(value="userSearchQuery") UserQuery query, Model model) throws Exception {
        if(LOG.isTraceEnabled()){
            LOG.trace("entering 'search' method...with query: " + query);
        }
        if(query.getQuerySize() == null){
            query.setQuerySize(pageSize);
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
        return "service/admin/user/adminUserList";
    }

    @RequestMapping(value="/admin/user/list", method = RequestMethod.GET)
    public String list(@ModelAttribute(value="userQuery") UserQuery query, @ModelAttribute(value="userPager") Pager pager, @RequestParam(value="pageNum", required=false) int pageNum, Model model) throws Exception {
        if(LOG.isTraceEnabled()){
            LOG.trace("entering 'list' method...with query: " + query + " pager: " + pager);
        }
        pager.setCurrentPage(pageNum);
        query.setStartRow(pager.getStartRow());
        model.addAttribute(Constants.USER_LIST, userManager.findByQuery(query));
        //model.addAttribute("userPager", pager);
        if(LOG.isTraceEnabled()){
            LOG.trace("finish 'list' method...with query: " + query + " pager: " + pager);
        }
        return "service/admin/user/adminUserList";
    }
    
    @RequestMapping(value = "/admin/user/update/{id}", method = RequestMethod.GET)
    public String adminUpdateForm(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("user", userManager.findById(id));
        return "service/admin/user/adminUserForm";
    }
      
    @RequestMapping(value = "/admin/user/update", method = RequestMethod.POST)
    public String adminUpdate(@Valid @ModelAttribute("user") User user, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        if(AUDIT.isInfoEnabled()){
            AUDIT.info(getCurrentUser().getUsername() + "try to update user: " + user);
        }
        if (bindingResult.hasErrors()) {
            model.addAttribute("message", "用户信息不正确");
            return "service/user/userForm";
        }
        userManager.save(user);
        redirectAttributes.addFlashAttribute("message", "更新用户" + user.getUsername() + "成功");
        return "redirect:/admin/user";
    }

    @RequestMapping(value = "/admin/user/delete/{id}")
    public String delete(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        User user = userManager.findById(id);
        if(AUDIT.isInfoEnabled()){
            AUDIT.info(getCurrentUser().getUsername() + "try to delete user: " + user);
        }
        userManager.remove(id);
        redirectAttributes.addFlashAttribute("message", "删除用户" + user.getUsername() + "成功");
        return "redirect:/admin/user";
    }

}
