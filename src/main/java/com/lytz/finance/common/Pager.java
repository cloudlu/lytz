/**
 * 
 */
package com.lytz.finance.common;

/**
 * @author cloudlu
 *
 */
public class Pager {
    
    private int totalRows;
    private int pageSize = 10;
    private int currentPage;
    private int totalPages;
    private int startRow;
    private int endRow;
    private boolean hasPrevious;
    private boolean hasNext;

    public Pager(int totalRows) {
        fillPager(totalRows);
    }

    private void fillPager(int totalRows) {
        this.totalRows = totalRows;
        totalPages = ((totalRows % pageSize == 0) ? (totalRows / pageSize) : (totalRows
                / pageSize + 1));
        currentPage = 1;
        setStartEndRow();
    }

    public int getEndRow() {
        return endRow;
    }

    public boolean hasPrevious() {
        return currentPage == 1 ? false : true;
    }

    public boolean hasNext() {
        return currentPage == totalPages ? false : true;
    }
    
    public int getStartRow() {
        return startRow;
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
            fillPager(totalRows);
        }
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public void setPageSize(int pageSize) {
        if(this.pageSize != pageSize){
            fillPager(totalRows);
        }
    }

    public int getTotalRows() {
        return totalRows;
    }

    public void first() {
        currentPage = 1;
        setStartEndRow();
    }

    public void previous() {
        if (currentPage == 1 || currentPage == 0) {
            return;
        }
        currentPage--;
        setStartEndRow();
    }

    private void setStartEndRow() {
        startRow = (currentPage - 1) * pageSize;
        endRow = startRow + pageSize;
        if(currentPage == totalPages){
            endRow = totalRows;
        }
    }

    public void next() {
        if (currentPage < totalPages) {
            currentPage++;
        }
        setStartEndRow();
    }

    public void last() {
        currentPage = totalPages;
        setStartEndRow();
    }

}
