/**
 * 
 */
package com.lytz.finance.service.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author cloudlu
 *
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ServiceException extends Exception {

    @Getter
    public enum ServiceError {
        UserExists(1), UserNotExists(2), IlleglaWord(3),RoleNotExists(4),DataMissing(5);
        
        private long value;
        
        private ServiceError(long value){
            this.value = value;
        }
    }
    
    /**
     * 
     */
    private static final long serialVersionUID = -7225663500230827779L;
    
    private long errorCode;  //error code
    private String errorData; //error detail

    public ServiceException(final String message, final Throwable cause) {
        super(message, cause);
    }
    
    public ServiceException(long errorCode, final String errorData, final String message, final Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
        this.errorData = errorData;
    }
}
