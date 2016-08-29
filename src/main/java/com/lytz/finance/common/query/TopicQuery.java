/**
 * 
 */
package com.lytz.finance.common.query;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import com.lytz.finance.vo.TopicStatus;

/**
 * @author cloudlu
 *
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class TopicQuery extends Query {

    private String username;
    
    private String title;
    
    private TopicStatus status;

    private String keyword;
    
    private TopicStatus excludeStatus;

    public TopicQuery(TopicQuery query){
        super(query);
        this.username = query.username;
        this.title = query.title;
        this.status = query.status;
        this.keyword = query.keyword;
        this.excludeStatus = query.excludeStatus;
    }
    
}
