/**
 * 
 */
package com.lytz.finance.common.query;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import com.google.common.base.MoreObjects;
import com.lytz.finance.vo.Status;

/**
 * @author cloudlu
 *
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class ShowQuery extends Query {
    
    private String title;
    private String keyword;
    private Status status;

    public ShowQuery(ShowQuery query){
        super(query);
        this.title = query.title;
        this.status = query.status;
        this.keyword = query.keyword;
    }
    
}
