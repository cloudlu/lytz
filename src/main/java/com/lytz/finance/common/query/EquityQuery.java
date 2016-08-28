/**
 * 
 */
package com.lytz.finance.common.query;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import com.lytz.finance.vo.Status;

/**
 * @author cloudlu
 *
 */
@Getter
@Setter
@ToString
@RequiredArgsConstructor  
@AllArgsConstructor
public class EquityQuery extends Query {

    private String title;
    private String keyword;
    private Status status;
    
}
