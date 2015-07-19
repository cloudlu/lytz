package com.lytz.finance.dao.impl;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.lytz.finance.common.TopicQuery;
import com.lytz.finance.dao.DAOUtils;
import com.lytz.finance.dao.TopicDAO;
import com.lytz.finance.vo.Status;
import com.lytz.finance.vo.TopicStatus;

@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration(
        locations = {"classpath:spring-*.xml"})
@Transactional  
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true) 
public class TopicDAOTest {
	
	private static Logger logger = LoggerFactory.getLogger(TopicDAOTest.class);
	
	@Autowired
	private TopicDAO topicDAO;
	
	
	private TopicQuery con;
	 
	/**
	 * execute before each test, for once execution, use BeforeClass static
	 */
	@Before
	public void setUp() {
		DAOUtils.reBuildIndex();
	}
	
	@After
	public void tearDown(){
		
	}
     
	 /**
	  * Test search 
	  */
     //@Test
     public void testDAOGetByTitle(){
         
     }
     
     //@Test 
     public void testDAOFindUser(){
     
     }    
     
     @Test
     public void testDAOGetTotal(){
    	 
    	 long count = topicDAO.getTotalCount();
    	 
    	 assertTrue(count == 3);
    	 
    	 logger.info("===========================" + count + "===========================");
    	 
    	 con = new TopicQuery();
    	 con.setStartRow(0);
    	 con.setQuerySize(10);
    	 con.setStatus(TopicStatus.SUBMITTED);
         count = topicDAO.getTotalCount(con);
         assertTrue(count == 3);
         logger.info("=================" + count + "===========================");
         
         con.setStatus(TopicStatus.ACCPECT);
         count = topicDAO.getTotalCount(con);
         assertTrue(count == 0);
         logger.info("=================" + count + "===========================");
         
    	 con.setStatus(null);
    	 con.setUsername("admin");
    	 count = topicDAO.getTotalCount(con);
    	 assertTrue(count == 1);
    	 logger.info("=================" + count + "===========================");
    	 
    	 con.setStatus(TopicStatus.SUBMITTED);
    	 count = topicDAO.getTotalCount(con);
         assertTrue(count == 1);
         logger.info("=================" + count + "===========================");
    	 
         con.setStatus(TopicStatus.DRAFT);
         count = topicDAO.getTotalCount(con);
         assertTrue(count == 0);
         logger.info("=================" + count + "===========================");
         
         
    	 con.setKeyword("测试");
    	 con.setStatus(TopicStatus.SUBMITTED);
         count = topicDAO.getTotalCount(con);
         assertTrue(count == 0);
         logger.info("=================" + count + "===========================");
    	 
         con.setStatus(null);
         count = topicDAO.getTotalCount(con);
         assertTrue(count == 0);
         logger.info("=================" + count + "===========================");
         
         
         //con.setStatus(TopicStatus.DRAFT);
         con.setUsername("123456");
         count = topicDAO.getTotalCount(con);
         assertTrue(count == 1);
         logger.info("=================" + count + "===========================");
         
    	 con.setKeyword("000582");
    	 con.setStatus(null);
    	 con.setUsername("admin");
         count = topicDAO.getTotalCount(con);
         assertTrue(count == 1);
         logger.info("=================" + count + "===========================");
         
         con.setKeyword("000582");
         con.setStatus(TopicStatus.ACCPECT);
         con.setUsername("admin");
         count = topicDAO.getTotalCount(con);
         assertTrue(count == 0);
         logger.info("=================" + count + "===========================");
         
         con.setKeyword("000582");
         con.setStatus(TopicStatus.SUBMITTED);
         con.setUsername("admin");
         count = topicDAO.getTotalCount(con);
         assertTrue(count == 1);
         logger.info("=================" + count + "===========================");
    	 }
     
 
}
