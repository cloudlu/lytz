package com.lytz.finance.dao;

import java.util.List;

import com.lytz.finance.common.UserQuery;
import com.lytz.finance.vo.User;

public interface UserDAO extends BaseDAO<User, Integer> {
	
	/**
	 * 
	 * @param name
	 * @return null if name is not exists, else return userInfo
	 */
	public User getUserByName(String name);
	
	/**
     * 
     * @param query
     * @return
     */
    public List<User> getTotalCount(UserQuery query);
	
	/**
	 * 
	 * @param query
	 * @return
	 */
	public List<User> findUserByQuery(UserQuery query);
}
