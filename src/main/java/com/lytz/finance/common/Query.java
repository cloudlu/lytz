/**
 * 
 */
package com.lytz.finance.common;

/**
 * @author cloud
 *
 */
public class Query {
    private int startRow;
    
    private String sortBy;
    
    private String sortType;
    
    private int querySize;
    
    public int getStartRow() {
        return startRow;
    }

    public void setStartRow(int startRow) {
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
    public int getQuerySize() {
        return querySize;
    }

    /**
     * @param querySize the querySize to set
     */
    public void setQuerySize(int querySize) {
        this.querySize = querySize;
    }
}
