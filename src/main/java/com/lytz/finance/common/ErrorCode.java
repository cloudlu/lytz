/**
 * 
 */
package com.lytz.finance.common;

import lombok.Getter;

/**
 * @author cloudlu
 *
 */
@Getter
public enum ErrorCode {

    UserExists(1), UserNotExists(2), RoleNotExists(3);
    
    private long code;
    
    private ErrorCode(long code){
        this.code = code;
    }
    
}
