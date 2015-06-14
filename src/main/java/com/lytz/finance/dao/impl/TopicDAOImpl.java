/**
 * 
 */
package com.lytz.finance.dao.impl;

import java.util.List;

import org.apache.commons.lang3.EnumUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.lytz.finance.common.TopicQuery;
import com.lytz.finance.dao.TopicDAO;
import com.lytz.finance.vo.Status;
import com.lytz.finance.vo.Topic;

/**
 * @author cloudlu
 *
 */
public class TopicDAOImpl extends BaseDAOImpl<Topic, Integer> implements TopicDAO{

    /*public Topic getTopicByTitle(String title) {
        // TODO Auto-generated method stub
        return null;
    }*/

    public List<Topic> findByQuery(TopicQuery query) {
        Criteria c = createCriteria(query);
        return c.list();
    }

    public int getTotalCount(TopicQuery query) {
        Criteria c = createCriteria(query);
        return ((Long)c.setProjection(Projections.rowCount()).uniqueResult()).intValue();
    }

    private Criteria createCriteria(TopicQuery query) {
        Criteria search = getSession().createCriteria(Topic.class);
        if(EnumUtils.isValidEnum(Status.class, query.getStatus())){
            search.add(Restrictions.eq("status", query.getStatus()));
        }
        if(query.getTitle() != null){
            search.add(Restrictions.eq("title", query.getTitle()));
        }
        if (query.getStartRow() != null){
            search.setFirstResult(query.getStartRow());
        }
        if (query.getQuerySize() != null){
            search.setMaxResults(query.getQuerySize());
        }
        return search;
    } 
}
