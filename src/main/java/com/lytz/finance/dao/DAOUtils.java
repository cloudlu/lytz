package com.lytz.finance.dao;

import lombok.extern.log4j.Log4j2;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@Log4j2
public class DAOUtils {

    public static void reBuildIndex() {
        if(LOG.isTraceEnabled()){
            LOG.trace("start to rebuild index");
        }
        
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-resources.xml");
        
        SessionFactory sessionFactory = (SessionFactory) context.getBean("sessionFactory");
        
        Session sess = sessionFactory.openSession();
        FullTextSession fullTextSession = Search.getFullTextSession(sess);
        try {
            fullTextSession.createIndexer().startAndWait();
        } catch (InterruptedException e) {
             LOG.error(e.getMessage(), e);
        } finally {
            fullTextSession.close();
        }
        ((AbstractApplicationContext)context).close();
        
        if(LOG.isTraceEnabled()){
            LOG.trace("end rebuild index");
        }
    }
}
