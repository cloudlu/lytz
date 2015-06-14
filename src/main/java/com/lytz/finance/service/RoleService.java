/**
 * 
 */
package com.lytz.finance.service;

import com.lytz.finance.vo.Role;

/**
 * @author cloud lu
 *
 */
public interface RoleService extends BaseService<Role, Integer> {
	
	public Role getRoleByName(String name);
}
