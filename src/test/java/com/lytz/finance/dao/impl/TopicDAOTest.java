package com.lytz.finance.dao.impl;

import static org.junit.Assert.assertTrue;
import lombok.extern.log4j.Log4j2;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.lytz.finance.common.query.TopicQuery;
import com.lytz.finance.dao.DAOUtils;
import com.lytz.finance.dao.TopicDAO;
import com.lytz.finance.vo.TopicStatus;

@Log4j2
@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration(
        locations = {"classpath:spring-*-dev.xml"})
@Transactional  
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true) 
public class TopicDAOTest {

	@Autowired
	private TopicDAO topicDAO;
	
	
	private TopicQuery con;
	 
	/**
	 * execute before each test, for once execution, use BeforeClass static
	 */
	@Before
	public void setUp() throws InterruptedException {
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
    	 
    	 LOG.info("===========================" + count + "===========================");
    	 
    	 con = new TopicQuery();
         con.setStartRow(0);
         con.setQuerySize(10);
        // con.setStatus(TopicStatus.SUBMITTED);
         con.setExcludeStatus(TopicStatus.CANCELLED);
         con.setUsername("123456");
         count = topicDAO.getTotalCount(con);
         assertTrue(count == 1);
         LOG.info("=================" + count + "===========================");
    	 
    	 con = new TopicQuery();
    	 con.setStartRow(0);
    	 con.setQuerySize(10);
    	 con.setStatus(TopicStatus.SUBMITTED);
         count = topicDAO.getTotalCount(con);
         assertTrue(count == 2);
         LOG.info("=================" + count + "===========================");
         
         con.setStatus(TopicStatus.ACCPECT);
         count = topicDAO.getTotalCount(con);
         assertTrue(count == 0);
         LOG.info("=================" + count + "===========================");
         
    	 con.setStatus(null);
    	 con.setUsername("admin");
    	 count = topicDAO.getTotalCount(con);
    	 assertTrue(count == 1);
    	 LOG.info("=================" + count + "===========================");
    	 
    	 con.setStatus(TopicStatus.SUBMITTED);
    	 count = topicDAO.getTotalCount(con);
         assertTrue(count == 1);
         LOG.info("=================" + count + "===========================");
    	 
         con.setStatus(TopicStatus.DRAFT);
         count = topicDAO.getTotalCount(con);
         assertTrue(count == 0);
         LOG.info("=================" + count + "===========================");
         
         
    	 con.setKeyword("测试");
    	 con.setStatus(TopicStatus.SUBMITTED);
         count = topicDAO.getTotalCount(con);
         assertTrue(count == 0);
         LOG.info("=================" + count + "===========================");
    	 
         con.setStatus(null);
         count = topicDAO.getTotalCount(con);
         assertTrue(count == 0);
         LOG.info("=================" + count + "===========================");
         
         
         //con.setStatus(TopicStatus.DRAFT);
         con.setUsername("123456");
         count = topicDAO.getTotalCount(con);
         assertTrue(count == 1);
         LOG.info("=================" + count + "===========================");
         
    	 con.setKeyword("000582");
    	 con.setStatus(null);
    	 con.setUsername("admin");
         count = topicDAO.getTotalCount(con);
         assertTrue(count == 1);
         LOG.info("=================" + count + "===========================");
         
         con.setKeyword("000582");
         con.setStatus(TopicStatus.ACCPECT);
         con.setUsername("admin");
         count = topicDAO.getTotalCount(con);
         assertTrue(count == 0);
         LOG.info("=================" + count + "===========================");
         
         con.setKeyword("000582");
         con.setStatus(TopicStatus.SUBMITTED);
         con.setUsername("admin");
         count = topicDAO.getTotalCount(con);
         assertTrue(count == 1);
         LOG.info("=================" + count + "===========================");
         
         con = new TopicQuery();
         con.setStartRow(0);
         con.setQuerySize(10);
         //con.setStatus(TopicStatus.SUBMITTED);
         con.setExcludeStatus(TopicStatus.CANCELLED);
         
         count = topicDAO.getTotalCount(con);
         //assertTrue(count == 3);
         LOG.info("=================" + count + "===========================");
         
         con = new TopicQuery();
         con.setStartRow(0);
         con.setQuerySize(10);
         //con.setStatus(TopicStatus.SUBMITTED);
         con.setUsername("123456");
         con.setExcludeStatus(TopicStatus.CANCELLED);
         con.setKeyword("上传");
         count = topicDAO.getTotalCount(con);
         LOG.info(topicDAO.findByQuery(con).toString());
         //assertTrue(count == 3);
         LOG.info("=================" + count + "===========================");
    	 }
     
 
}
