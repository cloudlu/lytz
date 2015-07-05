/**
 * 
 */
package com.lytz.finance.common;

import com.google.common.base.MoreObjects;
import com.lytz.finance.vo.ShowStatus;

/**
 * @author cloudlu
 *
 */
public class ShowQuery extends Query {
    
    private String title;
    private ShowStatus status;

    public ShowQuery(){
        
    }
    
    public ShowQuery(ShowQuery query){
        super(query);
        this.title = query.title;
    }
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    
    /**
     * @return the status
     */
    public ShowStatus getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(ShowStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this.getClass())
                .add("startRow", getStartRow()).add("querySize", getQuerySize())
                .add("sortBy", getSortBy()).add("sortType", getSortType())
                .add("title", title).add("status", status)
                .toString();
    }
    
}
