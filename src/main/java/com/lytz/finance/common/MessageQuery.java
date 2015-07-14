/**
 * 
 */
package com.lytz.finance.common;

import com.google.common.base.MoreObjects;
import com.lytz.finance.vo.MessageStatus;
import com.lytz.finance.vo.Status;

/**
 * @author cloud
 *
 */
public class MessageQuery extends Query {

    private MessageStatus status;

    public MessageQuery(){
        
    }
    
    public MessageQuery(MessageQuery query){
        super(query);
        this.status = query.status;
    }
    

    /**
     * @return the status
     */
    public MessageStatus getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(MessageStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this.getClass())
                .add("startRow", getStartRow()).add("querySize", getQuerySize())
                .add("sortBy", getSortBy()).add("sortType", getSortType())
                .add("status", status)
                .toString();
    }
}
