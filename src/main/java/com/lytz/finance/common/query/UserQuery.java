/**
 * 
 */
package com.lytz.finance.common.query;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author cloud
 *
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class UserQuery extends Query {

    private String username;

    private String rolename;
    
    public UserQuery(UserQuery query){
        super(query);
        this.username = query.username;
        this.rolename = query.rolename;
    }
}
