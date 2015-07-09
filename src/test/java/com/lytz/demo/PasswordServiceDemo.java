/**
 * 
 */
package com.lytz.demo;

import java.io.IOException;

import org.apache.shiro.authc.credential.PasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * @author cloudlu
 *
 */

public class PasswordServiceDemo {

    private static PasswordService passwordService;
    
    public static void main(String[] args) throws IOException {
    
        passwordService = new org.apache.shiro.authc.credential.DefaultPasswordService();
        
        String submitted = passwordService.encryptPassword("admin");
        
        String saved = passwordService.encryptPassword("admin");
        
        System.out.println(saved);
        System.out.println(submitted);
        saved = "$shiro1$SHA-256$500000$fhIqcpBJYp73cqA+IuEkZA==$f9+Zuno7YpSyrvFD7OVmwRw1y6tD7bOdUcQazW7/SF4=";
        System.out.println(passwordService.passwordsMatch("admin", saved));
        System.out.println(passwordService.passwordsMatch("admin", submitted));
        
        System.out.println(submitted.equals(saved));
        
    }
    
    
}
