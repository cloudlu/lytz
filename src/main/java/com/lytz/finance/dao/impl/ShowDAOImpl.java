/**
 * 
 */
package com.lytz.finance.dao.impl;

import java.util.List;

import org.apache.commons.lang3.EnumUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.lytz.finance.common.ShowQuery;
import com.lytz.finance.dao.ShowDAO;
import com.lytz.finance.vo.Show;
import com.lytz.finance.vo.ShowStatus;

/**
 * @author cloudlu
 *
 */
@Repository("showDAO")
public class ShowDAOImpl extends BaseDAOImpl<Show, Integer> implements ShowDAO {
    
    @SuppressWarnings("unchecked")
    public List<Show> findByQuery(ShowQuery query) {
        Criteria search = createCriteria(query);
        if (query.getStartRow() != null){
            search.setFirstResult(query.getStartRow());
        }
        if (query.getQuerySize() != null){
            search.setMaxResults(query.getQuerySize());
        }
        return search.list();
    }

    public int getTotalCount(ShowQuery query) {
        Criteria c = createCriteria(query);
        return ((Long)c.setProjection(Projections.rowCount()).uniqueResult()).intValue();
    }

    private Criteria createCriteria(ShowQuery query) {
        Criteria search = getSession().createCriteria(Show.class);
        if(query.getTitle() != null){
            search.add(Restrictions.eq("title", query.getTitle()));
        }
        if(query.getStatus() != null && EnumUtils.isValidEnum(ShowStatus.class, query.getStatus().name())){
            search.add(Restrictions.eq("status", query.getStatus()));
        }
        return search;
    }
}
