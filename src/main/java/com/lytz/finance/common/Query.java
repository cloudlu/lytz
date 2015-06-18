/**
 * 
 */
package com.lytz.finance.common;

import com.google.common.base.MoreObjects;

/**
 * @author cloud
 *
 */
public class Query {
    private Integer startRow;

    private String sortBy;

    private String sortType;

    private Integer querySize;
    
    public Query(){
        
    }
    
    public Query(Query query){
        this.startRow = query.startRow;
        this.sortBy = query.sortBy;
        this.sortType = query.sortType;
        this.querySize= query.querySize;
    }

    public Integer getStartRow() {
        return startRow;
    }

    public void setStartRow(Integer startRow) {
        this.startRow = startRow;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public String getSortType() {
        return sortType;
    }

    public void setSortType(String sortType) {
        this.sortType = sortType;
    }

    /**
     * @return the querySize
     */
    public Integer getQuerySize() {
        return querySize;
    }

    /**
     * @param querySize
     *            the querySize to set
     */
    public void setQuerySize(Integer querySize) {
        this.querySize = querySize;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this.getClass())
                .add("startRow", startRow).add("querySize", querySize)
                .add("sortBy", sortBy).add("sortType", sortType)
                .toString();
    }
}
