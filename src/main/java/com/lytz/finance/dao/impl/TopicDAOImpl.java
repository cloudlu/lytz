/**
 * 
 */
package com.lytz.finance.dao.impl;

import java.util.List;

import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.search.FullTextQuery;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.hibernate.search.query.dsl.MustJunction;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.lytz.finance.common.TopicQuery;
import com.lytz.finance.dao.TopicDAO;
import com.lytz.finance.vo.Topic;
import com.lytz.finance.vo.TopicStatus;

/**
 * @author cloudlu
 *
 */
@Repository("topicDAO")
public class TopicDAOImpl extends BaseDAOImpl<Topic, Integer> implements TopicDAO{

    private static Logger LOG = LoggerFactory.getLogger(TopicDAOImpl.class);
    
    @SuppressWarnings("unchecked")
    public List<Topic> findByQuery(TopicQuery query) {
        if(null == query){
            throw new IllegalArgumentException("query should not be null");
        }
        if(LOG.isDebugEnabled()){
            LOG.debug(query.toString());
        }
        if (StringUtils.isBlank(query.getKeyword())) {
            Criteria search = createCriteria(query);
            if (query.getStartRow() != null) {
                search.setFirstResult(query.getStartRow());
            }
            if (query.getQuerySize() != null) {
                search.setMaxResults(query.getQuerySize());
            }
            return search.list();
        } else {
            FullTextQuery hibernateQuery = findByKeywordQuery(query);
            if (query.getStartRow() != null) {
                hibernateQuery.setFirstResult(query.getStartRow());
            }
            if (query.getQuerySize() != null) {
                hibernateQuery.setMaxResults(query.getQuerySize());
            }
            return hibernateQuery.list();
        }
    }

    private FullTextQuery findByKeywordQuery(TopicQuery query) {
        FullTextSession fullTextSession = Search
                .getFullTextSession(getSession());

        QueryBuilder queryBuilder = fullTextSession.getSearchFactory()
                .buildQueryBuilder().forEntity(Topic.class).get();
        org.apache.lucene.search.Query luceneQuery = null;
        if (null == query.getStatus() && null == query.getUsername() && null == query.getExcludeStatus()) {
            luceneQuery = queryBuilder.keyword()// .wildcard()
                    .onFields("title", "content").matching(query.getKeyword())
                    // .matching("*" + query.getKeyword() + "*")
                    .createQuery();
            if(LOG.isDebugEnabled()){
                LOG.debug("create clean keyword search query: " + luceneQuery.toString());
            }
        } else {
           MustJunction term = queryBuilder.bool().must(queryBuilder.keyword()
                   // .wildcard()
                   .onFields("title", "content")
                   .matching(query.getKeyword()).createQuery());
           if(null != query.getStatus()){
               term.must(queryBuilder.keyword()
                            // .wildcard()
                            .onField("status")
                            .matching(query.getStatus()).createQuery());
           }
           if(null != query.getExcludeStatus()){
               term.must(queryBuilder.keyword()
                            // .wildcard()
                            .onField("status")
                            .matching(query.getExcludeStatus()).createQuery()).not();
           }
           if(null != query.getUsername()){
               term.must(queryBuilder.keyword()
                            // .wildcard()
                            .onField("owner.username")
                             .ignoreFieldBridge()
                            .matching(query.getUsername()).createQuery());
           }
           luceneQuery =term.createQuery();
           if(LOG.isDebugEnabled()){
               LOG.debug("create complicated keyword search query: " + luceneQuery.toString());
           }
        }
        // BooleanQuery
        FullTextQuery hibernateQuery = fullTextSession.createFullTextQuery(
                luceneQuery, Topic.class);
        return hibernateQuery;
    }

    public int getTotalCount(TopicQuery query) {
        if(null == query){
            throw new IllegalArgumentException("query should not be null");
        }
        if (StringUtils.isBlank(query.getKeyword())) {
            Criteria c = createCriteria(query);
            Long count = (Long) c.setProjection(Projections.rowCount())
                    .uniqueResult();
            if(null == count)
                return 0;
            return count.intValue();
        } else {
            FullTextQuery hibernateQuery = findByKeywordQuery(query);
            return hibernateQuery.getResultSize();
        }
    }

    private Criteria createCriteria(TopicQuery query) {
        Criteria search = getSession().createCriteria(Topic.class);
        if(query.getStatus() != null && EnumUtils.isValidEnum(TopicStatus.class, query.getStatus().name())){
            search.add(Restrictions.eq("status", query.getStatus()));
        }
        if(query.getExcludeStatus() != null && EnumUtils.isValidEnum(TopicStatus.class, query.getExcludeStatus().name())){
            search.add(Restrictions.not(Restrictions.eq("status", query.getExcludeStatus())));
        }
        
        if(query.getTitle() != null){
            search.add(Restrictions.eq("title", query.getTitle()));
        }
        if(query.getUsername() != null){
            search.createAlias("owner","user")
            .add( Restrictions.eq("user.username", query.getUsername()));
        }
        search.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        return search;
    } 
}
