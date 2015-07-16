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

import com.lytz.finance.common.MessageQuery;
import com.lytz.finance.dao.MessageDAO;
import com.lytz.finance.vo.Message;
import com.lytz.finance.vo.MessageStatus;

/**
 * @author cloud
 *
 */
@Repository("messageDAO")
public class MessageDAOImpl extends BaseDAOImpl<Message, Integer> implements
        MessageDAO {

    /* (non-Javadoc)
     * @see com.lytz.finance.dao.MessageDAO#findByQuery(com.lytz.finance.common.MessageQuery)
     */
    @SuppressWarnings("unchecked")
    public List<Message> findByQuery(MessageQuery query) {
        if(null == query){
            throw new IllegalArgumentException("query should not be null");
        }
        Criteria search = createCriteria(query);
        if (query.getStartRow() != null) {
            search.setFirstResult(query.getStartRow());
        }
        if (query.getQuerySize() != null) {
            search.setMaxResults(query.getQuerySize());
        }
        return search.list();
    }

    /* (non-Javadoc)
     * @see com.lytz.finance.dao.MessageDAO#getTotalCount(com.lytz.finance.common.MessageQuery)
     */
    public int getTotalCount(MessageQuery query) {
        if(null == query){
            throw new IllegalArgumentException("query should not be null");
        }
        Criteria c = createCriteria(query);
        Long count = (Long) c.setProjection(Projections.rowCount())
                .uniqueResult();
        if(null == count)
            return 0;
        return count.intValue();
    }

    private Criteria createCriteria(MessageQuery query) {
        Criteria search = getSession().createCriteria(Message.class);
        if (query.getStatus() != null
                && EnumUtils.isValidEnum(MessageStatus.class, query.getStatus()
                        .name())) {
            search.add(Restrictions.eq("status", query.getStatus()));
        }
        return search;
    }
}
