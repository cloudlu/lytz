/**
 * 
 */
package com.lytz.finance.dao.impl;

import java.util.List;

import org.apache.commons.lang3.EnumUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.lytz.finance.common.UserQuery;
import com.lytz.finance.dao.UserDAO;
import com.lytz.finance.vo.RoleNameEnum;
import com.lytz.finance.vo.User;

/**
 * @author cloudlu
 *
 */
@Repository("userDAO")
public class UserDAOImpl extends BaseDAOImpl<User, Integer> implements
UserDAO {

private static final Logger LOG = LoggerFactory.getLogger(UserDAOImpl.class);
	
	public User getUserByName(String name) {
		@SuppressWarnings("unchecked")
		List<User> list = getSession().getNamedQuery("findUserByName").setString("username", name).list();
		
		if(list.isEmpty())
			return null;
		return list.get(0);
	}

	public int getTotalCount(UserQuery query) {
	    Criteria c = createCriteria(query);
        return ((Long)c.setProjection(Projections.rowCount()).uniqueResult()).intValue();
	}

    private Criteria createCriteria(UserQuery query) {
        Criteria c = getSession().createCriteria(User.class);
	    if(EnumUtils.isValidEnum(RoleNameEnum.class, query.getRolename())){
	        c.createAlias("roles", "role");
	        c.add(Restrictions.eq( "role.name", query.getRolename()));
	    }
	    if (query.getStartRow() != 0){
            c.setFirstResult(query.getStartRow());
	    }
        if (query.getQuerySize() > 0){
            c.setMaxResults(query.getQuerySize());
        }
        return c;
    } 

	/**
	 * @param Query should not be null
	 * @return Result list (UserInfo), list is empty if no result found
	 */
	@SuppressWarnings("unchecked")
    public List<User> findUserByQuery(UserQuery query) {
	    Criteria c = createCriteria(query);
	    return c.list();
	}

}
