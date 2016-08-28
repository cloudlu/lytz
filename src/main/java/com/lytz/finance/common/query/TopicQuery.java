/**
 * 
 */
package com.lytz.finance.common.query;

import com.google.common.base.MoreObjects;
import com.lytz.finance.vo.TopicStatus;

/**
 * @author cloudlu
 *
 */
public class TopicQuery extends Query {

    private String username;
    
    private String title;
    
    private TopicStatus status;

    private String keyword;
    
    private TopicStatus excludeStatus;
    
    public TopicQuery(){
        
    }
    
    public TopicQuery(TopicQuery query){
        super(query);
        this.username = query.username;
        this.title = query.title;
        this.status = query.status;
        this.keyword = query.keyword;
        this.excludeStatus = query.excludeStatus;
    }
    
    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
    
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

    public TopicStatus getStatus() {
        return status;
    }

    public void setStatus(TopicStatus status) {
        this.status = status;
    }
    
    /**
     * @return the excludeStatus
     */
    public TopicStatus getExcludeStatus() {
        return excludeStatus;
    }

    /**
     * @param excludeStatus the excludeStatus to set
     */
    public void setExcludeStatus(TopicStatus excludeStatus) {
        this.excludeStatus = excludeStatus;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this.getClass())
                .add("startRow", getStartRow()).add("querySize", getQuerySize())
                .add("sortBy", getSortBy()).add("sortType", getSortType())
                .add("username", username).add("title", title).add("keyword", keyword)
                .add("status", status).add("excludeStatus", excludeStatus)
                .toString();
    }
    
}
