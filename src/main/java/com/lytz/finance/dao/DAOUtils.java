package com.lytz.finance.dao;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DAOUtils {

    private final static Logger LOG = LoggerFactory.getLogger(DAOUtils.class);
    
    @Resource
    private SessionFactory sessionFactory;

    public void reBuildIndex() {
        if(LOG.isTraceEnabled()){
            LOG.trace("start to rebuild index");
        }
        Session sess = sessionFactory.openSession();
        FullTextSession fullTextSession = Search.getFullTextSession(sess);
        try {
            fullTextSession.createIndexer().startAndWait();
        } catch (InterruptedException e) {
             LOG.error(e.getMessage(), e);
        } finally {
            fullTextSession.close();
        }
        if(LOG.isTraceEnabled()){
            LOG.trace("end rebuild index");
        }
    }
}
