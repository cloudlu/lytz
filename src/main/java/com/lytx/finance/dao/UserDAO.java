package com.lytx.finance.dao;

import com.lytx.finance.vo.User;

public interface UserDAO extends BaseDAO<User, Integer> {
	
	/**
	 * 
	 * @param name
	 * @return null if name is not exists, else return userInfo
	 */
	public User getUserByName(String name);
	
}
