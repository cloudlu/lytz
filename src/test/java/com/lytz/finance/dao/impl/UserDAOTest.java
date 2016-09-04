package com.lytz.finance.dao.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.Date;

import lombok.extern.log4j.Log4j2;

import org.apache.shiro.authc.credential.PasswordService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.lytz.finance.common.query.UserQuery;
import com.lytz.finance.dao.RoleDAO;
import com.lytz.finance.dao.UserDAO;
import com.lytz.finance.vo.Role;
import com.lytz.finance.vo.RoleNameEnum;
import com.lytz.finance.vo.User;

@Log4j2
@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration(
        locations = {"classpath:spring-*-dev.xml"})
@Transactional(transactionManager = "transactionManager")
@Rollback 
public class UserDAOTest {
	
	@Autowired
	private UserDAO userDAO;

	@Autowired
    private RoleDAO roleDAO;
	
	
	@Autowired
	private PasswordService passwordService;
	
	private UserQuery con;
	 
	/**
	 * execute before each test, for once execution, use BeforeClass static
	 */
	@Before
	public void setUp() {
		
	}
	
	@After
	public void tearDown(){
		
	}
	 
	 private void buildUser(User userInfo, String username, String realname, Date registerTime, Date expiredTime, String email){
		 userInfo.setUsername(username);
    	 userInfo.setRealname(realname);
    	 userInfo.setPassword("password");
    	 userInfo.setAccountExpired(false);
    	 userInfo.setAccountLocked(false);
    	 userInfo.setCredentialsExpired(false);
    	 userInfo.setEnabled(true);
    	 userInfo.setEmail(email);
    	 userInfo.setExpiredTime(expiredTime);
    	 userInfo.setPasswordHint("passwordHint");
    	 userInfo.setPhoneNumber("7572875728");
    	 userInfo.setCreatedTime(registerTime);
    	 userInfo.setPassword(passwordService.encryptPassword(userInfo.getPassword()));
	 }
	 
	 /* test save */
	 /**
	  * test add a new user into database
	  */
     @Test
     public void testDAOAddUser(){
    	 Calendar cl = Calendar.getInstance();
    	 Date registerTime = cl.getTime();
    	 cl.add(Calendar.MONTH, 6);
    	 Date expiredTime = cl.getTime();
    	 User user = addUser(registerTime, expiredTime);
    	 assertFalse(registerTime.equals(user.getCreatedTime()));
    	 assertEquals(expiredTime, user.getExpiredTime());
    	 assertEquals("ROLE_ADMIN", ((Role)(user.getRoles().toArray()[0])).getName());
    	 /* for oenjpa, version starts from 1 */
    	 /* for hibernate, version starts from 0 */
    	 assertTrue(0 == user.getVersion());
    	 //userInfoDAO.remove(user.getId());  
    	 
    	 
     }

     
     private User addUser(Date registerTime, Date expiredTime){
    	 String username = "addTest";
    	 String userId = username;
    	 User userInfo = new User();
    	  
    	 buildUser(userInfo, username, userId, registerTime, expiredTime, (username + "@gmail.com"));
    	 userInfo.addRole(roleDAO.findById(1));
    	 assertNull(userInfo.getId());
    	 LOG.info("add a new user to database--------------");
    	 long startTime = System.nanoTime();
    	 userInfo = userDAO.save(userInfo);
    	 long endTime = System.nanoTime();
    	 LOG.info("save a new user spent time: " + (endTime - startTime));
    	 return userInfo;
     }
     
