/**
 * 
 */
package com.lytz.finance.common.query;

import lombok.AllArgsConstructor;
import lombok.Getter;
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
@RequiredArgsConstructor  
@AllArgsConstructor
public class Query {
    private Integer startRow;

    private String sortBy;

    private String sortType;

    private Integer querySize;
    
}
