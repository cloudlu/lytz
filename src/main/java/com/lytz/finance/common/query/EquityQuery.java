/**
 * 
 */
package com.lytz.finance.common.query;

import lombok.Getter;
import lombok.NoArgsConstructor;
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
@NoArgsConstructor
public class EquityQuery extends Query {

    private String title;
    private String keyword;
    private Status status;
    
    public EquityQuery(EquityQuery query){
        super(query);
        this.title = query.title;
        this.status = query.status;
        this.keyword = query.keyword;
    }
}
