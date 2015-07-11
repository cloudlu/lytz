package com.lytz.finance.service.impl;


import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.credential.PasswordService;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.lytz.finance.common.UserQuery;
import com.lytz.finance.dao.UserDAO;
import com.lytz.finance.service.RoleService;
import com.lytz.finance.service.UserService;
import com.lytz.finance.service.exception.UserExistsException;
import com.lytz.finance.service.exception.UserNotExistsException;
import com.lytz.finance.vo.Role;
import com.lytz.finance.vo.RoleNameEnum;
import com.lytz.finance.vo.User;

@Service("userService")
public class UserServiceImpl extends BaseServiceImpl<User, Integer> implements
		UserService {

	private static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);
	
	private RoleService roleService;
	
	@Autowired
    @Qualifier("roleService")
	public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }
	
	private UserDAO userDAO;

    @Autowired
    @Qualifier("userDAO")
    public void setUserDAO(UserDAO userDAO) {
        this.dao = userDAO;
        this.userDAO = userDAO;
    }
    
    private PasswordService passwordService;
    
    @Autowired
    @Qualifier("passwordService")
	public void setPasswordService(PasswordService passwordService) {
		this.passwordService = passwordService;
	}
	/*
	 * protected IUserInfoDAO getUserInfoDAO() { if (userInfoDAO == null) {
	 * userInfoDAO = DAOFactory.getDAOFacotry().getIUserInfoDAO(); } return
	 * userInfoDAO; }
	 */

	

	public User getUserByName(String name) {
		return userDAO.getUserByName(name);
	}

	/*@Override
	@RolesAllowed({"ROLE_ADMIN","ROLE_USER"})
	public UserInfo getUserInfoByUserId(String userId) {
		return userInfoDAO.getUserInfoByUserId(userId);
	}*/

	//@Transactional
	public User save(User user) {
	    if(null == user.getId()){
	        //new user
	        return super.save(user);
	    }
	    //update
	    Subject userSubject = SecurityUtils.getSubject();
	    User currentUser = getUserByName((String) userSubject.getPrincipal());
	    if(!userSubject.hasRole(RoleNameEnum.ROLE_ADMIN.name())){
	        if(user.getId() != currentUser.getId()){
	            throw new IllegalArgumentException("非法用户，只能更新自己的信息");
	        }
	        currentUser.setConfirmPassword(user.getConfirmPassword());
            currentUser.setEmail(user.getEmail());
            currentUser.setPassword(user.getPassword());
            currentUser.setPasswordHint(user.getPasswordHint());
            currentUser.setPhoneNumber(user.getPhoneNumber());
            currentUser.setRealname(user.getRealname());
            currentUser.setVersion(user.getVersion());
            return  super.save(currentUser);
	    } else {
	        //admin can update any user
	        return super.save(user);
	    }
		
	}

	public boolean changeUserPassword(String username, String oldPassword,
			String newPassword) throws UserNotExistsException {
		
		User user = getUserByName(username);
		
		if(null == user){
			LOG.error(username + "not exists");
			throw new UserNotExistsException();
		}
		
		if(passwordService.passwordsMatch(oldPassword, user.getPassword())){
			user.setPassword(passwordService.encryptPassword(newPassword));
			return false;
		} else {
			LOG.warn("invalid password for user: " + username);
			return false;
		}	
	}

	public void registerUser(User user) throws UserExistsException {
		if(null != getUserByName(user.getUsername())){
			throw new UserExistsException(user.getUsername());
		}
		if(null != user.getPassword() && user.getPassword().equals(user.getConfirmPassword())){
    		user.setPassword(passwordService.encryptPassword(user.getPassword()));
    		user.setAccountExpired(false);
    		user.setAccountLocked(false);
    		user.setCredentialsExpired(false);
    		user.addRole(roleService.getRoleByName(RoleNameEnum.ROLE_USER.name()));
    		save(user);
		} else {
		    //TODO
		}
	}

	public Set<String> findRoles(String username) {
		User user = getUserByName(username);
		
		if(null == user){
			LOG.error(username + "not exists");
			return Collections.emptySet();
		}
		
		Set<String> roles = new HashSet<String>();
		for(Role role : user.getRoles()){
			roles.add(role.getName());
		}
		return roles;
	}

	public Set<String> findPermissions(String username) {
	    User user = getUserByName(username);
		
		if(null == user){
			LOG.error(username + "not exists");
			return Collections.emptySet();
		}
		
		Set<String> permissions = new HashSet<String>();
		/*for(Role role : user.getRoles()){
			//permissions.add(role.getPermissions());
		}*/
		
		return permissions;
	}

    public List<User> findByQuery(UserQuery query) {
        return userDAO.findUserByQuery(query);
    }
    
    public int getTotalCount(UserQuery query) {
        return userDAO.getTotalCount(query);
    }

}
