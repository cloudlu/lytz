/**
 * 
 */
package com.lytz.finance.common.query;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author cloud
 *
 */
@Getter
@Setter
@ToString
@NoArgsConstructor 
@AllArgsConstructor
public class Query {
    private Integer startRow;

    private String sortBy;

    private String sortType;

    private Integer querySize;
    
    public Query (@NonNull Query query) {
        this.startRow = query.startRow;
        this.sortBy = query.sortBy;
        this.sortType = query.sortType;
        this.querySize = query.querySize;
    }
    
}
