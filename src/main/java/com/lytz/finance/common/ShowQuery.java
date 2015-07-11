/**
 * 
 */
package com.lytz.finance.common;

import com.google.common.base.MoreObjects;
import com.lytz.finance.vo.Status;

/**
 * @author cloudlu
 *
 */
public class ShowQuery extends Query {
    
    private String title;
    private String keyword;
    private Status status;

    public ShowQuery(){
        
    }
    
    public ShowQuery(ShowQuery query){
        super(query);
        this.title = query.title;
        this.status = query.status;
        this.keyword = query.keyword;
    }
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    
    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    /**
     * @return the status
     */
    public Status getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this.getClass())
                .add("startRow", getStartRow()).add("querySize", getQuerySize())
                .add("sortBy", getSortBy()).add("sortType", getSortType())
                .add("title", title).add("status", status).add("keyword", keyword)
                .toString();
    }
    
}
