/**
 * 
 */
package com.lytz.finance.dao.impl;

import java.util.List;

import lombok.extern.log4j.Log4j2;

import org.apache.commons.lang3.EnumUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.lytz.finance.common.query.UserQuery;
import com.lytz.finance.dao.UserDAO;
import com.lytz.finance.vo.RoleNameEnum;
import com.lytz.finance.vo.User;

/**
 * @author cloudlu
 *
 */
@Log4j2
@Repository("userDAO")
public class UserDAOImpl extends BaseDAOImpl<User, Integer> implements
UserDAO {

	public User getUserByName(String name) {
		@SuppressWarnings("unchecked")
		List<User> list = getSession().getNamedQuery("findUserByName").setString("username", name).list();
		
		if(list.isEmpty())
			return null;
		return list.get(0);
	}

	public int getTotalCount(UserQuery query) {
	    if(null == query){
            throw new IllegalArgumentException("query should not be null");
        }
	    if(log.isDebugEnabled()){
            log.debug("create criteria with query: " + query);
        }
        Criteria c =  createCriteria(query);
        return ((Long)c.setProjection(Projections.rowCount()).uniqueResult()).intValue();
	}

    private Criteria createCriteria(UserQuery query) {
        if(log.isDebugEnabled()){
            log.debug("create criteria with query: " + query);
        }
        Criteria c = getSession().createCriteria(User.class);
	    if(EnumUtils.isValidEnum(RoleNameEnum.class, query.getRolename())){
	        c.createAlias("roles", "role");
	        c.add(Restrictions.eq("role.name", query.getRolename()));
	    }
        return c;
    } 

	/**
	 * @param Query should not be null
	 * @return Result list (UserInfo), list is empty if no result found
	 */
	@SuppressWarnings("unchecked")
    public List<User> findUserByQuery(UserQuery query) {
	    if(null == query){
            throw new IllegalArgumentException("query should not be null");
        }
	    Criteria c = createCriteria(query);
	    if (query.getStartRow() != null){
            c.setFirstResult(query.getStartRow());
        }
        if (query.getQuerySize() != null){
            c.setMaxResults(query.getQuerySize());
        }
	    return c.list();
	}

}
