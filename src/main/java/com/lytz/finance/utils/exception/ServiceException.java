/**
 * 
 */
package com.lytz.finance.utils.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author cloud
 *
 */
@Getter
@Setter
@NoArgsConstructor
public class ServiceException extends RuntimeException {

    /**
     * 
     */
    private static final long serialVersionUID = 4907235303578135228L;

    private long errorCode;
    private String errorData;
    
    public ServiceException(final String message, final Throwable cause){
        super(message, cause);
    }
}
