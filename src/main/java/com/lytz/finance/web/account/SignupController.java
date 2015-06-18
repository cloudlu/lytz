package com.lytz.finance.web.account;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.lytz.finance.service.UserService;
import com.lytz.finance.service.exception.UserExistsException;
import com.lytz.finance.vo.User;

@Controller
@RequestMapping(value = "/signup")
public class SignupController {

	@Autowired
	private UserService userService;

	/*@RequestMapping(method = RequestMethod.GET)
	public String registerForm() {
		return "";
	}*/

	@RequestMapping(method = RequestMethod.POST)
	public String register(@Valid User user, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
	    if (bindingResult.hasErrors()) {
            return "forward:/welcome";
        }
	    try {
			userService.registerUser(user);
			redirectAttributes.addFlashAttribute("username", user.getUsername());
			return "redirect:/";
		} catch (UserExistsException e) {
			return "redirect:/";
		}
		
		
	}

	/**
	 * Ajax请求校验loginName是否唯一。
	 */
	@RequestMapping(value = "checkLoginName")
	@ResponseBody
	public String checkLoginName(@RequestParam("username") String username) {
		if(null == userService.getUserByName(username)){
			return "true";
		} else {
			return "false";
		}
	}
}