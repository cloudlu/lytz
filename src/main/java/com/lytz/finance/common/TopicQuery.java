/**
 * 
 */
package com.lytz.finance.common;

import com.google.common.base.MoreObjects;

/**
 * @author cloudlu
 *
 */
public class TopicQuery extends Query {

    private String username;
    
    private String title;
    
    private String status;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this.getClass())
                .add("startRow", getStartRow()).add("querySize", getQuerySize())
                .add("sortBy", getSortBy()).add("sortType", getSortType())
                .add("username", username).add("title", title)
                .add("status", status)
                .toString();
    }
    
}
