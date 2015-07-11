/**
 * 
 */
package com.lytz.finance.common;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author cloudlu
 *
 */
public class Pager{
    
    private static final Logger LOG = LoggerFactory.getLogger(Pager.class);
    private boolean configured = false;
    private int totalRows;
    private int pageSize = 10;
    private int showPages = 5;
    private int showPageOffset = 2;
    private int currentPage;
    private int totalPages;

    public Pager(){
        
    }
    
    public Pager(int totalRows) {
        fillPager(totalRows, pageSize);
    }
    
    public Pager(int totalRows, int pageSize) {
        fillPager(totalRows, pageSize);
    }

    public void fillPager(int totalRows, int pageSize) {
        this.totalRows = totalRows;
        this.pageSize = pageSize;
        totalPages = ((totalRows % pageSize == 0) ? (totalRows / pageSize) : (totalRows
                / pageSize + 1));
        currentPage = 1;
        this.configured = true;
    }

    public boolean isConfigured(){
        return this.configured;
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

    
    public boolean isPreviousMoreThanOffset(){
        return ((currentPage - showPageOffset) > 1) ? true : false;
    }
    
    public boolean isNextMoreThanOffset(){
        return ((currentPage + showPageOffset) < totalPages) ? true : false;
    }
    
    public void setShowPageOffset(int newShowPageOffset){
        this.showPageOffset = newShowPageOffset;
        this.showPages = newShowPageOffset * 2 + 1;
    }
    
    public int getShowPageOffset(){
        return this.showPageOffset;
    }
    
    public int getShowPages(){
        return this.showPages;
    }
    
    public List<Integer> getDisplayPages(){
        List<Integer> list = new ArrayList<Integer>();
        int start = 1;
        int end = totalPages;
        if(totalPages > showPages){
            if(currentPage > showPageOffset){
                start = currentPage - showPageOffset;
                end = ((currentPage + showPageOffset) > totalPages) ? totalPages : (currentPage + showPageOffset);
            } else {
                end = this.showPages;
            }
        }
        for(int i = start; i <= end; i++){
            list.add(i);
        }
        return list;
    }
}
