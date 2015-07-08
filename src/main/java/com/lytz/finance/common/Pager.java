/**
 * 
 */
package com.lytz.finance.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author cloudlu
 *
 */
public class Pager{
    
    private static final Logger LOG = LoggerFactory.getLogger(Pager.class);
    
    private int totalRows;
    private int pageSize = 10;
    private int currentPage;
    private int totalPages;

    public Pager(int totalRows) {
        fillPager(totalRows, pageSize);
    }
    
    public Pager(int totalRows, int pageSize) {
        fillPager(totalRows, pageSize);
    }

    private void fillPager(int totalRows, int pageSize) {
        this.totalRows = totalRows;
        this.pageSize = pageSize;
        totalPages = ((totalRows % pageSize == 0) ? (totalRows / pageSize) : (totalRows
                / pageSize + 1));
        currentPage = 1;
    }

/*    public int getEndRow() {
        return endRow;
    }*/

    public boolean isPreviousExists() {
        if(0 == totalPages){
            return false;
        }
        return currentPage == 1 ? false : true;
    }

    public boolean isNextExists() {
        if(0 == totalPages){
            return false;
        }
        return currentPage == totalPages ? false : true;
    }
    
    public int getStartRow() {
        return (currentPage - 1) * pageSize;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setTotalRows(int totalRows) {
        if(this.totalRows != totalRows){
            this.totalRows = totalRows;
            fillPager(totalRows, this.pageSize);
        }
    }

    public void setCurrentPage(int currentPage) {
        if(currentPage < 1){
            if(LOG.isWarnEnabled()){
                LOG.warn("pageNum is invalid, input pageNum is: " + currentPage);
            }
            this.currentPage = 1;
        }
        if(currentPage > getTotalPages()){
            if(LOG.isWarnEnabled()){
                LOG.warn("pageNum is invalid, input pageNum is: " + currentPage);
            }
            this.currentPage = getTotalPages();
        }
        this.currentPage = currentPage;
    }

    public void setPageSize(int pageSize) {
        if(this.pageSize != pageSize){
            fillPager(totalRows, pageSize);
        }
    }

    public int getTotalRows() {
        return totalRows;
    }

    public int getFirstPage() {
        return 1;
    }

    public int getPreviousPage() {
        if (currentPage == 1 || currentPage == 0) {
            return 1;
        }
        return currentPage - 1;
    }

    public int getNextPage() {
        if (currentPage < totalPages) {
            return currentPage + 1;
        }
        return totalPages;
    }

    public int getLastPage() {
        return totalPages;
    }

}
