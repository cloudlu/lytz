/**
 * 
 */
package com.lytz.finance.dao;

import com.lytz.finance.vo.Role;

/**
 * @author cloudlu
 *
 */
public interface RoleDAO extends BaseDAO<Role, Integer> {

	
	public Role getRoleByName(String name);
}
