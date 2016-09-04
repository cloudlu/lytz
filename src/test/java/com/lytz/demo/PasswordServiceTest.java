/**
 * 
 */
package com.lytz.demo;

import static org.junit.Assert.*;

import java.io.IOException;

import lombok.extern.log4j.Log4j2;

import org.apache.shiro.authc.credential.PasswordService;
import org.junit.Test;

/**
 * @author cloudlu
 *
 */
@Log4j2
public class PasswordServiceTest {

    private PasswordService passwordService;
    
    @Test public void testEncode() throws IOException {
        passwordService = new org.apache.shiro.authc.credential.DefaultPasswordService();
        
        String submitted = passwordService.encryptPassword("admin");
        String saved = "$shiro1$SHA-256$500000$fhIqcpBJYp73cqA+IuEkZA==$f9+Zuno7YpSyrvFD7OVmwRw1y6tD7bOdUcQazW7/SF4=";
        if(LOG.isDebugEnabled()){
            LOG.debug("current encoded password for admin: {}", submitted);
            LOG.debug("previous encoded password for admin: {}", saved);
        }
        assertFalse(submitted.equals(saved));
        assertTrue(passwordService.passwordsMatch("admin", saved));
        assertTrue(passwordService.passwordsMatch("admin", submitted));
    }
    
    
}
