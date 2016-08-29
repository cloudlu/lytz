package com.lytz.finance.dao;

import lombok.Cleanup;
import lombok.extern.log4j.Log4j2;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@Log4j2
public class DAOUtils {

    public static void reBuildIndex() throws InterruptedException {
        if(LOG.isTraceEnabled()){
            LOG.trace("start to rebuild index");
        }
        
        @Cleanup AbstractApplicationContext context = new ClassPathXmlApplicationContext("spring-resources.xml");
        
        SessionFactory sessionFactory = (SessionFactory) context.getBean("sessionFactory");
        
        Session sess = sessionFactory.openSession();
        @Cleanup FullTextSession fullTextSession = Search.getFullTextSession(sess);
        fullTextSession.createIndexer().startAndWait();
        if(LOG.isTraceEnabled()){
            LOG.trace("end rebuild index");
        }
    }
}