     /* test save 
	 *//**
	  * test modify a existing user in database
	  *//*
     //@Test (expected = org.apache.openjpa.persistence.InvalidStateException.class)
     //@Test (expected = org.springframework.dao.InvalidDataAccessApiUsageException.class)
     @Test
     public void testDAOEditUserVersion(){
    	 logger.info("modify a existing user's version in database--------------");
    	 int id = 1;   	 
    	 UserInfo userInfo = userInfoDAO.findById(id);
    	 assertNotNull(userInfo);
    	 userInfo.setVersion(100);
    	 userInfo = userInfoDAO.save(userInfo);
    	  hibernate implementation is difference compare with openjpa 
    	  * it doesn't check the modification to version field, and update
    	  * the database with version++ based on the current version value
    	  * in database 
    	 System.out.println(userInfo.getVersion());
    	 userInfo = userInfoDAO.findById(id);
    	 assertTrue(100 == userInfo.getVersion());
     }
     
     @Test
	public void testDAOEditUserRole() {
		logger.info("modify a existing user's role in database--------------");
		int id = 1;
		UserInfo userInfo = userInfoDAO.findById(id);
		assertNotNull(userInfo);
		assertEquals(1, userInfo.getRoles().size());
		assertEquals(roleDAO.findById(1).getName(), ((Role) (userInfo
				.getRoles().toArray()[0])).getName());
		userInfo.addRole(roleDAO.findById(3));
		userInfo = userInfoDAO.save(userInfo);
		assertEquals(2, userInfo.getRoles().size());
		 check the roles 
		assertTrue((roleDAO.findById(3).getName()
				.equals(((Role) (userInfo.getRoles().toArray()[1])).getName()) && roleDAO
				.findById(1).getName()
				.equals(((Role) (userInfo.getRoles().toArray()[0])).getName()))
				|| (roleDAO
						.findById(3)
						.getName()
						.equals(((Role) (userInfo.getRoles().toArray()[0]))
								.getName()) && roleDAO
						.findById(1)
						.getName()
						.equals(((Role) (userInfo.getRoles().toArray()[1]))
								.getName())));
		userInfo.removeRole(roleDAO.findById(1));
		userInfo = userInfoDAO.save(userInfo);
		assertEquals(1, userInfo.getRoles().size());
		assertEquals(roleDAO.findById(3).getName(), ((Role) (userInfo
				.getRoles().toArray()[0])).getName());

	}
     
      test save 
	 *//**
	  * test modify a existing user in database
	  * version is not sync between openjpa and database, see
	  * @see BaseDAO.merge
	  *//*
     @Ignore 
     //@Test
     public void testDAOEditUser(){
    	 logger.info("modify a existing user in database--------------");
    	 int id = 1;   	 
    	 UserInfo userInfo = userInfoDAO.findById(id);
    	 assertNotNull(userInfo);
    	 assertTrue(1 == userInfo.getVersion());
    	 userInfo.setPasswordHint("for edit");
    	 userInfo = userInfoDAO.save(userInfo);
    	 assertEquals("for edit", userInfo.getPasswordHint());
    	// assertEquals(2, userInfo.getVersion().intValue());
    	 userInfo.setPasswordHint("for edit 2");
    	 userInfoDAO.save(userInfo);
    	 System.out.println(userInfo.getVersion());
    	 //assertTrue(2 == userInfo.getVersion());
    	 //userInfoDAO.remove(user.getId());    	 
     }
     
     *//**
      * test add a exists username user to database
     * @throws InterruptedException 
      *//*
     //@Test (expected = org.apache.openjpa.persistence.EntityExistsException.class)
     //@Test (expected = javax.persistence.PersistenceException.class)
     @Test (expected = org.springframework.dao.DataIntegrityViolationException.class)
     public void testDAOAddExistsUsernameUser() throws InterruptedException{
    	 Date registerTime = null;
    	 Date expiredTime = null; 
    	 UserInfo user = new UserInfo();
    	 Calendar clr = Calendar.getInstance();
    	 registerTime = clr.getTime();
    	 clr.add(Calendar.MONTH, 6);
    	 expiredTime = clr.getTime(); 
    	 buildUser(user, specialUsername, specialRealname, registerTime, expiredTime, "abdc@gmail.com", "ALL");
    	 assertNull(user.getId());
    	 logger.info("test add the same username user to database-------------");
    	 userInfoDAO.save(user);
    	 assertNull(user.getId());
    	  due to defaul rollback is true, need to do some more operation to trigger actual
    	  * save operation and exception in openjpa
    	  
    	// Thread.sleep(1000);
    	 userInfoDAO.findAll();
     }	 
    
     *//**
      * test add a exists email user to database
      *//*
     //@Test (expected = org.apache.openjpa.persistence.EntityExistsException.class)
     //@Test (expected = javax.persistence.PersistenceException.class)
     @Test (expected = org.springframework.dao.DataIntegrityViolationException.class)
     public void testDAOAddExistsEmailUser(){
    	 Date registerTime = null;
    	 Date expiredTime = null; 
    	 UserInfo user = new UserInfo();
    	 Calendar clr = Calendar.getInstance();
    	 registerTime = clr.getTime();
    	 clr.add(Calendar.MONTH, 6);
    	 expiredTime = clr.getTime(); 
    	 buildUser(user, "emailConflict", "emailConflict", registerTime, expiredTime, "luliang1984@gmail.com", "ALL");
    	 assertNull(user.getId());
    	 logger.info("test add the same email user to database-------------");
    	 userInfoDAO.save(user);
    	 assertNull(user.getId());
    	  due to defaul rollback is true, need to do some more operation to trigger actual
    	  * save operation and exception in openjpa
    	  
    	 userInfoDAO.findAll();
     }
     
     *//**
      * test add a miss data user into database, use spring,
      *//*
     // for openjpa
     //@Test(expected=org.apache.openjpa.persistence.InvalidStateException.class)
     //@Test (expected = org.springframework.dao.InvalidDataAccessApiUsageException.class)
     // for hibernate
     //@Test (expected = javax.persistence.PersistenceException.class)
     @Test (expected = org.springframework.dao.DataIntegrityViolationException.class)
     public void testDAOAddMissingInfoUser(){
    	 Date registerTime = null;
    	 Date expiredTime = null; 
    	 UserInfo user = new UserInfo();
    	 Calendar clr = Calendar.getInstance();
    	 registerTime = clr.getTime();
    	 clr.add(Calendar.MONTH, 6);
    	 expiredTime = clr.getTime(); 
    	 buildUser(user, "username", "realname", registerTime, expiredTime, "email","ALL");
    	 user.setPhoneNumber(null);
    	 logger.info("test add miss data user to database-------------");
    	 userInfoDAO.save(user);
    	 userInfoDAO.findAll();
     }*/
     
