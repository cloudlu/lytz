/**
 * 
 */
package com.lytz.finance.common;

/**
 * @author cloud
 *
 */
public class UserQuery extends Query {

    private String username;

    private String rolename;
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the rolename
     */
    public String getRolename() {
        return rolename;
    }

    /**
     * @param rolename the rolename to set
     */
    public void setRolename(String rolename) {
        this.rolename = rolename;
    }
}
