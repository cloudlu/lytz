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

import com.lytz.finance.common.query.ShowQuery;
import com.lytz.finance.dao.DAOUtils;
import com.lytz.finance.dao.ShowDAO;
import com.lytz.finance.vo.Status;

@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration(
        locations = {"classpath:spring-*-dev.xml"})
@Transactional  
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true) 
public class ShowDAOTest {
	
	private static Logger logger = LoggerFactory.getLogger(ShowDAOTest.class);
	
	@Autowired
	private ShowDAO showDAO;
	
	
	private ShowQuery con;
	 
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
    	 
    	 long count = showDAO.getTotalCount();
    	 
    	 assertTrue(count == 6);
    	 
    	 logger.info("===========================" + count + "===========================");
    	 
    	 con = new ShowQuery();
    	 con.setStartRow(0);
    	 con.setQuerySize(10);
    	 con.setKeyword("测试");
    	 count = showDAO.getTotalCount(con);
    	 assertTrue(count == 5);
    	 logger.info("=================" + count + "===========================");
    	 
    	 con.setKeyword("测试");
    	 con.setStatus(Status.COMPLETED);
         count = showDAO.getTotalCount(con);
         assertTrue(count == 2);
         logger.info("=================" + count + "===========================");
    	 
         con.setKeyword("测试");
         con.setStartRow(0);
         con.setQuerySize(2);
         con.setStatus(Status.DRAFT);
         count = showDAO.getTotalCount(con);
         assertTrue(count == 3);
         assertTrue(showDAO.findByQuery(con).size() == 2);
         con.setStartRow(2);
         assertTrue(showDAO.findByQuery(con).size() == 1);
         logger.info("=================" + count + "===========================");
         
    	 con.setKeyword("电商");
    	 con.setStatus(null);
         count = showDAO.getTotalCount(con);
         assertTrue(count == 1);
         logger.info("=================" + count + "===========================");
    	 }
     
 
}
