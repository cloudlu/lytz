/**
 * 
 */
package com.lytz.finance.common;

import com.google.common.base.MoreObjects;

/**
 * @author cloudlu
 *
 */
public class ShowQuery extends Query {
    
    private String title;

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

    
    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this.getClass())
                .add("startRow", getStartRow()).add("querySize", getQuerySize())
                .add("sortBy", getSortBy()).add("sortType", getSortType())
                .add("title", title)
                .toString();
    }
    
}