	 /**
	  * Test search 
	  */
     @Test
     public void testDAOGetUserByName(){
         String specialUsername = "admin";
    	 // Test get all userInfos with username: admin
    	 LOG.info("test search user by name: " + specialUsername + "-------------");
    	 User userInfo = userDAO.getUserByName(specialUsername);
    	 assertNotNull(userInfo);
    	 assertEquals(specialUsername, userInfo.getUsername());
    	 
    	 // Test get all userInfos with usrname not exist, like: ""
    	 LOG.info("test search user by no exists name, like: ''-------------");
    	 userInfo = userDAO.getUserByName("");
    	 assertNull(userInfo);
    	 
    	 //Test get all userInfos with username null
    	 userInfo = userDAO.getUserByName(null);
    	 LOG.info("test search user by null name-------------");
    	 assertNull(userInfo);
     }
     
     @Test 
     public void testDAOFindUser(){
    	 int id = 1;   	 
    	 User userInfo = userDAO.findById(id);
    	 assertNotNull(userInfo);
    	 assertEquals(2, userInfo.getRoles().size());
    	 //assertEquals(roleDAO.findById(1).getName(), ((Role)(userInfo.getRoles().toArray()[0])).getName());
     }    
     
     @Test
     public void testDAOGetTotalUser(){
    	 
    	 long userCount = userDAO.getTotalCount();
    	 
    	 assertNotNull(userCount);
    	 
    	 LOG.info("===========================" + userCount + "===========================");
    	 
    	 con = new UserQuery();
    	 con.setStartRow(0);
    	 con.setQuerySize(10);
    	 con.setRolename(RoleNameEnum.ROLE_ADMIN.name());
    	 
    	 userCount = userDAO.getTotalCount(con);
    	 
    	 LOG.info("=========query admin========" + userCount + "===========================");
    	 
    	 con = new UserQuery();
         con.setStartRow(0);
         con.setQuerySize(10);
         con.setRolename(RoleNameEnum.ROLE_VIP.name());
         
         userCount = userDAO.getTotalCount(con);
         
         LOG.info("=========query ROLE_VIP========" + userCount + "===========================");
         
         con = new UserQuery();
         con.setStartRow(0);
         con.setQuerySize(10);
         con.setRolename(RoleNameEnum.ROLE_USER.name());
         
         userCount = userDAO.getTotalCount(con);
         
         LOG.info("=========query ROLE_USER========" + userCount + "===========================");
     }
     
 
}
