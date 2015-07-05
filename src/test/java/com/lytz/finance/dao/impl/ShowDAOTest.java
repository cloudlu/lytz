package com.lytz.finance.dao.impl;

import static org.junit.Assert.assertNotNull;

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

import com.lytz.finance.common.ShowQuery;
import com.lytz.finance.dao.ShowDAO;

@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration(
        locations = {"classpath:spring-*.xml"})
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
     public void testDAOGetTotalUser(){
    	 
    	 long count = showDAO.getTotalCount();
    	 
    	 assertNotNull(count);
    	 
    	 logger.info("===========================" + count + "===========================");
    	 
    	 con = new ShowQuery();
    	 con.setStartRow(0);
    	 con.setQuerySize(10);
    	 
    	 count = showDAO.getTotalCount(con);
    	 
    	 logger.info("=========query admin========" + count + "===========================");
    	 }
     
 
}
