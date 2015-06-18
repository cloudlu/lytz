/**
 * 
 */
package com.lytz.finance.common;

import com.google.common.base.MoreObjects;

/**
 * @author cloud
 *
 */
public class UserQuery extends Query {

    private String username;

    private String rolename;
    
    public UserQuery(){
        
    }
    
    public UserQuery(UserQuery query){
        super(query);
        this.username = query.username;
        this.rolename = query.rolename;
    }
    
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
    
    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this.getClass())
                .add("startRow", getStartRow()).add("querySize", getQuerySize())
                .add("sortBy", getSortBy()).add("sortType", getSortType())
                .add("username", username).add("rolename", rolename)
                .toString();
    }
}
